DROP TABLE IF EXISTS books;
 
CREATE TABLE books (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  title VARCHAR(250) NOT NULL,  
  gender VARCHAR(250) NOT NULL, 
  author VARCHAR(250) NOT NULL, 
  total_price INTEGER, 
  publisher VARCHAR(250) NOT NULL,
  publication_date date NOT NULL
);
 
INSERT INTO books (title, gender, author, total_price, publisher, publication_date) VALUES
  ('libro 1', 'accion', 'williams', 5000, 'norma', '2018-01-01');