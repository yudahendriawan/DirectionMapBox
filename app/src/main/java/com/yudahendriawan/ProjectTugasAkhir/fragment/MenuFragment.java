package com.yudahendriawan.ProjectTugasAkhir.fragment;


import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yudahendriawan.ProjectTugasAkhir.EmailActivity;
import com.yudahendriawan.ProjectTugasAkhir.MainActivity;
import com.yudahendriawan.ProjectTugasAkhir.MenuActivity;
import com.yudahendriawan.ProjectTugasAkhir.R;
import com.yudahendriawan.ProjectTugasAkhir.wisata.WisataActivity;

import java.util.Date;

import static com.yudahendriawan.ProjectTugasAkhir.MenuActivity.prefConfig;

/**
 * A simple {@link Fragment} subclass.
 */
public class MenuFragment extends Fragment {

    private CardView findRoute, wisata, logout;
    private TextView textViewWelcome, currentTime;

    OnLogoutListener logoutListener;

    public interface OnLogoutListener {
        public void logoutPerform();
    }

    public MenuFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

//        getActivity().getActionBar().setTitle("HALLO");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        // ((AppCompatActivity) getActivity()).setTitle("");

        findRoute = view.findViewById(R.id.findRoute);
        wisata = view.findViewById(R.id.listWisata);
        logout = view.findViewById(R.id.logout);
        textViewWelcome = view.findViewById(R.id.welcome_text);
        currentTime = view.findViewById(R.id.current_time);

        textViewWelcome.setText("Hallo, " + prefConfig.readName());

        CountDownTimer newtimer = new CountDownTimer(1000000000, 1000) {

            public void onTick(long millisUntilFinished) {
                String currentDateTimeString = java.text.DateFormat.getDateTimeInstance().format(new Date());
                currentTime.setText(currentDateTimeString);
            }

            public void onFinish() {

            }
        };
        newtimer.start();

        findRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // graph.addEdgeDB();
                // Toast.makeText(v.getContext(), "Get Data Form Database", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);

            }
        });

        wisata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), WisataActivity.class);
                startActivity(intent);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutListener.logoutPerform();
            }
        });

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Activity activity = (Activity) context;
        logoutListener = (OnLogoutListener) activity;

    }

    //disable back presedd
    @Override
    public void onResume() {
        super.onResume();
        this.getView().setFocusableInTouchMode(true);
        this.getView().requestFocus();
        this.getView().setOnKeyListener((v, keyCode, event) -> {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                return true;
            }
            return false;
        });
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        //  Fragment fragment = getSupportFragmentManager().findFragmentById(new MenuFragment().getId());
        //  if(fragment instanceof  MenuFragment) {
        setMode(item.getItemId());
        return super.onOptionsItemSelected(item);
        //  }

    }

    public void setMode(int selectedMode) {

        switch (selectedMode) {
            case R.id.about:
                about();
                break;
            case R.id.exit:
                exit();
                break;
            case R.id.version:
                version();
                break;
            case R.id.profil:
                profil();
                break;
        }
    }

    public void about() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                getActivity());

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
    }

    public void profil() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                getActivity());

        // set title
        alertDialogBuilder.setTitle(null);

        // set dialog message
        alertDialogBuilder
                .setMessage(prefConfig.readName() + "")
                .setTitle("About")
                .setCancelable(false).setNegativeButton("OK", (dialog, which) -> dialog.cancel()).show();
//
    }

    public void exit() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                getActivity());

        // set title
        alertDialogBuilder.setTitle(null);

        // set dialog message
        alertDialogBuilder
                .setMessage("Hey, " + prefConfig.readName() + "\nAre you sure to exit?")
                .setCancelable(false)
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // if this button is clicked, close
                        // current activity
                        getActivity().finish();
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
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                getActivity());

        // set title
        alertDialogBuilder.setTitle(null);

        // set dialog message
        alertDialogBuilder
                .setMessage("App Version 1.0.0")
                .setTitle("Version")
                .setCancelable(false).setNegativeButton("OK", (dialog, which) -> dialog.cancel()).show();

    }


}
