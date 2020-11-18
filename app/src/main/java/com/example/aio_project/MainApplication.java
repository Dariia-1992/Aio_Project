package com.example.aio_project;

import android.app.Application;

import com.example.aio_project.utils.PurchaseManager;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.FirebaseApp;

import io.github.inflationx.calligraphy3.CalligraphyConfig;
import io.github.inflationx.calligraphy3.CalligraphyInterceptor;
import io.github.inflationx.viewpump.ViewPump;

/**
 * Created by Alexey Matrosov on 27.10.2020.
 */

public class MainApplication extends Application {
    public PurchaseManager purchaseManager = new PurchaseManager();

    @Override
    public void onCreate() {
        super.onCreate();

        ViewPump.init(ViewPump.builder()
                .addInterceptor(new CalligraphyInterceptor(new CalligraphyConfig.Builder().build()))
                .build());

        purchaseManager.init(this);

        MobileAds.initialize(this, initializationStatus -> {});
        FirebaseApp.initializeApp(this);
    }
}
