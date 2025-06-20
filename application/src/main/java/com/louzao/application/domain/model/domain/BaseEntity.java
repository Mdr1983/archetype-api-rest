package com.louzao.application.domain.model.domain;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import java.io.Serializable;
import java.time.Instant;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Data
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@MappedSuperclass
public class BaseEntity implements Serializable {

  @CreationTimestamp
  @Column(name = "created_at", updatable = false)
  protected Instant createdAt;

  @UpdateTimestamp
  @Column(name = "updated_at")
  protected Instant updatedAt;
}
