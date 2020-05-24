package com.yudahendriawan.ProjectTugasAkhir.fragment;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yudahendriawan.ProjectTugasAkhir.EmailActivity;
import com.yudahendriawan.ProjectTugasAkhir.MainActivity;
import com.yudahendriawan.ProjectTugasAkhir.R;
import com.yudahendriawan.ProjectTugasAkhir.wisata.WisataActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class MenuFragment extends Fragment {

    CardView findRoute, wisata, logout;

    OnLogoutListener logoutListener;

    public interface OnLogoutListener {
        public void logoutPerform();
    }

    public MenuFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu, container, false);

        findRoute = view.findViewById(R.id.findRoute);
        wisata = view.findViewById(R.id.listWisata);
        logout = view.findViewById(R.id.logout);
        // login = findViewById(R.id.login);
        // int vertices = 31;
        //  graph = new Graph(vertices, this);

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

//        login.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(v.getContext(), EmailActivity.class);
//                startActivity(intent);
//            }
//        });

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

}
