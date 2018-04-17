CREATE TABLE useraccount (
    id SERIAL,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    address VARCHAR(50),
    email VARCHAR(50) NOT NULL,
    password VARCHAR(300),
    phone_number VARCHAR(20),
    PRIMARY KEY(id)
);

CREATE TABLE antiquarian (
    id      SERIAL,
    name    VARCHAR(50) NOT NULL,
    address VARCHAR(50),
    web     VARCHAR(50),
    PRIMARY KEY (id)
);

CREATE TYPE order_status AS ENUM (
    'waiting', 'processed', 'sent'
);

CREATE TABLE userorder (
    id          SERIAL,
    orderdate   DATE NOT NULL,
    status      order_status DEFAULT 'waiting',
    userid      INT  NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (userid) REFERENCES useraccount (id)
);

CREATE TABLE work (
    id        SERIAL,
    auchtor   VARCHAR(50) NOT NULL,
    name      VARCHAR(50) NOT NULL,
    isbn      VARCHAR(20),
    published INT,
    genre     VARCHAR(50),
    type      VARCHAR(50),
    weight    DECIMAL     NOT NULL,
    PRIMARY KEY (id)
);

CREATE TYPE product_status AS ENUM (
    'free', 'taken', 'removed'
);

CREATE TABLE product (
    id             SERIAL,
    workid         INT NOT NULL,
    orderid        INT,
    status         product_status DEFAULT 'free',
    selling_price  DECIMAL,
    purchase_price DECIMAL,
    PRIMARY KEY (id),
    FOREIGN KEY (workid) REFERENCES work (id),
    FOREIGN KEY (orderid) REFERENCES userorder (id)
);