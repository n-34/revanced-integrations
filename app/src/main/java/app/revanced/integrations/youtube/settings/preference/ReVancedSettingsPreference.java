package app.revanced.integrations.youtube.settings.preference;

import static app.revanced.integrations.shared.utils.ResourceUtils.getLayoutIdentifier;
import static app.revanced.integrations.shared.utils.StringRef.str;
import static app.revanced.integrations.youtube.utils.ExtendedUtils.isSpoofingToLessThan;

import android.app.Activity;
import android.preference.Preference;
import android.preference.PreferenceCategory;
import android.preference.PreferenceScreen;
import android.preference.SwitchPreference;

import androidx.annotation.NonNull;

import app.revanced.integrations.shared.settings.Setting;
import app.revanced.integrations.shared.utils.Logger;
import app.revanced.integrations.youtube.patches.utils.PatchStatus;
import app.revanced.integrations.youtube.settings.Settings;
import app.revanced.integrations.youtube.utils.ExtendedUtils;

/**
 * @noinspection ALL
 */
public class ReVancedSettingsPreference extends ReVancedPreferenceFragment {
    private static final String EXTERNAL_DOWNLOADER_PREFERENCE_KEY = "revanced_external_downloader";


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
        FullScreenPanelPreferenceLinks();
        LayoutOverrideLinks();
        NavigationPreferenceLinks();
        QuickActionsPreferenceLinks();
        TabletLayoutLinks();
        setExternalDownloaderPreference(activity);
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
                ExtendedUtils.isTablet(),
                Settings.ENABLE_TABLET_LAYOUT,
                Settings.FORCE_FULLSCREEN
        );
        enableDisablePreferences(
                !ExtendedUtils.isTablet(),
                Settings.ENABLE_PHONE_LAYOUT
        );
    }

    /**
     * Enable/Disable Preferences not working in tablet layout
     */
    private static void TabletLayoutLinks() {
        final boolean isTabletDevice = ExtendedUtils.isTablet() &&
                !Settings.ENABLE_PHONE_LAYOUT.get();
        final boolean isEnabledTabletLayout = Settings.ENABLE_TABLET_LAYOUT.get();

        final boolean isTablet = isTabletDevice || isEnabledTabletLayout;

        enableDisablePreferences(
                isTablet,
                Settings.DISABLE_ENGAGEMENT_PANEL,
                Settings.HIDE_COMMUNITY_POSTS_CHANNEL,
                Settings.HIDE_COMMUNITY_POSTS_HOME_RELATED_VIDEOS,
                Settings.HIDE_COMMUNITY_POSTS_SUBSCRIPTIONS,
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
                Settings.QUICK_ACTIONS_TOP_MARGIN,
                Settings.HIDE_RELATED_VIDEO_OVERLAY,
                Settings.SHOW_VIDEO_TITLE_SECTION
        );
    }

    /**
     * Enable/Disable Preference related to Fullscreen Panel
     */
    private static void FullScreenPanelPreferenceLinks() {
        enableDisablePreferences(
                Settings.DISABLE_ENGAGEMENT_PANEL.get(),
                Settings.HIDE_RELATED_VIDEO_OVERLAY,
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
                Settings.DISABLE_ENGAGEMENT_PANEL.get() || Settings.HIDE_QUICK_ACTIONS.get();

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
                Settings.SWITCH_CREATE_WITH_NOTIFICATIONS_BUTTON.get(),
                Settings.HIDE_NAVIGATION_CREATE_BUTTON
        );
        enableDisablePreferences(
                !Settings.SWITCH_CREATE_WITH_NOTIFICATIONS_BUTTON.get(),
                Settings.HIDE_NAVIGATION_NOTIFICATIONS_BUTTON,
                Settings.REPLACE_TOOLBAR_CREATE_BUTTON,
                Settings.REPLACE_TOOLBAR_CREATE_BUTTON_TYPE
        );
    }

    /**
     * Add Preference to External downloader settings submenu
     */
    private static void setExternalDownloaderPreference(@NonNull Activity activity) {
        try {
            // check if Preference Screen is not null
            if (!(mPreferenceManager.findPreference("revanced_preference_screen_player_buttons") instanceof PreferenceScreen playerButtonPreferenceScreen))
                return;

            // The player buttons preference screen contains overlay buttons as well as other settings. (e.g. Hide autoplay button)
            // Make sure the overlay buttons patch is included.
            if (!PatchStatus.OverlayButtons())
                return;

            if (isSpoofingToLessThan("18.24.00"))
                return;

            PreferenceCategory experimentalPreferenceCategory = new PreferenceCategory(activity);
            experimentalPreferenceCategory.setTitle(str("revanced_preference_category_experimental_flag"));
            experimentalPreferenceCategory.setLayoutResource(getLayoutIdentifier("revanced_settings_preferences_category"));

            SwitchPreference overrideDownloadActionPreference = new SwitchPreference(activity);
            overrideDownloadActionPreference.setTitle(str("revanced_external_downloader_action_title"));
            overrideDownloadActionPreference.setSummaryOn(str("revanced_external_downloader_action_summary_on"));
            overrideDownloadActionPreference.setSummaryOff(str("revanced_external_downloader_action_summary_off"));
            overrideDownloadActionPreference.setKey(Settings.EXTERNAL_DOWNLOADER_ACTION_BUTTON.key);
            overrideDownloadActionPreference.setDefaultValue(Settings.EXTERNAL_DOWNLOADER_ACTION_BUTTON.defaultValue);

            playerButtonPreferenceScreen.addPreference(experimentalPreferenceCategory);
            playerButtonPreferenceScreen.addPreference(overrideDownloadActionPreference);
        } catch (Throwable th) {
            Logger.printException(() -> "Error setting setExternalDownloaderPreference" + th);
        }
    }
}