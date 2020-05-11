package com.yudahendriawan.ProjectTugasAkhir;

public class Criteria {

    double jarak;
    double wisata;
    double kepadatan;

    public Criteria() {
    }

    public Criteria(double jarak, double wisata, double padat) {
        this.jarak = jarak;
        this.wisata = wisata;
        this.kepadatan = padat;
    }

    public double getJarak() {
        return jarak;
    }

    public void setJarak(double jarak) {
        this.jarak = jarak;
    }

    public double getWisata() {
        return wisata;
    }

    public double getKepadatan() {
        return kepadatan;
    }

    public void setWisata(double wisata) {
        this.wisata = wisata;
    }

    public void setKepadatan(double kepadatan) {
        this.kepadatan = kepadatan;
    }

}
