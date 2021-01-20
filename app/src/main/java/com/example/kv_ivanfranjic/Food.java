package com.example.kv_ivanfranjic;

public class Food {
    private String naziv, cijena, slika, id;

    public Food() {
    }

    public Food(String naziv, String cijena, String slika, String id) {
        this.naziv = naziv;
        this.cijena = cijena;
        this.slika = slika;
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

    public String getSlika() {
        return slika;
    }

    public void setSlika(String slika) {
        this.slika = slika;
    }

    public String getId() {

        return id;
    }

    public void setId(String id){
        this.id = id;
    }
}
