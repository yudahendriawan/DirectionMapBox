package com.yudahendriawan.ProjectTugasAkhir.wisata;

import com.yudahendriawan.ProjectTugasAkhir.api.ApiClient;
import com.yudahendriawan.ProjectTugasAkhir.api.ApiInterface;
import com.yudahendriawan.ProjectTugasAkhir.model.Places;

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
        Call<List<Places>> call = apiInterface.getPlaces();

        call.enqueue(new Callback<List<Places>>() {
            @Override
            public void onResponse(Call<List<Places>> call, Response<List<Places>> response) {
                view.onHideLoading();

                if (response.isSuccessful() && response.body() != null) {
                    view.onGetResult(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Places>> call, Throwable t) {
                view.onHideLoading();
                view.onErrorLoading(t.getLocalizedMessage());
            }
        });
    }
}
