package com.example.aio_project;

import android.app.Application;

import io.github.inflationx.calligraphy3.CalligraphyConfig;
import io.github.inflationx.calligraphy3.CalligraphyInterceptor;
import io.github.inflationx.viewpump.ViewPump;

/**
 * Created by Alexey Matrosov on 27.10.2020.
 */

public class MainApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        ViewPump.init(ViewPump.builder()
                .addInterceptor(new CalligraphyInterceptor(
                        new CalligraphyConfig.Builder()
//                                .setDefaultFontPath("fonts/PIXELADE.TTF") // TODO: set default
//                                .setFontAttrId(R.attr.fontPath)
                                .build()))
                .build());

//        MobileAds.initialize(this, initializationStatus -> {});
//        FirebaseApp.initializeApp(this);
    }
}
