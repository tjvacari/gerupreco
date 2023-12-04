package com.vacari.gerupreco.activity.idea;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.vacari.gerupreco.R;
import com.vacari.gerupreco.activity.lowestprice.LowestPriceActivity;
import com.vacari.gerupreco.adapter.idea.IdeaAdapter;
import com.vacari.gerupreco.adapter.lowestprice.ItemAdapter;
import com.vacari.gerupreco.repository.IdeaRepository;
import com.vacari.gerupreco.repository.ItemRepository;

import java.util.ArrayList;

public class IdeaActivity extends AppCompatActivity {

    private IdeaAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_idea);
        initGui();
        searchIdeas();
    }

    private void initGui() {
        RecyclerView mRecyclerView = findViewById(R.id.idea_recycler);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mAdapter = new IdeaAdapter(new ArrayList<>(), this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_idea, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.menu_add_idea) {
            openRegisterIdea();
            return true;
        }

        return false;
    }

    private void openRegisterIdea() {
        Intent intent = new Intent(this, RegisterIdeaActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        searchIdeas();
    }

    private void searchIdeas() {
        IdeaRepository.searchIdea(data -> {
            mAdapter.refresh(data);
            SwipeRefreshLayout swipe = findViewById(R.id.swipe_idea);
            swipe.setRefreshing(false);
        });
    }
}