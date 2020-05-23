package com.yudahendriawan.ProjectTugasAkhir.model;

import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("response")
    private String response;

    @SerializedName("name")
    private String name;

    public String getResponse() {
        return response;
    }

    public String getName() {
        return name;
    }
}
