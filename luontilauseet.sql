CREATE TABLE kayttaja (
    tunnus SERIAL,
    etunimi VARCHAR(50) NOT NULL,
    sukunimi VARCHAR(50) NOT NULL,
    osoite VARCHAR(50),
    email VARCHAR(50),
    puhelinnumero VARCHAR(20),
    PRIMARY KEY(tunnus)
);

CREATE TABLE keskusdivari (
    tunnus SERIAL,
    nimi VARCHAR(50) NOT NULL,
    osoite VARCHAR(50),
    web VARCHAR(50),
    PRIMARY KEY(tunnus)
);

CREATE TABLE divari (
    tunnus SERIAL,
    nimi VARCHAR(50) NOT NULL,
    osoite VARCHAR(50),
    PRIMARY KEY(tunnus)
);

CREATE TYPE tilaus_tila AS ENUM (
    'odottaa', 'käsitelty', 'lähetetty'
 );

CREATE TABLE tilaus (
    id SERIAL,
    tilauspvm DATE NOT NULL,
    tila tilaus_tila,
    kayttajatunnus INT NOT NULL,
    PRIMARY KEY(id),
    FOREIGN KEY(kayttajatunnus) REFERENCES kayttaja(tunnus)
);

CREATE TABLE teos (
    id SERIAL,
    tekija VARCHAR(50) NOT NULL,
    nimi VARCHAR(50) NOT NULL,
    isbn VARCHAR(20),
    julkaisuvuosi INT,
    genre VARCHAR(50),
    tyyppi VARCHAR(50),
    paino DECIMAL NOT NULL,
    PRIMARY KEY(id)
);

CREATE TYPE tuote_tila AS ENUM (
    'vapaa', 'varattu', 'poistettu'
);

CREATE TABLE tuote (
    id SERIAL,
    isbn VARCHAR(20) NOT NULL,
    tilaus INT,
    tila tuote_tila DEFAULT 'vapaa',
    myyntihinta DECIMAL,
    sisaanostohinta DECIMAL,
    PRIMARY KEY(id),
    FOREIGN KEY(isbn) REFERENCES teokset,
    FOREIGN KEY(tilaus) REFERENCES tilaus(id)
);