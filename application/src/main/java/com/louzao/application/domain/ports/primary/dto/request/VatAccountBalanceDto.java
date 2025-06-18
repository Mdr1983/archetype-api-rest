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
public class VatAccountBalanceDto implements Serializable {

  private static final long serialVersionUID = 1221994234577476811L;

  @Schema(description = "Unique identifier of the VAT input record", example = "12345")
  private Integer id;

  @Schema(description = "Company code", required = true, example = "COMP01")
  @NotBlank
  private String company;

  @Schema(description = "Account number", required = true, example = "12345.67")
  @NotNull
  private BigDecimal account;

  @Schema(description = "Description of the account", example = "Sales Account")
  private String accountDescription;

  @Schema(description = "Year of the balance record", required = true, example = "2023")
  @NotNull
  private Integer year;

  @Schema(description = "Month of the balance record (1-12)", required = true, example = "1")
  @NotNull
  private Integer month;

  @Schema(description = "Debit amount for the period", required = true, example = "1500.75")
  @NotNull
  private BigDecimal debit;

  @Schema(description = "Credit amount for the period", required = true, example = "1500.75")
  @NotNull
  private BigDecimal credit;

  @Schema(description = "Balance amount for the period", required = true, example = "1500.75")
  @NotNull
  private BigDecimal balance;
}
