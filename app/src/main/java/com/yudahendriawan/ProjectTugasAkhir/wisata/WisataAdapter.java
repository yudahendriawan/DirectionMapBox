package com.yudahendriawan.ProjectTugasAkhir.wisata;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yudahendriawan.ProjectTugasAkhir.model.Places;

import java.util.List;

public class WisataAdapter extends RecyclerView.Adapter<WisataAdapter.RecyclerViewAdapter> {

    private Context context;
    private List<Places> places;
    private ItemClickListener itemClickListener;

    public WisataAdapter(Context context, List<Places> wisatas, ItemClickListener itemClickListener) {
        this.context = context;
        this.places = wisatas;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public WisataAdapter.RecyclerViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull WisataAdapter.RecyclerViewAdapter holder, int position) {
        holder.binding(places.get(position));
    }

    @Override
    public int getItemCount() {
        return places.size();
    }

    public class RecyclerViewAdapter extends RecyclerView.ViewHolder {


        public RecyclerViewAdapter(@NonNull View itemView) {
            super(itemView);
        }

        public void binding(final Places places) {


        }
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
