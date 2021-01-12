package me.foolishchow.android.pictureMedia;

import android.app.Activity;
import android.util.Log;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.listener.OnResultCallbackListener;

import java.util.List;

import me.foolishchow.android.pictureMedia.crop.CropActivity;

/**
 * Description:
 * Author: foolishchow
 * Date: 6/1/2021 9:18 AM
 */
public class MediaUtils {

    private static final String TAG = "MediaUtils";

    public static void selectSingleImage(
            Activity activity
    ) {
        PictureSelector.create(activity)
                .openGallery(PictureMimeType.ofImage())// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                .imageEngine(GlideEngine.createGlideEngine())// 外部传入图片加载引擎，必传项
                .isMaxSelectEnabledMask(true)// 选择数到了最大阀值列表是否启用蒙层效果
                .isSingleDirectReturn(true)
                // 最大图片选择数量
                .selectionMode(PictureConfig.SINGLE)
                .forResult(new OnResultCallbackListener<LocalMedia>() {
                    @Override
                    public void onResult(List<LocalMedia> result) {
                        Log.e(TAG, String.valueOf(result.size()));
                    }

                    @Override
                    public void onCancel() {
                        Log.e(TAG, "cancel");
                    }
                });
    }

    public static void selectAndCropSingleImage(
            final Activity activity,
            int width, int height
    ) {
        PictureSelector.create(activity)
                .openGallery(PictureMimeType.ofImage())// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                .imageEngine(GlideEngine.createGlideEngine())// 外部传入图片加载引擎，必传项
                .isWithVideoImage(true)// 图片和视频是否可以同选,只在ofAll模式下有效
                .isMaxSelectEnabledMask(true)// 选择数到了最大阀值列表是否启用蒙层效果
                .isSingleDirectReturn(true)
                // 最大图片选择数量
                .selectionMode(PictureConfig.SINGLE)
                .forResult(new OnResultCallbackListener<LocalMedia>() {
                    @Override
                    public void onResult(List<LocalMedia> result) {
                        if (result == null || result.size() == 0) return;
                        Crop(activity, result.get(0));
                    }

                    @Override
                    public void onCancel() {
                        Log.e(TAG, "cancel");
                    }
                });
    }


    private static void Crop(Activity activity, LocalMedia media) {
        CropActivity.crop(activity, media);
    }


}
