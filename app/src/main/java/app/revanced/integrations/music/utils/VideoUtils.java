package app.revanced.integrations.music.utils;

import static app.revanced.integrations.shared.utils.StringRef.str;
import static app.revanced.integrations.shared.utils.Utils.showToastShort;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.net.Uri;

import androidx.annotation.NonNull;

import app.revanced.integrations.music.patches.video.VideoInformation;
import app.revanced.integrations.music.settings.Settings;
import app.revanced.integrations.shared.utils.Logger;

@SuppressWarnings("unused")
public class VideoUtils {

    public static void downloadMusic(@NonNull Context context) {
        String downloaderPackageName = Settings.EXTERNAL_DOWNLOADER_PACKAGE_NAME.get();

        if (downloaderPackageName.isEmpty()) {
            Settings.EXTERNAL_DOWNLOADER_PACKAGE_NAME.resetToDefault();
            downloaderPackageName = Settings.EXTERNAL_DOWNLOADER_PACKAGE_NAME.defaultValue;
        }

        if (!ExtendedUtils.isPackageEnabled(context, downloaderPackageName)) {
            showToastShort(str("revanced_external_downloader_not_installed_warning", downloaderPackageName));
            return;
        }

        startDownloaderActivity(context, downloaderPackageName, String.format("https://music.youtube.com/watch?v=%s", VideoInformation.getVideoId()));
    }

    public static void startDownloaderActivity(@NonNull Context context, @NonNull String downloaderPackageName, @NonNull String content) {
        Intent intent = new Intent("android.intent.action.SEND");
        intent.setType("text/plain");
        intent.setPackage(downloaderPackageName);
        intent.putExtra("android.intent.extra.TEXT", content);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    @SuppressLint("IntentReset")
    public static void openInYouTube(@NonNull Context context) {
        AudioManager audioManager = (AudioManager) context.getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
        final String videoId = VideoInformation.getVideoId();
        if (videoId.isEmpty()) {
            showToastShort(str("revanced_watch_on_youtube_warning"));
            return;
        }

        if (audioManager != null) {
            audioManager.requestAudioFocus(null, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
        }

        String url = String.format("vnd.youtube://%s", videoId);
        if (Settings.REPLACE_FLYOUT_PANEL_DISMISS_QUEUE_CONTINUE_WATCH.get()) {
            long seconds = VideoInformation.getVideoTime() / 1000;
            url += String.format("?t=%s", seconds);
        }

        Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(url));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static void openInMusic(@NonNull Context context, @NonNull String songId) {
        final String url = String.format("vnd.youtube.music://%s", songId);
        Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(url));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setPackage(ExtendedUtils.packageName);
        context.startActivity(intent);
    }

    /**
     * Rest of the implementation added by patch.
     */
    public static void showPlaybackSpeedFlyoutPanel() {
        Logger.printDebug(() -> "Playback speed flyout panel opened");
    }
}
