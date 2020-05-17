package com.yudahendriawan.ProjectTugasAkhir.wisata;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.yudahendriawan.ProjectTugasAkhir.R;
import com.yudahendriawan.ProjectTugasAkhir.model.Places;
import com.yudahendriawan.ProjectTugasAkhir.model.Wisata;
import com.yudahendriawan.ProjectTugasAkhir.util.Key;

import java.util.List;

public class WisataAdapter extends RecyclerView.Adapter<WisataAdapter.RecyclerViewAdapter> {

    private Context context;
    private List<Wisata> wisatas;
    private ItemClickListener itemClickListener;

    public WisataAdapter(Context context, List<Wisata> wisatas, ItemClickListener itemClickListener) {
        this.context = context;
        this.wisatas = wisatas;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public WisataAdapter.RecyclerViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_wisata, parent, false);
        return new RecyclerViewAdapter(view, itemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull WisataAdapter.RecyclerViewAdapter holder, int position) {
        holder.binding(wisatas.get(position));
    }

    @Override
    public int getItemCount() {
        return wisatas.size();
    }

    public class RecyclerViewAdapter extends RecyclerView.ViewHolder {

        ImageView image;
        ItemClickListener itemClickListener;
        TextView name;

        public RecyclerViewAdapter(@NonNull View itemView, final ItemClickListener itemClickListener) {
            super(itemView);
            this.itemClickListener = itemClickListener;
            image = itemView.findViewById(R.id.image_wisata);
            name = itemView.findViewById(R.id.name_wisata);

        }

        public void binding(final Wisata wisata) {
            Glide.with(context).load(wisata.getImgUrl()).into(image);
            name.setText(wisata.getName());

            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //     itemClickListener.onItemClick(v,getAdapterPosition());
                    Intent intent = new Intent(v.getContext(), DetailWisataActivity.class);
                    intent.putExtra(Key.INTENT_DATA, wisata);
                    v.getContext().startActivity(intent);
                }
            });

        }
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
