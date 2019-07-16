package com.inhuman.scanner.stokopname.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import com.inhuman.scanner.stokopname.CameraActivity;
import com.inhuman.scanner.stokopname.EditProdukActivity;
import com.inhuman.scanner.stokopname.Model.Produk;
import com.inhuman.scanner.stokopname.Model.StokOpnameDetail;
import com.inhuman.scanner.stokopname.Model.StokOpnamePost;
import com.inhuman.scanner.stokopname.R;
import com.inhuman.scanner.stokopname.ScannerActivity;
import com.inhuman.scanner.stokopname.ServiceApi;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProdukAdapter extends RecyclerView.Adapter<ProdukAdapter.ProdukViewHodler> implements Filterable {
    private Context mContext;
    private static List<Produk> listProduk;
    private List<Produk> listProdukFull;
    private AdapterView.OnItemClickListener mListener;
    private static List<Produk> produks = new ArrayList<>();
    private static OnItemClickListener listener;
    AlertDialog alertDialog;

    public ProdukAdapter(List<Produk> listProduk) {
        this.listProduk = listProduk;
        listProdukFull = new ArrayList<>(listProduk);
    }

    public static class ProdukViewHodler extends RecyclerView.ViewHolder {
        private TextView textViewNamaProduk;
        private TextView textViewKode;
        private TextView textViewBarcode;
        private TextView textViewSatuan;
        private TextView textViewDetailJenis;
        private CardView popUpMasterProduk;

        public ProdukViewHodler(@NonNull View itemView) {
            super(itemView);

            popUpMasterProduk = itemView.findViewById(R.id.masterProdukPopUp);
            textViewNamaProduk = itemView.findViewById(R.id.tv_mpNamaProduk);
            textViewKode = itemView.findViewById(R.id.tv_mpKodeProduk);
            textViewSatuan = itemView.findViewById(R.id.tv_mpSatuan);
            textViewDetailJenis = itemView.findViewById(R.id.tv_mpDetailJenis);
            textViewBarcode = itemView.findViewById(R.id.tv_mpBarcode);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();

                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(listProduk.get(position));
                    }

                }
            });
        }
    }

    @NonNull
    @Override
    public ProdukViewHodler onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView;
        itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_master_produk, viewGroup, false);
        final ProdukViewHodler vHolder = new ProdukViewHodler(itemView);

        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProdukViewHodler produkViewHodler, int i) {
        final Produk produk = listProduk.get(i);

        produkViewHodler.textViewNamaProduk.setText(produk.getNamaProduk());
        produkViewHodler.textViewKode.setText(produk.getKodeProduk());
        produkViewHodler.textViewBarcode.setText(produk.getKdBarcode());
        produkViewHodler.textViewDetailJenis.setText(produk.getDetailJenisProduk());
        produkViewHodler.textViewSatuan.setText(produk.getSatuanStandar());
//        produkViewHodler.popUpMasterProduk.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent( mContext, EditProdukActivity.class);
//                intent.putExtra("kodeProduk",produk.getKodeProduk());
//                intent.putExtra("namaProduk",produk.getNamaProduk());
//                intent.putExtra("kdBarcode",produk.getKdBarcode());
//                intent.putExtra("satuanStandar",produk.getSatuanStandar());
//                intent.putExtra("detailJenis",produk.getDetailJenisProduk());
//                mContext.startActivity(intent);
//                Objects.requireNonNull(etKodeProduk.getEditText()).setText(stok.getKodeProduk());
//                Objects.requireNonNull(etNamaProduk.getEditText()).setText(stok.getNamaProduk());
//                etJumlah.getEditText().setText(Integer.toString(stok.getQtyProduk()));
//                etStokSistem.getEditText().setText(Integer.toString(stok.getQtyProduk()));
//                etJumlah.requestFocus();
//                myDialog.show();

//            }
//        });
    }

    @Override
    public int getItemCount() {
        return listProduk.size();
    }
    public Produk getNoteAt(int position) {
        return produks.get(position);
    }
    public void setNotes(List<Produk> produks) {
        this.produks = produks;
        notifyDataSetChanged();
    }

    public Filter getFilter() {
        return filterNamaProduk;
    }

    private Filter filterNamaProduk = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Produk> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(listProdukFull);
            } else {
                String filterProduk = constraint.toString().toLowerCase().trim();

                for (Produk item : listProdukFull) {
                    if (item.getNamaProduk().toLowerCase().contains(filterProduk)) {
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            listProduk.clear();
            listProduk.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    public interface OnItemClickListener {
        void onItemClick(Produk produk);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
