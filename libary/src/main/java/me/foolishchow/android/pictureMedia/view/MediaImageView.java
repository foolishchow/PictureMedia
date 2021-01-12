package me.foolishchow.android.pictureMedia.view;

/**
 * Description:
 * Author: foolishchow
 * Date: 11/1/2021 10:53 AM
 */

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.os.Build;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

import java.util.Arrays;

import me.foolishchow.android.pictureMedia.R;


public class MediaImageView extends AppCompatImageView {

    private int cornerRadius; // 统一设置圆角半径，优先级高于单独设置每个角的半径
    private Xfermode xfermode;

    private int width;
    private int height;

    private float[] srcRadii;

    private RectF srcRectF; // 图片占的矩形区域

    private Paint paint;
    private Path path; // 用来裁剪图片的ptah
    private Path srcPath; // 图片区域大小的path

    public MediaImageView(Context context) {
        this(context, null);
    }

    public MediaImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MediaImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }


    private void init(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.MediaImageView, 0, 0);
        for (int i = 0; i < ta.getIndexCount(); i++) {
            int attr = ta.getIndex(i);
            if (attr == R.styleable.MediaImageView_ni_corner_radius) {
                cornerRadius = ta.getDimensionPixelSize(attr, cornerRadius);
            }
        }
        ta.recycle();

        srcRadii = new float[8];

        srcRectF = new RectF();

        paint = new Paint();
        path = new Path();

        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.O_MR1) {
            srcPath = new Path();
            xfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_IN);
        } else {
            xfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_OUT);
            srcPath = new Path();
        }

        calculateRadii();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
        initSrcRectF();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // 使用图形混合模式来显示指定区域的图片
        canvas.saveLayer(srcRectF, null, Canvas.ALL_SAVE_FLAG);

        super.onDraw(canvas);
        paint.reset();
        path.reset();
        srcPath.reset();
        path.addRoundRect(srcRectF, srcRadii, Path.Direction.CCW);

        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setXfermode(xfermode);
        if (!isInEditMode()) {
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.O_MR1) {
                canvas.drawPath(path, paint);
            } else {
                srcPath.addRect(srcRectF, Path.Direction.CCW);
                // 计算tempPath和path的差集
                srcPath.op(path, Path.Op.DIFFERENCE);
                canvas.drawPath(srcPath, paint);
            }
        }

        paint.setXfermode(null);
        // 恢复画布
        canvas.restore();
    }


    /**
     * 计算图片原始区域的RectF
     */
    private void initSrcRectF() {
        srcRectF.set(0, 0, width, height);
    }

    /**
     * 计算RectF的圆角半径
     */
    private void calculateRadii() {

        if (cornerRadius > 0) {
            Arrays.fill(srcRadii, cornerRadius);
        }
    }




}
