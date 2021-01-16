package com.example.kv_ivanfranjic;

public class Food {
    private String naziv;
    private String cijena;
    private String slika;
   // private boolean permission;

    public Food() {
    }

    public Food(String naziv, String cijena, String slika/*, boolean permission*/) {
        this.naziv = naziv;
        this.cijena = cijena;
        this.slika = slika;
        //this.permission = permission;
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

    /*public boolean getPermission() {
        return permission;
    }

    public void setPermission(boolean permission) {
        this.permission = permission;
    }*/
}
