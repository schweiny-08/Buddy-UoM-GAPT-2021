package com.example.buddypersonal;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class VenueEventsHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    TextView mStart, mEnd, mTitle, mDesc, mLoc;
    ItemClickListener itemClickListener;

    VenueEventsHolder(@NonNull View itemView) {
        super(itemView);

        this.mStart = itemView.findViewById(R.id.ven_start);
        this.mEnd = itemView.findViewById(R.id.ven_end);
        this.mTitle = itemView.findViewById(R.id.ven_title);
        this.mLoc = itemView.findViewById(R.id.ven_loc);
        this.mDesc = itemView.findViewById(R.id.ven_desc);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        this.itemClickListener.inItemClickListener(v, getLayoutPosition());
    }

    public void setItemClickListener(ItemClickListener ic){
        this.itemClickListener = ic;
    }
}
