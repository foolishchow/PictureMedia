package me.foolishchow.android.pictureMedia.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.engine.ImageEngine;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.listener.OnResultCallbackListener;

import java.util.ArrayList;
import java.util.List;

import me.foolishchow.android.pictureMedia.GlideEngine;
import me.foolishchow.android.pictureMedia.MediaConfig;
import me.foolishchow.android.pictureMedia.MediaItem;
import me.foolishchow.android.pictureMedia.R;

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

        mAdapter = new Adapter(context,9);
        setAdapter(mAdapter);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 3);
        setLayoutManager(gridLayoutManager);

        int unit = dp2px(context, 5);
        addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull State state) {
                outRect.left = unit;
                outRect.top = unit;
                outRect.right = unit;
                outRect.bottom = unit;
            }
        });


    }

    /**
     * dip转pix
     *
     * @param context
     * @param dp
     * @return
     */
    public static int dp2px(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }


    private boolean mShowPick = true;
    private Adapter mAdapter;

    public static class Adapter
            extends RecyclerView.Adapter<ViewHolder>
            implements View.OnClickListener{
        private List<MediaItem> mList = new ArrayList<>();

        private ImageEngine mEngine = MediaConfig.getEngine();
        private int mMaxCount = 9;
        private Context mContext;
        public Adapter(Context context,int maxCount) {
            mContext = context;
            mMaxCount = maxCount;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View inflate = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.media_item, null, false);
            ViewHolder holder = new ViewHolder(inflate);
            holder.mWrap.setOnClickListener(this);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

            holder.mWrap.setTag(position);
            if (position > mList.size() - 1) {
                holder.mImage.setImageResource(R.mipmap.media_icon_add);
                return;
            }
            MediaItem mediaItem = mList.get(position);
            if (mediaItem.getPreviewPath() != null) {
                mEngine.loadImage(holder.mImage.getContext(), mediaItem.getPreviewPath(), holder.mImage);
            } else if (mediaItem.getUrl() != null) {
                mEngine.loadImage(holder.mImage.getContext(), mediaItem.getUrl(), holder.mImage);
            }
        }


        @Override
        public int getItemCount() {
            if (mList == null || mList.size() == 0) {
                return 1;
            }
            if (mList.size() < mMaxCount) {
                return mList.size() + 1;
            }
            return mList.size();
        }

        private boolean isAddItem(int position) {
            return position > mList.size() - 1;
        }

        @Override
        public void onClick(View v) {
            if(ClickUtils.aborted(v)) return;
            Integer tag = (Integer) v.getTag();
            if(tag == null) return;
            if(isAddItem(tag)){
                selectPic();
            }else{

            }
        }

        private void selectPic() {
            int size = 0;
            if (mList.size() <= mMaxCount) {
                size = mMaxCount - mList.size();
            }

            PictureSelector.create((Activity) mContext)
                    .openGallery(PictureMimeType.ofImage())// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                    .imageEngine(GlideEngine.createGlideEngine())// 外部传入图片加载引擎，必传项
                    .isMaxSelectEnabledMask(true)// 选择数到了最大阀值列表是否启用蒙层效果
                    .maxSelectNum(size)// 最大图片选择数量
                    .forResult(new OnResultCallbackListener<LocalMedia>() {
                        @Override
                        public void onResult(List<LocalMedia> result) {
                            for (LocalMedia localMedia : result) {
                                mList.add(new MediaItem(localMedia));
                            }
                            notifyDataSetChanged();
                        }

                        @Override
                        public void onCancel() {

                        }
                    });
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView mImage;
        private ViewGroup mWrap;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mWrap = itemView.findViewById(R.id.wrap);
            mImage = itemView.findViewById(R.id.imageView);
        }

    }
}
