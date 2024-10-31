DELETE FROM category;
INSERT INTO category (code, description)
VALUES ('BOLSO', 'Bolsos'),
       ('ZAPATO', 'Zapatos');

DELETE FROM purchase_order;
INSERT INTO purchase_order (code, purchase_order_date)
VALUES ('ORD-Test1', '2024-10-02T13:37:00Z'),
       ('ORD-Test2', '2024-10-12T13:37:00Z'),
       ('ORD-Test3', '2024-10-13T13:37:00Z'),
       ('ORD-Test4', '2024-10-20T13:37:00Z');

DELETE FROM purchase_order_line;
INSERT INTO purchase_order_line (purchase_order_id, item, description, category_id, quantity)
VALUES (1, 'B-125789', 'Bolso cuero negro', 1, 1),
       (2, 'B-125789', 'Bolso cuero negro', 1, 2),
       (3, 'Z-125789', 'Zapatilla deportiva negro', 2, 1),
       (3, 'Z-234545', 'Zapatilla suela alta negro', 2, 1),
       (4, 'Z-234545', 'Zapatilla suela alta negro', 2, 1);
