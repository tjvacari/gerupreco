package com.vacari.gerupreco.adapter;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.vacari.gerupreco.R;
import com.vacari.gerupreco.activity.MainActivity;
import com.vacari.gerupreco.model.Item;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter implements View.OnCreateContextMenuListener {

    private List<Item> itemList;
    private MainActivity mActivity;

    public ItemAdapter(List<Item> itemList, MainActivity mActivity) {
        this.itemList = itemList;
        this.mActivity = mActivity;
    }

    public void refresh(List<Item> itemList) {
        this.itemList.clear();;
        this.itemList.addAll(itemList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mActivity)
                .inflate(R.layout.item_listview, viewGroup, false);
        view.setOnCreateContextMenuListener(this);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ViewHolder holder = (ViewHolder) viewHolder;

        Item item = itemList.get(i);

        configureActions(holder, item);

        holder.description.setText(item.getDescription());
        holder.size.setText(item.getSize());
        holder.unitMeasure.setText(item.getUnitMeasure());
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuInflater inflater = new MenuInflater(v.getContext());
        inflater.inflate(R.menu.context_menu, menu);
    }

    private void configureActions(ViewHolder holder, Item item) {
        holder.card.setOnClickListener(view -> {
            mActivity.openLowestPrice(item.getBarCode());
        });

//        holder.card.setOnLongClickListener(view -> {
//            mActivity.deleteItem(item);
//            return false;
//        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public boolean existProduct(String barCode) {
        for(Item item : itemList) {
            if(item.getBarCode().equals(barCode)) {
                return true;
            }
        }
        return false;
    }

    public Item getItemByPosition(int position) {
        return itemList.get(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        final MaterialCardView card;
        final TextView description;
        final TextView size;
        final TextView unitMeasure;

        public ViewHolder(View view) {
            super(view);
            card = view.findViewById(R.id.item_card);
            description = view.findViewById(R.id.item_description);
            size = view.findViewById(R.id.item_size);
            unitMeasure = view.findViewById(R.id.item_unitMeasure);
        }
    }
}