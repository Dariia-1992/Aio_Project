package com.example.aio_project.utils;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.example.aio_project.MainApplication;
import com.example.aio_project.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.appopen.AppOpenAd;

import java.util.Date;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ProcessLifecycleOwner;

import static androidx.lifecycle.Lifecycle.Event.ON_START;

/**
 * Created by Alexey Matrosov on 26.11.2020.
 */

public class AppOpenManager implements LifecycleObserver, Application.ActivityLifecycleCallbacks {
    private AppOpenAd appOpenAd = null;
    private boolean isShowingAd = false;
    private long loadTime = 0;

    private MainApplication application;
    private Activity activity;

    public void init(MainApplication application) {
        this.application = application;
        application.registerActivityLifecycleCallbacks(this);
        ProcessLifecycleOwner.get().getLifecycle().addObserver(this);
    }

    public void fetchAd() {
        if (isAdAvailable()) {
            return;
        }

        AppOpenAd.AppOpenAdLoadCallback loadCallback = new AppOpenAd.AppOpenAdLoadCallback() {
            @Override
            public void onAppOpenAdLoaded(AppOpenAd appOpenAd) {
                AppOpenManager.this.appOpenAd = appOpenAd;
                AppOpenManager.this.loadTime = (new Date()).getTime();
            }
        };

        AdRequest request = getAdRequest();
        AppOpenAd.load(application,
                application.getString(R.string.id_ads_open),
                request,
                AppOpenAd.APP_OPEN_AD_ORIENTATION_PORTRAIT,
                loadCallback);
    }

    public void showAdIfAvailable() {
        if (application.purchaseManager.isProVersion()) {
            return;
        }

        if (!isShowingAd && isAdAvailable()) {
            FullScreenContentCallback fullScreenContentCallback = new FullScreenContentCallback() {
                @Override
                public void onAdShowedFullScreenContent() {
                    AppOpenManager.this.isShowingAd = true;
                }

                @Override
                public void onAdDismissedFullScreenContent() {
                    AppOpenManager.this.appOpenAd = null;
                    AppOpenManager.this.isShowingAd = false;
                    fetchAd();
                }
            };

            appOpenAd.show(activity, fullScreenContentCallback);
        } else {
            fetchAd();
        }
    }

    private AdRequest getAdRequest() { return new AdRequest.Builder().build(); }

    // region Application.ActivityLifecycleCallbacks

    @Override public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle bundle) { }
    @Override public void onActivityStarted(@NonNull Activity activity) { this.activity = activity; }
    @Override public void onActivityResumed(@NonNull Activity activity) { this.activity = activity; }
    @Override public void onActivityPaused(@NonNull Activity activity) { }
    @Override public void onActivityStopped(@NonNull Activity activity) { }
    @Override public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle bundle) { }
    @Override public void onActivityDestroyed(@NonNull Activity activity) { this.activity = null; }

    // endregion

    // region LifecycleObserver

    @OnLifecycleEvent(ON_START)
    public void onStart() {
        showAdIfAvailable();
    }

    // endregion

    // region Helpers

    private boolean wasLoadTimeLessThanNMinutesAgo(long minutes) {
        long dateDifference = (new Date()).getTime() - this.loadTime;
        long numMilliSecondsPerHour = 60000;
        return (dateDifference < (numMilliSecondsPerHour * minutes));
    }

    public boolean isAdAvailable() {
        return appOpenAd != null /*&& wasLoadTimeLessThanNMinutesAgo(1)*/;
    }

    // endregion
}
