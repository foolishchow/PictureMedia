package me.foolishchow.android.pictureMedia.view;

import android.view.View;

/**
 * Description:
 * Author: foolishchow
 * Date: 11/1/2021 2:16 PM
 */
public class ClickUtils {
    private static long lastClickResponseStamp = 0l;
    private static int lastResponseId = -1;

    public static boolean accept(View view){
        int id = view.getId();
        long current = System.currentTimeMillis();
        if(lastResponseId != id){
            lastClickResponseStamp = current;
            lastResponseId = id;
            return  true;
        }else if(current - lastClickResponseStamp> 1350L){
            lastClickResponseStamp = current;
            lastResponseId = id;
            return true;
        }
        return false;
    }

    public static boolean aborted(View view){
        return !accept(view);
    }
}
