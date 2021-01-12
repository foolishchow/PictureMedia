package me.foolishchow.android.picture_preview;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

/**
 * Description:
 * Author: foolishchow
 * Date: 11/1/2021 4:55 PM
 */
public class PreviewViewPager extends ViewPager {
    public PreviewViewPager(@NonNull Context context) {
        super(context);
    }

    public PreviewViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }
}
