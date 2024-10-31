package com.mdemanuel.application.domain.model.domain.mongo;

import com.mdemanuel.application.domain.model.domain.BaseDocument;
import java.io.Serializable;
import java.util.Map;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode
@ToString
@SuperBuilder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class GenericDocument extends BaseDocument implements Serializable {

  private static final long serialVersionUID = 1234297222237476811L;

  private Map<String, Object> metadata;

  private Map<String, Object> data;

}
