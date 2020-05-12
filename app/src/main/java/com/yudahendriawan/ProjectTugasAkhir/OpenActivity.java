package com.yudahendriawan.ProjectTugasAkhir;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class OpenActivity extends AppCompatActivity implements NodeView {

    ProgressDialog dialog;
    int vertices = 31;
    NodeView view;
    Button buttonProses;
    // Graph graph = new Graph(vertices,this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open);

        // graph.addEdgeDB();

        buttonProses = findViewById(R.id.buttonProses);

        buttonProses.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), MainActivity.class);
            //   intent.putExtras();
            startActivity(intent);

        });

    }

    @Override
    public void showLoading() {
        dialog.setMessage("Waiting");
        dialog.show();
    }

    @Override
    public void hideLoading() {
        dialog.dismiss();

    }
}
