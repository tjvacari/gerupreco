package com.vacari.gerupreco.adapter.idea;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.vacari.gerupreco.R;
import com.vacari.gerupreco.activity.idea.IdeaActivity;
import com.vacari.gerupreco.model.firebase.Idea;

import java.util.List;

public class IdeaAdapter extends RecyclerView.Adapter {

    private List<Idea> ideaList;
    private IdeaActivity mActivity;

    public IdeaAdapter(List<Idea> ideaList, IdeaActivity mActivity) {
        this.ideaList = ideaList;
        this.mActivity = mActivity;
    }

    public void refresh(List<Idea> ideaList) {
        this.ideaList.clear();;
        this.ideaList.addAll(ideaList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mActivity)
                .inflate(R.layout.idea_listview, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ViewHolder holder = (ViewHolder) viewHolder;

        Idea idea = ideaList.get(i);

        holder.description.setText(idea.getDescription());
        holder.user.setText(idea.getUser().getName());
    }

    @Override
    public int getItemCount() {
        return ideaList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        final MaterialCardView card;
        final TextView user;
        final TextView description;

        public ViewHolder(View view) {
            super(view);

            card = view.findViewById(R.id.item_card);
            description = view.findViewById(R.id.idea_description);
            user = view.findViewById(R.id.user_idea);
        }
    }
}