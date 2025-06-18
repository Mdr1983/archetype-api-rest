package com.louzao.application.util.auditlogging;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.louzao.application.domain.model.domain.mssql.autoline.audit.AuditEntryEntity;
import com.louzao.application.domain.service.audit.AuditEntryService;
import io.micrometer.tracing.Tracer;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

@Slf4j
@Component
public class AuditEntryLoggingFilter implements Filter {

  // Patrones de recursos a buscar
  private List<Pattern> baseResourcePatterns = Arrays.asList(
      Pattern.compile("/autoline-back/master/(category)/(.+)"),
      Pattern.compile("/autoline-back/mongo/master/(category)/(.+)"));

  @Value("${excludeAuditEntry}")
  private String excludeAuditEntry;
  @Value("${server.servlet.context-path}")
  private String contexPath;

  @Autowired
  private ObjectMapper objectMapper;
  @Autowired
  private Tracer tracer;
  @Autowired
  private AuditEntryService auditEntryService;

  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
      throws IOException, ServletException {
    if (excludeAuditEntry(getUrl(servletRequest))) {
      filterChain.doFilter(servletRequest, servletResponse);

      return;
    }

    long init = Instant.now().toEpochMilli();

    ContentCachingRequestWrapper contentCachingRequestWrapper = new ContentCachingRequestWrapper(
        (HttpServletRequest) servletRequest);
    ContentCachingResponseWrapper contentCachingResponseWrapper = new ContentCachingResponseWrapper(
        (HttpServletResponse) servletResponse);

    byte[] requestBody = contentCachingResponseWrapper.getContentAsByteArray();
    byte[] responseBody = null;

    logRequest(contentCachingRequestWrapper, requestBody);

    int httpStatus = HttpStatus.OK.value();

    try {
      filterChain.doFilter(contentCachingRequestWrapper, contentCachingResponseWrapper);
      httpStatus = contentCachingResponseWrapper.getStatus();
      responseBody = contentCachingResponseWrapper.getContentAsByteArray();
    } catch (IOException | ServletException e) {
      httpStatus = HttpStatus.INTERNAL_SERVER_ERROR.value();
    } finally {
      long end = Instant.now().toEpochMilli();
      long elapsedTime = end - init;

      saveAudit(contentCachingRequestWrapper, requestBody, responseBody, httpStatus, init, elapsedTime);

      logResponse(contentCachingResponseWrapper, contentCachingResponseWrapper.getContentAsByteArray());

      // Importante: copiar el cuerpo al flujo de respuesta real.
      contentCachingResponseWrapper.copyBodyToResponse();
    }
  }

  private void logRequest(ContentCachingRequestWrapper contentCachingRequestWrapper, byte[] requestBody) {
    try {
      StringBuilder sb = new StringBuilder("Audit Entry Request:\n");
      sb.append("  URI: ").append(getUrl(contentCachingRequestWrapper)).append("\n");
      sb.append("  Method: ").append(contentCachingRequestWrapper.getMethod()).append("\n");
      if (requestBody.length > 0) {
        sb.append("  Body: ").append(new String(requestBody)).append("\n");
      }
      log.debug(sb.toString());
    } catch (Exception e) {
      log.warn("Error on request logging", e);
    }
  }

  private void logResponse(ContentCachingResponseWrapper contentCachingResponseWrapper, byte[] responseBody) {
    try {
      StringBuilder sb = new StringBuilder("Audit Entry Response:\n");
      sb.append("  Status code: ").append(contentCachingResponseWrapper.getStatus()).append("\n");
      if (responseBody != null && responseBody.length > 0) { // Check for null and empty body
        sb.append("  Body: ").append(new String(responseBody)).append("\n");
      } else {
        sb.append("  Body: ").append("Empty or null").append("\n");
      }
      log.debug(sb.toString());
    } catch (Exception e) {
      log.warn("Error on response logging", e);
    }
  }

  private void saveAudit(ContentCachingRequestWrapper reqWrapper, byte[] requestBody, byte[] responseBody,
      int httpStatus, long init, long elapsedTime) {
    try {
      String url = getUrl(reqWrapper);
      String method = reqWrapper.getMethod();

      String requestBodyString = new String(requestBody, StandardCharsets.UTF_8);
      requestBodyString = requestBodyString == null || requestBodyString.isEmpty() ? "{}" : requestBodyString;
      String responseBodyString = new String(responseBody, StandardCharsets.UTF_8);
      responseBodyString = responseBodyString == null || responseBodyString.isEmpty() ? "{}" : responseBodyString;

      if (!excludeAuditEntry(url)) {
        try {
          AuditEntryEntity auditEntryEntity = AuditEntryEntity.builder()
              .traceId(Objects.requireNonNull(tracer.currentTraceContext().context()).traceId())
              .spanId(Objects.requireNonNull(tracer.currentTraceContext().context()).spanId()).url(url)
              .httpMethod(reqWrapper.getMethod()).keyValue(getKey(url, method, requestBodyString))
              .parameters(getParams(reqWrapper)).requestBody(requestBody).responseBody(responseBody)
              .httpStatus(httpStatus).elapsedTime(elapsedTime).createdAt(Instant.ofEpochMilli(init)).build();

          auditEntryService.save(auditEntryEntity);
        } catch (Exception e) {
          log.warn("Error on save audit entry in Mssql", e);
        }
      }
    } catch (Exception e) {
      log.warn("Error on save audit entry", e);
    }
  }

  private String getUrl(ServletRequest servletRequest) {
    HttpServletRequest request = (HttpServletRequest) servletRequest;

    return request.getContextPath() + request.getServletPath();
  }

  private String getParams(ContentCachingRequestWrapper reqWrapper) {
    return reqWrapper.getQueryString();
  }

  @SneakyThrows
  private String getKey(String url, String method, String body) {
    String key = null;

    switch (method) {
      case "GET":
        for (Pattern pattern : baseResourcePatterns) {
          Matcher matcher = pattern.matcher(url);
          if (matcher.matches()) {
            String resourceType = matcher.group(1);
            String resourceId = matcher.group(2); // Puede ser null si no hay grupo de captura 2

            if (resourceId != null) {
              return resourceId;
            } else {
              return null;
            }
          }

          return null;
        }
      case "PUT":
      case "DELETE":
        return url.substring(url.lastIndexOf("/") + 1);
      case "POST":
        /*if (body != null && !body.isEmpty()) {
          switch (url.substring(url.lastIndexOf("/") + 1)) {
            case "category":
              return objectMapper.readValue(body, CategoryDto.class).getData().getCode();

            default:
              return null;
          }
        }*/

        return null;
      default:
        return null;
    }
  }

  private boolean excludeAuditEntry(String url) {
    return Arrays.stream(excludeAuditEntry.split(",")).anyMatch(api -> url.startsWith(contexPath + api));
  }
}
