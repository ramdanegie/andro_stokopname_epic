package com.inhuman.scanner.dashboad.Model;

public class StokProduk {

    private String kodeProduk;
    private String namaProduk;
    private int qtyProduk;
//    private String satuanStandar;
    private String kdBarcode;
    private int selisih;
    public StokProduk() {

    }


    public StokProduk(String kodeProduk , String namaProduk, int qtyProduk, int selisih) {

        this.kodeProduk = kodeProduk;
        this.namaProduk = namaProduk;
        this.qtyProduk = qtyProduk;
        this.selisih = selisih;
    }


    public String getKodeProduk() {

        return kodeProduk;
    }


    public void setKodeProduk(String kodeProduk) {

        this.kodeProduk = kodeProduk;
    }


    public String getNamaProduk() {

        return namaProduk;
    }


    public void setNamaProduk(String namaProduk) {

        this.namaProduk = namaProduk;
    }


    public int getQtyProduk() {

        return qtyProduk;
    }


    public void setQtyProduk(int qtyProduk) {

        this.qtyProduk = qtyProduk;
    }

    public int getSelisih() {

        return selisih;
    }


    public void setSelisih(int selisih) {

        this.selisih = selisih;
    }

}

