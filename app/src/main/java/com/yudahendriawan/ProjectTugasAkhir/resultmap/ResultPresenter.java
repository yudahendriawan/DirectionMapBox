package com.yudahendriawan.ProjectTugasAkhir.resultmap;

import android.util.Log;

import androidx.annotation.NonNull;

import com.yudahendriawan.ProjectTugasAkhir.api.ApiClient;
import com.yudahendriawan.ProjectTugasAkhir.api.ApiInterface;
import com.yudahendriawan.ProjectTugasAkhir.model.Places;
import com.yudahendriawan.ProjectTugasAkhir.model.Wisata;
import com.yudahendriawan.ProjectTugasAkhir.wisata.WisataView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResultPresenter {

    private ResultView view;

    public ResultPresenter(ResultView view) {
        this.view = view;
    }

    void getData(int[] pathFix) {
        view.onShowLoading();

        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<Wisata>> call = apiInterface.getWisata();

        call.enqueue(new Callback<List<Wisata>>() {
            @Override
            public void onResponse(@NotNull Call<List<Wisata>> call, @NotNull Response<List<Wisata>> response) {
                view.onHideLoading();

                List<Wisata> listWisata = new ArrayList<>();

                if (response.isSuccessful() && response.body() != null) {
                    for (int i = 0; i < response.body().size(); i++) {
                        for (int j = 0; j < pathFix.length; j++) {
                            if (response.body().get(i).getNumber() == pathFix[j]) {
                                listWisata.add(response.body().get(i));
                                Log.d("listWisata", response.body().get(i).toString());
                            }
                        }
                    }
                    view.onGetResult(listWisata);
                }
            }

            @Override
            public void onFailure(@NotNull Call<List<Wisata>> call, @NotNull Throwable t) {
                view.onHideLoading();
                view.onErrorLoading(t.getLocalizedMessage());
            }
        });
    }

//    void getData(int[] pathFix) {
//        view.onShowLoading();
//
//        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
//        Call<List<Wisata>> call = apiInterface.getWisata();
//
//        call.enqueue(new Callback<List<Wisata>>() {
//            @Override
//            public void onResponse(@NonNull Call<List<Wisata>> call, @NonNull Response<List<Wisata>> response) {
//                view.onHideLoading();
//
//                if (response.isSuccessful() && response.body() != null) {
//
//                    List<Wisata> wisatas;
//                    for(int i = 0; i<pathFix.length; i++){
//                        //if(response.body().get(i).equals(pathFix[i]) && response.body().get(i).get){
//
//                        }
//                    }
//                    view.onGetResult(response.body());
//                }
//            }
//
//            @Override
//            public void onFailure(@NonNull Call<List<Wisata>> call, @NonNull Throwable t) {
//                view.onHideLoading();
//                view.onErrorLoading(t.getLocalizedMessage());
//            }
//        });
//    }
}
