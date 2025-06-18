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

@Table(name = DbTables.VW_VAT_ACCOUNT_BALANCE)
@Data
@EqualsAndHashCode
@ToString
@SuperBuilder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class VatAccountBalanceEntity extends BaseEntity implements Serializable {

  private static final long serialVersionUID = 2360297222217476811L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Integer id;

  @Column(name = "company")
  private String company;

  @Column(name = "account")
  private BigDecimal account;

  @Column(name = "account_description")
  private String accountDescription;

  @Column(name = "year")
  private Integer year;

  @Column(name = "month")
  private Integer month;

  @Column(name = "debit")
  private BigDecimal debit;

  @Column(name = "credit")
  private BigDecimal credit;

  @Column(name = "balance")
  private BigDecimal balance;
}
