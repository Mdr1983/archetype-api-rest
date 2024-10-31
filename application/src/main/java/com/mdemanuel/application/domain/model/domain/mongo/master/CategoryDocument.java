package com.mdemanuel.application.domain.model.domain.mongo.master;

import com.mdemanuel.application.domain.model.domain.BaseDocument;
import com.mdemanuel.application.domain.model.domain.BaseEntity;
import com.mdemanuel.application.domain.model.util.DbDocuments;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = DbDocuments.CATEGORY)
@Data
@EqualsAndHashCode
@ToString
@SuperBuilder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class CategoryDocument extends BaseDocument {

  private static final long serialVersionUID = 1234297234577476811L;

  @Id
  private String id;

  @Indexed
  @Field(name = "code")
  @NotNull
  private String code;

  @Field(name = "description")
  @NotNull
  private String description;
}
