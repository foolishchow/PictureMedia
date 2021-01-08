package me.foolishchow.android.pictureMedia;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Description:
 * Author: foolishchow
 * Date: 8/1/2021 6:12 PM
 */
public class PictureListSelectView extends RecyclerView {
    public PictureListSelectView(@NonNull Context context) {
        this(context, null, 0);
    }

    public PictureListSelectView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PictureListSelectView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


}
