DROP TABLE IF EXISTS OrderItems;
DROP TABLE IF EXISTS Orders;
DROP TABLE IF EXISTS ProductPriceHistory;
DROP TABLE IF EXISTS Products;
DROP TABLE IF EXISTS ProductCategories;
DROP TABLE IF EXISTS SupplierAddresses;
DROP TABLE IF EXISTS CustomerAddresses;
DROP TABLE IF EXISTS Suppliers;
DROP TABLE IF EXISTS Customers;

CREATE TABLE Customers (
                           id INT AUTO_INCREMENT PRIMARY KEY,
                           first_name VARCHAR(100) NOT NULL,
                           last_name VARCHAR(100) NOT NULL,
                           email VARCHAR(255) NOT NULL UNIQUE,
                           phone VARCHAR(30)
);

CREATE TABLE CustomerAddresses (
                                   id INT AUTO_INCREMENT PRIMARY KEY,
                                   customer_id INT NOT NULL,
                                   street_address VARCHAR(255) NOT NULL,
                                   postal_code VARCHAR(20),
                                   city VARCHAR(100) NOT NULL,
                                   country VARCHAR(100),

                                   CONSTRAINT fk_customeraddress_customer FOREIGN KEY (customer_id) REFERENCES Customers(id)
);

CREATE TABLE Suppliers (
                           id INT AUTO_INCREMENT PRIMARY KEY,
                           name VARCHAR(255) NOT NULL,
                           contact_name VARCHAR(100),
                           phone VARCHAR(30),
                           email VARCHAR(255)
);

CREATE TABLE SupplierAddresses (
                                   id INT AUTO_INCREMENT PRIMARY KEY,
                                   supplier_id INT NOT NULL,
                                   street_address VARCHAR(255) NOT NULL,
                                   postal_code VARCHAR(20),
                                   city VARCHAR(100) NOT NULL,
                                   country VARCHAR(100),

                                   CONSTRAINT fk_supplieraddress_supplier FOREIGN KEY (supplier_id) REFERENCES Suppliers(id)
);

CREATE TABLE ProductCategories (
                                   id INT AUTO_INCREMENT PRIMARY KEY,
                                   name VARCHAR(100) NOT NULL,
                                   description TEXT
);

CREATE TABLE Products (
                          id INT AUTO_INCREMENT PRIMARY KEY,
                          name VARCHAR(255) NOT NULL,
                          description TEXT,
                          price DECIMAL(10,2) NOT NULL,
                          stock_quantity INT NOT NULL DEFAULT 0,
                          category_id INT,
                          supplier_id INT,

                          CONSTRAINT fk_product_category FOREIGN KEY (category_id) REFERENCES ProductCategories(id),
                          CONSTRAINT fk_product_supplier FOREIGN KEY (supplier_id) REFERENCES Suppliers(id)
);

CREATE TABLE ProductPriceHistory (
                                     id INT AUTO_INCREMENT PRIMARY KEY,
                                     product_id INT NOT NULL,
                                     price DECIMAL(10,2) NOT NULL,
                                     valid_from DATETIME DEFAULT CURRENT_TIMESTAMP,
                                     valid_to DATETIME,

                                     CONSTRAINT fk_pricehistory_product FOREIGN KEY (product_id) REFERENCES Products(id)
);

CREATE TABLE Orders (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        customer_id INT NOT NULL,
                        order_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                        delivery_date DATETIME,
                        shipping_address_id INT,
                        status VARCHAR(50) DEFAULT 'NEW',

                        CONSTRAINT fk_order_customer FOREIGN KEY (customer_id) REFERENCES Customers(id),
                        CONSTRAINT fk_order_shipping_address FOREIGN KEY (shipping_address_id) REFERENCES CustomerAddresses(id)
);

CREATE TABLE OrderItems (
                            order_id INT NOT NULL,
                            product_id INT NOT NULL,
                            quantity INT NOT NULL,
                            unit_price DECIMAL(10,2) NOT NULL,

                            PRIMARY KEY (order_id, product_id),

                            CONSTRAINT fk_orderitem_order FOREIGN KEY (order_id) REFERENCES Orders(id),
                            CONSTRAINT fk_orderitem_product FOREIGN KEY (product_id) REFERENCES Products(id)
);

-- CREATE INDEX starts here
CREATE INDEX idx_orders_customer ON Orders(customer_id);
CREATE INDEX idx_products_category ON Products(category_id);
CREATE INDEX idx_products_supplier ON Products(supplier_id);
CREATE INDEX idx_orderitems_product ON OrderItems(product_id);
CREATE INDEX idx_orders_date ON Orders(order_date);
-- CREATE INDEX ends here

-- CREATE VIEW starts here
CREATE VIEW OrderReceipt AS
SELECT
    o.id AS order_id,
    o.order_date,
    c.first_name,
    c.last_name,
    p.name AS product_name,
    oi.quantity,
    oi.unit_price,
    (oi.quantity * oi.unit_price) AS total_price
FROM Orders o
         JOIN Customers c ON o.customer_id = c.id
         JOIN OrderItems oi ON oi.order_id = o.id
         JOIN Products p ON oi.product_id = p.id;

CREATE VIEW OrderTotals AS
SELECT
    o.id AS order_id,
    SUM(oi.quantity * oi.unit_price) AS total_amount
FROM Orders o
         JOIN OrderItems oi ON o.id = oi.order_id
GROUP BY o.id;

CREATE VIEW ProductCatalog AS
SELECT
    p.id,
    p.name,
    p.description,
    p.price,
    p.stock_quantity,
    c.name AS category,
    s.name AS supplier
FROM Products p
         JOIN ProductCategories c ON p.category_id = c.id
         JOIN Suppliers s ON p.supplier_id = s.id;
-- CREATE VIEW ends here


-- update stock after order being created
DELIMITER //
CREATE TRIGGER update_stock
    AFTER INSERT ON OrderItems
    FOR EACH ROW
BEGIN
    UPDATE Products SET stock_quantity = stock_quantity - NEW.quantity WHERE id = NEW.product_id;
END//

DELIMITER ;


-- Track price changes
DELIMITER //
CREATE TRIGGER track_price_change
    AFTER UPDATE ON Products
    FOR EACH ROW
BEGIN
    IF NEW.price <> OLD.price THEN
    UPDATE ProductPriceHistory SET valid_to = NOW() WHERE product_id = OLD.id AND valid_to IS NULL;
    INSERT INTO ProductPriceHistory(product_id, price) VALUES (OLD.id, NEW.price);
END IF;
END//
DELIMITER ;

DELIMITER //
CREATE TRIGGER check_stock
    BEFORE INSERT ON OrderItems
    FOR EACH ROW
BEGIN
    DECLARE current_stock INT;

    SELECT stock_quantity INTO current_stock FROM Products WHERE id = NEW.product_id;

    IF current_stock < NEW.quantity THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Not enough stock available';
END IF;
END//
DELIMITER ;

-- mock data
INSERT INTO Customers (first_name,last_name,email,phone)
VALUES
    ('Matti','Meikäläinen','matti@email.com','0401234567'),
    ('Anna','Virtanen','anna@email.com','0507654321');

INSERT INTO CustomerAddresses (customer_id, street_address, postal_code, city, country)
VALUES
    (1,'Testikatu 1','00100','Helsinki','Finland'),
    (2,'Esimerkkitie 5','33100','Tampere','Finland');

INSERT INTO Suppliers (name, contact_name, phone, email)
VALUES
    ('TechSupplier','Timo','040111222','tech@supplier.com'),
    ('HardwareWorld','Helena','050333444','hw@supplier.com');

INSERT INTO ProductCategories (name, description)
VALUES
    ('Electronics','Electronic devices like laptops, phones'),
    ('Accessories','Computer and mobile accessories');

INSERT INTO Products (name, description, price, stock_quantity, category_id, supplier_id)
VALUES
    ('Laptop','High performance laptop',1200,10,1,1),
    ('Gaming Mouse','High precision mouse',70,20,2,2),
    ('Mechanical Keyboard','RGB mechanical keyboard',120,15,2,2);

INSERT INTO ProductPriceHistory (product_id, price)
SELECT id, price FROM Products;