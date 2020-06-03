package com.yudahendriawan.ProjectTugasAkhir.resultmap;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.yudahendriawan.ProjectTugasAkhir.R;
import com.yudahendriawan.ProjectTugasAkhir.model.Places;
import com.yudahendriawan.ProjectTugasAkhir.model.Wisata;
import com.yudahendriawan.ProjectTugasAkhir.util.Key;
import com.yudahendriawan.ProjectTugasAkhir.wisata.DetailWisataActivity;

import java.util.List;

public class ResultMapAdapter extends RecyclerView.Adapter<ResultMapAdapter.RecyclerViewAdapter> {
    private Context context;
    private List<Wisata> wisatas;
    private ItemClickListener itemClickListener;

    public ResultMapAdapter(Context context, List<Wisata> wisataList, ItemClickListener itemClickListener) {
        this.context = context;
        this.wisatas = wisataList;
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
        holder.binding(wisatas.get(position));

        Toast.makeText(context, "Ditemukan " + wisatas.size() + " wisata pada jalur", Toast.LENGTH_SHORT).show();
    }

    @Override
    public int getItemCount() {
        return wisatas.size();
    }

    public class RecyclerViewAdapter extends RecyclerView.ViewHolder {

        ItemClickListener itemClickListener;
        TextView name, operation_time, fee_wisata;
        ImageView img_wisata;
        LinearLayout linearLayout_list;



        public RecyclerViewAdapter(@NonNull View itemView, ItemClickListener itemClickListener) {
            super(itemView);
            this.itemClickListener = itemClickListener;
            name = itemView.findViewById(R.id.txt_name_wisata);
            operation_time = itemView.findViewById(R.id.operation_time_result_wisata);
            fee_wisata = itemView.findViewById(R.id.fee_result_wisata);
            img_wisata = itemView.findViewById(R.id.img_result_wisata);
            linearLayout_list = itemView.findViewById(R.id.ll_list_result_wisata);

        }

        public void binding(final Wisata wisata) {
            name.setText(wisata.getName());
            operation_time.setText(wisata.getOpenTime());
            fee_wisata.setText(wisata.getFee());

            Glide.with(context).load(wisata.getImgUrl()).into(img_wisata);

            linearLayout_list.setOnClickListener(v -> {
                Intent intent = new Intent(v.getContext(), DetailWisataActivity.class);
                intent.putExtra(Key.INTENT_DATA, wisata);
                v.getContext().startActivity(intent);
            });
        }
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
