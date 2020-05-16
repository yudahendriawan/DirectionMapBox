package com.yudahendriawan.ProjectTugasAkhir.wisata;

import androidx.annotation.NonNull;

import com.yudahendriawan.ProjectTugasAkhir.api.ApiClient;
import com.yudahendriawan.ProjectTugasAkhir.api.ApiInterface;
import com.yudahendriawan.ProjectTugasAkhir.model.Places;
import com.yudahendriawan.ProjectTugasAkhir.model.Wisata;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WisataPresenter {

    private WisataView view;

    public WisataPresenter(WisataView view) {
        this.view = view;
    }

    void getData() {
        view.onShowLoading();

        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<Wisata>> call = apiInterface.getWisata();

        call.enqueue(new Callback<List<Wisata>>() {
            @Override
            public void onResponse(@NonNull Call<List<Wisata>> call, @NonNull Response<List<Wisata>> response) {
                view.onHideLoading();

                if (response.isSuccessful() && response.body() != null) {
                    view.onGetResult(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Wisata>> call, @NonNull Throwable t) {
                view.onHideLoading();
                view.onErrorLoading(t.getLocalizedMessage());
            }
        });
    }
}
