package com.yudahendriawan.ProjectTugasAkhir;

import androidx.annotation.NonNull;

import com.yudahendriawan.ProjectTugasAkhir.api.ApiClient;
import com.yudahendriawan.ProjectTugasAkhir.api.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NodePresenter {

    NodeView view;

    public NodePresenter(NodeView view) {
        this.view = view;
    }

    public NodePresenter() {
    }

//    void getData(){
//        view.showLoading();
//
//        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
//        Call<ArrayList<Node>> call = apiInterface.getNode();
//
//        call.enqueue(new Callback<List<Node>>() {
//            @Override
//            public void onResponse(@NonNull Call<List<Node>> call,@NonNull Response<List<Node>> response) {
//                view.hideLoading();
//
//                if (response.isSuccessful() && response.body() != null) {
//                    view.onGetResult(response.body());
//                }
//            }
//
//            @Override
//            public void onFailure(@NonNull Call<List<Node>> call,@NonNull Throwable t) {
//                view.hideLoading();
//                view.onErrorLoading(t.getLocalizedMessage());
//            }
//        });
//    }
}
