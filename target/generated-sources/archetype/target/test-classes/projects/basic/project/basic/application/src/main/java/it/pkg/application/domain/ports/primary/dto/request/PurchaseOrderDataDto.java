package it.pkg.application.domain.ports.primary.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.Instant;
import java.util.List;
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
public class PurchaseOrderDataDto implements Serializable {

  private static final long serialVersionUID = 2300297234571230011L;

  @Schema(description = "Purchase order code",
      required = true,
      example = "ORD-123456789")
  @NotBlank
  private String code;

  @Schema(description = "Purchase order date",
      required = true,
      example = "2024-10-22T13:37:00Z")
  @NotNull
  private Instant purchaseOrderDate;

  @Schema(description = "Purchase order lines",
      required = true)
  private List<PurchaseOrderLineDto> purchaseOrderLines;
}