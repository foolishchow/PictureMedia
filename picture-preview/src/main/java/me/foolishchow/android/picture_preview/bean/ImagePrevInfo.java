package me.foolishchow.android.picture_preview.bean;

/**
 * Description:
 * Author: foolishchow
 * Date: 11/1/2021 4:46 PM
 */
public class ImagePrevInfo {
    private String thumbnailUrl;
    private String originUrl;

    public ImagePrevInfo(String thumbnailUrl, String originUrl) {
        this.thumbnailUrl = thumbnailUrl;
        this.originUrl = originUrl;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getOriginUrl() {
        return originUrl;
    }

    public void setOriginUrl(String originUrl) {
        this.originUrl = originUrl;
    }
}
