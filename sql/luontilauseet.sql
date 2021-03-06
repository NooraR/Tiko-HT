CREATE TABLE useraccount (
    id SERIAL,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    address VARCHAR(50) NOT NULL,
    email VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(300) NOT NULL,
    phone_number VARCHAR(20),
    isadmin BOOLEAN DEFAULT FALSE NOT NULL,
    PRIMARY KEY(id)
);

CREATE TABLE antiquarian (
    id      SERIAL,
    name    VARCHAR(50) NOT NULL,
    address VARCHAR(50),
    web     VARCHAR(50),
    db_schema VARCHAR(40),
    PRIMARY KEY (id)
);

CREATE TABLE userorder (
    id          SERIAL,
    orderdate   DATE NOT NULL,
    status      VARCHAR(15) DEFAULT 'WAITING' NOT NULL,
    userid      INT  NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (userid) REFERENCES useraccount (id)
);

CREATE TABLE work (
    id        SERIAL,
    author   VARCHAR(50) NOT NULL,
    name      VARCHAR(50) NOT NULL,
    isbn      VARCHAR(20) UNIQUE,
    published INT,
    genre     VARCHAR(50),
    type      VARCHAR(50),
    weight    DECIMAL     NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE product (
    id             SERIAL,
    workid         INT NOT NULL,
    orderid        INT,
    status         VARCHAR(15) DEFAULT 'FREE' NOT NULL,
    selling_price  DECIMAL,
    purchase_price DECIMAL,
    antiquary_id   INT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (workid) REFERENCES work (id),
    FOREIGN KEY (orderid) REFERENCES userorder (id),
    FOREIGN KEY (antiquary_id) REFERENCES antiquarian (id)
);

CREATE TABLE postage (
    weight  DECIMAL NOT NULL,
    fee     DECIMAL NOT NULL,
    PRIMARY KEY (weight)
);