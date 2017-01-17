package com.ob.myntra.ui.vo;

import android.graphics.drawable.Drawable;

/***
 * Created by Hemant4524 on 16/Jan/2017.
 */

public class HorizontalListVo {

    private String name;
    private Drawable drawable;

    public HorizontalListVo(String name, Drawable drawable) {
        this.name = name;
        this.drawable = drawable;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Drawable getDrawable() {
        return drawable;
    }

    public void setDrawable(Drawable drawable) {
        this.drawable = drawable;
    }
}
