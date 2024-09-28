package com.mdemanuel.archetype_logistic_api.application.domain.ports.primary.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mdemanuel.archetype_logistic_api.application.domain.model.domain.audit.AuditEntryEntity;
import com.mdemanuel.archetype_logistic_api.application.domain.ports.primary.dto.request.DataTypeDto;
import com.mdemanuel.archetype_logistic_api.application.domain.service.audit.AuditEntryService;
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
public class AuditEntryFilter implements Filter {

  // Patrones de recursos a buscar
  private List<Pattern> baseResourcePatterns = Arrays.asList(
      Pattern.compile("/archetype-logistic-api/master/(dataType)/(.+)")
  );

  @Value("${excludeAuditEntry}")
  private String excludeAuditEntry;
  @Value("${server.servlet.context-path}")
  private String contexPath;

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

    // Si hacemos auditoria
    long init = Instant.now().toEpochMilli();

    ContentCachingRequestWrapper contentCachingRequestWrapper = new ContentCachingRequestWrapper(
        (HttpServletRequest) servletRequest);
    ContentCachingResponseWrapper contentCachingResponseWrapper = new ContentCachingResponseWrapper(
        (HttpServletResponse) servletResponse);

    int httpStatus = HttpStatus.OK.value();

    try {
      filterChain.doFilter(contentCachingRequestWrapper, contentCachingResponseWrapper);
      httpStatus = contentCachingResponseWrapper.getStatus();
    } catch (IOException | ServletException e) {
      httpStatus = HttpStatus.INTERNAL_SERVER_ERROR.value();
    } finally {
      long end = Instant.now().toEpochMilli();
      long elapsedTime = end - init;

      if (!excludeAuditEntry(getUrl(contentCachingRequestWrapper))) {
        saveAudit(contentCachingRequestWrapper, contentCachingResponseWrapper, httpStatus, init, elapsedTime);
      }

      // Importante: copiar el cuerpo al flujo de respuesta real.
      contentCachingResponseWrapper.copyBodyToResponse();
    }
  }

  private void saveAudit(ContentCachingRequestWrapper reqWrapper, ContentCachingResponseWrapper resWrapper,
      int httpStatus, long init, long elapsedTime) {
    try {
      String url = getUrl(reqWrapper);
      String method = reqWrapper.getMethod();
      String requestBody = new String(reqWrapper.getContentAsByteArray(), StandardCharsets.UTF_8);
      requestBody = requestBody == null || requestBody.isEmpty() ? "{}" : requestBody;
      String responseBody = new String(resWrapper.getContentAsByteArray(), StandardCharsets.UTF_8);
      responseBody = responseBody == null || responseBody.isEmpty() ? "{}" : responseBody;

      if (!excludeAuditEntry(url)) {
        AuditEntryEntity auditEntryEntity =
            AuditEntryEntity.builder().traceId(Objects.requireNonNull(tracer.currentTraceContext().context()).traceId())
                .spanId(Objects.requireNonNull(tracer.currentTraceContext().context()).spanId())
                .url(url)
                .httpMethod(reqWrapper.getMethod())
                .keyValue(getKey(url, method, requestBody))
                .parameters(getParams(reqWrapper))
                .requestBody(requestBody)
                .responseBody(responseBody)
                .httpStatus(httpStatus)
                .elapsedTime(elapsedTime)
                .createdAt(Instant.ofEpochMilli(init))
                .build();

        auditEntryService.save(auditEntryEntity);
      }

      resWrapper.copyBodyToResponse();
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
        if (body != null && !body.isEmpty()) {
          ObjectMapper objectMapper = new ObjectMapper();
          DataTypeDto dto = objectMapper.readValue(body, DataTypeDto.class);

          return dto.getDataTypeCode();
        }

        return null;
      default:
        return null;
    }
  }

  private boolean excludeAuditEntry(String url) {
    return Arrays.stream(excludeAuditEntry.split(",")).anyMatch(api -> url.startsWith(contexPath + api));
  }
}