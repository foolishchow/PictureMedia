package me.foolishchow.android.pictureMedia;

import com.luck.picture.lib.entity.LocalMedia;

/**
 * Description:
 * Author: foolishchow
 * Date: 11/1/2021 10:11 AM
 */
public class MediaItem {

    /**
     *
     */
    private String previewPath;
    /**
     *
     */
    private String realPath;
    /**
     *
     */
    private String url;

    private int width;
    private int height;


    public MediaItem(String path, String realPath, String url, int width, int height) {
        this.previewPath = path;
        this.realPath = realPath;
        this.url = url;
        this.width = width;
        this.height = height;
    }

    public MediaItem(LocalMedia media) {
        previewPath = media.getPath();
        realPath = media.getRealPath();
        width = media.getWidth();
        height = media.getHeight();
    }

    public String getPreviewPath() {
        return previewPath;
    }

    public void setPreviewPath(String previewPath) {
        this.previewPath = previewPath;
    }

    public String getRealPath() {
        return realPath;
    }

    public void setRealPath(String realPath) {
        this.realPath = realPath;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
