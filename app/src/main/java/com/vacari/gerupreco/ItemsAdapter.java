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

import java.util.List;

public class ItemsAdapter extends RecyclerView.Adapter {

    private List<ItemTO> itemList;
    private MainActivity mActivity;

    public ItemsAdapter(List<ItemTO> itemList, MainActivity mActivity) {
        this.itemList = itemList;
        this.mActivity = mActivity;
    }

    public void update(List<ItemTO> itemList) {
        this.itemList = itemList;
        notifyDataSetChanged();
    }

    public void refresh(List<ItemTO> itemList) {
        this.itemList.clear();;
        this.itemList.addAll(itemList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mActivity)
                .inflate(R.layout.item_listview, viewGroup, false);
        view.setOnClickListener(v -> {
            RecyclerView mRecyclerView = mActivity.findViewById(R.id.recycler_id);
            int itemPosition = mRecyclerView.getChildLayoutPosition(view);
            ItemTO item = itemList.get(itemPosition);

            Intent intent = new Intent(mActivity, MenorPrecoActivity.class);
            intent.putExtra("BARCODE", item.getCodigoBarras());
            mActivity.startActivity(intent);
        });
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ViewHolder holder = (ViewHolder) viewHolder;

        configureActions(holder);

        ItemTO item = itemList.get(i);

        holder.descricao.setText(item.getDescricao());
        holder.tamanho.setText(item.getTamanho());
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
        final TextView tamanho;

        public ViewHolder(View view) {
            super(view);
            descricao = (TextView) view.findViewById(R.id.item_descricao);
            tamanho = (TextView) view.findViewById(R.id.item_tamanho);
        }
    }
}