package app.revanced.integrations.music.utils;

import static app.revanced.integrations.shared.utils.StringRef.str;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.AudioManager;

import androidx.annotation.NonNull;

import app.revanced.integrations.music.patches.video.VideoInformation;
import app.revanced.integrations.music.settings.Settings;
import app.revanced.integrations.shared.utils.IntentUtils;
import app.revanced.integrations.shared.utils.Logger;

@SuppressWarnings("unused")
public class VideoUtils extends IntentUtils {

    public static void launchExternalDownloader() {
        launchExternalDownloader(VideoInformation.getVideoId());
    }

    public static void launchExternalDownloader(@NonNull String videoId) {
        String downloaderPackageName = Settings.EXTERNAL_DOWNLOADER_PACKAGE_NAME.get().trim();

        if (downloaderPackageName.isEmpty()) {
            Settings.EXTERNAL_DOWNLOADER_PACKAGE_NAME.resetToDefault();
            downloaderPackageName = Settings.EXTERNAL_DOWNLOADER_PACKAGE_NAME.defaultValue;
        }

        if (!ExtendedUtils.isPackageEnabled(downloaderPackageName)) {
            showToastShort(str("revanced_external_downloader_not_installed_warning", downloaderPackageName));
            return;
        }

        final String content = String.format("https://music.youtube.com/watch?v=%s", videoId);
        launchExternalDownloader(content, downloaderPackageName);
    }

    @SuppressLint("IntentReset")
    public static void openInYouTube() {
        final String videoId = VideoInformation.getVideoId();
        if (videoId.isEmpty()) {
            showToastShort(str("revanced_watch_on_youtube_warning"));
            return;
        }

        if (context.getApplicationContext().getSystemService(Context.AUDIO_SERVICE) instanceof AudioManager audioManager) {
            audioManager.requestAudioFocus(null, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
        }

        String url = String.format("vnd.youtube://%s", videoId);
        if (Settings.REPLACE_FLYOUT_PANEL_DISMISS_QUEUE_CONTINUE_WATCH.get()) {
            long seconds = VideoInformation.getVideoTime() / 1000;
            url += String.format("?t=%s", seconds);
        }

        launchView(url);
    }

    public static void openInYouTubeMusic(@NonNull String songId) {
        final String url = String.format("vnd.youtube.music://%s", songId);
        launchView(url, context.getPackageName());
    }

    /**
     * Rest of the implementation added by patch.
     */
    public static void showPlaybackSpeedFlyoutMenu() {
        Logger.printDebug(() -> "Playback speed flyout menu opened");
    }
}
