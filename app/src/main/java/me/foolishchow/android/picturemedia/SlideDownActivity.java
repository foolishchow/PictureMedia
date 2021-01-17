package me.foolishchow.android.picturemedia;

import android.app.Activity;
import android.app.SharedElementCallback;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import me.foolishchow.android.picturemedia.databinding.ActivitySlideDownBinding;
import me.foolishchow.android.picturemedia.databinding.ViewDownBinding;

public class SlideDownActivity extends AppCompatActivity {

    private static String getIndex(int index) {
        return String.format("index_%s", index);
    }

    public static void Start(Activity context,
                             int index,
                             ArrayList<String> Urls,
                             View... shared
    ) {
        Intent intent = new Intent(context, SlideDownActivity.class);
        intent.putExtra("index", index);
        intent.putStringArrayListExtra("images", Urls);
        if (Build.VERSION.SDK_INT >= 22 && shared.length > 0) {
            Pair<View, String>[] share = new Pair[shared.length];
            for (int i = 0; i < shared.length; i++) {
                share[i] = new Pair<>(shared[i],getIndex(i));
            }
            ActivityOptionsCompat options = ActivityOptionsCompat
                    .makeSceneTransitionAnimation(context, share);// mAdapter.get
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
        ArrayList<String> images = getIntent().getStringArrayListExtra("images");
        int index = getIntent().getIntExtra("index", 0);
        String index1 = getIndex(index);
        mView.viewpager.setAdapter(new ViewPagerAdapter(images));
        mView.viewpager.setCurrentItem(index, false);

//        supportPostponeEnterTransition();
        if (Build.VERSION.SDK_INT >= 22) {
            //这个可以看做个管道  每次进入和退出的时候都会进行调用  进入的时候获取到前面传来的共享元素的信息
            //退出的时候 把这些信息传递给前面的activity
            //同时向sharedElements里面put view,跟对view添加transitionname作用一样
            setEnterSharedElementCallback(new SharedElementCallback() {
                @Override
                public void onMapSharedElements(List<String> names, Map<String, View> sharedElements) {
                    sharedElements.clear();
                    if(sharedElements.containsKey(index1)){
                        sharedElements.put("index1", mView.viewpager.getChildAt(index));
                    }
                }

                @Override
                public void onSharedElementEnd(List<String> sharedElementNames, List<View> sharedElements, List<View> sharedElementSnapshots) {
                    super.onSharedElementEnd(sharedElementNames, sharedElements, sharedElementSnapshots);
//                    mView.viewer.setUp();
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}