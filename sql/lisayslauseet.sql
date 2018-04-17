-- antiquarians

INSERT INTO antiquarian VALUES
    ('Pirkanmaan keskusdivari', 'Hämeenkatu 13, 33100 Tampere', 'www.pkd.fi');

INSERT INTO antiquarian VALUES
    ('Markon antikvariaatti', 'Pinninkatu 21, 33100 Tampere', NULL);

INSERT INTO antiquarian VALUES
    ('Katin kirjakulmaus', 'Pinninkatu 27, 33100 Tampere', NULL);


-- users

INSERT INTO user VALUES
    ('Pertti', 'Pääkäyttäjä', 'Pitkämäenkatu 17, 33270 Tampere', 'pertti.paakayttaja@sapo.fi', '00000000000');

INSERT INTO user VALUES
    ('Maija', 'Meikäläinen', 'Pitkämäenkatu 12, 33270 Tampere', 'maija.meikalainen@sapo.fi', '00000000000');


-- Teokset

INSERT INTO product VALUES
    ('Madeleine Brent', 'Elektran tytär', 9155430674, 1986, 'romantiikka', 'romaani', 1.2);

INSERT INTO product VALUES
    ('Madeleine Brent', 'Tuulentavoittelijan morsian', 9156381451, 1978, 'romantiikka', 'romaani', 0,8);

INSERT INTO product VALUES
    ('Mika Waltari', 'Turms kuolematon', NULL,1995, 'historia', 'romaani', 1.4);


INSERT INTO product VALUES
    ('Mika Waltari', 'Komisario Palmun erehdys', NULL, 1940, 'dekkari', 'romaani', 0.8);

INSERT INTO product VALUES
    ('Shelton Gilbert', 'Friikkilän pojat Mexicossa', NULL, 1989, 'huumori', 'sarjakuva', 0.3);

INSERT INTO product VALUES
    ('Dale Carnegien', 'Miten saan ystäviä, menestystä, vaikutusvaltaa', 9789510396230, 1939, 'opas', 'tietokirja', 1.9);

