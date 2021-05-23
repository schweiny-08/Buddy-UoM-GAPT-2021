package com.example.buddypersonal;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ItineraryAdapter extends RecyclerView.Adapter<ItineraryAdapter.ViewHolder> {

    private ArrayList<EventModel> mEventModel = new ArrayList<>();
    private EventClickListener mEventClickListener;

    public ItineraryAdapter(ArrayList<EventModel> eventModel, EventClickListener eventClickListener) {
        this.mEventModel = eventModel;
        this.mEventClickListener = eventClickListener;
    }

    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.private_events, viewGroup, false);
        return new ViewHolder(view, mEventClickListener);
    }

    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.startTime.setText(mEventModel.get(i).getStartTime());
        viewHolder.endTime.setText(mEventModel.get(i).getEndTime());
        viewHolder.title.setText(mEventModel.get(i).getTitle());
    }
    public int getItemCount() {
        return mEventModel.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView startTime, endTime, title;
        EventClickListener mEventClickListener;

        public ViewHolder(@NonNull View itemView, EventClickListener eventClickListener) {
            super(itemView);
            startTime = itemView.findViewById(R.id.itn_start);
            endTime = itemView.findViewById(R.id.itn_end);
            title = itemView.findViewById(R.id.itn_title);
            mEventClickListener = eventClickListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mEventClickListener.onEventClick(getAdapterPosition());
        }
    }

    public interface EventClickListener {
        void onEventClick(int position);
    }
}
