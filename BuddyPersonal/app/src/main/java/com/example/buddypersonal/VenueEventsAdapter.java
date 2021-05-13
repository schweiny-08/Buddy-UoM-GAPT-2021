package com.example.buddypersonal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class VenueEventsAdapter extends RecyclerView.Adapter<VenueEventsHolder> {
    Context c;
    ArrayList<Model> models;

    public VenueEventsAdapter(Context c, ArrayList<Model> models) {
        this.c = c;
        this.models = models;
    }

    @NonNull
    @Override
    public VenueEventsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.public_events, null);
        return new VenueEventsHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull VenueEventsHolder holder, int position) {
        holder.mStart.setText(models.get(position).getStartTime());
        holder.mEnd.setText(models.get(position).getEndTime());
        holder.mTitle.setText(models.get(position).getTitle());
        holder.mLoc.setText(models.get(position).getLoc());
        holder.mDesc.setText(models.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        return models.size();
    }
}
