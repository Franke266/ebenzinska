package com.example.kv_ivanfranjic;

public class Fuel {

    private Integer id;
    private String naziv;
    private String cijena;

    public Fuel() {
    }

    public Fuel(Integer id, String naziv, String cijena) {
        this.id = id;
        this.naziv = naziv;
        this.cijena = cijena;
    }

    public Integer getId() {

        return id;
    }

    public void setId(Integer id) {

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


}
