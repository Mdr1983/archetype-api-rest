package com.mdemanuel.archetype_logistic_api.application.domain.model.domain.master;

import com.mdemanuel.archetype_logistic_api.application.domain.model.domain.BaseEntity;
import com.mdemanuel.archetype_logistic_api.application.domain.model.util.DbTables;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Table(name = DbTables.DATA_TYPE)
@Entity
@Data
@EqualsAndHashCode
@ToString
@SuperBuilder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class DataTypeEntity extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "data_type_id")
  private Integer dataTypeId;

  @Column(name = "data_type_code")
  @NotNull
  private String dataTypeCode;

  @Column(name = "description")
  @NotNull
  private String description;
}
