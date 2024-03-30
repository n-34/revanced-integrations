package app.revanced.integrations.youtube.settings.preference;

import static app.revanced.integrations.shared.settings.preference.AbstractPreferenceFragment.showRestartDialog;
import static app.revanced.integrations.shared.utils.StringRef.str;
import static app.revanced.integrations.youtube.utils.ExtendedUtils.getStringArray;
import static app.revanced.integrations.youtube.utils.ExtendedUtils.isPackageEnabled;
import static app.revanced.integrations.youtube.utils.ExtendedUtils.isSpoofingToLessThan;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;
import android.preference.SwitchPreference;

import androidx.annotation.NonNull;

import java.util.Objects;

import app.revanced.integrations.shared.settings.Setting;
import app.revanced.integrations.shared.utils.Logger;
import app.revanced.integrations.youtube.settings.Settings;
import app.revanced.integrations.youtube.utils.ExtendedUtils;

/**
 * @noinspection ALL
 */
public class ReVancedSettingsPreference extends ReVancedPreferenceFragment {
    private static final String EXTERNAL_DOWNLOADER_PREFERENCE_KEY = "revanced_external_downloader";
    private static PreferenceManager mPreferenceManager;

    public static void setPreferenceManager(PreferenceManager mPreferenceManager) {
        ReVancedSettingsPreference.mPreferenceManager = mPreferenceManager;
    }

    public static void enableDisablePreferences() {
        for (Setting<?> setting : Setting.allLoadedSettings()) {
            final Preference preference = mPreferenceManager.findPreference(setting.key);
            if (preference != null) {
                preference.setEnabled(setting.isAvailable());
            }
        }
    }

    private static void enableDisablePreferences(final boolean isAvailable, final Setting<?>... unavailableEnum) {
        if (!isAvailable) {
            return;
        }
        for (Setting<?> setting : unavailableEnum) {
            final Preference preference = mPreferenceManager.findPreference(setting.key);
            if (preference != null) {
                preference.setEnabled(false);
            }
        }
    }

    public static void initializeReVancedSettings(@NonNull Activity activity) {
        AmbientModePreferenceLinks();
        EnableHDRCodecPreferenceLinks();
        FullScreenPanelPreferenceLinks();
        LayoutOverrideLinks();
        NavigationPreferenceLinks();
        QuickActionsPreferenceLinks();
        TabletLayoutLinks();
        setExternalDownloaderPreference(activity);
        setOpenSettingsPreference(activity);
    }

    /**
     * Enable/Disable Preference related to Ambient Mode
     */
    private static void AmbientModePreferenceLinks() {
        enableDisablePreferences(
                Settings.DISABLE_AMBIENT_MODE.get(),
                Settings.BYPASS_AMBIENT_MODE_RESTRICTIONS,
                Settings.DISABLE_AMBIENT_MODE_IN_FULLSCREEN
        );
    }

    /**
     * Enable/Disable Layout Override Preference
     */
    private static void LayoutOverrideLinks() {
        enableDisablePreferences(
                ExtendedUtils.isTablet,
                Settings.ENABLE_TABLET_LAYOUT,
                Settings.FORCE_FULLSCREEN
        );
        enableDisablePreferences(
                !ExtendedUtils.isTablet,
                Settings.ENABLE_PHONE_LAYOUT
        );
    }

    /**
     * Enable/Disable Preferences not working in tablet layout
     */
    private static void TabletLayoutLinks() {
        final boolean isTabletDevice = ExtendedUtils.isTablet &&
                !Settings.ENABLE_PHONE_LAYOUT.get();
        final boolean isEnabledTabletLayout = Settings.ENABLE_TABLET_LAYOUT.get();

        final boolean isTablet = isTabletDevice || isEnabledTabletLayout;

        enableDisablePreferences(
                isTablet,
                Settings.HIDE_CHANNEL_LIST_SUBMENU,
                Settings.HIDE_COMMUNITY_POSTS_HOME,
                Settings.HIDE_COMMUNITY_POSTS_SUBSCRIPTIONS,
                Settings.HIDE_END_SCREEN_OVERLAY,
                Settings.HIDE_FULLSCREEN_PANELS,
                Settings.HIDE_LATEST_VIDEOS_BUTTON,
                Settings.HIDE_MIX_PLAYLISTS,
                Settings.HIDE_QUICK_ACTIONS,
                Settings.HIDE_QUICK_ACTIONS_COMMENT_BUTTON,
                Settings.HIDE_QUICK_ACTIONS_DISLIKE_BUTTON,
                Settings.HIDE_QUICK_ACTIONS_LIKE_BUTTON,
                Settings.HIDE_QUICK_ACTIONS_LIVE_CHAT_BUTTON,
                Settings.HIDE_QUICK_ACTIONS_MORE_BUTTON,
                Settings.HIDE_QUICK_ACTIONS_OPEN_MIX_PLAYLIST_BUTTON,
                Settings.HIDE_QUICK_ACTIONS_OPEN_PLAYLIST_BUTTON,
                Settings.HIDE_QUICK_ACTIONS_RELATED_VIDEO,
                Settings.HIDE_QUICK_ACTIONS_SAVE_TO_PLAYLIST_BUTTON,
                Settings.HIDE_QUICK_ACTIONS_SHARE_BUTTON,
                Settings.QUICK_ACTIONS_MARGIN_TOP,
                Settings.SHOW_FULLSCREEN_TITLE
        );
    }

    /**
     * Enable/Disable Preference related to Enable HDR Codec
     */
    private static void EnableHDRCodecPreferenceLinks() {
        enableDisablePreferences(
                Settings.ENABLE_VIDEO_CODEC.get() && Settings.ENABLE_VIDEO_CODEC_TYPE.get(),
                Settings.DISABLE_HDR_VIDEO
        );
    }

    /**
     * Enable/Disable Preference related to Fullscreen Panel
     */
    private static void FullScreenPanelPreferenceLinks() {
        enableDisablePreferences(
                Settings.HIDE_FULLSCREEN_PANELS.get(),
                Settings.HIDE_END_SCREEN_OVERLAY,
                Settings.HIDE_QUICK_ACTIONS,
                Settings.HIDE_QUICK_ACTIONS_COMMENT_BUTTON,
                Settings.HIDE_QUICK_ACTIONS_DISLIKE_BUTTON,
                Settings.HIDE_QUICK_ACTIONS_LIKE_BUTTON,
                Settings.HIDE_QUICK_ACTIONS_LIVE_CHAT_BUTTON,
                Settings.HIDE_QUICK_ACTIONS_MORE_BUTTON,
                Settings.HIDE_QUICK_ACTIONS_OPEN_MIX_PLAYLIST_BUTTON,
                Settings.HIDE_QUICK_ACTIONS_OPEN_PLAYLIST_BUTTON,
                Settings.HIDE_QUICK_ACTIONS_RELATED_VIDEO,
                Settings.HIDE_QUICK_ACTIONS_SAVE_TO_PLAYLIST_BUTTON,
                Settings.HIDE_QUICK_ACTIONS_SHARE_BUTTON
        );

        enableDisablePreferences(
                Settings.DISABLE_LANDSCAPE_MODE.get(),
                Settings.FORCE_FULLSCREEN
        );

        enableDisablePreferences(
                Settings.FORCE_FULLSCREEN.get(),
                Settings.DISABLE_LANDSCAPE_MODE
        );

    }

    /**
     * Enable/Disable Preference related to Hide Quick Actions
     */
    private static void QuickActionsPreferenceLinks() {
        final boolean isEnabled =
                Settings.HIDE_FULLSCREEN_PANELS.get() || Settings.HIDE_QUICK_ACTIONS.get();

        enableDisablePreferences(
                isEnabled,
                Settings.HIDE_QUICK_ACTIONS_COMMENT_BUTTON,
                Settings.HIDE_QUICK_ACTIONS_DISLIKE_BUTTON,
                Settings.HIDE_QUICK_ACTIONS_LIKE_BUTTON,
                Settings.HIDE_QUICK_ACTIONS_LIVE_CHAT_BUTTON,
                Settings.HIDE_QUICK_ACTIONS_MORE_BUTTON,
                Settings.HIDE_QUICK_ACTIONS_OPEN_MIX_PLAYLIST_BUTTON,
                Settings.HIDE_QUICK_ACTIONS_OPEN_PLAYLIST_BUTTON,
                Settings.HIDE_QUICK_ACTIONS_RELATED_VIDEO,
                Settings.HIDE_QUICK_ACTIONS_SAVE_TO_PLAYLIST_BUTTON,
                Settings.HIDE_QUICK_ACTIONS_SHARE_BUTTON
        );
    }

    /**
     * Enable/Disable Preference related to Navigation settings
     */
    private static void NavigationPreferenceLinks() {
        enableDisablePreferences(
                Settings.SWITCH_CREATE_NOTIFICATION.get(),
                Settings.HIDE_CREATE_BUTTON
        );
        enableDisablePreferences(
                !Settings.SWITCH_CREATE_NOTIFICATION.get(),
                Settings.HIDE_NOTIFICATIONS_BUTTON
        );
    }

    /**
     * Add Preference to External downloader settings submenu
     */
    private static void setExternalDownloaderPreference(@NonNull Activity activity) {
        try {
            final PreferenceScreen externalDownloaderPreferenceScreen = (PreferenceScreen) mPreferenceManager.findPreference("external_downloader");
            if (externalDownloaderPreferenceScreen == null)
                return;

            final String[] labelArray = getStringArray(activity, EXTERNAL_DOWNLOADER_PREFERENCE_KEY + "_label");
            final String[] packageNameArray = getStringArray(activity, EXTERNAL_DOWNLOADER_PREFERENCE_KEY + "_package_name");
            final String[] websiteArray = getStringArray(activity, EXTERNAL_DOWNLOADER_PREFERENCE_KEY + "_website");

            final String[] mEntries = {str("revanced_external_downloader_download"), str("revanced_external_downloader_set"), str("accessibility_bottom_sheet_close_button")};

            for (int index = 0; index < labelArray.length; index++) {
                final String label = labelArray[index];
                final String packageName = packageNameArray[index];
                final Uri uri = Uri.parse(websiteArray[index]);

                final String installedMessage = isPackageEnabled(activity, packageName)
                        ? str("revanced_external_downloader_installed")
                        : str("revanced_external_downloader_not_installed");

                Preference externalDownloaderPreference = new Preference(activity);

                externalDownloaderPreference.setTitle(label);
                externalDownloaderPreference.setSummary(packageName);
                externalDownloaderPreference.setOnPreferenceClickListener(preference -> {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(activity);

                    builder.setTitle(String.format("%s (%s)", label, installedMessage));
                    builder.setItems(mEntries, (mDialog, mIndex) -> {
                        switch (mIndex) {
                            case 0 -> {
                                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                activity.startActivity(intent);
                            }
                            case 1 -> {
                                Settings.EXTERNAL_DOWNLOADER_PACKAGE_NAME.save(packageName);
                                showRestartDialog(activity);
                            }
                            case 2 -> mDialog.dismiss();
                        }
                    });
                    builder.show();

                    return false;
                });
                externalDownloaderPreferenceScreen.addPreference(externalDownloaderPreference);
            }

            if (isSpoofingToLessThan("18.24.00"))
                return;

            Preference experimentalPreference = new Preference(activity);
            experimentalPreference.setTitle(" ");
            experimentalPreference.setSummary(str("revanced_experimental_flag"));

            SwitchPreference hookDownloadButtonPreference = new SwitchPreference(activity);
            hookDownloadButtonPreference.setTitle(str("revanced_hook_download_button_title"));
            hookDownloadButtonPreference.setSummary(str("revanced_hook_download_button_summary"));
            hookDownloadButtonPreference.setKey("revanced_hook_download_button");
            hookDownloadButtonPreference.setDefaultValue(false);

            externalDownloaderPreferenceScreen.addPreference(experimentalPreference);
            externalDownloaderPreferenceScreen.addPreference(hookDownloadButtonPreference);
        } catch (Throwable th) {
            Logger.printException(() -> "Error setting setExternalDownloaderPreference" + th);
        }
    }

    /**
     * Set Open External Link Preference onClickListener
     */
    private static void setOpenSettingsPreference(@NonNull Activity activity) {
        try {
            final Uri uri = Uri.parse("package:" + activity.getPackageName());

            final Intent intent = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S
                    ? new Intent(android.provider.Settings.ACTION_APP_OPEN_BY_DEFAULT_SETTINGS, uri)
                    : new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS, uri);

            Objects.requireNonNull(mPreferenceManager.findPreference("revanced_default_app_settings"))
                    .setOnPreferenceClickListener(pref -> {
                        activity.startActivity(intent);
                        return false;
                    });
        } catch (Throwable th) {
            Logger.printException(() -> "Error setting setOpenSettingsPreference" + th);
        }
    }
}