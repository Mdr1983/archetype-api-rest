package com.louzao.application.domain.ports.primary.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
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
public class OutputVatConciliationDto implements Serializable {

  private static final long serialVersionUID = 2222054789577476811L;

  @Schema(description = "Unique identifier of the VAT input conciliation record", example = "1")
  private Integer id; // El ID podría ser opcional en algunos DTOs de creación, pero lo incluimos para consistencia

  @Schema(description = "Company code", required = true, example = "COMP01")
  @NotBlank
  private String company;

  @Schema(description = "Year of the conciliation", required = true, example = "2023")
  @NotNull
  private Integer year;

  @Schema(description = "Month of the conciliation", required = true, example = "1")
  @NotNull
  private Integer month;

  @Schema(description = "VAT group identifier", required = true, example = "A")
  @NotBlank
  private String vatGroup;

  @Schema(description = "Account number", required = true, example = "12345678")
  @NotNull
  private BigDecimal account;

  @Schema(description = "Transformed VAT code", required = true, example = "S1")
  @NotBlank
  private String transformedVatCode;

  @Schema(description = "Description of the VAT", required = true, example = "IVA Standard")
  @NotBlank
  private String vatDescription;

  @Schema(description = "VAT rate percentage", required = true, example = "21.000")
  @NotNull
  private BigDecimal vatRate;

  @Schema(description = "Total base value of invoices", required = true, example = "15000.00")
  @NotNull
  private BigDecimal totalInvoiceBase;

  @Schema(description = "Total base value of credit notes", required = true, example = "500.00")
  @NotNull
  private BigDecimal totalCreditBase;

  @Schema(description = "Total IVA amount of invoices", required = true, example = "3150.00")
  @NotNull
  private BigDecimal totalInvoiceIva;

  @Schema(description = "Total IVA amount of credit notes", required = true, example = "105.00")
  @NotNull
  private BigDecimal totalCreditIva;
}
