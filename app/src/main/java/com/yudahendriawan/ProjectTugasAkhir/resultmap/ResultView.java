package com.yudahendriawan.ProjectTugasAkhir.resultmap;

import com.yudahendriawan.ProjectTugasAkhir.model.Places;
import com.yudahendriawan.ProjectTugasAkhir.model.Wisata;

import java.util.List;

public interface ResultView {

    void onShowLoading();

    void onHideLoading();

    void onGetResult(List<Wisata> placesWisata);

    void onErrorLoading(String message);
}
