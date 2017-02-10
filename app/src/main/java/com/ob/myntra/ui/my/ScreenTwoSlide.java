package com.ob.myntra.ui.my;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.heinrichreimersoftware.materialintro.app.SlideFragment;
import com.ob.myntra.R;

public class ScreenTwoSlide extends SlideFragment {
    private CheckBox checkBox;

    public static ScreenTwoSlide newInstance() {
        return new ScreenTwoSlide();
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.two_slide, container, false);


        return root;
    }

}