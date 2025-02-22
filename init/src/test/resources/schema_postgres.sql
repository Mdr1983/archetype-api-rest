DROP TABLE IF EXISTS audit_entry;
CREATE TABLE audit_entry (
  id INT GENERATED ALWAYS AS IDENTITY,
  trace_id VARCHAR(32) NOT NULL,
  span_id VARCHAR(32) NOT NULL,
  url VARCHAR(100) NOT NULL,
  http_method VARCHAR(10) NOT NULL,
  parameters VARCHAR(5000) NULL,
  request_body JSONB NOT NULL,
  response_body JSONB NOT NULL,
  http_status SMALLINT NOT NULL,
  key_value VARCHAR(100) DEFAULT NULL,
  elapsed_time INT NULL DEFAULT NULL,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT NULL,
  PRIMARY KEY (id)
);
CREATE INDEX idx_audit_entry_trace_id ON audit_entry(trace_id);
CREATE INDEX idx_audit_entry_url ON audit_entry(url);
CREATE INDEX idx_audit_entry_status ON audit_entry(http_status);
CREATE INDEX idx_audit_entry_key_value ON audit_entry(key_value);
CREATE INDEX idx_audit_entry_created_at ON audit_entry(created_at);
CREATE INDEX idx_audit_entry_updated_at ON audit_entry(updated_at);

DROP TABLE IF EXISTS audit_exit;
CREATE TABLE audit_exit (
  id INT GENERATED ALWAYS AS IDENTITY,
  audit_entry_id INT NOT NULL,
  trace_id VARCHAR(32) NOT NULL,
  span_id VARCHAR(32) NOT NULL,
  url VARCHAR(500) NOT NULL,
  http_method VARCHAR(10) NOT NULL,
  request_body BYTEA NOT NULL,
  response_body BYTEA NOT NULL,
  http_status SMALLINT NOT NULL,
  elapsed_time INT NULL,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (audit_entry_id) REFERENCES audit_entry(id)
);
CREATE INDEX idx_audit_exit_trace_id ON audit_exit(trace_id);
CREATE INDEX idx_audit_exit_url ON audit_exit(url);
CREATE INDEX idx_audit_exit_status ON audit_exit(http_status);
CREATE INDEX idx_audit_exit_created_at ON audit_exit(created_at);
CREATE INDEX idx_audit_exit_updated_at ON audit_exit(updated_at);

DROP TABLE IF EXISTS category;
CREATE TABLE category (
  id INT GENERATED ALWAYS AS IDENTITY,
  code VARCHAR(100) NOT NULL,
  description VARCHAR(150) NOT NULL,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT NULL,
  PRIMARY KEY (id)
);
CREATE UNIQUE INDEX idx_category_code ON category(code);

DROP TABLE IF EXISTS purchase_order;
CREATE TABLE purchase_order (
  id INT GENERATED ALWAYS AS IDENTITY,
  code VARCHAR(100) NOT NULL,
  purchase_order_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT NULL,
  PRIMARY KEY (id)
);
CREATE UNIQUE INDEX idx_purchase_order_code ON purchase_order(code);

DROP TABLE IF EXISTS purchase_order_line;
CREATE TABLE purchase_order_line (
  id INT GENERATED ALWAYS AS IDENTITY,
  purchase_order_id INT NOT NULL,
  item VARCHAR(100) NOT NULL,
  description VARCHAR(150) NOT NULL,
  category_id INT NOT NULL,
  quantity SMALLINT NOT NULL,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (purchase_order_id) REFERENCES purchase_order(id),
  FOREIGN KEY (category_id) REFERENCES category(id)
);
CREATE INDEX idx_purchase_order_line_item ON purchase_order_line(item);
