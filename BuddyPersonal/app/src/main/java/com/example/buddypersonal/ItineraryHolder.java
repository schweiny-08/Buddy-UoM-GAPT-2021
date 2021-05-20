package com.example.buddypersonal;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ItineraryHolder extends RecyclerView.ViewHolder /*implements View.OnClickListener*/ {

    TextView mStart, mEnd, mTitle, mLoc, mNotes;
//    ItemClickListener itemClickListener;

    ItineraryHolder(@NonNull View itemView) {
        super(itemView);

        //row.xml views
        this.mStart = itemView.findViewById(R.id.itn_start);
        this.mEnd = itemView.findViewById(R.id.itn_end);
        this.mTitle = itemView.findViewById(R.id.itn_title);
//        this.mLoc = itemView.findViewById(R.id.itn_location);
//        this.mNotes = itemView.findViewById(R.id.itn_notes);

//        itemView.setOnClickListener(this);
    }

//    @Override
//    public void onClick(View v) {
//        this.itemClickListener.inItemClickListener(v, getLayoutPosition());
//    }

//    public void setItemClickListener(ItemClickListener ic){
//        this.itemClickListener = ic;
//    }
}
