package me.foolishchow.android.picture_preview;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import me.foolishchow.android.picture_preview.databinding.MediaPreviewBinding;

/**
 * Description:
 * Author: foolishchow
 * Date: 11/1/2021 4:48 PM
 */
public class ImagePreviewActivity extends AppCompatActivity {


    private MediaPreviewBinding mBinding;

    public static void preview(Context context, @Nullable View transitionView) {
        Intent intent = new Intent();
        intent.setClass(context,ImagePreviewActivity.class);

        if(transitionView != null && android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP){
            String transitionShareElementName = "viewpager";//.getTransitionShareElementName();
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(
                    (Activity) context,
                    transitionView,
                    transitionShareElementName
            );
            context.startActivity(intent, options.toBundle());
        }else{
            context.startActivity(intent);
            if (context instanceof Activity) {
                ((Activity) context).overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        //只有安卓版本大于 5.0 才可使用过度动画
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS);
            findViewById(android.R.id.content).setTransitionName("shared_element_container");
            setEnterSharedElementCallback(new MaterialContainerTransformSharedElementCallback());
            getWindow().setSharedElementEnterTransition(new MaterialContainerTransform().
                    addTarget(android.R.id.content).
                    setDuration(300L));

            getWindow().setSharedElementReturnTransition(new MaterialContainerTransform()
                    .addTarget(android.R.id.content)
                    .setDuration(250L));
        }
        super.onCreate(savedInstanceState);
        mBinding = MediaPreviewBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        init();
    }

    private void init(){

    }
}
