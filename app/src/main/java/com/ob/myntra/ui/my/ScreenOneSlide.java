package com.ob.myntra.ui.my;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.heinrichreimersoftware.materialintro.app.SlideFragment;
import com.ob.myntra.R;

public class ScreenOneSlide extends SlideFragment {
    private CheckBox checkBox;

    public static ScreenOneSlide newInstance() {
        return new ScreenOneSlide();
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.one_slide, container, false);


        return root;
    }

}