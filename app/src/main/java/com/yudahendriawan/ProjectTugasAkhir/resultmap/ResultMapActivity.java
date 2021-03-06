package com.yudahendriawan.ProjectTugasAkhir.resultmap;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.yudahendriawan.ProjectTugasAkhir.R;
import com.yudahendriawan.ProjectTugasAkhir.model.Places;
import com.yudahendriawan.ProjectTugasAkhir.model.Wisata;
import com.yudahendriawan.ProjectTugasAkhir.util.Key;

import java.util.List;


public class ResultMapActivity extends AppCompatActivity implements ResultView {

    List<Wisata> wisatas;
    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;
    ResultPresenter presenter;
    ResultMapAdapter adapter;
    ResultMapAdapter.ItemClickListener itemClickListener;
    TextView countWisata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_map);

        getSupportActionBar().hide();

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        recyclerView = findViewById(R.id.recyclerview);
        swipeRefreshLayout = findViewById(R.id.swipe_refresh);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        countWisata = findViewById(R.id.tv_count_wisataa);
        countWisata.setVisibility(View.GONE);

        int[] pathFix = getIntent().getIntArrayExtra("pathResultFix");

        presenter = new ResultPresenter(this);
        presenter.getData(pathFix);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.getData(pathFix);
            }
        });

    }

    @Override
    public void onShowLoading() {
        swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void onHideLoading() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onGetResult(List<Wisata> placesList) {
        adapter = new ResultMapAdapter(this, placesList, itemClickListener);
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
        placesList = wisatas;
    }

    @Override
    public void onErrorLoading(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
