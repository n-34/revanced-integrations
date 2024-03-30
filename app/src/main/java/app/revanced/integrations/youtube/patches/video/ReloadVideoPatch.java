package app.revanced.integrations.youtube.patches.video;

import androidx.annotation.NonNull;

import app.revanced.integrations.shared.utils.Utils;
import app.revanced.integrations.youtube.settings.Settings;
import app.revanced.integrations.youtube.shared.PlayerType;

@SuppressWarnings("unused")
public class ReloadVideoPatch {
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

        Utils.runOnMainThreadDelayed(VideoInformation::reloadVideo, 700);
    }
}
