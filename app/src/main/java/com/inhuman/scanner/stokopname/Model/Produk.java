package com.inhuman.scanner.stokopname.Model;

public class Produk {
    private String kodeProduk;
    private String namaProduk;
    private String satuanStandar;
    private String kdBarcode;
    private String detailJenisProduk;

    public Produk(String kodeProduk, String namaProduk, String satuanStandar, String kdBarcode, String detailJenisProduk) {
        this.kodeProduk = kodeProduk;
        this.namaProduk = namaProduk;
        this.satuanStandar = satuanStandar;
        this.kdBarcode = kdBarcode;
        this.detailJenisProduk = detailJenisProduk;
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

    public String getSatuanStandar() {
        return satuanStandar;
    }

    public void setSatuanStandar(String satuanStandar) {
        this.satuanStandar = satuanStandar;
    }

    public String getKdBarcode() {
        return kdBarcode;
    }

    public void setKdBarcode(String kdBarcode) {
        this.kdBarcode = kdBarcode;
    }

    public String getDetailJenisProduk() {
        return detailJenisProduk;
    }

    public void setDetailJenisProduk(String detailJenisProduk) {
        this.detailJenisProduk = detailJenisProduk;
    }
}
