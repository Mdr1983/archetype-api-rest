package com.louzao.application.domain.ports.primary.dto.response.controller;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiResponseDto<T> implements Serializable {

  private static final long serialVersionUID = 1236711111270169043L;

  @Schema(name = "httpStatusCode", required = true)
  private int httpStatusCode;
  @Schema(name = "httpReasonPhrase", required = true)
  private String httpReasonPhrase;
  @Schema(name = "instance", required = true)
  private String instance;
  @Schema(name = "data", required = true)
  private T data;
}
