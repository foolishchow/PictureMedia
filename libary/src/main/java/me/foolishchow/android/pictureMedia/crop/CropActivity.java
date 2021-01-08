package me.foolishchow.android.pictureMedia.crop;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.tools.PictureFileUtils;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.text.SimpleDateFormat;

public class CropActivity
        extends UCropActivity
{
    public static final String TAG = CropActivity.class.getName();

    public static final String MEDIA_DATA = "TopicPreviewCropActivity_mediaData";
    public static void crop(Activity context, LocalMedia media) {
        String suffix = media.getMimeType().replace("image/", ".");
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd_HHmmssSS");
        long millis = System.currentTimeMillis();

        File file = new File(
                PictureFileUtils.getDiskCacheDir(context),
                "ran_cao_topic_crop_" + sf.format(millis) + suffix
        );
        Uri uri;
        String path = media.getPath();
        if (path == null && media.getRealPath() != null) {
            path = media.getRealPath();
        }
        //@todo fix to Q
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
            uri = Uri.parse(path);
        } else {
            uri = Uri.fromFile(new File(path));
        }
        //配置基础参数
        UCrop uCrop = UCrop.of(uri, Uri.fromFile(file));

        //uCrop = basisConfig(uCrop);
        //uCrop = advancedConfig(uCrop);
        //手动设置基础选项
        uCrop.withMaxResultSize(1440, 2960);
        //这是目前最高分辨率的手机了吧（当然，跟图片分辨率没啥关系哈，主要是指呈现出来的分辨率）
        //手动设置高级选项
        UCrop.Options options = new UCrop.Options();
        options.setCompressionFormat(Bitmap.CompressFormat.PNG);
        options.setCompressionQuality(100);
        options.setFreeStyleCropEnabled(false);
        options.setShowCropGrid(false);

        options.withAspectRatio(1, 1);
        options.setToolbarTitle("封面预览");
        options.setAllowedGestures(com.yalantis.ucrop.UCropActivity.ALL,
                com.yalantis.ucrop.UCropActivity.ALL,com.yalantis.ucrop.UCropActivity.ALL);
        options.setRootViewBackgroundColor(Color.BLACK);
        options.setDimmedLayerColor(Color.argb(50, 0x00, 0x00, 0x00));
        options.setCropFrameColor(Color.argb(150,0xff,0xff,0xff));
        uCrop = uCrop.withOptions(options);

        //跳转到我们的activity，而不是用uCrop自带的跳转到UCropActivity
        Intent uCropIntent = uCrop.getIntent(context);
        uCropIntent.putExtra(MEDIA_DATA,media);
        uCropIntent.setClass(context, CropActivity.class);
        context.startActivityForResult(uCropIntent, UCrop.REQUEST_CROP);
    }

    @Override
    public void onCreate(
            @Nullable Bundle savedInstanceState
    ) {
        super.onCreate(savedInstanceState);
        parseIntent();
    }

    private LocalMedia media;

    private void parseIntent() {
        Intent intent = getIntent();
        if (intent == null) return;
        media = intent.getExtras().getParcelable(MEDIA_DATA);
    }


    @Override
    protected void setResultUri(Uri uri, float resultAspectRatio, int offsetX, int offsetY, int imageWidth, int imageHeight) {
        media.setCut(true);
        media.setCutPath(uri.getPath());
        media.setWidth(imageWidth);
        media.setHeight(imageHeight);

        setResult(RESULT_OK, new Intent().putExtra(MEDIA_DATA, media));
    }
}
