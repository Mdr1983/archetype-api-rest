package com.mdemanuel.application.domain.model.domain.postgres.master;

import com.mdemanuel.application.domain.model.domain.BaseEntity;
import com.mdemanuel.application.domain.model.util.DbTables;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Table(name = DbTables.CATEGORY)
@Entity
@Data
@EqualsAndHashCode
@ToString
@SuperBuilder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class CategoryEntity extends BaseEntity implements Serializable {

  private static final long serialVersionUID = 2360297234577476811L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Integer id;

  @Column(name = "code")
  @NotNull
  private String code;

  @Column(name = "description")
  @NotNull
  private String description;
}