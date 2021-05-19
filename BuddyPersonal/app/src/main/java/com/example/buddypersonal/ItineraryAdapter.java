package com.example.buddypersonal;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ItineraryAdapter extends RecyclerView.Adapter<ItineraryHolder> {

    Context c;
    ArrayList<Model> models;

    public ItineraryAdapter(Context c, ArrayList<Model> models) {
        this.c = c;
        this.models = models;
    }

    @NonNull
    @Override
    public ItineraryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.private_events, null);
        return new ItineraryHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ItineraryHolder holder, int position) {
        holder.mStart.setText(models.get(position).getStartTime());
        holder.mEnd.setText(models.get(position).getEndTime());
        holder.mTitle.setText(models.get(position).getTitle());
        holder.mLoc.setText(models.get(position).getLoc());
        holder.mNotes.setText(models.get(position).getNotes());

//        holder.setItemClickListener(new ItemClickListener() {
//            @Override
//            public void inItemClickListener(View v, int position) {
//                Intent intent = new Intent(c, ViewEvent.class);
//                intent.putExtra("iTitle", models.get(position).getTitle());
//                intent.putExtra("iStartTime", models.get(position).getStartTime());
//                intent.putExtra("iStartDate", models.get(position).getStartDate());
//                intent.putExtra("iEndTime", models.get(position).getEndTime());
//                intent.putExtra("iEndDate", models.get(position).getEndDate());
//                intent.putExtra("iLocation", models.get(position).getLoc());
//                intent.putExtra("iNotes", models.get(position).getNotes());
//                c.startActivity(intent);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return models.size();
    }
}
