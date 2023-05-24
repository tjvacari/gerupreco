package com.vacari.gerupreco.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vacari.gerupreco.R;
import com.vacari.gerupreco.activity.LowestPriceActivity;
import com.vacari.gerupreco.activity.NotificationActivity;
import com.vacari.gerupreco.model.notaparana.Company;
import com.vacari.gerupreco.model.notaparana.Product;
import com.vacari.gerupreco.model.sqlite.Notification;
import com.vacari.gerupreco.util.DateUtil;
import com.vacari.gerupreco.util.StringUtil;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter {

    private List<Notification> itemList;
    private NotificationActivity mActivity;

    public NotificationAdapter(List<Notification> itemList, NotificationActivity mActivity) {
        this.itemList = itemList;
        this.mActivity = mActivity;
    }

    public void refresh(List<Notification> itemList) {
        this.itemList.clear();;
        this.itemList.addAll(itemList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mActivity)
                .inflate(R.layout.notification_listview, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ViewHolder holder = (ViewHolder) viewHolder;

        Notification notification = itemList.get(i);

        holder.description.setText(notification.getDescription());
        holder.price.setText("R$ " + notification.getTargetPrice());
        holder.active.setChecked(notification.isActive());
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public Notification getItemByPosition(int position) {
        return itemList.get(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        final TextView description;
        final TextView price;
        final Switch active;

        public ViewHolder(View view) {
            super(view);
            description = view.findViewById(R.id.nt_description);
            active = view.findViewById(R.id.nt_active);
            price = view.findViewById(R.id.nt_price);

            configureActions();
        }

        private void configureActions() {
            active.setOnCheckedChangeListener((compoundButton, checked) -> {
                if(compoundButton.isPressed()) {
                    Notification notification = getItemByPosition(getAdapterPosition());
                    notification.setActive(checked);
                    mActivity.updateNotification(notification);
                }
            });
        }
    }
}