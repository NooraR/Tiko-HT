-- antiquarians

INSERT INTO antiquarian (name, address, web) VALUES
  ('Pirkanmaan keskusdivari', 'Hämeenkatu 13, 33100 Tampere', 'www.pkd.fi');

INSERT INTO antiquarian (name, address, web) VALUES
  ('Markon antikvariaatti', 'Pinninkatu 21, 33100 Tampere', NULL);

INSERT INTO antiquarian (name, address, web) VALUES
  ('Katin kirjakulmaus', 'Pinninkatu 27, 33100 Tampere', NULL);


-- users

INSERT INTO useraccount (first_name, last_name, address, email, password, phone_number) VALUES
  ('Pertti', 'Pääkäyttäjä', 'Pitkämäenkatu 17, 33270 Tampere', 'pertti.paakayttaja@sapo.fi', 'testi123', '00000000000');

INSERT INTO useraccount (first_name, last_name, address, email, password, phone_number) VALUES
  ('Maija', 'Meikäläinen', 'Pitkämäenkatu 12, 33270 Tampere', 'maija.meikalainen@sapo.fi', 'Maija123', '00000000000');


-- Teokset

INSERT INTO work (author, name, isbn, published, genre, type, weight) VALUES
  ('Madeleine Brent', 'Elektran tytär', 9155430674, 1986, 'romantiikka', 'romaani', 1.2);

INSERT INTO work (author, name, isbn, published, genre, type, weight) VALUES
  ('Madeleine Brent', 'Tuulentavoittelijan morsian', 9156381451, 1978, 'romantiikka', 'romaani', 0.8);

INSERT INTO work (author, name, isbn, published, genre, type, weight) VALUES
  ('Mika Waltari', 'Turms kuolematon', NULL,1995, 'historia', 'romaani', 1.4);


INSERT INTO work (author, name, isbn, published, genre, type, weight) VALUES
  ('Mika Waltari', 'Komisario Palmun erehdys', NULL, 1940, 'dekkari', 'romaani', 0.8);

INSERT INTO work (author, name, isbn, published, genre, type, weight) VALUES
  ('Shelton Gilbert', 'Friikkilän pojat Mexicossa', NULL, 1989, 'huumori', 'sarjakuva', 0.3);

INSERT INTO work (author, name, isbn, published, genre, type, weight) VALUES
  ('Dale Carnegien', 'Miten saan ystäviä, menestystä, vaikutusvaltaa', 9789510396230, 1939, 'opas', 'tietokirja', 1.9);

-- Postituskulut

INSERT INTO postage VALUES (0.05, 1.40);
INSERT INTO postage VALUES (0.1, 2.10);
INSERT INTO postage VALUES (0.25, 2.80);
INSERT INTO postage VALUES (0.5, 5.60);
INSERT INTO postage VALUES (1.0, 8.40);
INSERT INTO postage VALUES (2.0, 14.00);