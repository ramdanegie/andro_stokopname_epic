package com.inhuman.scanner.stokopname.Model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class StokOpnamePost {
    private  String ruanganId;
    private  String namaRuangan;
    private  String tglClosing;
    private List<StokOpnameDetail> stokProduk;

    public StokOpnamePost(String ruanganId, String namaRuangan, String tglClosing, List<StokOpnameDetail> stokProduk) {
        this.ruanganId = ruanganId;
        this.namaRuangan = namaRuangan;
        this.tglClosing = tglClosing;
        this.stokProduk = stokProduk;
    }

//    private class DetailsStok{
//        private  String produkfk;
//        private  Float stokSistem;
//        private  Float stokReal;
//        private  Float selisih;
//
//        public DetailsStok(String produkfk, Float stokSistem, Float stokReal, Float selisih) {
//            this.produkfk = produkfk;
//            this.stokSistem = stokSistem;
//            this.stokReal = stokReal;
//            this.selisih = selisih;
//        }
//    }
//    @SerializedName("ruanganId")
//    @Expose
//    private String ruanganId;
//
//    @SerializedName("namaRuangan")
//    @Expose
//    private String namaRuangan;
//
//    String now = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(new Date());
//    @SerializedName("tglClosing")
//    @Expose
//    private Date tglClosing;
//
//
//    @SerializedName("stokProduk")
//    @Expose
//    private List<DetailsStok> stokProduk ;
//    private class DetailsStok{
//        @SerializedName("produkfk")
//        @Expose
//        private String produkfk;
//
//        @SerializedName("stokSistem")
//        @Expose
//        private Float stokSistem;
//
//
//        @SerializedName("stokReal")
//        @Expose
//        private Float stokReal;
//
//        @SerializedName("selisih")
//        @Expose
//        private Float selisih;
//
//
//        public String getProdukfk() {
//            return produkfk;
//        }
//        public void setProdukfk(String produkfk) {
//            this.produkfk = produkfk;
//        }
//
//        public Float getStokSistem() {
//            return stokSistem;
//        }
//
//        public void setStokSistem(Float stokSistem) {
//            this.stokSistem = stokSistem;
//        }
//
//        public Float getStokReal() {
//            return stokReal;
//        }
//
//        public void setStokReal(Float stokReal) {
//            this.stokReal = stokReal;
//        }
//
//        public Float getSelisih() {
//            return selisih;
//        }
//        public void setSelisih(Float selisih) {
//            this.selisih = selisih;
//        }
//
//
//    }
//    public String getRuanganId() {
//        return ruanganId;
//    }
//
//    public void setRuanganId(String ruanganId) {
//        this.ruanganId = ruanganId;
//    }
//
//    public String getNamaRuangan() {
//        return namaRuangan;
//    }
//
//    public void setNamaRuangan(String namaRuangan) {
//        this.namaRuangan = namaRuangan;
//    }
//
//    public Date getTglClosing() {
//        return tglClosing;
//    }
//
//    public void setTglClosing(Date tglClosing) {
//        this.tglClosing = tglClosing;
//    }
//
//    public List<DetailsStok> getStokProduk() {
//        return stokProduk;
//    }
//
//    public void setStokProduk(List<DetailsStok> stokProduk) {
//        this.stokProduk = stokProduk;
//    }
//

}

