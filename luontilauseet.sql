CREATE TABLE user (
    id INT SERIAL,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    address VARCHAR(50),
    email VARCHAR(50),
    phone_number VARCHAR(20),
    PRIMARY KEY(id)
);

CREATE TABLE antiquarian (
    id INT SERIAL,
    name VARCHAR(50) NOT NULL,
    address VARCHAR(50),
    web VARCHAR(50),
    PRIMARY KEY(tunnus)
);

CREATE TYPE order_status AS ENUM (
    'waiting', 'processed', 'sent'
 );

CREATE TABLE order (
    id INT SERIAL,
    orderdate DATE NOT NULL,
    status order_status,
    username INT NOT NULL,
    PRIMARY KEY(id),
    FOREIGN KEY(username) REFERENCES user(id)
);

CREATE TABLE work (
    id INT SERIAL,
    auchtor VARCHAR(50) NOT NULL,
    name VARCHAR(50) NOT NULL,
    isbn VARCHAR(20),
    published INT,
    genre VARCHAR(50),
    type VARCHAR(50),
    weight DECIMAL NOT NULL,
    PRIMARY KEY(id)
);

CREATE TYPE product_status AS ENUM (
    'free', 'taken', 'removed'
);

CREATE TABLE product (
    id INT SERIAL,
    isbn VARCHAR(20) NOT NULL,
    order INT,
    status product_status DEFAULT 'free',
    selling price DECIMAL,
    purchase_price DECIMAL,
    PRIMARY KEY(id),
    FOREIGN KEY(isbn) REFERENCES work(isbn),
    FOREIGN KEY(order) REFERENCES order(id)
);