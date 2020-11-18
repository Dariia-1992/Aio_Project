package com.example.aio_project.fragment;

import android.Manifest;
import android.app.Activity;
import android.app.Application;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aio_project.BuildConfig;
import com.example.aio_project.MainActivity;
import com.example.aio_project.MainApplication;
import com.example.aio_project.R;
import com.example.aio_project.adapter.ImagePagerAdapter;
import com.example.aio_project.dialogs.DownloadCompleteDialog;
import com.example.aio_project.dialogs.NotFoundDialog;
import com.example.aio_project.model.Category;
import com.example.aio_project.model.DataRepository;
import com.example.aio_project.model.ModelDTO;
import com.example.aio_project.utils.ClipboardHelper;
import com.example.aio_project.utils.DownloadHelper;
import com.example.aio_project.utils.LocalStorage;
import com.example.aio_project.utils.PurchaseManager;
import com.example.aio_project.utils.TextUtils;
import com.google.android.ads.nativetemplates.NativeTemplateStyle;
import com.google.android.ads.nativetemplates.TemplateView;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.formats.UnifiedNativeAd;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.transition.TransitionManager;
import androidx.viewpager.widget.ViewPager;
import me.relex.circleindicator.CircleIndicator;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by Alexey Matrosov on 27.10.2020.
 */

public class DetailsFragment extends Fragment {
    private static final int RC_WRITE_PERMISSIONS = 1000;
    private static final long UPDATE_PERIOD = 250;

    private View view;
    private ModelDTO entry;

    private View downloadButton;
    private View installButton;
    private View copySeedContainer;
    private View progressContainer;
    private ProgressBar downloadingProgress;

    private View detailsContainer;
    private View readMoreButton;
    private TextView detailsText;

    private Handler handler = new Handler();
    private Runnable handlerRunnable;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_details, container, false);

        Bundle args = getArguments();
        if (args == null)
            return view;

        String id = DetailsFragmentArgs.fromBundle(args).getItemId();
        entry = DataRepository.findById(id);
        if (entry == null)
            return view;

        // Init toolbar
        TextView toolbarTitle = view.findViewById(R.id.toolbarTitle);
        View backButton = view.findViewById(R.id.toolbarIconBack);
        View vipButton = view.findViewById(R.id.vipIcon);

        toolbarTitle.setText(entry.getTitle());
        backButton.setOnClickListener(v -> requireActivity().onBackPressed());
        vipButton.setOnClickListener(vipClickListener);

        downloadButton = view.findViewById(R.id.downloadButtonContainer);
        installButton = view.findViewById(R.id.installButtonContainer);
        copySeedContainer = view.findViewById(R.id.copySeedContainer);
        progressContainer = view.findViewById(R.id.progressBarContainer);
        downloadingProgress = view.findViewById(R.id.progressBar);
        detailsContainer = view.findViewById(R.id.detailsContainer);
        readMoreButton = view.findViewById(R.id.detailsReadMore);
        detailsText = view.findViewById(R.id.detailsText);

        ViewPager viewPager = view.findViewById(R.id.viewPager);
        CircleIndicator indicator = view.findViewById(R.id.circleIndicator);

        List<String> images = new ArrayList<>();
        images.add(DataRepository.getThumbnailUrl(entry.getId()));

        if (entry.getLocalCategory() != Category.SKIN )
            images.addAll(DataRepository.getItemScreenshots(entry.getId()));

        viewPager.setAdapter(new ImagePagerAdapter(getContext(), images, entry.getLocalCategory() == Category.SKIN));
        indicator.setViewPager(viewPager);
        indicator.setVisibility(images.size() == 1 ? View.GONE : View.VISIBLE);

        initViews();
        initAd();

        // Show interstitial add
        int showsWithoutAdCount = LocalStorage.getOpensWithoutAd(requireContext());
        if (showsWithoutAdCount >= BuildConfig.SHOWS_WITHOUT_AD_COUNT || showsWithoutAdCount < 0) {
            if (getActivity() instanceof MainActivity)
                ((MainActivity) getActivity()).showInterstitialAd();
        } else
            LocalStorage.setOpensWithoutAd(requireContext(), showsWithoutAdCount + 1);

        // Callbacks
        View copySeedButton = view.findViewById(R.id.copySeedButton);
        copySeedButton.setOnClickListener(v -> {
            DataRepository.updateDownloadsCount(entry);

            ClipboardHelper.copy(requireContext(), entry.getSeed());
            Toast.makeText(requireContext(), "Seed was copied to clipboard", Toast.LENGTH_SHORT).show();
        });

        downloadButton.setOnClickListener(v -> {
            if (getActivity() instanceof MainActivity)
                ((MainActivity) getActivity()).showInterstitialAd();

            startDownloading();
        });
        installButton.setOnClickListener(v -> openDownloadedFile());

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        if (handlerRunnable != null)
            handler.post(handlerRunnable);
        else {
            // Check downloading status
            DownloadHelper.DownloadingState state = DownloadHelper.getDownloadingStatus(requireContext(), entry);
            updateState(state);

            // If downloading -- show progressBar
            if (state == DownloadHelper.DownloadingState.Downloading) {
                long id = LocalStorage.getIdForModInfo(requireContext(), entry);
                if (id != 0) {
                    handlerRunnable = () -> {
                        updateDownloadProgress(id);
                        if (handlerRunnable != null)
                            handler.postDelayed(handlerRunnable, UPDATE_PERIOD);
                    };
                    handler.post(handlerRunnable);
                }
            }
        }

        Context context = requireContext();
        context.registerReceiver(receiver, new IntentFilter((DownloadManager.ACTION_DOWNLOAD_COMPLETE)));
    }

    @Override
    public void onPause() {
        super.onPause();

        handler.removeCallbacks(handlerRunnable);

        Context context = requireContext();
        context.unregisterReceiver(receiver);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    private void initViews() {
        TextView seedView = view.findViewById(R.id.seedInfoText);
        if (entry.getLocalCategory() == Category.SEED && entry.getSeed() != null)
            seedView.setText(String.format("SEED : %s", entry.getSeed()));

        readMoreButton.setVisibility(View.GONE);
        detailsText.setVisibility(View.GONE);

        // Init text part
        String description = entry.getDescription();
        if (description != null) {
            readMoreButton.setVisibility(View.VISIBLE);
            TextUtils.setHtmlText(description, detailsText);
        }

        // Click listeners
        readMoreButton.setOnClickListener(readMoreButtonClickListener);
    }

    private void initAd() {
        PurchaseManager manager = getPurchaseManager();
        if (manager == null || manager.isProVersion())
            return;

        AdLoader adLoader = new AdLoader.Builder(requireContext(), getString(R.string.id_ads_native))
                .forUnifiedNativeAd(unifiedNativeAd -> {
                    if (isDetached()) {
                        unifiedNativeAd.destroy();
                        return;
                    }

                    NativeTemplateStyle styles = new NativeTemplateStyle.Builder()
                            .withMainBackgroundColor(new ColorDrawable(getResources().getColor(R.color.screen_background)))
                            .withCallToActionBackgroundColor(new ColorDrawable(getResources().getColor(R.color.ad_install)))
                            .build();

                    TemplateView template = view.findViewById(R.id.my_template);
                    template.setStyles(styles);
                    template.setNativeAd(unifiedNativeAd);
                    template.setVisibility(View.VISIBLE);

                }).build();

        adLoader.loadAd(new AdRequest.Builder().build());
    }

    private void updateState(DownloadHelper.DownloadingState state) {
        if (entry.getLocalCategory() == Category.SEED) {
            downloadButton.setVisibility(View.GONE);
            installButton.setVisibility(View.GONE);
            progressContainer.setVisibility(View.GONE);
            copySeedContainer.setVisibility(View.VISIBLE);
            return;
        }

        switch (state) {
            case NotDownloaded: {
                downloadButton.setVisibility(View.VISIBLE);
                installButton.setVisibility(View.GONE);
                progressContainer.setVisibility(View.GONE);
                copySeedContainer.setVisibility(View.GONE);
                break;
            }
            case Downloading: {
                downloadButton.setVisibility(View.GONE);
                installButton.setVisibility(View.GONE);
                progressContainer.setVisibility(View.VISIBLE);
                copySeedContainer.setVisibility(View.GONE);
                break;
            }
            case Downloaded: {
                downloadButton.setVisibility(View.GONE);
                installButton.setVisibility(View.VISIBLE);
                progressContainer.setVisibility(View.GONE);
                copySeedContainer.setVisibility(View.GONE);
                break;
            }
        }
    }

    @AfterPermissionGranted(RC_WRITE_PERMISSIONS)
    private void startDownloading() {
        Context context = getContext();
        if (context == null)
            return;

        if (EasyPermissions.hasPermissions(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            long id = DownloadHelper.downloadFile(requireContext(), DownloadHelper.getDownloadUrl(entry));
            if (id == 0) {
                Toast.makeText(context, "Download error. Please try later", Toast.LENGTH_LONG).show();
            } else {
                LocalStorage.saveDownloadId(context, entry, id);
                handlerRunnable = () -> {
                    updateDownloadProgress(id);
                    if (handlerRunnable != null)
                        handler.postDelayed(handlerRunnable, UPDATE_PERIOD);
                };
                handler.post(handlerRunnable);
                updateState(DownloadHelper.DownloadingState.Downloading);

                DataRepository.updateDownloadsCount(entry);
            }
        } else {
            EasyPermissions.requestPermissions(this, getString(R.string.rationale_write_permission), RC_WRITE_PERMISSIONS, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
    }

    private void updateDownloadProgress(long downloadId) {
        DownloadManager downloadManager = (DownloadManager) requireContext().getSystemService(Context.DOWNLOAD_SERVICE);
        DownloadManager.Query query = new DownloadManager.Query();
        query.setFilterById(downloadId);

        Cursor cursor = downloadManager.query(query);
        if (cursor.moveToFirst()) {
            int bytesDownloaded = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
            int bytesTotal = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));

            if (cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS)) == DownloadManager.STATUS_SUCCESSFUL) {
                handler.removeCallbacks(handlerRunnable);
                handlerRunnable = null;
                finishDownloading();
            }
            int progress = (int) ((bytesDownloaded * 100L) / bytesTotal);
            if (progress >= 100) {
                handler.removeCallbacks(handlerRunnable);
                handlerRunnable = null;
            }

            downloadingProgress.setProgress(progress);
        }
        cursor.close();
    }

    private void finishDownloading() {
        updateState(DownloadHelper.DownloadingState.Downloaded);
        downloadingProgress.setProgress(100);

         showDialogSuccessfully();
    }

    private void showDialogSuccessfully() {
        if (LocalStorage.isShowRateDialogAgain(requireContext())) {
            //listener = this::showDialogDownloadNeverComplete;
            DownloadCompleteDialog dialog = DownloadCompleteDialog.createDialog(/*listener*/);
            dialog.show(getChildFragmentManager(), DownloadCompleteDialog.class.getSimpleName());
        } else {
            //showDialogDownloadNeverComplete();
        }
    }

    private void openDownloadedFile() {
        String url = DownloadHelper.getDownloadUrl(entry);

        String fileName = Uri.parse(url).getLastPathSegment();
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), fileName);
        Uri fileUri = FileProvider.getUriForFile(requireContext(), BuildConfig.APPLICATION_ID + ".fileprovider", file);
        Intent myIntent = new Intent(Intent.ACTION_VIEW);
        myIntent.setData(fileUri);
        myIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        Intent j = Intent.createChooser(myIntent, "Choose Minecraft to install file:");

        PackageManager pm = requireActivity().getPackageManager();
        List<ResolveInfo> activities;

        if (android.os.Build.VERSION.SDK_INT >= 23) {
            activities = pm.queryIntentActivities(myIntent, PackageManager.MATCH_ALL);
        } else {
            activities = pm.queryIntentActivities(myIntent, 0);
        }

        if (activities.size() > 0) {
            startActivity(j);
        } else {
            showDialogNotFound();
        }
    }

    private void showDialogNotFound() {
        NotFoundDialog dialog = new NotFoundDialog();
        dialog.show(getChildFragmentManager(), NotFoundDialog.class.getSimpleName());
    }

    private PurchaseManager getPurchaseManager() {
        Activity activity = getActivity();
        if (activity == null)
            return null;

        Application application = activity.getApplication();
        if (application instanceof MainApplication) {
            return ((MainApplication) application).purchaseManager;
        }

        return null;
    }

    private final View.OnClickListener vipClickListener = v -> {
        Navigation.findNavController(view)
                .navigate(R.id.action_details_to_vip);
    };

    private final View.OnClickListener readMoreButtonClickListener = v -> {
        TransitionManager.beginDelayedTransition((RelativeLayout) view);

        detailsText.setVisibility(View.VISIBLE);
        readMoreButton.setVisibility(View.GONE);
    };

    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (!DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(action))
                return;

            long downloadId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, 0);
            long currentPageId = LocalStorage.getIdForModInfo(context, entry);

            // Check if finished file from current page
            if ((downloadId == currentPageId) && downloadId != 0) {
                DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
                DownloadManager.Query query = new DownloadManager.Query();
                query.setFilterById(downloadId);

                Cursor cursor = downloadManager.query(query);
                if (cursor.moveToFirst()) {
                    int columnIndex = cursor.getColumnIndex(DownloadManager.COLUMN_STATUS);
                    if (cursor.getInt(columnIndex) == DownloadManager.STATUS_SUCCESSFUL) {
                        finishDownloading();
                    }
                }
                handler.removeCallbacks(handlerRunnable);
                handlerRunnable = null;
                cursor.close();
            }
        }
    };
}
