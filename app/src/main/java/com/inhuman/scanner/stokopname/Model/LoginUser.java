package com.inhuman.scanner.stokopname.Model;

public class LoginUser {
    String namaUser;
    String kataSandi;
    String namaPegawai;
    String kdUser;
    String token;
    Pegawai pegawai ;
    public LoginUser(String namaUser, String kataSandi, String namaPegawai, String kdUser, String token, Pegawai pegawai) {
        this.namaUser = namaUser;
        this.kataSandi = kataSandi;
        this.namaPegawai = namaPegawai;
        this.kdUser = kdUser;
        this.token = token;
        this.pegawai = pegawai;
    }

    public Pegawai getPegawai() {
        return pegawai;
    }

    public void setPegawai(Pegawai pegawai) {
        this.pegawai = pegawai;
    }

    public String getNamaUser() {
        return namaUser;
    }

    public void setNamaUser(String namaUser) {
        this.namaUser = namaUser;
    }

    public String getKataSandi() {
        return kataSandi;
    }

    public void setKataSandi(String kataSandi) {
        this.kataSandi = kataSandi;
    }

    public String getNamaPegawai() {
        return namaPegawai;
    }

    public void setNamaPegawai(String namaPegawai) {
        this.namaPegawai = namaPegawai;
    }

    public String getKdUser() {
        return kdUser;
    }

    public void setKdUser(String kdUser) {
        this.kdUser = kdUser;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
