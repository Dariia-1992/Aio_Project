package com.example.aio_project.utils;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.android.billingclient.api.AcknowledgePurchaseParams;
import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingFlowParams;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.android.billingclient.api.SkuDetails;
import com.android.billingclient.api.SkuDetailsParams;
import com.example.aio_project.MainActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.Nullable;

/**
 * Created by Alexey Matrosov on 16.11.2020.
 */

public class PurchaseManager implements PurchasesUpdatedListener {
    private static final String proSkuId = "sku_sub_pro_version"; // TODO: change to real

    private BillingClient client;
    private final Map<String, SkuDetails> skuDetailsMap = new HashMap<>();

    private boolean isProBought;

    public void init(Context context/*, Action2 onProBought*/) {
        //onBuyEvent = onProBought;

        client = BillingClient
                .newBuilder(context)
                .enablePendingPurchases()
                .setListener(this)
                .build();

        client.startConnection(new BillingClientStateListener() {
            @Override
            public void onBillingSetupFinished(BillingResult billingResult) {
                if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {

                    // Load all sku from store
                    loadSkuDetails();

                    // Check if Pro is already bought
                    checkIfBought(getPurchases());
                }
            }

            @Override
            public void onBillingServiceDisconnected() { }
        });
    }

    public void update() {
        checkIfBought(getPurchases());
    }

    public void buyProVersion(Activity activity) {
        if (!isProVersion())
            launchBilling(proSkuId, activity);
    }

    public boolean isProVersion() {
        return isProBought;
    }

    @Override
    public void onPurchasesUpdated(BillingResult billingResult, @Nullable List<Purchase> purchases) {
        if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
            checkIfBought(purchases);
        }
    }

    private void loadSkuDetails() {
        SkuDetailsParams.Builder builder = SkuDetailsParams.newBuilder();
        List<String> skuList = new ArrayList<>();
        skuList.add(proSkuId);

        builder.setSkusList(skuList);
        builder.setType(BillingClient.SkuType.SUBS);

        client.querySkuDetailsAsync(builder.build(), (billingResult, skuDetailsList) -> {
            if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK && skuDetailsList != null) {
                for (SkuDetails details : skuDetailsList)
                    skuDetailsMap.put(details.getSku(), details);
            }
        });
    }

    private List<Purchase> getPurchases() {
        Purchase.PurchasesResult result = client.queryPurchases(BillingClient.SkuType.SUBS);
        return result.getPurchasesList();
    }

    private void payProComplete() {
        boolean wasBought = isProBought;
        isProBought = true;

        // Send event to main manager
        /*if (!wasBought && onBuyEvent != null)
            onBuyEvent.start();*/
    }

    private void launchBilling(String skuId, Activity activity) {
        SkuDetails details = skuDetailsMap.get(skuId);
        if (details == null) {
            Log.e("PurchaseManager", "SKU not found");
            return;
        }

        BillingFlowParams params = BillingFlowParams.newBuilder()
                .setSkuDetails(details)
                .build();

        client.launchBillingFlow(activity, params);
    }

    private void checkIfBought(@Nullable List<Purchase> purchases) {
        if (purchases == null)
            return;

        for (Purchase purchase : purchases) {
            if (purchase.getPurchaseState() == Purchase.PurchaseState.PURCHASED) {
                if (!purchase.isAcknowledged()) {
                    AcknowledgePurchaseParams params = AcknowledgePurchaseParams.newBuilder()
                            .setPurchaseToken(purchase.getPurchaseToken())
                            .build();

                    client.acknowledgePurchase(params, billingResult -> { });
                }
            }

            if (TextUtils.equals(proSkuId, purchase.getSku())) {
                payProComplete();
                return;
            }
        }
    }
}
