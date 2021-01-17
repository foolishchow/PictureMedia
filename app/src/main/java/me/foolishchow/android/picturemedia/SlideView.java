package me.foolishchow.android.picturemedia;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;

import me.panpf.sketch.SketchImageView;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

/**
 * Description:
 * Author: foolishchow
 * Date: 11/1/2021 6:43 PM
 */
public class SlideView extends FrameLayout {

    private final int MAX_TRANSLATE_Y;
    private final int screenWidth;
    private final int screenHeight;
    private final int MAX_Y;
    private final SketchImageView mImageView;
    private final MarginViewWrapper mMarginHelper;
    private float mDownX;
    private float mDownY;
    private float mTranslateX;
    private float mTranslateY;
    private int mLastY;
    private float mYDistanceTraveled;
    private float mXDistanceTraveled;
    private boolean isAnimating = false;
    int touchSlop = ViewConfiguration.getTouchSlop();
    private boolean isDrag = false;
    private float mAlpha = 1;
    private View mBackground;


    public SlideView(@NonNull Context context) {
        this(context, null, 0);
    }

    public SlideView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlideView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setBackgroundColor(Color.TRANSPARENT);
        screenWidth = getResources().getDisplayMetrics().widthPixels;
        screenHeight = getResources().getDisplayMetrics().heightPixels;
        MAX_TRANSLATE_Y = screenHeight / 6;
        MAX_Y = screenHeight - screenHeight / 8;

        mBackground = new View(context);
        mBackground.setBackgroundColor(Color.BLACK);
        addView(mBackground);

        mImageView = new SketchImageView(context);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT);
        layoutParams.gravity = Gravity.CENTER;
        addView(mImageView, layoutParams);
        mMarginHelper = new MarginViewWrapper(mImageView);
        createImageView();
    }

    public SketchImageView getImageView() {
        return mImageView;
    }

    private boolean loaded = false;

    public void setUp() {
        if (loaded) return;
        loaded = true;
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT);
        layoutParams.gravity = Gravity.CENTER;
        mImageView.setLayoutParams(layoutParams);
        mImageView.setZoomEnabled(true);
        mImageView.redisplay(null);
    }

    private void createImageView() {
        mImageView.setBackgroundColor(Color.TRANSPARENT);
//        Glide.with(mImageView)
//                .asBitmap()
//                .load("https://jiangruihao-pub.oss-cn-hangzhou.aliyuncs.com/defaults/aaaaa.jpg")
//                .into(new CustomTarget<Bitmap>() {
//                    @Override
//                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
//                        mImageView.setImageBitmap(resource);
//                    }
//
//                    @Override
//                    public void onLoadCleared(@Nullable Drawable placeholder) {
//
//                    }
//                });
        //mImageView.displayImage("https://jiangruihao-pub.oss-cn-hangzhou.aliyuncs.com/defaults/aaaaa.jpg");
//        mImageView.scrollTo(0, 0);

    }


    private boolean isMultiFinger;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        int y = (int) event.getY();
        SketchImageView sketchImageView = mImageView;
        //如果是长图  没有缩放到最小,则不给事件
        if (sketchImageView.getZoomer() != null) {
            if (sketchImageView.getZoomer().getZoomScale() - 0.00001f > sketchImageView.getZoomer().getMinZoomScale()) {
                Log.e("sketchImageView", String.format("scale %.9f min %.9f ",
                        sketchImageView.getZoomer().getZoomScale(),
                        sketchImageView.getZoomer().getMinZoomScale()));
                return super.dispatchTouchEvent(event);
            }
        }

        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_POINTER_DOWN:
                isMultiFinger = true;
                break;
            case MotionEvent.ACTION_DOWN:
                mDownX = event.getX();
                mDownY = event.getY();
                mTranslateX = 0;
                mTranslateY = 0;
                break;
            case MotionEvent.ACTION_MOVE:
                if (event.getPointerCount() > 1) {
                    isMultiFinger = true;
                    break;
                }
                if (isAnimating) {
                    break;
                }
                float moveX = event.getX();
                float moveY = event.getY();
                mTranslateX = moveX - mDownX;
                mTranslateY = moveY - mDownY;
                mYDistanceTraveled += Math.abs(mTranslateY);
                mXDistanceTraveled += Math.abs(mTranslateX);

                if (!isDrag) {
                    //如果滑动距离不足,则不需要事件
                    if (Math.abs(mYDistanceTraveled) < touchSlop || (Math.abs(mTranslateX) > Math.abs(mYDistanceTraveled))) {
                        mYDistanceTraveled = 0;
                        break;
                    }
                    isDrag = true;
                }

                int dy = y - mLastY;
                int newMarY = mMarginHelper.getMarginTop() + dy;

                //根据触摸点的Y坐标和屏幕的比例来更改透明度
                float alphaChangePercent = mTranslateY / screenHeight;
                mAlpha = 1 - alphaChangePercent;
                dragAnd2Normal(newMarY);
                break;
            case MotionEvent.ACTION_UP:
                if (isAnimating) {
                    break;
                }
                //如果滑动距离不足,则不需要事件
                if (Math.abs(mYDistanceTraveled) < touchSlop || (Math.abs(mYDistanceTraveled) > Math.abs(mYDistanceTraveled) && !isDrag)) {
                    isMultiFinger = false;
                    break;
                }
                //防止滑动时出现多点触控
                if (isMultiFinger && !isDrag) {
                    isMultiFinger = false;
                    break;
                }
                isMultiFinger = false;
                //if (mTranslateY > MAX_TRANSLATE_Y) {
                //    backToMin();
                //} else {
                //    backToNormal();
                //}
                float alpha = mBackground.getAlpha();
                if(alpha < .8f){
                    backToMin();
                }else{
                    backToNormal();
                }
                isDrag = false;
                mYDistanceTraveled = 0;
                break;
        }

        mLastY = y;
        return super.dispatchTouchEvent(event);
    }

    private void backToMin() {
        ((AppCompatActivity)getContext()).onBackPressed();
    }

    private void backToNormal() {
        //isAnimating = true;
        mBackground.setAlpha(1f);
        mMarginHelper.setHeight(MATCH_PARENT);
        mMarginHelper.setWidth(MATCH_PARENT);
        mMarginHelper.setMarginTop(0);
        mMarginHelper.setMarginLeft(0);
        //mImageView.setLayoutParams(new ViewGroup.LayoutParams(MATCH_PARENT,MATCH_PARENT));
        //ValueAnimator valueAnimator = ValueAnimator.ofFloat(imageWrapper.getMarginTop(), targetImageTop);
        //valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
        //    @Override
        //    public void onAnimationUpdate(ValueAnimator animation) {
        //        float value = (float) animation.getAnimatedValue();
        //        dragAnd2Normal(value, false);
        //    }
        //});
        //valueAnimator.addListener(new AnimatorListenerAdapter() {
        //    @Override
        //    public void onAnimationEnd(Animator animation) {
        //        isAnimating = false;
        //    }
        //});
        //valueAnimator.setDuration(animationDuration).start();
        //if (onReleaseListener != null) {
        //    onReleaseListener.onRelease(true, false);
        //}
    }

    private final float DEFAULT_MIN_SCALE = 0.3f;

    void dragAnd2Normal(float currentY) {
        float nodeMarginPercent = (MAX_Y - currentY + 0) / MAX_Y;
        float widthPercent = DEFAULT_MIN_SCALE + (1f - DEFAULT_MIN_SCALE) * nodeMarginPercent;
        int originLeftOffset = 0;
        int leftOffset = (int) ((screenWidth - screenWidth * widthPercent) / 2);

        float left;
        if (nodeMarginPercent >= 1) {
            //处于拖动到正常大小上方
            mMarginHelper.setWidth(screenWidth);
            mMarginHelper.setHeight(screenHeight);
            left = mTranslateX;
            mAlpha = nodeMarginPercent;
        } else {
            mMarginHelper.setWidth(screenWidth * widthPercent);
            mMarginHelper.setHeight(screenHeight * widthPercent);
            left = mTranslateX + leftOffset;
        }
        //if (!isDrag) {
        //    left = (currentY - 0) / (releaseY - 0) * releaseLeft;
        //}
        mBackground.setAlpha(mAlpha);
        mMarginHelper.setMarginLeft(Math.round(left + originLeftOffset));
        mMarginHelper.setMarginTop((int) (currentY));
    }


    //public void backToMin() {
    //    if (isAnimating) {
    //        return;
    //    }
    //    //到最小时,先把imageView的大小设置为imageView可见的大小,而不是包含黑色空隙部分
    //    if (isPhoto) {
    //        // 注意:这里 imageWrapper.getHeight() 获取的高度 是经过拖动缩放后的
    //        float draggingToReleaseScale = imageWrapper.getHeight() / (float) screenHeight;
    //        if (imageWrapper.getHeight() != imageHeightOfAnimatorEnd) {
    //            releaseHeight = (int) (draggingToReleaseScale * imageHeightOfAnimatorEnd);
    //        } else {
    //            releaseHeight = imageWrapper.getHeight();
    //        }
    //        if (imageWrapper.getWidth() != imageWidthOfAnimatorEnd) {
    //            releaseWidth = (int) (draggingToReleaseScale * imageWidthOfAnimatorEnd);
    //        } else {
    //            releaseWidth = imageWrapper.getWidth();
    //        }
    //        if (imageWrapper.getMarginTop() != imageTopOfAnimatorEnd) {
    //            releaseY = imageWrapper.getMarginTop() + (int) (draggingToReleaseScale * imageTopOfAnimatorEnd);
    //        } else {
    //            releaseY = imageWrapper.getMarginTop();
    //        }
    //        if (imageWrapper.getMarginLeft() != imageLeftOfAnimatorEnd) {
    //            releaseLeft = imageWrapper.getMarginLeft() + (int) (draggingToReleaseScale * imageLeftOfAnimatorEnd);
    //        } else {
    //            releaseLeft = imageWrapper.getMarginLeft();
    //        }
    //        imageWrapper.setWidth(releaseWidth);
    //        imageWrapper.setHeight(releaseHeight);
    //        imageWrapper.setMarginTop((int) releaseY);
    //        imageWrapper.setMarginLeft(releaseLeft);
    //    } else {
    //        releaseLeft = imageWrapper.getMarginLeft();
    //        releaseY = imageWrapper.getMarginTop();
    //        releaseWidth = imageWrapper.getWidth();
    //        releaseHeight = imageWrapper.getHeight();
    //    }
    //
    //    if ((isLongHeightImage || isLongWidthImage) && getContentView() instanceof SketchImageView) {
    //        SketchImageView sketchImageView = (SketchImageView) getContentView();
    //        if (sketchImageView.getZoomer() != null) {
    //            //如果是长图 则重新更改宽高 因为长图缩放到最小时需要大小变化
    //            float ratio = sketchImageView.getZoomer().getZoomScale() / sketchImageView.getZoomer().getMaxZoomScale();
    //            if (isLongHeightImage) {
    //                int tempWidth = (int) (screenWidth * ratio);
    //                releaseLeft = releaseLeft + (releaseWidth - tempWidth) / 2;
    //                releaseWidth = tempWidth;
    //            } else {
    //                int tempHeight = (int) (screenHeight * ratio);
    //                releaseY = releaseY + (releaseHeight - tempHeight) / 2;
    //                releaseHeight = tempHeight;
    //            }
    //            changeImageViewToCenterCrop();
    //        }
    //    }
    //    if (errorImag){
    //
    //        changeImageViewToCenterCrop();
    //        ValueAnimator valueAnimator = ValueAnimator.ofFloat(releaseY, mOriginTop);
    //        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
    //            @Override
    //            public void onAnimationUpdate(ValueAnimator animation) {
    //                float value = (float) animation.getAnimatedValue();
    //                min2NormalAndDrag2Min(value, releaseY, mOriginTop, releaseLeft, mOriginLeft, releaseWidth, mOriginWidth, releaseHeight, mOriginHeight);
    //            }
    //        });
    //        valueAnimator.setDuration(animationDuration).start();
    //
    //    }
    //
    //
    //    if (onReleaseListener != null) {
    //        onReleaseListener.onRelease(false, true);
    //    }
    //    changeBackgroundViewAlpha(true);
    //}
    //
    //
    //void backToNormal() {
    //
    //    isAnimating = true;
    //    releaseLeft = imageWrapper.getMarginLeft() - (screenWidth - targetImageWidth) / 2;
    //    releaseY = imageWrapper.getMarginTop();
    //    ValueAnimator valueAnimator = ValueAnimator.ofFloat(imageWrapper.getMarginTop(), targetImageTop);
    //    valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
    //        @Override
    //        public void onAnimationUpdate(ValueAnimator animation) {
    //            float value = (float) animation.getAnimatedValue();
    //            dragAnd2Normal(value, false);
    //        }
    //    });
    //    valueAnimator.addListener(new AnimatorListenerAdapter() {
    //        @Override
    //        public void onAnimationEnd(Animator animation) {
    //            isAnimating = false;
    //        }
    //    });
    //    valueAnimator.setDuration(animationDuration).start();
    //    if (onReleaseListener != null) {
    //        onReleaseListener.onRelease(true, false);
    //    }
    //    changeBackgroundViewAlpha(false);
    //}
}
