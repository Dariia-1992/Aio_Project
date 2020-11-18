package com.example.aio_project;

import androidx.appcompat.app.AppCompatActivity;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import com.example.aio_project.model.DataRepository;
import com.example.aio_project.utils.LocalStorage;
import com.example.aio_project.utils.PurchaseManager;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

public class MainActivity extends AppCompatActivity {
    private InterstitialAd interstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);     // TODO: is it necessary?
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DataRepository.loadImages();

        interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId(getString(R.string.id_ads_interstitial));
        interstitialAd.loadAd(new AdRequest.Builder().build());
        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                interstitialAd.loadAd(new AdRequest.Builder().build());
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        PurchaseManager manager = getPurchaseManager();
        if (manager != null) {
            manager.update();
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }

    public void buyVip() {
        PurchaseManager manager = getPurchaseManager();
        if (manager != null) {
            manager.buyProVersion(this);
        }
    }

    public void showInterstitialAd() {
        if (interstitialAd != null && interstitialAd.isLoaded()) {
            PurchaseManager manager = getPurchaseManager();
            if (manager != null && !manager.isProVersion()) {
                interstitialAd.show();
            }

            LocalStorage.setOpensWithoutAd(this, 0);
        }
    }

    private PurchaseManager getPurchaseManager() {
        Application application = getApplication();
        if (application instanceof MainApplication) {
            return ((MainApplication) application).purchaseManager;
        }

        return null;
    }
}