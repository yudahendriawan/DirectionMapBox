package com.yudahendriawan.ProjectTugasAkhir;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.yudahendriawan.ProjectTugasAkhir.api.ApiClient;
import com.yudahendriawan.ProjectTugasAkhir.api.ApiInterface;
import com.yudahendriawan.ProjectTugasAkhir.config.PrefConfig;
import com.yudahendriawan.ProjectTugasAkhir.fragment.LoginFragment;
import com.yudahendriawan.ProjectTugasAkhir.fragment.MenuFragment;
import com.yudahendriawan.ProjectTugasAkhir.fragment.RegristrationFragment;
import com.yudahendriawan.ProjectTugasAkhir.util.Key;
import com.yudahendriawan.ProjectTugasAkhir.wisata.WisataActivity;

public class MenuActivity extends AppCompatActivity implements LoginFragment.OnLoginFormActivityListener, MenuFragment.OnLogoutListener {
//    CardView findRoute, wisata, login;
//    Graph graph;

    public static PrefConfig prefConfig;
    public static ApiInterface apiInterface;
    boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        prefConfig = new PrefConfig(this);
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        if (findViewById(R.id.fragment_container) != null) {
            if (savedInstanceState != null) {
                return;
            }

            if (prefConfig.readLoginStatus()) {
                getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, new MenuFragment()).commit();
            } else {
                getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, new LoginFragment()).commit();
            }
        }

//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
//
//        getSupportActionBar().setTitle(Key.MENU_TITLE);
//
//
//        findRoute = findViewById(R.id.findRoute);
//        wisata = findViewById(R.id.listWisata);
//        login = findViewById(R.id.login);
//        // int vertices = 31;
//        //  graph = new Graph(vertices, this);
//
//        findRoute.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // graph.addEdgeDB();
//                // Toast.makeText(v.getContext(), "Get Data Form Database", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(v.getContext(), MainActivity.class);
//                startActivity(intent);
//
//            }
//        });
//
//        wisata.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(v.getContext(), WisataActivity.class);
//                startActivity(intent);
//            }
//        });
//
//        login.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(v.getContext(), EmailActivity.class);
//                startActivity(intent);
//            }
//        });

    }

    @Override
    public void performRegister() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, new RegristrationFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void performLogin(String name) {
        prefConfig.writeName(name);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, new MenuFragment())
                .commit();
        Toast.makeText(this, "Welcome " + name, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void logoutPerform() {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);

        // set title
        alertDialogBuilder.setTitle(null);

        // set dialog message
        alertDialogBuilder
                .setMessage("Hey " + prefConfig.readName() + ", Are you sure to Logout?")
                .setCancelable(false)
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        prefConfig.writeLoginStatus(false);
                        prefConfig.writeName("User");
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.fragment_container, new LoginFragment())
                                .commit();

                    }
                })
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // if this button is clicked, just close
                        // the dialog box and do nothing
                        dialog.cancel();
                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }

//    @Override
//    public void onBackPressed() {
//        if (doubleBackToExitPressedOnce) {
//            super.onBackPressed();
//            return;
//        }
//
//        this.doubleBackToExitPressedOnce = true;
//        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();
//
//        new Handler().postDelayed(new Runnable() {
//
//            @Override
//            public void run() {
//                doubleBackToExitPressedOnce = false;
//            }
//        }, 2000);
//    }
}
