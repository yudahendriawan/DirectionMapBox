package com.yudahendriawan.ProjectTugasAkhir.api;


import com.yudahendriawan.ProjectTugasAkhir.model.Node;
import com.yudahendriawan.ProjectTugasAkhir.model.Places;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {

    @GET("edge.php")
    Call<List<Node>> getNode();

    @GET("places.php")
    Call<List<Places>> getPlaces();
}
