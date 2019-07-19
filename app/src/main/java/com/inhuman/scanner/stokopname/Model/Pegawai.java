package com.inhuman.scanner.stokopname.Model;

public class Pegawai {
    String id;
    String namaLengkap;
    String tempatLahir;
    String tglLahir;
    String noIdentitas;
    String jenisKelamin;
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNamaLengkap() {
        return namaLengkap;
    }

    public void setNamaLengkap(String namaLengkap) {
        this.namaLengkap = namaLengkap;
    }

    public String getTempatLahir() {
        return tempatLahir;
    }

    public void setTempatLahir(String tempatLahir) {
        this.tempatLahir = tempatLahir;
    }

    public String getTglLahir() {
        return tglLahir;
    }

    public void setTglLahir(String tglLahir) {
        this.tglLahir = tglLahir;
    }

    public String getNoIdentitas() {
        return noIdentitas;
    }

    public void setNoIdentitas(String noIdentitas) {
        this.noIdentitas = noIdentitas;
    }

    public String getJenisKelamin() {
        return jenisKelamin;
    }

    public void setJenisKelamin(String jenisKelamin) {
        this.jenisKelamin = jenisKelamin;
    }

    public Pegawai(String id, String namaLengkap, String tempatLahir, String tglLahir, String noIdentitas, String jenisKelamin) {
        this.id = id;
        this.namaLengkap = namaLengkap;
        this.tempatLahir = tempatLahir;
        this.tglLahir = tglLahir;
        this.noIdentitas = noIdentitas;
        this.jenisKelamin = jenisKelamin;
    }

}
