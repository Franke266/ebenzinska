package com.example.kv_ivanfranjic;

public class Fuel {

    private String naziv, cijena, id;

    public Fuel() {
    }

    public Fuel(String naziv, String cijena, String id) {
        this.naziv = naziv;
        this.cijena = cijena;
        this.id = id;
    }

    public String getNaziv() {

        return naziv;
    }

    public void setNaziv(String naziv) {

        this.naziv = naziv;
    }

    public String getCijena() {

        return cijena;
    }

    public void setCijena(String cijena) {

        this.cijena = cijena;
    }

    public String getId() {

        return id;
    }

    public void setId(String id) {

        this.id = id;
    }


}
