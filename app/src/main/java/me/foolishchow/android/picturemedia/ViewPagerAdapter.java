package me.foolishchow.android.picturemedia;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;

import java.util.ArrayList;

import me.foolishchow.android.picturemedia.databinding.ViewDownBinding;

public class ViewPagerAdapter extends RecyclerView.Adapter<ViewPagerAdapter.ViewHolder<ViewDownBinding>> {

    private final ArrayList<String> urls;

    public ViewPagerAdapter(ArrayList<String> urls) {
        this.urls = urls;
    }

    @NonNull
    @Override
    public ViewHolder<ViewDownBinding> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewDownBinding inflate = ViewDownBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder<>(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder<ViewDownBinding> holder, int position) {
        holder.mBinding.viewer.getImageView().displayImage(urls.get(position));
        holder.mBinding.viewer.setUp();
    }

    @Override
    public int getItemCount() {
        return urls.size();
    }

    public static class ViewHolder<T extends ViewBinding> extends RecyclerView.ViewHolder{

        public final T mBinding;
        public ViewHolder(@NonNull T itemView) {
            super(itemView.getRoot());
            mBinding = itemView;

        }
    }
}
