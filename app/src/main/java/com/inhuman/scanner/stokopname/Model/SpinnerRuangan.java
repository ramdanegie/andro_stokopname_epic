package com.inhuman.scanner.stokopname.Model;

public class SpinnerRuangan {
    private int id;
    private String namaruangan;

    public SpinnerRuangan(int id, String namaruangan) {
        this.id = id;
        this.namaruangan = namaruangan;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNamaruangan() {
        return namaruangan;
    }

    public void setNamaruangan(String namaruangan) {
        this.namaruangan = namaruangan;
    }

//    @androidx.annotation.NonNull
    @Override
    public String toString() {
        return namaruangan;
    }
}
