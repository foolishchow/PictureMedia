package me.foolishchow.android.picturemedia;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.foolishchow.android.pictureMedia.MediaUtils;
import me.foolishchow.android.picturemedia.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding mView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mView = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mView.getRoot());
        mView.select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaUtils.selectSingleImage(MainActivity.this);
            }
        });
        mView.selectCrop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaUtils.selectAndCropSingleImage(MainActivity.this, 1000, 1000);
            }
        });
        //"https://jiangruihao-pub.oss-cn-hangzhou.aliyuncs.com/defaults/aaaaa.jpg"
        Glide.with(mView.preview)
                .load("https://jiangruihao-pub.oss-cn-hangzhou.aliyuncs.com/defaults/aaaaa.jpg")
                .into(mView.preview);
        mView.preview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Click();
            }
        });
    }


    private void Click() {
        String host = "https://jiangruihao-pub.oss-cn-hangzhou.aliyuncs.com/";

        String[] current = new String[]{
                host + "app_users/653627389913333760/dynamaic/1594877537192687524.jpeg",
                host + "app_users/653627389913333760/dynamaic/1594877537298988593.jpeg",
                host + "app_users/653627389913333760/dynamaic/1594877537403497016.png",
                host + "app_users/653627389913333760/dynamaic/1594877537610611248.jpeg",
                host + "app_users/653627389913333760/dynamaic/1594877537701916660.jpeg",
                host + "app_users/653627389913333760/dynamaic/1594877537771298953.jpeg",
                host + "app_users/653627389913333760/dynamaic/1594877538455822381.png"
        };

        List<String> strings = Arrays.asList(current);
        SlideDownActivity.Start(
                this, 1,new ArrayList<String>(strings));
//        "https://jiangruihao-pub.oss-cn-hangzhou.aliyuncs" +
//                        ".com/defaults/aaaaa.jpg"
        //startActivity(new Intent(this,SlideDownActivity.class));
        //List<ImageInfo> list = new ArrayList<>();
        //for (String url : current) {
        //    ImageInfo imageInfo = new ImageInfo();
        //    imageInfo.setOriginUrl(host + url);
        //    imageInfo.setThumbnailUrl(host + url + "?x-oss-process=style/400");
        //    list.add(imageInfo);
        //}
        //PreviewUtils.test(this,list);
    }
}