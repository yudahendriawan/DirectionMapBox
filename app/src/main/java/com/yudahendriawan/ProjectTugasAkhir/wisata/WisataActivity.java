package com.yudahendriawan.ProjectTugasAkhir.wisata;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.stone.vega.library.VegaLayoutManager;
import com.yudahendriawan.ProjectTugasAkhir.R;
import com.yudahendriawan.ProjectTugasAkhir.model.Places;
import com.yudahendriawan.ProjectTugasAkhir.model.Wisata;
import com.yudahendriawan.ProjectTugasAkhir.util.Key;

import java.util.List;

public class WisataActivity extends AppCompatActivity implements WisataView {

    private static final int INTENT_EDIT = 200;
    private static final int INTENT_ADD = 100;

    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefresh;

    WisataPresenter presenter;
    WisataAdapter adapter;
    WisataAdapter.ItemClickListener itemClickListener;
    List<Wisata> wisatas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wisata);

        getSupportActionBar().setTitle(Key.TITLE_WISATA);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        recyclerView = findViewById(R.id.recyclerview);
        swipeRefresh = findViewById(R.id.swipe_refresh);

        recyclerView.setLayoutManager(new VegaLayoutManager());

        presenter = new WisataPresenter(this);
        presenter.getData();

        swipeRefresh.setOnRefreshListener(() -> presenter.getData());

        itemClickListener = ((view, position) -> {
            int id = wisatas.get(position).getNumber();

        });


    }

    @Override
    public void onShowLoading() {
        swipeRefresh.setRefreshing(true);
    }

    @Override
    public void onHideLoading() {
        swipeRefresh.setRefreshing(false);
    }

    @Override
    public void onGetResult(List<Wisata> wisataList) {
        adapter = new WisataAdapter(this, wisataList, itemClickListener);
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);

        wisataList = wisatas;
    }

    @Override
    public void onErrorLoading(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
