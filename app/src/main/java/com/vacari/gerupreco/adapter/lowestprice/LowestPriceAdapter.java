package com.vacari.gerupreco.adapter.lowestprice;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vacari.gerupreco.R;
import com.vacari.gerupreco.activity.lowestprice.LowestPriceActivity;
import com.vacari.gerupreco.model.notaparana.Company;
import com.vacari.gerupreco.model.notaparana.Product;
import com.vacari.gerupreco.util.DateUtil;
import com.vacari.gerupreco.util.StringUtil;

import java.util.List;

public class LowestPriceAdapter extends RecyclerView.Adapter {

    private List<Product> itemList;
    private LowestPriceActivity mActivity;

    public LowestPriceAdapter(List<Product> itemList, LowestPriceActivity mActivity) {
        this.itemList = itemList;
        this.mActivity = mActivity;
    }

    public void refresh(List<Product> itemList) {
        this.itemList.clear();;
        this.itemList.addAll(itemList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mActivity)
                .inflate(R.layout.lowest_price_listview, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ViewHolder holder = (ViewHolder) viewHolder;

        Product item = itemList.get(i);
        Company company = item.getEstabelecimento();

        holder.description.setText(item.getDesc());
        holder.price.setText("R$ " + item.getValor());
        holder.date.setText(DateUtil.format.format(item.getDatahora()));
        holder.time.setText(item.getTempo());
        holder.company.setText(StringUtil.or(company.getNm_fan(), company.getNm_emp()));
        holder.address.setText(company.getFullAddress());
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        final TextView description;
        final TextView price;
        final TextView date;
        final TextView time;
        final TextView company;
        final TextView address;

        public ViewHolder(View view) {
            super(view);
            description = view.findViewById(R.id.lp_description);
            price = view.findViewById(R.id.lp_price);
            date = view.findViewById(R.id.lp_date);
            time = view.findViewById(R.id.lp_time);
            company = view.findViewById(R.id.lp_company);
            address = view.findViewById(R.id.lp_address);
        }
    }
}