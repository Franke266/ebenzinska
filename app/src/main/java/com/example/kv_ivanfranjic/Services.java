package com.example.kv_ivanfranjic;

public class Services {

    private String naziv;
    private String cijena;

    public Services() {
    }

    public Services(String naziv, String cijena) {
        this.naziv = naziv;
        this.cijena = cijena;
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
}
