package com.inhuman.scanner.stokopname.Model;

public class StokOpnameDetail {
        private  String produkfk;
        private  float stokSistem;
        private  float stokReal;
        private  float selisih;

        public StokOpnameDetail(String produkfk, float stokSistem, float stokReal, float selisih) {
            this.produkfk = produkfk;
            this.stokSistem = stokSistem;
            this.stokReal = stokReal;
            this.selisih = selisih;
        }
    }

