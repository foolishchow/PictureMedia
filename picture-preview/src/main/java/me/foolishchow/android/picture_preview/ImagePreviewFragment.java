package me.foolishchow.android.picture_preview;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import me.foolishchow.android.picture_preview.databinding.MediaPreviewFragmentBinding;

/**
 * Description:
 * Author: foolishchow
 * Date: 11/1/2021 4:52 PM
 */
public class ImagePreviewFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }


    private MediaPreviewFragmentBinding mView;

}
