package com.example.paperchase;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerViewHolder> {
    private ArrayList<RecyclerItem> mRecyclerList;

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder {
        public TextView recyclerTextView;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            recyclerTextView = itemView.findViewById(R.id.textView);
        }
    }

    public RecyclerAdapter(ArrayList<RecyclerItem> recyclerList) {
        mRecyclerList = recyclerList;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false);
        RecyclerViewHolder rvh = new RecyclerViewHolder(v);
        return rvh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        RecyclerItem currentItem = mRecyclerList.get(position);
        holder.recyclerTextView.setText(currentItem.getItem());
    }

    @Override
    public int getItemCount() {
        return mRecyclerList.size();
    }
}
