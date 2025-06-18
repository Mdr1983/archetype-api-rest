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
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Table(name = DbTables.VW_OUTPUT_VAT)
@Data
@EqualsAndHashCode
@ToString
@SuperBuilder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class OutputVatEntity extends BaseEntity implements Serializable {

  private static final long serialVersionUID = 2360297234577412001L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Integer id;

  @Column(name = "company")
  private String company;

  @Column(name = "vat_group")
  private String vatGroup;

  @Column(name = "account")
  private BigDecimal account;

  @Column(name = "long_document_number")
  private String longDocumentNumber;

  @Column(name = "document_number")
  private Long documentNumber;

  @Column(name = "autoline_module")
  private String autolineModule;

  @Column(name = "customer_reference")
  private String customerReference;

  @Column(name = "document_date")
  private LocalDate documentDate;

  @Column(name = "vat_number")
  private String vatNumber;

  @Column(name = "client_name")
  private String clientName;

  @Column(name = "base_value")
  private BigDecimal baseValue;

  @Column(name = "vat_code")
  private String vatCode;

  @Column(name = "transformed_vat_code")
  private String transformedVatCode;

  @Column(name = "vat_description")
  private String vatDescription;

  @Column(name = "vat_rate")
  private BigDecimal vatRate;

  @Column(name = "deductible_vat")
  private BigDecimal deductibleVat;

  @Column(name = "non_deductible_vat")
  private BigDecimal nonDeductibleVat;

  @Column(name = "document_postdate")
  private LocalDate documentPostdate;

  @Column(name = "client_account")
  private String clientAccount;

  @Column(name = "suffix")
  private String suffix;

  @Column(name = "day_book")
  private Long dayBook;
}
