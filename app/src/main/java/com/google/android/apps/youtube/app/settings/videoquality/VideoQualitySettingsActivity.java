package com.google.android.apps.youtube.app.settings.videoquality;

import static app.revanced.integrations.shared.utils.ResourceUtils.identifier;
import static app.revanced.integrations.shared.utils.Utils.getChildView;
import static app.revanced.integrations.youtube.utils.ThemeUtils.setBackButtonDrawable;

import android.app.Activity;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Objects;

import app.revanced.integrations.shared.utils.Logger;
import app.revanced.integrations.shared.utils.ResourceType;
import app.revanced.integrations.youtube.settings.preference.ReVancedPreferenceFragment;
import app.revanced.integrations.youtube.settings.preference.ReturnYouTubeDislikePreferenceFragment;
import app.revanced.integrations.youtube.settings.preference.SponsorBlockPreferenceFragment;
import app.revanced.integrations.youtube.utils.ThemeUtils;

/**
 * @noinspection ALL
 */
public class VideoQualitySettingsActivity extends Activity {

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        try {
            setTheme(ThemeUtils.getThemeId());
            setContentView(identifier("revanced_settings_with_toolbar", ResourceType.LAYOUT));

            final int fragmentId = identifier("revanced_settings_fragments", ResourceType.ID);
            final ViewGroup toolBar = Objects.requireNonNull(findViewById(identifier("revanced_toolbar", ResourceType.ID)));

            setBackButton(toolBar);

            PreferenceFragment fragment;
            String toolbarTitleResourceName;
            String dataString = Objects.requireNonNull(getIntent().getDataString());
            switch (dataString) {
                case "revanced_sb_settings_intent" -> {
                    fragment = new SponsorBlockPreferenceFragment();
                    toolbarTitleResourceName = "revanced_sb_settings_title";
                }
                case "revanced_ryd_settings_intent" -> {
                    fragment = new ReturnYouTubeDislikePreferenceFragment();
                    toolbarTitleResourceName = "revanced_ryd_settings_title";
                }
                case "revanced_extended_settings_intent" -> {
                    fragment = new ReVancedPreferenceFragment();
                    toolbarTitleResourceName = "revanced_extended_settings_title";
                }
                default -> {
                    Logger.printException(() -> "Unknown setting: " + dataString);
                    return;
                }
            }

            setToolbarTitle(toolBar, toolbarTitleResourceName);
            getFragmentManager()
                    .beginTransaction()
                    .replace(fragmentId, fragment)
                    .commit();
        } catch (Exception ex) {
            Logger.printException(() -> "onCreate failure", ex);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void setBackButton(ViewGroup toolBar) {
        ImageButton imageButton = Objects.requireNonNull(getChildView(toolBar, view -> view instanceof ImageButton));
        imageButton.setOnClickListener(view -> VideoQualitySettingsActivity.this.onBackPressed());
        setBackButtonDrawable(imageButton);
    }

    private void setToolbarTitle(ViewGroup toolBar, String toolbarTitleResourceName) {
        TextView toolbarTextView = Objects.requireNonNull(getChildView(toolBar, view -> view instanceof TextView));
        toolbarTextView.setText(identifier(toolbarTitleResourceName, ResourceType.STRING));
    }
}