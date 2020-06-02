package com.yudahendriawan.ProjectTugasAkhir.resultmap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yudahendriawan.ProjectTugasAkhir.R;
import com.yudahendriawan.ProjectTugasAkhir.model.Places;
import com.yudahendriawan.ProjectTugasAkhir.wisata.WisataAdapter;

import java.util.List;

public class ResultMapAdapter extends RecyclerView.Adapter<ResultMapAdapter.RecyclerViewAdapter> {
    private Context context;
    private List<Places> places;
    private ItemClickListener itemClickListener;

    public ResultMapAdapter(Context context, List<Places> places, ItemClickListener itemClickListener) {
        this.context = context;
        this.places = places;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public ResultMapAdapter.RecyclerViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_result_wisata, parent, false);
        return new RecyclerViewAdapter(view, itemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ResultMapAdapter.RecyclerViewAdapter holder, int position) {
        holder.binding(places.get(position));
    }

    @Override
    public int getItemCount() {
        return places.size();
    }

    public class RecyclerViewAdapter extends RecyclerView.ViewHolder {

        ItemClickListener itemClickListener;
        TextView name;

        public RecyclerViewAdapter(@NonNull View itemView, ItemClickListener itemClickListener) {
            super(itemView);
            this.itemClickListener = itemClickListener;
            name = itemView.findViewById(R.id.txt_name_wisata);
        }

        public void binding(final Places places) {
            name.setText(places.getName());
        }
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
