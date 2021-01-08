package me.foolishchow.android.pictureMedia.select;

import com.luck.picture.lib.entity.LocalMedia;

/**
 * Description:
 * Author: foolishchow
 * Date: 8/1/2021 6:19 PM
 */
public class Media  {
    private LocalMedia localMedia;
    private String remoteUrl;

    public Media(String remoteUrl) {
        this.remoteUrl = remoteUrl;
    }

    public Media(LocalMedia localMedia) {
        this.localMedia = localMedia;
    }

    public Media(LocalMedia localMedia, String remoteUrl) {
        this.localMedia = localMedia;
        this.remoteUrl = remoteUrl;
    }

    public LocalMedia getLocalMedia() {
        return localMedia;
    }

    public void setLocalMedia(LocalMedia localMedia) {
        this.localMedia = localMedia;
    }

    public String getRemoteUrl() {
        return remoteUrl;
    }

    public void setRemoteUrl(String remoteUrl) {
        this.remoteUrl = remoteUrl;
    }
}
