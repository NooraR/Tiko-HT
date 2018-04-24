CREATE TABLE useraccount (
  id SERIAL,
  first_name VARCHAR(50) NOT NULL,
  last_name VARCHAR(50) NOT NULL,
  address VARCHAR(50),
  email VARCHAR(50) NOT NULL UNIQUE,
  password VARCHAR(300),
  phone_number VARCHAR(20),
  PRIMARY KEY(id)
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
  isbn      VARCHAR(20),
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
  antiquary_id   INT,
  PRIMARY KEY (id),
  FOREIGN KEY (workid) REFERENCES work (id),
  FOREIGN KEY (orderid) REFERENCES userorder (id)
);