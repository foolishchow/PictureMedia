package me.foolishchow.android.picturemedia;

import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * Description:
 * Author: foolishchow
 * Date: 11/1/2021 7:05 PM
 */
public class MarginViewWrapper {
    private ViewGroup.MarginLayoutParams params;
    private View viewWrapper;

    MarginViewWrapper(View view) {
        this.viewWrapper = view;
        params = (ViewGroup.MarginLayoutParams) viewWrapper.getLayoutParams();
        if (params instanceof LinearLayout.LayoutParams) {
            ((LinearLayout.LayoutParams) params).gravity = Gravity.START;
        }
    }

    public int getWidth() {
        return params.width;
    }

    public int getHeight() {
        return params.height;
    }

    void setWidth(float width) {
        params.width = Math.round(width);
        viewWrapper.setLayoutParams(params);
    }

    void setHeight(float height) {
        params.height = Math.round(height);
        viewWrapper.setLayoutParams(params);
    }

    void setMarginTop(int m) {
        params.topMargin = m;
        viewWrapper.setLayoutParams(params);
    }

    void setMarginBottom(int m) {
        params.bottomMargin = m;
        viewWrapper.setLayoutParams(params);
    }

    public int getMarginTop() {
        return params.topMargin;
    }

    void setMarginRight(int mr) {
        params.rightMargin = mr;
        viewWrapper.setLayoutParams(params);
    }

    void setMarginLeft(int mr) {
        params.leftMargin = mr;
        viewWrapper.setLayoutParams(params);
    }

    int getMarginRight() {
        return params.rightMargin;
    }

    public int getMarginLeft() {
        return params.leftMargin;
    }

    int getMarginBottom() {
        return params.bottomMargin;
    }
}
