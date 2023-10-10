package com.example.m411;

public class Person {
    private String name, vorname, geschlecht, geburtsdatum, ahvNummer, idRegion;
    private int kinderanzahl;

    public Person(String name, String vorname, String geschlecht, String geburtsdatum, String ahvNummer, String idRegion, int kinderanzahl) {
        this.name = name;
        this.vorname = vorname;
        this.geschlecht = geschlecht;
        this.geburtsdatum = geburtsdatum;
        this.ahvNummer = ahvNummer;
        this.idRegion = idRegion;
        this.kinderanzahl = kinderanzahl;
    }

    public String getName() { return name; }
    public String getVorname() { return vorname; }
    public String getGeschlecht() { return geschlecht; }
    public String getGeburtsdatum() { return geburtsdatum; }
    public String getAhvNummer() { return ahvNummer; }
    public String getIdRegion() { return idRegion; }
    public int getKinderanzahl() { return kinderanzahl; }

    public void setName(String name) { this.name = name; }
    public void setVorname(String vorname) { this.vorname = vorname; }
    public void setGeschlecht(String geschlecht) { this.geschlecht = geschlecht; }
    public void setGeburtsdatum(String geburtsdatum) { this.geburtsdatum = geburtsdatum; }
    public void setAhvNummer(String ahvNummer) { this.ahvNummer = ahvNummer; }
    public void setIdRegion(String idRegion) { this.idRegion = idRegion; }
    public void setKinderanzahl(int kinderanzahl) { this.kinderanzahl = kinderanzahl; }
}
