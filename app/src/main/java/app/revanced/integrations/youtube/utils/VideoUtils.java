package app.revanced.integrations.youtube.utils;

import static app.revanced.integrations.shared.utils.ResourceUtils.getStringArray;
import static app.revanced.integrations.shared.utils.StringRef.str;
import static app.revanced.integrations.youtube.patches.video.PlaybackSpeedPatch.userSelectedPlaybackSpeed;
import static app.revanced.integrations.youtube.utils.ExtendedUtils.isPackageEnabled;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.time.Duration;
import java.util.Arrays;

import app.revanced.integrations.shared.utils.IntentUtils;
import app.revanced.integrations.shared.utils.Logger;
import app.revanced.integrations.youtube.patches.video.CustomPlaybackSpeedPatch;
import app.revanced.integrations.youtube.patches.video.VideoInformation;
import app.revanced.integrations.youtube.settings.Settings;

@SuppressWarnings("unused")
public class VideoUtils extends IntentUtils {
    private static volatile boolean isPiPAvailable = true;

    public static void copyUrl(boolean withTimestamp) {
        StringBuilder builder = new StringBuilder("https://youtu.be/");
        builder.append(VideoInformation.getVideoId());
        final long currentVideoTimeInSeconds = VideoInformation.getVideoTime() / 1000;
        if (withTimestamp && currentVideoTimeInSeconds > 0) {
            builder.append("?t=");
            builder.append(currentVideoTimeInSeconds);
        }

        setClipboard(builder.toString(), withTimestamp
                ? str("revanced_share_copy_url_timestamp_success")
                : str("revanced_share_copy_url_success")
        );
    }

    @TargetApi(26)
    @SuppressLint("DefaultLocale")
    public static void copyTimeStamp() {
        final long currentVideoTime = VideoInformation.getVideoTime();
        final Duration duration = Duration.ofMillis(currentVideoTime);

        final long h = duration.toHours();
        final long m = duration.toMinutes() % 60;
        final long s = duration.getSeconds() % 60;

        final String timeStamp = h > 0
                ? String.format("%02d:%02d:%02d", h, m, s)
                : String.format("%02d:%02d", m, s);

        setClipboard(timeStamp, str("revanced_share_copy_timestamp_success", timeStamp));
    }

    @NonNull
    private static String getExternalDownloaderAppName(@NonNull Context context, @NonNull String packageName) {
        try {
            final String EXTERNAL_DOWNLOADER_LABEL_PREFERENCE_KEY = "revanced_external_downloader_label";
            final String EXTERNAL_DOWNLOADER_PACKAGE_NAME_PREFERENCE_KEY = "revanced_external_downloader_package_name";

            final String[] labelArray = getStringArray(EXTERNAL_DOWNLOADER_LABEL_PREFERENCE_KEY);
            final String[] packageNameArray = getStringArray(EXTERNAL_DOWNLOADER_PACKAGE_NAME_PREFERENCE_KEY);

            final int findIndex = Arrays.binarySearch(packageNameArray, packageName);

            return findIndex >= 0 ? labelArray[findIndex] : packageName;
        } catch (Exception e) {
            Logger.printException(() -> "Failed to set ExternalDownloaderName", e);
        }
        return packageName;
    }

    public static void launchExternalDownloader() {
        launchExternalDownloader(VideoInformation.getVideoId());
    }

    public static void launchExternalDownloader(@NonNull String videoId) {
        String downloaderPackageName = Settings.EXTERNAL_DOWNLOADER_PACKAGE_NAME.get().trim();

        if (downloaderPackageName.isEmpty()) {
            Settings.EXTERNAL_DOWNLOADER_PACKAGE_NAME.resetToDefault();
            downloaderPackageName = Settings.EXTERNAL_DOWNLOADER_PACKAGE_NAME.defaultValue;
        }

        if (!isPackageEnabled(downloaderPackageName)) {
            showToastShort(str("revanced_external_downloader_not_installed_warning", getExternalDownloaderAppName(context, downloaderPackageName)));
            return;
        }

        isPiPAvailable = false;
        final String content = String.format("https://youtu.be/%s", videoId);
        launchExternalDownloader(content, downloaderPackageName);
        runOnMainThreadDelayed(() -> isPiPAvailable = true, 500L);
    }

    public static void showPlaybackSpeedDialog(@NonNull Context context) {
        final String[] playbackSpeedWithAutoEntries = CustomPlaybackSpeedPatch.getListEntries();
        final String[] playbackSpeedWithAutoEntryValues = CustomPlaybackSpeedPatch.getListEntryValues();

        final String[] playbackSpeedEntries = Arrays.copyOfRange(playbackSpeedWithAutoEntries, 1, playbackSpeedWithAutoEntries.length);
        final String[] playbackSpeedEntryValues = Arrays.copyOfRange(playbackSpeedWithAutoEntryValues, 1, playbackSpeedWithAutoEntryValues.length);

        final float playbackSpeed = VideoInformation.getPlaybackSpeed();
        final int index = Arrays.binarySearch(playbackSpeedEntryValues, String.valueOf(playbackSpeed));

        new AlertDialog.Builder(context)
                .setSingleChoiceItems(playbackSpeedEntries, index, (mDialog, mIndex) -> {
                    final float selectedPlaybackSpeed = Float.parseFloat(playbackSpeedEntryValues[mIndex] + "f");
                    VideoInformation.overridePlaybackSpeed(selectedPlaybackSpeed);
                    userSelectedPlaybackSpeed(selectedPlaybackSpeed);
                    mDialog.dismiss();
                })
                .show();
    }

    /**
     * Rest of the implementation added by patch.
     */
    public static void showPlaybackSpeedFlyoutPanel() {
        Logger.printDebug(() -> "Playback speed flyout panel opened");
    }

    public static String getFormattedQualityString(@Nullable String prefix) {
        final String qualityString = VideoInformation.getVideoQualityString();

        return prefix == null ? qualityString : String.format("%s\u2009•\u2009%s", prefix, qualityString);
    }

    public static String getFormattedSpeedString(@Nullable String prefix) {
        final float playbackSpeed = VideoInformation.getPlaybackSpeed();

        final String playbackSpeedString = isRightToLeftTextLayout()
                ? "\u2066x\u2069" + playbackSpeed
                : playbackSpeed + "x";

        return prefix == null ? playbackSpeedString : String.format("%s\u2009•\u2009%s", prefix, playbackSpeedString);
    }

    /**
     * Injection point.
     */
    public static boolean isPiPAvailable(boolean original) {
        return original && isPiPAvailable;
    }
}
