package com.louzao.application.domain.model.domain.mssql.autoline.vat;

import com.louzao.application.domain.model.domain.BaseEntity;
import com.louzao.application.domain.model.util.DbTables;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Table(name = DbTables.VW_OUTPUT_VAT_CONCILIATION)
@Data
@EqualsAndHashCode
@ToString
@SuperBuilder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class OutputVatConciliationEntity extends BaseEntity implements Serializable {

  private static final long serialVersionUID = 2361233234577412222L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Integer id;

  @Column(name = "company")
  private String company;

  @Column(name = "year")
  private Integer year;

  @Column(name = "month")
  private Integer month;

  @Column(name = "vat_group")
  private String vatGroup;

  @Column(name = "account")
  private BigDecimal account;

  @Column(name = "transformed_vat_code")
  private String transformedVatCode;

  @Column(name = "vat_description")
  private String vatDescription;

  @Column(name = "vat_rate")
  private BigDecimal vatRate;

  @Column(name = "total_invoice_base")
  private BigDecimal totalInvoiceBase;

  @Column(name = "total_credit_base")
  private BigDecimal totalCreditBase;

  @Column(name = "total_invoice_iva")
  private BigDecimal totalInvoiceIva;

  @Column(name = "total_credit_iva")
  private BigDecimal totalCreditIva;
}
