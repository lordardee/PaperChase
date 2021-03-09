package com.example.paperchase;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerViewHolder> {
    private final ArrayList<RecyclerItem> mRecyclerList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder {
        public TextView recyclerTextView;
        public ImageView mImageView;

        public RecyclerViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            recyclerTextView = itemView.findViewById(R.id.textView);
            mImageView = itemView.findViewById(R.id.imageView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }

    public RecyclerAdapter(ArrayList<RecyclerItem> recyclerList) {
        mRecyclerList = recyclerList;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false);
        RecyclerViewHolder rvh = new RecyclerViewHolder(v, mListener);
        return rvh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        RecyclerItem currentItem = mRecyclerList.get(position);
        holder.mImageView.setImageResource(currentItem.getImageResource());
        holder.recyclerTextView.setText(currentItem.getItem());
    }

    @Override
    public int getItemCount() {
        return mRecyclerList.size();
    }
}
