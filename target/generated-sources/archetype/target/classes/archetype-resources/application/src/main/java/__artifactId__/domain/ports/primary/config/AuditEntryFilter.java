#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.${artifactId}.domain.ports.primary.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import ${package}.${artifactId}.domain.model.domain.mongo.audit.AuditEntryDocument;
import ${package}.${artifactId}.domain.model.domain.postgres.audit.AuditEntryEntity;
import ${package}.${artifactId}.domain.ports.primary.dto.request.CategoryDto;
import ${package}.${artifactId}.domain.ports.primary.dto.request.PurchaseOrderDto;
import ${package}.${artifactId}.domain.service.audit.AuditEntryDocumentService;
import ${package}.${artifactId}.domain.service.audit.AuditEntryService;
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
import java.util.Map;
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
      Pattern.compile("/${parentArtifactId}/master/(category)/(.+)"),
      Pattern.compile("/${parentArtifactId}/purchase_order/(.+)"),
      Pattern.compile("/${parentArtifactId}/mongo/master/(category)/(.+)"),
      Pattern.compile("/${parentArtifactId}/mongo/purchase_order/(.+)"),
      Pattern.compile("/${parentArtifactId}/mongo/master/generic/(category)/(.+)"),
      Pattern.compile("/${parentArtifactId}/mongo/master/purchase_order/(.+)")
  );

  @Value("${symbol_dollar}{excludeAuditEntry}")
  private String excludeAuditEntry;
  @Value("${symbol_dollar}{server.servlet.context-path}")
  private String contexPath;

  @Autowired
  private ObjectMapper objectMapper;
  @Autowired
  private Tracer tracer;
  @Autowired
  private AuditEntryService auditEntryService;
  @Autowired
  private AuditEntryDocumentService auditEntryDocumentService;

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
        Map<String, Object> mapRequestBody = objectMapper.readValue(requestBody, Map.class);
        Map<String, Object> mapResponseBody = objectMapper.readValue(responseBody, Map.class);

        try {
          AuditEntryEntity auditEntryEntity =
              AuditEntryEntity.builder()
                  .traceId(Objects.requireNonNull(tracer.currentTraceContext().context()).traceId())
                  .spanId(Objects.requireNonNull(tracer.currentTraceContext().context()).spanId())
                  .url(url)
                  .httpMethod(reqWrapper.getMethod())
                  .keyValue(getKey(url, method, requestBody))
                  .parameters(getParams(reqWrapper))
                  .requestBody(mapRequestBody)
                  .responseBody(mapResponseBody)
                  .httpStatus(httpStatus)
                  .elapsedTime(elapsedTime)
                  .createdAt(Instant.ofEpochMilli(init))
                  .build();

          auditEntryService.save(auditEntryEntity);
        } catch (Exception e) {
          log.warn("Error on save audit entry in Postgres", e);
        }

        try {
          AuditEntryDocument auditEntryDocument =
              AuditEntryDocument.builder()
                  .traceId(Objects.requireNonNull(tracer.currentTraceContext().context()).traceId())
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

          auditEntryDocumentService.save(auditEntryDocument);
        } catch (Exception e) {
          log.warn("Error on save audit entry in Mongo", e);
        }
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
          switch (url.substring(url.lastIndexOf("/") + 1)) {
            case "category":
              return objectMapper.readValue(body, CategoryDto.class).getData().getCode();

            case "purchase_order":
              return objectMapper.readValue(body, PurchaseOrderDto.class).getData().getCode();

            default:
              return null;
          }
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