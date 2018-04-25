-- Yksittäisten divarien triggerit

-- Muista asettaa scheman nimi!
SET search_path TO x;

-- work / teokset taulu
CREATE OR REPLACE FUNCTION update_work() RETURNS TRIGGER AS
$BODY$
BEGIN
  INSERT INTO central.work(author, name, isbn, published, genre, type, weight)
    SELECT new.author, new.name, new.isbn, new.published, new.genre, new.type, new.weight
    WHERE NOT EXISTS
    (SELECT * FROM central.work AS central
        WHERE central.author = new.author
              AND central.name = new.name
              AND central.isbn = new.isbn
              AND central.published = new.published
              AND central.genre = new.genre
              AND central.type = new.type
              AND central.weight = new.weight
    );
  RETURN new;
END;
$BODY$
LANGUAGE plpgsql;

CREATE TRIGGER update_work
  AFTER INSERT ON work
  FOR EACH ROW
  EXECUTE PROCEDURE update_work();

-- product / tuotteet taulu
CREATE OR REPLACE FUNCTION update_product() RETURNS TRIGGER AS
$BODY$
BEGIN
  INSERT INTO central.product(workid, orderid, selling_price, purchase_price, antiquary_id)
    SELECT new.workid, new.orderid, new.selling_price, new.purchase_price, new.antiquary_id;
  RETURN new;
END;
$BODY$
LANGUAGE plpgsql;

CREATE TRIGGER update_product
  AFTER INSERT ON product
  FOR EACH ROW
  EXECUTE PROCEDURE update_product();