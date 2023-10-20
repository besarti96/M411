CREATE DATABASE m411db;

CREATE TABLE RegionTB (
  ID_Region INT PRIMARY KEY AUTO_INCREMENT,
  Region VARCHAR(50) NOT NULL
);

INSERT INTO RegionTB (Region) VALUES ('Schwyz');
INSERT INTO RegionTB (Region) VALUES ('Zuerich');
INSERT INTO RegionTB (Region) VALUES ('St.Gallen');

CREATE TABLE Person (
  ID INT PRIMARY KEY AUTO_INCREMENT,
  Vorname VARCHAR(50) NOT NULL,
  Name VARCHAR(50) NOT NULL,
  Geschlecht ENUM('Männlich', 'Weiblich', 'Divers') NOT NULL, 
  Geburtsdatum DATE NOT NULL,
  AHV_Nummer VARCHAR(20) UNIQUE NOT NULL,
  ID_Region INT,
  Kinderanzahl INT,
  FOREIGN KEY (ID_Region) REFERENCES RegionTB(ID_Region)
);

INSERT INTO Person (Vorname, Name, Geschlecht, Geburtsdatum, AHV_Nummer, ID_Region, Kinderanzahl)
VALUES ('Rinor', 'Recica', 'Männlich', '1995-04-22', '756.1234.5678.90', 1, 2);

INSERT INTO Person (Vorname, Name, Geschlecht, Geburtsdatum, AHV_Nummer, ID_Region, Kinderanzahl)
VALUES ('Besart', 'Jashari', 'Männlich', '1990-12-05', '756.9876.5432.10', 2, 1);

INSERT INTO Person (Vorname, Name, Geschlecht, Geburtsdatum, AHV_Nummer, ID_Region, Kinderanzahl)
VALUES ('Isa', 'Halimi', 'Männlich', '1985-08-15', '756.1111.2222.33', 3, 3);
