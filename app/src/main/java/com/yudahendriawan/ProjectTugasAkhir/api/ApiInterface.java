package com.yudahendriawan.ProjectTugasAkhir.api;


import com.yudahendriawan.ProjectTugasAkhir.model.Node;
import com.yudahendriawan.ProjectTugasAkhir.model.Places;
import com.yudahendriawan.ProjectTugasAkhir.model.User;
import com.yudahendriawan.ProjectTugasAkhir.model.Wisata;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("edge.php")
    Call<List<Node>> getNode();

    @GET("places.php")
    Call<List<Places>> getPlaces();

    @GET("wisataTA.php")
    Call<List<Wisata>> getWisata();

    @GET("register.php")
    Call<User> performRegristration(@Query("name") String name,
                                    @Query("user_name") String user_name,
                                    @Query("user_password") String user_password);

    @GET("login.php")
    Call<User> performUserLogin(@Query("user_name") String user_name,
                                @Query("user_password") String user_password);

}
