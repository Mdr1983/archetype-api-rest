package com.louzao.application.util.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.louzao.application.domain.service.exceptions.ValidateJsonException;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion;
import com.networknt.schema.ValidationMessage;
import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Data
public class JsonValidationSchema {

  private final Map<TypeSchema, JsonSchema> schemas = new ConcurrentHashMap<>();

  @Autowired
  private ObjectMapper objectMapper;

  public void validateJson(TypeSchema typeSchema, Object json)
      throws IOException, ValidateJsonException {
    Set<ValidationMessage> validationMessage = schemas.get(typeSchema)
        .validate(objectMapper.readTree(objectMapper.writeValueAsBytes(json)));

    if (validationMessage != null && !validationMessage.isEmpty()) {
      throw new ValidateJsonException(validationMessage.toString());
    }
  }

  public void validateJson(TypeSchema typeSchema, String json)
      throws IOException, ValidateJsonException {
    Set<ValidationMessage> validationMessage = schemas.get(typeSchema)
        .validate(objectMapper.readTree(objectMapper.writeValueAsBytes(json)));

    if (validationMessage != null && !validationMessage.isEmpty()) {
      throw new ValidateJsonException(validationMessage.toString());
    }
  }

  @PostConstruct
  public void loadSchemas() {
    try {
      JsonSchemaFactory schemaFactory = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V7);

      ClassPathResource schemaResource = new ClassPathResource("jsonSchema/search_criteria.json");

      // Leer el contenido del esquema una sola vez
      byte[] schemaBytes = schemaResource.getInputStream().readAllBytes();

      // Registrar el schema
      schemas.put(TypeSchema.SEARCH_CRITERIA,
          schemaFactory.getSchema(objectMapper.readTree(schemaBytes)));
    } catch (IOException e) {
      log.error("Error al cargar los json schemas", e);
      throw new RuntimeException(e);
    }
  }

  public enum TypeSchema {

    SEARCH_CRITERIA
  }
}
