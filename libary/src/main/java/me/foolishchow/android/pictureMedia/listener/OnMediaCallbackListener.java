package me.foolishchow.android.pictureMedia.listener;

import java.util.List;

/**
 * Description:
 * Author: foolishchow
 * Date: 11/1/2021 10:23 AM
 */
public interface OnMediaCallbackListener<T> {
    /**
     * return LocalMedia result
     *
     * @param result
     */
    void onPictureMediaSelected(List<T> result);


    void onPictureMediaSelectCancel();
}
