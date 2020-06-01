package com.yudahendriawan.ProjectTugasAkhir;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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

import java.util.Calendar;
import java.util.Date;

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

        //getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().setTitle("");

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
                .setMessage("Hey " + prefConfig.readName() + ",\n Are you sure to Logout?")
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

    public boolean onCreateOptionsMenu(Menu menu) {
        Fragment fragment = getSupportFragmentManager().findFragmentById(new MenuFragment().getId());
        if (fragment instanceof MenuFragment) {
            getMenuInflater().inflate(R.menu.menu, menu);
            return super.onCreateOptionsMenu(menu);
        } else {
            return false;
        }
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        Fragment fragment = getSupportFragmentManager().findFragmentById(new MenuFragment().getId());
        if (fragment instanceof MenuFragment) {
            setMode(item.getItemId());
            return super.onOptionsItemSelected(item);
        } else
            return false;
    }

    public void setMode(int selectedMode) {

        switch (selectedMode) {
            case R.id.about:
                about();
                break;
            case R.id.exit:
                exit();
                break;
//            case R.id.version:
//                version();
//                break;
            case R.id.profil:
                profil();
                break;
        }
    }

    public void about() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);

        // set title
        alertDialogBuilder.setTitle(null);

        // set dialog message
        alertDialogBuilder
                .setMessage(getString(R.string.about_me))
                .setTitle("About")
                .setCancelable(false).setNegativeButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        }).show();
//                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        // if this button is clicked, close
//                        // current activity
//                        MainActivity.this.finish();
//                    }
//                })
//                .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        // if this button is clicked, just close
//                        // the dialog box and do nothing
//                        dialog.cancel();
    }

    public void profil() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);

        // set title
        alertDialogBuilder.setTitle(null);

        // set dialog message
        alertDialogBuilder
                .setMessage(prefConfig.readName() + "")
                .setTitle("About")
                .setCancelable(false).setNegativeButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        }).show();
//
    }

    public void exit() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);

        // set title
        alertDialogBuilder.setTitle(null);

        // set dialog message
        alertDialogBuilder
                .setMessage("Hey, " + prefConfig.readName() + " Are you sure to exit?")
                .setCancelable(false)
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // if this button is clicked, close
                        // current activity
                        MenuActivity.this.finish();
                    }
                })
                .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
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

    public void version() {
        Date currentTime = Calendar.getInstance().getTime();
        String currentDateTimeString = java.text.DateFormat.getDateTimeInstance().format(new Date());
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);

        // set title
        alertDialogBuilder.setTitle(null);

        // set dialog message
        alertDialogBuilder
                .setMessage("Version Apps : " + currentDateTimeString)
                .setTitle("About")
                .setCancelable(false).setNegativeButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        }).show();

    }

}
