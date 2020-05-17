package com.yudahendriawan.ProjectTugasAkhir;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.yudahendriawan.ProjectTugasAkhir.util.Key;
import com.yudahendriawan.ProjectTugasAkhir.wisata.WisataActivity;

public class MenuActivity extends AppCompatActivity {
    CardView findRoute, wisata, login;
    Graph graph;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        getSupportActionBar().setTitle(Key.MENU_TITLE);


        findRoute = findViewById(R.id.findRoute);
        wisata = findViewById(R.id.listWisata);
        login = findViewById(R.id.login);
        // int vertices = 31;
        //  graph = new Graph(vertices, this);


        findRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // graph.addEdgeDB();
                // Toast.makeText(v.getContext(), "Get Data Form Database", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(v.getContext(), MainActivity.class);
                startActivity(intent);

            }
        });

        wisata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), WisataActivity.class);
                startActivity(intent);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), EmailActivity.class);
                startActivity(intent);
            }
        });

    }
}
