package com.louzao.application.domain.ports.primary.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
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
public class InputVatDto implements Serializable {

  private static final long serialVersionUID = 1222005234577476811L;

  @Schema(description = "Unique identifier of the VAT input record", example = "12345")
  private Integer id; // El ID podría ser opcional en algunos DTOs de creación, pero lo incluimos para consistencia

  @Schema(description = "Company code", required = true, example = "COMP01")
  @NotBlank
  private String company;

  @Schema(description = "VAT group identifier", required = true, example = "A")
  @NotBlank
  private String vatGroup;

  @Schema(description = "Account number", required = true, example = "12345678")
  @NotNull
  private BigDecimal account;

  @Schema(description = "VAT number of the client", required = true, example = "ES12345678Z")
  @NotBlank
  private String vatNumber;

  @Schema(description = "Document posting date", required = true, example = "2023-01-15")
  @NotNull
  private LocalDate documentPostdate;

  @Schema(description = "Client's name", required = true, example = "Cliente Ejemplo S.L.")
  @NotBlank
  private String clientName;

  @Schema(description = "Document number", required = true, example = "987654321")
  @NotNull
  private Long documentNumber;

  @Schema(description = "Autoline module code", required = true, example = "AP")
  @NotBlank
  private String autolineModule;

  @Schema(description = "Base value of the transaction", required = true, example = "1000.00")
  @NotNull
  private BigDecimal baseValue;

  @Schema(description = "VAT code applied", required = true, example = "S")
  @NotBlank
  private String vatCode;

  @Schema(description = "Transformed VAT code", required = true, example = "S1")
  @NotBlank
  private String transformedVatCode;

  @Schema(description = "Description of the VAT", required = true, example = "IVA Standard")
  @NotBlank
  private String vatDescription;

  @Schema(description = "VAT rate percentage", required = true, example = "21.000")
  @NotNull
  private BigDecimal vatRate;

  @Schema(description = "Deductible VAT amount", required = true, example = "210.00")
  @NotNull
  private BigDecimal deductibleVat;

  @Schema(description = "Non-deductible VAT amount", required = true, example = "0.00")
  @NotNull
  private BigDecimal nonDeductibleVat;

  @Schema(description = "Customer reference for the document", required = true, example = "REF-2023-001")
  @NotBlank
  private String customerReference;

  @Schema(description = "Original document date", required = true, example = "2023-01-10")
  @NotNull
  private LocalDate documentDate;

  @Schema(description = "Client's account number", required = true, example = "CLIACC01")
  @NotBlank
  private String clientAccount;

  @Schema(description = "Suffix for the document", required = true, example = "INV")
  @NotBlank
  private String suffix;

  @Schema(description = "Day book entry number", required = true, example = "1001")
  @NotNull
  private Long dayBook;
}
