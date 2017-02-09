package com.ob.myntra.ui;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

/***
 * Created by Hemant4524 on 24/Jan/2017.
 */

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
    }
}
