package app.revanced.integrations.youtube.utils;

import static app.revanced.integrations.shared.utils.ResourceUtils.identifier;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import app.revanced.integrations.shared.settings.BooleanSetting;
import app.revanced.integrations.shared.settings.Setting;
import app.revanced.integrations.shared.utils.Logger;
import app.revanced.integrations.shared.utils.ResourceType;
import app.revanced.integrations.youtube.settings.Settings;

public class ExtendedUtils {
    public static String applicationLabel = "ReVanced_Extended";
    public static boolean isTablet = false;
    public static String packageName = "app.rvx.android.youtube";
    public static String appVersionName = "18.29.38";

    private ExtendedUtils() {
    } // utility class

    @Nullable
    private static PackageInfo getPackageInfo(@NonNull Context context) {
        try {
            final PackageManager packageManager = getPackageManager(context);
            final String packageName = context.getPackageName();
            return Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU
                    ? packageManager.getPackageInfo(packageName, PackageManager.PackageInfoFlags.of(0))
                    : packageManager.getPackageInfo(packageName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            Logger.printException(() -> "Failed to get package Info!" + e);
        }
        return null;
    }

    @NonNull
    private static PackageManager getPackageManager(@NonNull Context context) {
        return context.getPackageManager();
    }

    @NonNull
    public static String[] getStringArray(@NonNull Context context, @NonNull String key) {
        return context.getResources().getStringArray(identifier(key, ResourceType.ARRAY));
    }

    private static boolean isAdditionalSettingsEnabled() {
        // In the old player flyout panels, the video quality icon and additional quality icon are the same
        // Therefore, additional Settings should not be blocked in old player flyout panels
        if (isSpoofingToLessThan("18.22.00"))
            return false;

        boolean additionalSettingsEnabled = true;
        final BooleanSetting[] additionalSettings = {
                Settings.HIDE_PLAYER_FLYOUT_PANEL_AMBIENT,
                Settings.HIDE_PLAYER_FLYOUT_PANEL_HELP,
                Settings.HIDE_PLAYER_FLYOUT_PANEL_LOOP,
                Settings.HIDE_PLAYER_FLYOUT_PANEL_PREMIUM_CONTROLS,
                Settings.HIDE_PLAYER_FLYOUT_PANEL_STABLE_VOLUME,
                Settings.HIDE_PLAYER_FLYOUT_PANEL_STATS_FOR_NERDS,
                Settings.HIDE_PLAYER_FLYOUT_PANEL_WATCH_IN_VR,
                Settings.HIDE_PLAYER_FLYOUT_PANEL_YT_MUSIC,
        };
        for (BooleanSetting s : additionalSettings) {
            additionalSettingsEnabled &= s.get();
        }
        return additionalSettingsEnabled;
    }

    public static boolean isFullscreenHidden() {
        boolean isFullscreenHidden = isTablet &&
                !Settings.ENABLE_PHONE_LAYOUT.get();
        final BooleanSetting[] hideFullscreenSettings = {
                Settings.ENABLE_TABLET_LAYOUT,
                Settings.HIDE_QUICK_ACTIONS,
                Settings.HIDE_FULLSCREEN_PANELS
        };
        for (BooleanSetting s : hideFullscreenSettings) {
            isFullscreenHidden |= s.get();
        }
        return isFullscreenHidden;
    }

    public static boolean isPackageEnabled(@NonNull Context context, @NonNull String packageName) {
        try {
            return context.getPackageManager().getApplicationInfo(packageName, 0).enabled;
        } catch (PackageManager.NameNotFoundException ignored) {
        }

        return false;
    }

    public static boolean isSpoofingToLessThan(@NonNull String versionName) {
        if (!Settings.SPOOF_APP_VERSION.get())
            return false;

        final int spoofedVersion = Integer.parseInt(Settings.SPOOF_APP_VERSION_TARGET.get().replaceAll("\\.", ""));
        final int targetVersion = Integer.parseInt(versionName.replaceAll("\\.", ""));
        return spoofedVersion < targetVersion;
    }

    public static void setApplicationLabel(@NonNull Context context) {
        final PackageInfo packageInfo = getPackageInfo(context);
        if (packageInfo != null) {
            applicationLabel = (String) packageInfo.applicationInfo.loadLabel(getPackageManager(context));
        }
    }

    public static void setCommentPreviewSettings() {
        final boolean enabled = Settings.HIDE_PREVIEW_COMMENT.get();
        final boolean newMethod = Settings.HIDE_PREVIEW_COMMENT_TYPE.get();

        Settings.HIDE_PREVIEW_COMMENT_OLD_METHOD.save(enabled && !newMethod);
        Settings.HIDE_PREVIEW_COMMENT_NEW_METHOD.save(enabled && newMethod);
    }

    private static final Setting<?>[] additionalSettings = {
            Settings.HIDE_PLAYER_FLYOUT_PANEL_AMBIENT,
            Settings.HIDE_PLAYER_FLYOUT_PANEL_HELP,
            Settings.HIDE_PLAYER_FLYOUT_PANEL_LOOP,
            Settings.HIDE_PLAYER_FLYOUT_PANEL_PREMIUM_CONTROLS,
            Settings.HIDE_PLAYER_FLYOUT_PANEL_STABLE_VOLUME,
            Settings.HIDE_PLAYER_FLYOUT_PANEL_STATS_FOR_NERDS,
            Settings.HIDE_PLAYER_FLYOUT_PANEL_WATCH_IN_VR,
            Settings.HIDE_PLAYER_FLYOUT_PANEL_YT_MUSIC,
            Settings.SPOOF_APP_VERSION,
            Settings.SPOOF_APP_VERSION_TARGET
    };

    public static boolean anyMatchSetting(Setting<?> setting) {
        for (Setting<?> s : additionalSettings) {
            if (setting == s) return true;
        }
        return false;
    }

    public static void setPlayerFlyoutPanelAdditionalSettings() {
        Settings.HIDE_PLAYER_FLYOUT_PANEL_ADDITIONAL_SETTINGS.save(isAdditionalSettingsEnabled());
    }

    public static void setIsTablet(@NonNull Context context) {
        isTablet = context.getResources().getConfiguration().smallestScreenWidthDp >= 600;
    }

    public static void setPackageName(@NonNull Context context) {
        packageName = context.getPackageName();
    }

    public static void setVersionName(@NonNull Context context) {
        final PackageInfo packageInfo = getPackageInfo(context);
        if (packageInfo != null) {
            appVersionName = packageInfo.versionName;
        }
    }
}