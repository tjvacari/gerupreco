package com.vacari.gerupreco;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vacari.gerupreco.to.ItemTO;
import com.vacari.gerupreco.to.Produto;

import java.text.SimpleDateFormat;
import java.util.List;

public class MenorPrecoAdapter extends RecyclerView.Adapter {

    private List<Produto> itemList;
    private MenorPrecoActivity mActivity;

    public MenorPrecoAdapter(List<Produto> itemList, MenorPrecoActivity mActivity) {
        this.itemList = itemList;
        this.mActivity = mActivity;
    }

    public void update(List<Produto> itemList) {
        this.itemList = itemList;
        notifyDataSetChanged();
    }

    public void refresh(List<Produto> itemList) {
        this.itemList.clear();;
        this.itemList.addAll(itemList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mActivity)
                .inflate(R.layout.menor_preco_listview, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ViewHolder holder = (ViewHolder) viewHolder;

        configureActions(holder);

        Produto item = itemList.get(i);

        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        holder.descricao.setText(item.getDesc());
        holder.preco.setText(item.getValor());
        holder.data.setText(format.format(item.getDatahora()));
        holder.local.setText(item.getEstabelecimento().getNm_fan());
    }

    private void configureActions(ViewHolder holder) {
//        holder.imagem.setOnClickListener((View v) -> {
//            new ImageDialog(mActivity, (ImageView) v).show();
//        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        final TextView descricao;
        final TextView preco;
        final TextView data;
        final TextView local;

        public ViewHolder(View view) {
            super(view);
            descricao = (TextView) view.findViewById(R.id.descricao);
            preco = (TextView) view.findViewById(R.id.preco);
            data = (TextView) view.findViewById(R.id.data);
            local = (TextView) view.findViewById(R.id.local);
        }
    }
}