DELETE FROM vas_type;
INSERT INTO vas_type (vas_type_code, description)
VALUES ('ORDER', 'VAS a nivel de order'),
       ('ORDER_LINE', 'VAS a nivel de order line');

DELETE FROM vas;
INSERT INTO vas (vas_type_id, vas_code, description, priority, is_historical)
VALUES (1, 'VAS_ORDER_TEST_1', 'VAS order test 1', 1, 0),
       (1, 'VAS_ORDER_TEST_2', 'VAS order test 2', 2, 0),
       (2, 'VAS_ORDER_LINE_TEST_1', 'VAS order line test 1', 1, 0),
       (2, 'VAS_ORDER_LINE_TEST_2', 'VAS order line test 2', 2, 0);

DELETE FROM data_type;
INSERT INTO data_type (data_type_code, description)
VALUES ('NUMBER', 'Número'),
       ('DATE', 'Fecha'),
       ('BOOLEAN', 'Boolean'),
       ('STRING', 'Cadena de texto');

DELETE FROM metadata_order;
INSERT INTO metadata_order (metadata_order_code, description, data_type_id)
VALUES ('CLIENT_CODE', 'Código del cliente', 4),
       ('CHANNEL_TYPE', 'Tipo de canal de venta', 4),
       ('STOCK_ORIGIN', 'Origen del stock', 4),
       ('STOCK_ORIGIN_TYPE', 'Tipo del origen de stock', 4),
       ('COUNTRY_ORIGIN', 'País del origen de stock', 4),
       ('COUNTRY_DELIVERY', 'País del destino de stock', 4);

DELETE FROM metadata_order_line;
INSERT INTO metadata_order_line (metadata_order_line_code, description, data_type_id)
VALUES ('FAMILY', 'Familia del item', 4),
       ('SUBFAMILY', 'Subfamilia del item', 4);