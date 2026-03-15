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
    phone VARCHAR(30),
    active BOOLEAN DEFAULT TRUE,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE CustomerAddresses (
    id INT AUTO_INCREMENT PRIMARY KEY,
    customer_id INT NOT NULL,
    street_address VARCHAR(255) NOT NULL,
    postal_code VARCHAR(20),
    city VARCHAR(100) NOT NULL,
    country VARCHAR(100),
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_customeraddress_customer FOREIGN KEY (customer_id) REFERENCES Customers(id)
);

CREATE TABLE Suppliers (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    contact_name VARCHAR(100),
    phone VARCHAR(30),
    email VARCHAR(255),
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE SupplierAddresses (
    id INT AUTO_INCREMENT PRIMARY KEY,
    supplier_id INT NOT NULL,
    street_address VARCHAR(255) NOT NULL,
    postal_code VARCHAR(20),
    city VARCHAR(100) NOT NULL,
    country VARCHAR(100),
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_supplieraddress_supplier FOREIGN KEY (supplier_id) REFERENCES Suppliers(id)
);

CREATE TABLE ProductCategories (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE Products (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    price DECIMAL(10,2) NOT NULL,
    stock_quantity INT NOT NULL DEFAULT 0,
    category_id INT,
    supplier_id INT,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_product_category FOREIGN KEY (category_id) REFERENCES ProductCategories(id),
    CONSTRAINT fk_product_supplier FOREIGN KEY (supplier_id) REFERENCES Suppliers(id)
);

-- PRODUCT PRICE HISTORY
CREATE TABLE ProductPriceHistory (
    id INT AUTO_INCREMENT PRIMARY KEY,
    product_id INT NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    valid_from DATETIME DEFAULT CURRENT_TIMESTAMP,
    valid_to DATETIME,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_pricehistory_product FOREIGN KEY (product_id) REFERENCES Products(id)
);

CREATE TABLE Orders (
    id INT AUTO_INCREMENT PRIMARY KEY,
    customer_id INT NOT NULL,
    order_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    delivery_date DATETIME,
    shipping_address_id INT,
    status VARCHAR(50) DEFAULT 'NEW',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_order_customer FOREIGN KEY (customer_id) REFERENCES Customers(id),
    CONSTRAINT fk_order_shipping_address FOREIGN KEY (shipping_address_id) REFERENCES CustomerAddresses(id)
);

CREATE TABLE OrderItems (
    order_id INT NOT NULL,
    product_id INT NOT NULL,
    quantity INT NOT NULL,
    unit_price DECIMAL(10,2) NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (order_id, product_id),
    CONSTRAINT fk_orderitem_order FOREIGN KEY (order_id) REFERENCES Orders(id),
    CONSTRAINT fk_orderitem_product FOREIGN KEY (product_id) REFERENCES Products(id)
);

-- CREATE INDEXes
CREATE INDEX idx_orders_customer ON Orders(customer_id);
CREATE INDEX idx_products_category ON Products(category_id);
CREATE INDEX idx_products_supplier ON Products(supplier_id);
CREATE INDEX idx_orderitems_product ON OrderItems(product_id);
CREATE INDEX idx_orders_date ON Orders(order_date);

-- CREATE VIEWS
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

-- MOCK DATA WITH METADATA
INSERT INTO Customers (first_name,last_name,email,phone,created_at)
VALUES
    ('Matti','Meikäläinen','matti@email.com','0401234567', CURRENT_TIMESTAMP),
    ('Anna','Virtanen','anna@email.com','0507654321', CURRENT_TIMESTAMP);

INSERT INTO CustomerAddresses (customer_id, street_address, postal_code, city, country, created_at)
VALUES
    (1,'Testikatu 1','00100','Helsinki','Finland', CURRENT_TIMESTAMP),
    (2,'Esimerkkitie 5','33100','Tampere','Finland', CURRENT_TIMESTAMP);

INSERT INTO Suppliers (name, contact_name, phone, email, created_at)
VALUES
    ('TechSupplier','Timo','040111222','tech@supplier.com', CURRENT_TIMESTAMP),
    ('HardwareWorld','Helena','050333444','hw@supplier.com', CURRENT_TIMESTAMP);

INSERT INTO SupplierAddresses (supplier_id, street_address, postal_code, city, country, created_at)
VALUES
    (1,'Supplier Street 1','00100','Helsinki','Finland', CURRENT_TIMESTAMP),
    (2,'Supplier Road 5','33100','Tampere','Finland', CURRENT_TIMESTAMP);

INSERT INTO ProductCategories (name, description, created_at)
VALUES
    ('Electronics','Electronic devices like laptops, phones', CURRENT_TIMESTAMP),
    ('Accessories','Computer and mobile accessories', CURRENT_TIMESTAMP);

INSERT INTO Products (name, description, price, stock_quantity, category_id, supplier_id, created_at)
VALUES
    ('Laptop','High performance laptop',1200,10,1,1, CURRENT_TIMESTAMP),
    ('Gaming Mouse','High precision mouse',70,20,2,2, CURRENT_TIMESTAMP),
    ('Mechanical Keyboard','RGB mechanical keyboard',120,15,2,2, CURRENT_TIMESTAMP);

INSERT INTO ProductPriceHistory (product_id, price, created_at)
SELECT id, price, CURRENT_TIMESTAMP FROM Products;

INSERT INTO Orders (customer_id, shipping_address_id, status, created_at)
VALUES
    (1,1,'NEW', CURRENT_TIMESTAMP),
    (2,2,'NEW', CURRENT_TIMESTAMP);

INSERT INTO OrderItems (order_id, product_id, quantity, unit_price, created_at)
VALUES
    (1,1,1,1200,CURRENT_TIMESTAMP),
    (1,2,2,70,CURRENT_TIMESTAMP),
    (2,3,1,120,CURRENT_TIMESTAMP);