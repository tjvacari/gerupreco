package com.vacari.gerupreco.activity.lowestprice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.vacari.gerupreco.R;
import com.vacari.gerupreco.adapter.lowestprice.NotificationAdapter;
import com.vacari.gerupreco.model.sqlite.Notification;
import com.vacari.gerupreco.repository.NotificationRepository;

import java.util.ArrayList;

public class NotificationActivity extends AppCompatActivity {

    private NotificationAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.notifications);
        setContentView(R.layout.activity_notification);

        initGUI();
        search();
    }

    private void initGUI() {
        RecyclerView mRecyclerView = findViewById(R.id.recycler_notification_id);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mAdapter = new NotificationAdapter(new ArrayList<>(), this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    private void search() {
        mAdapter.refresh(NotificationRepository.searchAllNotifications(this));
    }

    public void updateNotification(Notification notification) {
        NotificationRepository.saveNotification(this, notification);
    }
}