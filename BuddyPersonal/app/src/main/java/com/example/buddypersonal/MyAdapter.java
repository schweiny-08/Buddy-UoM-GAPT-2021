package com.example.buddypersonal;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyHolder> {

    Context c;
    ArrayList<Model> models;

    public MyAdapter(Context c, ArrayList<Model> models) {
        this.c = c;
        this.models = models;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, null);
        return new MyHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        holder.mStart.setText(models.get(position).getStartTime());
        holder.mEnd.setText(models.get(position).getEndTime());
        holder.mTitle.setText(models.get(position).getTitle());

//        holder.setItemClickListener(new ItemClickListener() {
//            @Override
//            public void inItemClickListener(View v, int position) {
//                String gTitle = models.get(position).getTitle();
//                String gType = models.get(position).getType();
//                String gStartTime = models.get(position).getStartTime();
//                String gEndTime = models.get(position).getEndTime();

//                Intent intent = new Intent(c, EventDetails.class);
//                intent.putExtra("iTitle", gTitle);
//                intent.putExtra("iType", gType);
//                intent.putExtra("iStartTime", gStartTime);
//                intent.putExtra("iEndTime", gEndTime);
//                c.startActivity(intent);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return models.size();
    }
}