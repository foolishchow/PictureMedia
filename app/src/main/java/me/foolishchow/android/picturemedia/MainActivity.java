package me.foolishchow.android.picturemedia;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import me.foolishchow.android.pictureMedia.MediaUtils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.select).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaUtils.selectSingleImage(MainActivity.this);
            }
        });
        findViewById(R.id.select_crop).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaUtils.selectAndCropSingleImage(MainActivity.this,1000,1000);
            }
        });
    }
}