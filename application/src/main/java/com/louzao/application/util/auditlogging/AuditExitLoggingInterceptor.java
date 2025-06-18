package com.louzao.application.util.auditlogging;

import com.louzao.application.domain.model.domain.mssql.autoline.audit.AuditExitEntity;
import com.louzao.application.domain.service.audit.AuditExitService;
import io.micrometer.tracing.Tracer;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.Instant;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

@Slf4j
@Component
public class AuditExitLoggingInterceptor implements ClientHttpRequestInterceptor {

  @Autowired
  private Tracer tracer;
  @Autowired
  private AuditExitService auditExitService;

  @Override
  public ClientHttpResponse intercept(HttpRequest request, byte[] requestBody, ClientHttpRequestExecution execution)
      throws IOException {
    long init = Instant.now().toEpochMilli();

    logRequest(request, requestBody);

    int httpStatus = HttpStatus.OK.value();
    ClientHttpResponse response = null;
    byte[] responseBody = null;

    try {
      response = execution.execute(request, requestBody);
      httpStatus = HttpStatus.valueOf(response.getStatusCode().value()).value();

      // Leer el body como bytes y guardarlo
      try (InputStream responseBodyStream = response.getBody()) {
        responseBody = StreamUtils.copyToByteArray(responseBodyStream);
      }
    } catch (Exception e) {
      httpStatus = HttpStatus.INTERNAL_SERVER_ERROR.value();
    } finally {
      long end = Instant.now().toEpochMilli();
      long elapsedTime = end - init;

      saveAudit(request, requestBody, responseBody, httpStatus, init, elapsedTime);

      // Importante: copiar el cuerpo al flujo de respuesta real.
      if (responseBody != null) {
        response = new CustomClientHttpResponse(response, responseBody);
      }

      logResponse(response, responseBody);
    }

    return response;
  }

  private void logRequest(HttpRequest request, byte[] requestBody) {
    try {
      StringBuilder sb = new StringBuilder("Audit Exit Request:\n");
      sb.append("  URI: ").append(request.getURI()).append("\n");
      sb.append("  Method: ").append(request.getMethod()).append("\n");
      if (requestBody.length > 0) {
        sb.append("  Body: ").append(new String(requestBody)).append("\n");
      }
      log.debug(sb.toString());
    } catch (Exception e) {
      log.warn("Error on request logging", e);
    }
  }

  private void logResponse(ClientHttpResponse response, byte[] responseBody) {
    try {
      StringBuilder sb = new StringBuilder("Audit Exit Response:\n");
      sb.append("  Status code: ").append(response.getStatusCode()).append("\n");
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

  private void saveAudit(HttpRequest request, byte[] requestBody, byte[] responseBody, int httpStatus, long init,
      long elapsedTime) {
    try {
      AuditExitEntity auditExitEntity =
          AuditExitEntity.builder()
              .traceId(Objects.requireNonNull(tracer.currentTraceContext().context()).traceId())
              .spanId(Objects.requireNonNull(tracer.currentTraceContext().context()).spanId())
              .url(request.getURI().toString())
              .httpMethod(request.getMethod().toString())
              .requestBody(requestBody)
              .responseBody(responseBody)
              .httpStatus(httpStatus)
              .elapsedTime(elapsedTime)
              .createdAt(Instant.ofEpochMilli(init))
              .build();

      auditExitService.save(auditExitEntity);
    } catch (Exception e) {
      log.warn("Error on save audit exit in Mssql", e);
    }
  }

  //Clase CustomClientHttpResponse para crear una nueva respuesta
  private static class CustomClientHttpResponse implements ClientHttpResponse {

    private final ClientHttpResponse originalResponse;
    private final byte[] body;

    public CustomClientHttpResponse(ClientHttpResponse originalResponse, byte[] body) {
      this.originalResponse = originalResponse;
      this.body = body;
    }

    @Override
    public HttpStatus getStatusCode()
        throws IOException {
      return HttpStatus.valueOf(originalResponse.getStatusCode().value());
    }

    @Override
    public int getRawStatusCode()
        throws IOException {
      return originalResponse.getRawStatusCode();
    }

    @Override
    public String getStatusText()
        throws IOException {
      return originalResponse.getStatusText();
    }

    @Override
    public HttpHeaders getHeaders() {
      return originalResponse.getHeaders();
    }

    @Override
    public InputStream getBody()
        throws IOException {
      return new ByteArrayInputStream(body);
    }

    @Override
    public void close() {
      originalResponse.close();
    }
  }
}
