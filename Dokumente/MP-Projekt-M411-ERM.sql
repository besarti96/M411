CREATE TABLE [RegionTB] (
  [ID_Region] INT PRIMARY KEY IDENTITY(1, 1),
  [Region] NVARCHAR(50) NOT NULL
)
GO

CREATE TABLE [Person] (
  [ID] INT PRIMARY KEY IDENTITY(1, 1),
  [Vorname] NVARCHAR(50) NOT NULL,
  [Name] NVARCHAR(50) NOT NULL,
  [Geschlecht] Person_Geschlecht_enum NOT NULL,
  [Geburtsdatum] DATE NOT NULL,
  [AHV_Nummer] NVARCHAR(20) UNIQUE NOT NULL,
  [ID_Region] INT,
  [Kinderanzahl] INT
)
GO

ALTER TABLE [Person] ADD FOREIGN KEY ([ID_Region]) REFERENCES [RegionTB] ([ID_Region])
GO
