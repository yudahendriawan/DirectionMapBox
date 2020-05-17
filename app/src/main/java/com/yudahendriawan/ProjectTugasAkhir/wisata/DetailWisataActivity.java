package com.yudahendriawan.ProjectTugasAkhir.wisata;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yudahendriawan.ProjectTugasAkhir.R;
import com.yudahendriawan.ProjectTugasAkhir.model.Wisata;
import com.yudahendriawan.ProjectTugasAkhir.util.Key;

public class DetailWisataActivity extends AppCompatActivity {
    ImageView imageWisata;
    TextView wisataAddress, wisataName, wisataSummary, wisataOpenTime, wisataContact;
    Wisata wisata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_wisata);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        wisataAddress = findViewById(R.id.wisata_address);
        wisataContact = findViewById(R.id.wisata_contact);
        wisataName = findViewById(R.id.detail_wisata_name);
        wisataOpenTime = findViewById(R.id.wisata_openTime);
        wisataSummary = findViewById(R.id.wisata_summary);

        imageWisata = findViewById(R.id.image_detail_wisata);

        wisata = getIntent().getExtras().getParcelable(Key.INTENT_DATA);

        Glide.with(this).load(wisata.getImgUrl()).into(imageWisata);
        wisataName.setText(wisata.getName());
        wisataAddress.setText(wisata.getAddress());
        wisataSummary.setText(wisata.getSummary());
        wisataOpenTime.setText(wisata.getOpenTime());
        wisataContact.setText(wisata.getContactPerson());

        getSupportActionBar().hide();

    }
}
