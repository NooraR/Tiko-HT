CREATE TABLE rooli (
    id INT,
    nimi VARCHAR(25) NOT NULL,
    PRIMARY KEY(id)
);

CREATE TABLE kayttaja (
    tunnus INT,
    etunimi VARCHAR(50) NOT NULL,
    sukunimi VARCHAR(50) NOT NULL,
    osoite VARCHAR(50),
    email VARCHAR(50),
    puhelinnumero VARCHAR(20),
    PRIMARY KEY(tunnus)
);

CREATE TABLE roolitus (
    id INT,
    tunnus INT,
    PRIMARY KEY(id, tunnus),
    FOREIGN KEY(id) REFERENCES rooli,
    FOREIGN KEY(tunnus) REFERENCES kayttaja
);

CREATE TABLE keskusdivari (
    tunnus INT,
    nimi VARCHAR(50) NOT NULL,
    osoite VARCHAR(50),
    web VARCHAR(50),
    PRIMARY KEY(tunnus)
);

CREATE TABLE divari (
    tunnus INT,
    nimi VARCHAR(50) NOT NULL,
    osoite VARCHAR(50),
    PRIMARY KEY(tunnus)
);

CREATE TABLE tilaus (
    id INT,
    tilauspvm DATE NOT NULL,
    kayttajatunnus INT NOT NULL,
    PRIMARY KEY(id),
    FOREIGN KEY(kayttajatunnus) REFERENCES kayttaja(tunnus)
);

CREATE TABLE teokset (
    isbn VARCHAR(20),
    nimi VARCHAR(50) NOT NULL,
    tekija VARCHAR(50) NOT NULL,
    tyyppi VARCHAR(50),
    genre VARCHAR(50),
    paino DECIMAL NOT NULL,
    PRIMARY KEY(isbn)
);

CREATE TABLE tuotteet(
    id INT,
    divari INT NOT NULL,
    isbn VARCHAR(20) NOT NULL,
    tilaus INT,
    tila INT,
    myyntihinta DECIMAL,
    sisaanostohinta DECIMAL,
    PRIMARY KEY(id),
    FOREIGN KEY(divari) REFERENCES divari(tunnus),
    FOREIGN KEY(isbn) REFERENCES teokset,
    FOREIGN KEY(tilaus) REFERENCES tilaus(id)
);