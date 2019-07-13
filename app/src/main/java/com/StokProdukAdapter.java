package com;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Movie;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.inhuman.scanner.dashboad.Model.StokProduk;
import com.inhuman.scanner.dashboad.R;
import com.inhuman.scanner.dashboad.ScannerActivity;

import java.util.ArrayList;
import java.util.List;
//
//public class StokProdukAdapter extends RecyclerView.Adapter<StokProdukAdapter.ViewHolder> {
//
//
//    private Context context;
//
//    private List<StokProduk> list;
//
//
//    public StokProdukAdapter(Context context, List<StokProduk> list) {
//
//        this.context = context;
//
//        this.list = list;
//    }
//
//    @NonNull
//    @Override
//    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View v = LayoutInflater.
//                from(context).inflate(R.layout.stokproduk_item, parent, false);
//
//        return new ViewHolder(v);
//    }
//
//    @Override
//
//    public void onBindViewHolder(ViewHolder holder, int position) {
//        StokProduk stoks =
//                list.get(position);
//        holder.textViewNamaProduk.setText(stoks.getNamaProduk());
//        holder.textViewKode.setText(stoks.getKodeProduk());
//        holder.textViewStok.setText(String.valueOf(stoks.getQtyProduk()));
//        holder.textViewSelisih.setText(String.valueOf(stoks.getSelisih()));
//
//
//    }
//
//    @Override
//    public int getItemCount() {
//
//        return list.size();
//    }
//
//
//    public class ViewHolder extends RecyclerView.ViewHolder {
//
//        private TextView textViewNamaProduk;
//        private TextView textViewStok;
//        private TextView textViewKode;
//        private TextView textViewSelisih;
//
//
//        public ViewHolder(View itemView) {
//
//            super(itemView);
//
//
//            textViewNamaProduk = itemView.findViewById(R.id.text_view_namaProduk);
//            textViewStok = itemView.findViewById(R.id.text_view_kodeProduk);
//            textViewKode = itemView.findViewById(R.id.text_view_qtyReal);
//            textViewSelisih = itemView.findViewById(R.id.text_view_selisih);
//
//        }
//    }
//
//}
public class StokProdukAdapter extends RecyclerView.Adapter<StokProdukAdapter.StokProdukHolder> {

    private List<StokProduk> stoks = new ArrayList<>();

    private Context context;
    private List<StokProduk> list;

    Dialog myDialog;
    public StokProdukAdapter(List<StokProduk> data) {
        this.list = data;
    }


    @NonNull
    @Override
    public StokProdukHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView ;
        itemView  = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.stokproduk_item,viewGroup,false);
        final StokProdukHolder vHolder = new StokProdukHolder(itemView);

        myDialog = new Dialog(viewGroup.getContext());
        myDialog.setContentView(R.layout.dialog_stok_edit);


        return vHolder;
//            return  new StokProdukHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull StokProdukHolder stokProdukHolder, int i) {
        final   StokProduk stok = list.get(i);

        stokProdukHolder.textViewNamaProduk.setText(stok.getNamaProduk());
        stokProdukHolder.textViewKode.setText(stok.getKodeProduk());
        stokProdukHolder.textViewStok.setText(String.valueOf(stok.getQtyProduk()));
        stokProdukHolder.textViewSelisih.setText(String.valueOf(stok.getSelisih()));
        stokProdukHolder.popUp_stok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextInputLayout etKodeProduk = (TextInputLayout) myDialog.findViewById(R.id.etKodeProduk);
                TextInputLayout etNamaProduk = (TextInputLayout) myDialog.findViewById(R.id.etNamaProduk);
                TextInputLayout etJumlah = (TextInputLayout) myDialog.findViewById(R.id.etJumlah);
                etKodeProduk.getEditText().setText(stok.getKodeProduk());
                etNamaProduk.getEditText().setText(stok.getNamaProduk());
                etJumlah.getEditText().setText(Integer.toString(stok.getQtyProduk()));

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
        private TextView textViewSelisih;

        private CardView popUp_stok;
//        private ImageButton popUpCloseBtn;

        public StokProdukHolder(View itemView) {
            super(itemView);
            popUp_stok = itemView.findViewById(R.id.popUp_stok);
//            popUpCloseBtn = itemView.findViewById(R.id.ib_close);
            textViewNamaProduk = itemView.findViewById(R.id.text_view_namaProduk);
            textViewStok = itemView.findViewById(R.id.text_view_qtyReal);
            textViewKode = itemView.findViewById(R.id.text_view_kodeProduk);
            textViewSelisih = itemView.findViewById(R.id.text_view_selisih);

        }

    }
}
