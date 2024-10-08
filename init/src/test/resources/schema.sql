-- DROP TABLE IF EXISTS y CREAR TABLAS
DROP TABLE IF EXISTS audit_entry;
CREATE TABLE audit_entry (
  audit_entry_id INT GENERATED ALWAYS AS IDENTITY,
  trace_id VARCHAR(32) NOT NULL,
  span_id VARCHAR(32) NOT NULL,
  url VARCHAR(100) NOT NULL,
  http_method VARCHAR(10) NOT NULL,
  parameters VARCHAR(5000) NULL,
  request_body VARBINARY NOT NULL,
  response_body VARBINARY NOT NULL,
  http_status SMALLINT NOT NULL,
  key_value VARCHAR(100) DEFAULT NULL,
  elapsed_time INT NULL DEFAULT NULL,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT NULL,
  PRIMARY KEY (audit_entry_id)
);
CREATE INDEX idx_audit_entry_trace_id ON audit_entry(trace_id);
CREATE INDEX idx_audit_entry_url ON audit_entry(url);
CREATE INDEX idx_audit_entry_status ON audit_entry(http_status);
CREATE INDEX idx_audit_entry_key_value ON audit_entry(key_value);
CREATE INDEX idx_audit_entry_created_at ON audit_entry(created_at);
CREATE INDEX idx_audit_entry_updated_at ON audit_entry(updated_at);

DROP TABLE IF EXISTS audit_exit;
CREATE TABLE audit_exit (
  audit_exit_id INT GENERATED ALWAYS AS IDENTITY,
  audit_entry_id INT NOT NULL,
  trace_id VARCHAR(32) NOT NULL,
  span_id VARCHAR(32) NOT NULL,
  url VARCHAR(500) NOT NULL,
  http_method VARCHAR(10) NOT NULL,
  request_body VARBINARY NOT NULL,
  response_body VARBINARY NOT NULL,
  http_status SMALLINT NOT NULL,
  elapsed_time INT NULL,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT NULL,
  PRIMARY KEY (audit_exit_id),
  FOREIGN KEY (audit_entry_id) REFERENCES audit_entry(audit_entry_id)
);
CREATE INDEX idx_audit_exit_trace_id ON audit_exit(trace_id);
CREATE INDEX idx_audit_exit_url ON audit_exit(url);
CREATE INDEX idx_audit_exit_status ON audit_exit(http_status);
CREATE INDEX idx_audit_exit_created_at ON audit_exit(created_at);
CREATE INDEX idx_audit_exit_updated_at ON audit_exit(updated_at);

DROP TABLE IF EXISTS vas_type;
CREATE TABLE vas_type (
  vas_type_id INT GENERATED ALWAYS AS IDENTITY,
  vas_type_code VARCHAR(100) NOT NULL,
  description VARCHAR(150) NOT NULL,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT NULL,
  PRIMARY KEY (vas_type_id),
  CONSTRAINT idx_vas_type_vas_type_code UNIQUE (vas_type_code)
);

DROP TABLE IF EXISTS vas;
CREATE TABLE vas (
  vas_id INT GENERATED ALWAYS AS IDENTITY,
  vas_type_id INT NOT NULL,
  vas_code VARCHAR(100) NOT NULL,
  description VARCHAR(150) NOT NULL,
  priority SMALLINT NOT NULL,
  is_historical BIT NOT NULL DEFAULT 0,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT NULL,
  PRIMARY KEY (vas_id),
  CONSTRAINT idx_vas_vas_code UNIQUE (vas_code),
  FOREIGN KEY (vas_type_id) REFERENCES vas_type(vas_type_id)
);

DROP TABLE IF EXISTS data_type;
CREATE TABLE data_type (
  data_type_id INT GENERATED ALWAYS AS IDENTITY,
  data_type_code VARCHAR(100) NOT NULL,
  description VARCHAR(150) NOT NULL,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT NULL,
  PRIMARY KEY (data_type_id),
  CONSTRAINT idx_data_type_data_type_code UNIQUE (data_type_code)
);

DROP TABLE IF EXISTS metadata_order;
CREATE TABLE metadata_order (
  metadata_order_id INT GENERATED ALWAYS AS IDENTITY,
  metadata_order_code VARCHAR(100) NOT NULL,
  description VARCHAR(150) NOT NULL,
  data_type_id INT NOT NULL,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT NULL,
  PRIMARY KEY (metadata_order_id),
  CONSTRAINT idx_metadata_order_metadata_order_code UNIQUE (metadata_order_code),
  FOREIGN KEY (data_type_id) REFERENCES data_type(data_type_id)
);

DROP TABLE IF EXISTS metadata_order_line;
CREATE TABLE metadata_order_line (
  metadata_order_line_id INT GENERATED ALWAYS AS IDENTITY,
  metadata_order_line_code VARCHAR(100) NOT NULL,
  description VARCHAR(150) NOT NULL,
  data_type_id INT NOT NULL,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT NULL,
  PRIMARY KEY (metadata_order_line_id),
  CONSTRAINT idx_metadata_order_line_metadata_order_line_code UNIQUE (metadata_order_line_code),
  FOREIGN KEY (data_type_id) REFERENCES data_type(data_type_id)
);

DROP TABLE IF EXISTS "order";
CREATE TABLE "order" (
  order_id INT GENERATED ALWAYS AS IDENTITY,
  order_code VARCHAR(100) NOT NULL,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT NULL,
  PRIMARY KEY (order_id),
  CONSTRAINT idx_order_order_code UNIQUE (order_code)
);

DROP TABLE IF EXISTS order_line;
CREATE TABLE order_line (
  order_line_id INT GENERATED ALWAYS AS IDENTITY,
  order_id INT NOT NULL,
  order_line_number INT NOT NULL,
  item VARCHAR(100) NOT NULL,
  quantity INT NOT NULL,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT NULL,
  PRIMARY KEY (order_line_id),
  FOREIGN KEY (order_id) REFERENCES "order"(order_id)
);

DROP TABLE IF EXISTS order_metadata_order;
CREATE TABLE order_metadata_order (
  order_id INT NOT NULL,
  metadata_order_id INT NOT NULL,
  key_value VARCHAR(500) NOT NULL,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT NULL,
  PRIMARY KEY (order_id, metadata_order_id)
);

DROP TABLE IF EXISTS order_line_metadata_order_line;
CREATE TABLE order_line_metadata_order_line (
  order_line_id INT NOT NULL,
  metadata_order_line_id INT NOT NULL,
  key_value VARCHAR(500) NOT NULL,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT NULL,
  PRIMARY KEY (order_line_id, metadata_order_line_id)
);

DROP TABLE IF EXISTS order_line_vas;
CREATE TABLE order_line_vas (
  order_line_id INT NOT NULL,
  vas_id INT NOT NULL,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT NULL,
  PRIMARY KEY (order_line_id, vas_id)
);

DROP TABLE IF EXISTS vas_selector_rule;
CREATE TABLE vas_selector_rule (
  vas_selector_rule_id INT GENERATED ALWAYS AS IDENTITY,
  channel_type_id INT DEFAULT NULL,
  source_document_type_id INT DEFAULT NULL,
  shipment_origin_id INT DEFAULT NULL,
  shipment_delivery_type_id INT DEFAULT NULL,
  season_id INT DEFAULT NULL,
  country_id INT DEFAULT NULL,
  customer_id INT DEFAULT NULL,
  shipment_delivery_id INT DEFAULT NULL,
  group_id INT DEFAULT NULL,
  superfamily_id INT DEFAULT NULL,
  department_id INT DEFAULT NULL,
  store_classification_id INT DEFAULT NULL,
  is_check_ue BIT DEFAULT NULL,
  is_gift BIT DEFAULT NULL,
  operation_origin_type_id INT DEFAULT NULL,
  operation_origin_id INT DEFAULT NULL,
  vas_id INT DEFAULT NULL,
  start_dt TIMESTAMP NULL DEFAULT NULL,
  end_dt TIMESTAMP NULL DEFAULT NULL,
  is_active BIT DEFAULT NULL,
  is_historical BIT NOT NULL DEFAULT 0,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT NULL,
  PRIMARY KEY (vas_selector_rule_id)
);
ALTER TABLE vas_selector_rule ALTER COLUMN vas_selector_rule_id RESTART WITH 1;

DROP TABLE IF EXISTS container;
CREATE TABLE container (
  container_id INT GENERATED ALWAYS AS IDENTITY,
  container_code VARCHAR(25) NOT NULL,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT NULL,
  PRIMARY KEY (container_id)
);

DROP TABLE IF EXISTS container_order_line;
CREATE TABLE container_order_line (
  container_id INT NOT NULL,
  order_line_id SMALLINT NOT NULL,
  quantity SMALLINT NOT NULL,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT NULL,
  PRIMARY KEY (container_id, order_line_id)
);

DROP TABLE IF EXISTS container_order_line_order_line_vas;
CREATE TABLE container_order_line_order_line_vas (
  container_id INT NOT NULL,
  order_line_id INT NOT NULL,
  vas_id INT NOT NULL,
  is_confirmed BIT NOT NULL,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT NULL,
  PRIMARY KEY (container_id, order_line_id, vas_id)
);

DROP TABLE IF EXISTS vas_selector_rule_order_line;
CREATE TABLE vas_selector_rule_order_line (
  vas_selector_rule_id INT NOT NULL,
  order_line_id INT NOT NULL,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT NULL,
  PRIMARY KEY (vas_selector_rule_id, order_line_id)
);
