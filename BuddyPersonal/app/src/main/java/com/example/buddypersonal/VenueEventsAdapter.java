package com.example.buddypersonal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class VenueEventsAdapter extends RecyclerView.Adapter<VenueEventsAdapter.ViewHolder> {

    private ArrayList<PublicEventModel> mPuEModel = new ArrayList<>();

    public VenueEventsAdapter(ArrayList<PublicEventModel> eventModel) {
        this.mPuEModel = eventModel;
    }

    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.public_events, viewGroup, false);
        return new ViewHolder(view);
    }

    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.startTime.setText(mPuEModel.get(i).getStartTime());
        viewHolder.endTime.setText(mPuEModel.get(i).getEndTime());
        viewHolder.title.setText(mPuEModel.get(i).getTitle());
        viewHolder.location.setText(mPuEModel.get(i).getLocation());
        viewHolder.description.setText(mPuEModel.get(i).getDescription());
    }

    @Override
    public int getItemCount() {
        return mPuEModel.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView startTime, endTime, title, location, description;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            startTime = itemView.findViewById(R.id.ven_start);
            endTime = itemView.findViewById(R.id.ven_end);
            title = itemView.findViewById(R.id.ven_title);
            location = itemView.findViewById(R.id.ven_loc);
            description = itemView.findViewById(R.id.ven_desc);
        }
    }
}
