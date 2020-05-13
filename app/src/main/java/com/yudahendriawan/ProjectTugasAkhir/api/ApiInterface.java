package com.yudahendriawan.ProjectTugasAkhir.api;


import com.yudahendriawan.ProjectTugasAkhir.Node;
import com.yudahendriawan.ProjectTugasAkhir.Places;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {

    @GET("edge.php")
    Call<List<Node>> getNode();

    @GET("places.php")
    Call<List<Places>> getPlaces();
}
