package com.mdemanuel.application.domain.ports.primary.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Map;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode
@SuperBuilder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class GenericDto implements Serializable {

  private static final long serialVersionUID = 1268767234577476811L;

  @Schema(description = "Metadata in format json",
      required = true,
      example = "{\n"
          + "  \"executedBy\": \"testuser\"\n"
          + "}")
  @NotNull
  private Map<String, Object> metadata;
  @Schema(description = "Data in format json",
      required = true,
      example = "{\n"
          + "  \"code\": \"BOLSO\",\n"
          + "  \"description\": \"Bolso piel\"\n"
          + "}")
  @NotNull
  private Map<String, Object> data;
}
