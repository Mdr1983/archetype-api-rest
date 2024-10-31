package com.mdemanuel.application.domain.ports.primary.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
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
public class CategoryDataDto implements Serializable {

  private static final long serialVersionUID = 1202005234577476811L;

  @Schema(description = "Category code",
      required = true,
      example = "BOLSO")
  @NotBlank
  private String code;

  @Schema(description = "Description",
      required = true,
      example = "Bolso piel")
  @NotBlank
  private String description;
}
