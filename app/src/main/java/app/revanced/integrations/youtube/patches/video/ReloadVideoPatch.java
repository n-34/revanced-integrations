package app.revanced.integrations.youtube.patches.video;

import static app.revanced.integrations.shared.utils.StringRef.str;

import androidx.annotation.NonNull;

import app.revanced.integrations.shared.utils.Utils;
import app.revanced.integrations.youtube.settings.Settings;
import app.revanced.integrations.youtube.shared.PlayerType;
import app.revanced.integrations.youtube.shared.VideoInformation;

@SuppressWarnings("unused")
public class ReloadVideoPatch {
    private static final int RELOAD_VIDEO_TIME_MILLISECONDS = 15000;

    @NonNull
    public static String videoId = "";

    /**
     * Injection point.
     *
     * @param newlyLoadedVideoId id of the current video
     */
    public static void setVideoId(@NonNull String newlyLoadedVideoId) {
        if (!Settings.SKIP_PRELOADED_BUFFER.get()) {
            return;
        }

        if (PlayerType.getCurrent() == PlayerType.INLINE_MINIMAL) {
            return;
        }

        if (videoId.equals(newlyLoadedVideoId))
            return;

        videoId = newlyLoadedVideoId;

        Utils.runOnMainThreadDelayed(ReloadVideoPatch::reloadVideo, 700);
    }

    public static void reloadVideo() {
        final long videoLength = VideoInformation.getVideoLength();
        final boolean videoIsLiveStream = VideoInformation.getLiveStreamState();

        if (videoLength < RELOAD_VIDEO_TIME_MILLISECONDS || videoIsLiveStream)
            return;

        final long lastVideoTime = VideoInformation.getVideoTime();
        final float playbackSpeed = VideoInformation.getPlaybackSpeed();
        final long speedAdjustedTimeThreshold = (long) (playbackSpeed * 1000);
        VideoInformation.overrideVideoTime(RELOAD_VIDEO_TIME_MILLISECONDS);
        VideoInformation.overrideVideoTime(lastVideoTime + speedAdjustedTimeThreshold);

        if (!Settings.SKIP_PRELOADED_BUFFER_TOAST.get())
            return;

        Utils.showToastShort(str("revanced_skipped_preloaded_buffer"));
    }
}
