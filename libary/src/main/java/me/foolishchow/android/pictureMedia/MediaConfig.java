package me.foolishchow.android.pictureMedia;

import com.luck.picture.lib.engine.ImageEngine;

/**
 * Description:
 * Author: foolishchow
 * Date: 11/1/2021 11:03 AM
 */
public class MediaConfig {

    public static ImageEngine mEngine = GlideEngine.createGlideEngine();

    public static ImageEngine getEngine(){
        return mEngine;
    }
}
