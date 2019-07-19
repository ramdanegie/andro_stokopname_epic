package com.inhuman.scanner.stokopname.Response;

import com.inhuman.scanner.stokopname.Model.NoSo;
import com.inhuman.scanner.stokopname.Model.StokProduk;

public class JsonResponse {
    NoSo noSO;

    public NoSo getNoSo() {
        return noSO;
    }

    public void setNoSo(NoSo noSO) {
        this.noSO = noSO;
    }

    public JsonResponse(NoSo noSO) {
        this.noSO = noSO;
    }
}
