package com;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.inhuman.scanner.stokopname.Model.StokOpnameDetail;
import com.inhuman.scanner.stokopname.Model.StokOpnamePost;
import com.inhuman.scanner.stokopname.Model.StokProduk;
import com.inhuman.scanner.stokopname.R;
import com.inhuman.scanner.stokopname.ScannerActivity;
import com.inhuman.scanner.stokopname.ServiceApi;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class StokProdukAdapter extends RecyclerView.Adapter<StokProdukAdapter.StokProdukHolder> {

    private List<StokProduk> stoks = new ArrayList<>();

    private Context context;
    private List<StokProduk> list;

    AlertDialog myDialog;
//    DialogEditStok dialogEditStoks;

    TextInputLayout etKodeProduk;
    TextInputLayout etNamaProduk;
    TextInputLayout etJumlah;
    TextInputLayout etStokSistem;
    TextInputLayout etIdRuangan;
    public Boolean postSukses;

    public StokProdukAdapter(List<StokProduk> data) {
        this.list = data;
    }

    @NonNull
    @Override
    public StokProdukHolder onCreateViewHolder(@NonNull final ViewGroup viewGroup, int i) {
        View itemView ;
        itemView  = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.stokproduk_item,viewGroup,false);
        final StokProdukHolder vHolder = new StokProdukHolder(itemView);

        //alert
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(viewGroup.getContext());
        View viewDialog =  LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_edit_stok,viewGroup,false);
        alertDialog.setView(viewDialog)
            .setTitle("Edit Stok Produk")
            .setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
//                    Objects.requireNonNull(etKodeProduk.getEditText()).setText("");
//                    Objects.requireNonNull(etNamaProduk.getEditText()).setText("");
//                    Objects.requireNonNull(etJumlah.getEditText()).setText(0);
//                    myDialog.dismiss();
                }
            })
            .setPositiveButton("Simpan", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
                        float stokSistem = Float.valueOf(Objects.requireNonNull(etStokSistem.getEditText().getText()).toString());
                        float stokReal = Float.valueOf(Objects.requireNonNull(etJumlah.getEditText().getText()).toString());
                        float selisih = stokReal - stokSistem;

                      List<StokOpnameDetail> so_detail = new ArrayList<>();
                      so_detail.add(new StokOpnameDetail(  etKodeProduk.getEditText().getText().toString(),
                            stokSistem,
                            stokReal,
                            selisih
                            ));

                        StokOpnamePost so_data = new StokOpnamePost(
                                ScannerActivity.textViewIdRuangan.getText().toString(),
                                "",
                                now,
                                so_detail

                        );
                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl(ServiceApi.baseUrlApi )
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();


                        Log.d("json",so_data.toString() );
                        ServiceApi serviceApi = retrofit.create(ServiceApi.class);
                        Call<StokOpnamePost> call = serviceApi.postStokOpname(so_data);

                        call.enqueue(new Callback<StokOpnamePost>() {
                            @Override
                            public void onResponse(Call<StokOpnamePost> call, Response<StokOpnamePost> response) {
                                if(!response.isSuccessful()){
                                    Toast.makeText(viewGroup.getContext(), "Simpan Gagal", Toast.LENGTH_LONG).show();
                                    return;
                                }
                                postSukses = true;
                                ((ScannerActivity)viewGroup.getContext()).loadListRecycle(ScannerActivity.resultTextView.getText().toString());
                                Toast.makeText(viewGroup.getContext(), "Simpan Sukses", Toast.LENGTH_LONG).show();

                            }

                            @Override
                            public void onFailure(Call<StokOpnamePost> call, Throwable t) {
                                Toast.makeText(viewGroup.getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                                postSukses = false;
                            }
                        });

                    }
            });
        etKodeProduk =  viewDialog.findViewById(R.id.etKodeProduk);
        etNamaProduk =  viewDialog.findViewById(R.id.etNamaProduk);
        etJumlah =  viewDialog.findViewById(R.id.etJumlah);
        etStokSistem = viewDialog.findViewById(R.id.etStokSistem);
        myDialog = alertDialog.create();
        //end alert

        return vHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull StokProdukHolder stokProdukHolder, int i) {
        final   StokProduk stok = list.get(i);

        stokProdukHolder.textViewNamaProduk.setText(stok.getNamaProduk());
        stokProdukHolder.textViewKode.setText(stok.getKodeProduk());
        stokProdukHolder.textViewStok.setText(String.valueOf(stok.getQtyProduk()));
        stokProdukHolder.textViewBarcode.setText(stok.getKdBarcode());
        stokProdukHolder.popUp_stok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Objects.requireNonNull(etKodeProduk.getEditText()).setText(stok.getKodeProduk());
                Objects.requireNonNull(etNamaProduk.getEditText()).setText(stok.getNamaProduk());
                etJumlah.getEditText().setText(Integer.toString(stok.getQtyProduk()));
                etStokSistem.getEditText().setText(Integer.toString(stok.getQtyProduk()));
                etJumlah.requestFocus();
                myDialog.show();

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class StokProdukHolder extends RecyclerView.ViewHolder {
        private TextView textViewNamaProduk;
        private TextView textViewStok;
        private TextView textViewKode;
        private TextView textViewBarcode;

        private CardView popUp_stok;
//        private ImageButton popUpCloseBtn;

        public StokProdukHolder(View itemView) {
            super(itemView);
            popUp_stok = itemView.findViewById(R.id.popUp_stok);
//            popUpCloseBtn = itemView.findViewById(R.id.ib_close);
            textViewNamaProduk = itemView.findViewById(R.id.text_view_namaProduk);
            textViewStok = itemView.findViewById(R.id.text_view_qtyReal);
            textViewKode = itemView.findViewById(R.id.text_view_kodeProduk);
            textViewBarcode = itemView.findViewById(R.id.text_view_barcode);

        }

    }
}
