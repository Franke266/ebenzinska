package com.example.kv_ivanfranjic;

public class Equipment {
    private String naziv;
    private String cijena;
    private String slika;
    // private boolean permission;

    public Equipment() {
    }

    public Equipment(String naziv, String cijena, String slika) {
        this.naziv = naziv;
        this.cijena = cijena;
        this.slika = slika;
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
}
