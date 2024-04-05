package app.revanced.integrations.youtube.patches.video;

import static app.revanced.integrations.shared.utils.StringRef.str;
import static app.revanced.integrations.shared.utils.Utils.showToastShort;
import static app.revanced.integrations.youtube.patches.video.VideoInformation.getLiveStreamState;

import java.util.Objects;

import app.revanced.integrations.shared.utils.Logger;
import app.revanced.integrations.youtube.patches.utils.PatchStatus;
import app.revanced.integrations.youtube.settings.Settings;

@SuppressWarnings("unused")
public class PlaybackSpeedPatch {
    private static String currentVideoCpn;

    /**
     * Injection point.
     */
    public static void newVideoStarted(final String newlyLoadedVideoCpn, boolean isLiveStream) {
        try {
            if (Objects.equals(currentVideoCpn, newlyLoadedVideoCpn))
                return;
            currentVideoCpn = newlyLoadedVideoCpn;

            if (Settings.DISABLE_DEFAULT_PLAYBACK_SPEED_LIVE.get() && isLiveStream)
                return;

            VideoInformation.overridePlaybackSpeed(Settings.DEFAULT_PLAYBACK_SPEED.get());
        } catch (Exception ex) {
            Logger.printException(() -> "Failed to setDefaultPlaybackSpeed", ex);
        }
    }

    /**
     * Injection point.
     */
    public static float getPlaybackSpeedInShorts(final float playbackSpeed) {
        if (!Settings.ENABLE_DEFAULT_PLAYBACK_SPEED_SHORTS.get())
            return playbackSpeed;

        if (Settings.DISABLE_DEFAULT_PLAYBACK_SPEED_LIVE.get() && getLiveStreamState())
            return playbackSpeed;

        return Settings.DEFAULT_PLAYBACK_SPEED.get();
    }

    /**
     * Injection point.
     * Overrides the video speed.  Called after video loads, and immediately after user selects a different playback speed
     */
    public static float getPlaybackSpeedOverride() {
        return VideoInformation.getPlaybackSpeed();
    }

    /**
     * Injection point.
     * Called when user selects a playback speed.
     *
     * @param playbackSpeed The playback speed the user selected
     */
    public static void userSelectedPlaybackSpeed(float playbackSpeed) {
        if (!Settings.ENABLE_SAVE_PLAYBACK_SPEED.get())
            return;

        if (!PatchStatus.DefaultPlaybackSpeed())
            return;

        Settings.DEFAULT_PLAYBACK_SPEED.save(playbackSpeed);
        showToastShort(str("revanced_save_playback_speed", playbackSpeed + "x"));
    }
}
