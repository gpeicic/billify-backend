-- Kreiranje tablica
CREATE TABLE client (
                        id INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
                        email VARCHAR(255) UNIQUE NOT NULL,
                        password VARCHAR(255) NOT NULL
);

CREATE TABLE item (
                      id INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
                      serialnumber VARCHAR(255) NOT NULL,
                      itemname VARCHAR(255) NOT NULL,
                      price NUMERIC NOT NULL
);

CREATE TABLE store (
                       id INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
                       store_name VARCHAR(255) NOT NULL,
                       address VARCHAR(255) NOT NULL
);

CREATE TABLE receipt (
                         id INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
                         date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
                         total_price NUMERIC NOT NULL,
                         ducan_id INTEGER NOT NULL,
                         kupac_id INTEGER NOT NULL,
                         CONSTRAINT fk_store
                             FOREIGN KEY (ducan_id) REFERENCES store(id),
                         CONSTRAINT fk_client
                             FOREIGN KEY (kupac_id) REFERENCES client(id)
);

CREATE TABLE itemreceipt (
                             id INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
                             amount NUMERIC NOT NULL,
                             receipt_id INTEGER NOT NULL,
                             item_id INTEGER NOT NULL,
                             CONSTRAINT fk_receipt
                                 FOREIGN KEY (receipt_id) REFERENCES receipt(id),
                             CONSTRAINT fk_item
                                 FOREIGN KEY (item_id) REFERENCES item(id)
);


INSERT INTO item (serialnumber, itemname, price) VALUES
                                                     ('SN123456789', 'Laptop', 999.99),
                                                     ('SN987654321', 'Smartphone', 499.99),
                                                     ('SN456789123', 'Headphones', 89.99),
                                                     ('SN321654987', 'Keyboard', 59.99),
                                                     ('SN654987321', 'Monitor', 199.99),
                                                     ('SN789321456', 'Mouse', 29.99);

INSERT INTO store (store_name, address) VALUES
                                            ('Konzum', 'Ulica Hrvatskih Branitelja 12, Zagreb'),
                                            ('Plodine', 'Trg Bana Jelačića 5, Rijeka'),
                                            ('Kaufland', 'Zagrebačka cesta 43, Osijek');

INSERT INTO client (email, password) VALUES
    ('novi_kupac@example.com', 'lozinka');

-- Unos računa bez total_price
INSERT INTO receipt (date, total_price, ducan_id, kupac_id) VALUES
                                                                ('2024-09-09 10:00:00', 0, 1, 1),
                                                                ('2024-09-09 11:00:00', 0, 2, 1),
                                                                ('2024-09-09 12:00:00', 0, 3, 1);

-- Unos itemreceipt podataka
INSERT INTO itemreceipt (amount, receipt_id, item_id) VALUES
                                                          (1, 1, 1),
                                                          (2, 1, 2),
                                                          (1, 2, 3),
                                                          (1, 2, 4),
                                                          (2, 2, 5),
                                                          (3, 3, 6);

-- Izračun i ažuriranje total_price u tablici receipt
UPDATE receipt r
SET total_price = subquery.calculated_total_price
    FROM (
    SELECT
        r.id AS receipt_id,
        SUM(ir.amount * i.price) AS calculated_total_price
    FROM
        receipt r
    JOIN
        itemreceipt ir ON r.id = ir.receipt_id
    JOIN
        item i ON ir.item_id = i.id
    GROUP BY
        r.id
) AS subquery
WHERE r.id = subquery.receipt_id;
