package me.foolishchow.android.picturemedia;

import android.app.Activity;
import android.app.SharedElementCallback;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;

import java.util.List;
import java.util.Map;

import me.foolishchow.android.picturemedia.databinding.ActivitySlideDownBinding;

public class SlideDownActivity extends AppCompatActivity {


    public static void Start(Activity context, String Url, View shared){
        Intent intent = new Intent(context, SlideDownActivity.class);
        if (Build.VERSION.SDK_INT >= 22) {
            ActivityOptionsCompat options = ActivityOptionsCompat
                    .makeSceneTransitionAnimation(context, shared, "preview");// mAdapter.get
            // (position).getUrl()
            context.startActivity(intent, options.toBundle());
        } else {
            context.startActivity(intent);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivitySlideDownBinding mView = ActivitySlideDownBinding.inflate(getLayoutInflater());
        setContentView(mView.getRoot());
        //supportPostponeEnterTransition();

        if (Build.VERSION.SDK_INT >= 22) {
            //这个可以看做个管道  每次进入和退出的时候都会进行调用  进入的时候获取到前面传来的共享元素的信息
            //退出的时候 把这些信息传递给前面的activity
            //同时向sharedElements里面put view,跟对view添加transitionname作用一样
            setEnterSharedElementCallback(new SharedElementCallback() {
                @Override
                public void onMapSharedElements(List<String> names, Map<String, View> sharedElements) {
                    sharedElements.clear();
                    sharedElements.put("preview", mView.viewer.getImageView());
                }

                @Override
                public void onSharedElementEnd(List<String> sharedElementNames, List<View> sharedElements, List<View> sharedElementSnapshots) {
                    super.onSharedElementEnd(sharedElementNames, sharedElements, sharedElementSnapshots);
                    mView.viewer.setUp();
                }
            });
        }
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        boolean result = true;
        try {
            result = super.dispatchTouchEvent(ev);
        } catch (RuntimeException e) {
            e.printStackTrace();
        }

        return result;
    }
}