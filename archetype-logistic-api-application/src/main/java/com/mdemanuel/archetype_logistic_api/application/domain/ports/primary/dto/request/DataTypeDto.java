package com.mdemanuel.archetype_logistic_api.application.domain.ports.primary.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
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
public class DataTypeDto implements Serializable {

  private static final long serialVersionUID = 1200297234577476811L;

  @Schema(
      description = "Data type id",
      required = true,
      example = "1")
  @NotNull
  private int dataTypeId;

  @Schema(description = "Data type code",
      required = true,
      example = "STRING")
  @NotBlank
  private String dataTypeCode;

  @Schema(description = "Description",
      required = true,
      example = "Cadena de texto")
  @NotBlank
  private String description;
}
