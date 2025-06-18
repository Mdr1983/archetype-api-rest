-- Drop existing tables if they exist
DROP TABLE IF EXISTS audit_entry;
-- Create audit_entry table
CREATE TABLE audit_entry (
  id INT IDENTITY(1,1) PRIMARY KEY,
  trace_id VARCHAR(32) NOT NULL,
  span_id VARCHAR(32) NOT NULL,
  url VARCHAR(100) NOT NULL,
  http_method VARCHAR(10) NOT NULL,
  parameters VARCHAR(5000) NULL,
  request_body VARBINARY(MAX) NOT NULL,
  response_body VARBINARY(MAX) NOT NULL,
  http_status SMALLINT NOT NULL,
  key_value VARCHAR(100) DEFAULT NULL,
  elapsed_time INT NULL DEFAULT NULL,
  created_at DATETIME2 DEFAULT GETDATE(),
  updated_at DATETIME2 NULL,
  CONSTRAINT UC_audit_entry_trace_id_span_id UNIQUE (trace_id, span_id) -- Consider a unique constraint
);
-- Create indexes
CREATE INDEX idx_audit_entry_url ON audit_entry (url);
CREATE INDEX idx_audit_entry_status ON audit_entry (http_status);
CREATE INDEX idx_audit_entry_key_value ON audit_entry (key_value);
CREATE INDEX idx_audit_entry_created_at ON audit_entry (created_at);
CREATE INDEX idx_audit_entry_updated_at ON audit_entry (updated_at);

-- Drop existing tables if they exist
DROP TABLE IF EXISTS audit_exit;
-- Create audit_exit table
CREATE TABLE audit_exit (
  id INT IDENTITY(1,1) PRIMARY KEY,
  trace_id VARCHAR(32) NOT NULL,
  span_id VARCHAR(32) NOT NULL,
  url VARCHAR(500) NOT NULL,
  http_method VARCHAR(10) NOT NULL,
  request_body VARBINARY(MAX) NOT NULL,
  response_body VARBINARY(MAX) NOT NULL,
  http_status SMALLINT NOT NULL,
  elapsed_time INT NULL,
  created_at DATETIME2 DEFAULT GETDATE(),
  updated_at DATETIME2 NULL,
  CONSTRAINT UC_audit_exit_trace_id_span_id UNIQUE (trace_id, span_id) -- Consider a unique constraint
);
-- Create indexes
CREATE INDEX idx_audit_exit_url ON audit_exit (url);
CREATE INDEX idx_audit_exit_status ON audit_exit (http_status);
CREATE INDEX idx_audit_exit_created_at ON audit_exit (created_at);
CREATE INDEX idx_audit_exit_updated_at ON audit_exit (updated_at);
