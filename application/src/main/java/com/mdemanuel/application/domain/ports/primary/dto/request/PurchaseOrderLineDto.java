package com.mdemanuel.application.domain.ports.primary.dto.request;

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
public class PurchaseOrderLineDto implements Serializable {

  private static final long serialVersionUID = 2300297234577472211L;

  @Schema(description = "Item",
      required = true,
      example = "B-125789")
  @NotBlank
  private String item;

  @Schema(description = "Description",
      required = true,
      example = "Bolso cuero negro")
  @NotBlank
  private String description;

  @Schema(description = "Category",
      required = true,
      example = "BOLSO")
  @NotBlank
  private String categoryCode;

  @Schema(description = "Quantity",
      required = true,
      example = "1")
  @NotNull
  private int quantity;

}
