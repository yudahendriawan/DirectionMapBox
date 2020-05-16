package com.yudahendriawan.ProjectTugasAkhir.wisata;

import com.yudahendriawan.ProjectTugasAkhir.model.Places;
import com.yudahendriawan.ProjectTugasAkhir.model.Wisata;

import java.util.List;

public interface WisataView {
    void onShowLoading();

    void onHideLoading();

    void onGetResult(List<Places> wisataList);

    void onErrorLoading(String message);
}
