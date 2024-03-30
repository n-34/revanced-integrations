package app.revanced.integrations.youtube.patches.video;

import static app.revanced.integrations.shared.utils.StringRef.str;
import static app.revanced.integrations.shared.utils.Utils.showToastShort;
import static app.revanced.integrations.youtube.patches.video.VideoInformation.isLiveStream;

import java.util.Objects;

import app.revanced.integrations.shared.utils.Logger;
import app.revanced.integrations.youtube.patches.utils.PatchStatus;
import app.revanced.integrations.youtube.settings.Settings;

@SuppressWarnings("unused")
public class PlaybackSpeedPatch {
    private static String currentContentCpn;
    private static float currentPlaybackSpeed = 1.0f;

    public static void newVideoStarted(final String contentCpn) {
        try {
            if (contentCpn.isEmpty() || Objects.equals(currentContentCpn, contentCpn))
                return;

            currentContentCpn = contentCpn;

            if (Settings.DISABLE_DEFAULT_PLAYBACK_SPEED_LIVE.get() && isLiveStream)
                return;

            currentPlaybackSpeed = Settings.DEFAULT_PLAYBACK_SPEED.get();

            overrideSpeed(currentPlaybackSpeed);
        } catch (Exception ex) {
            Logger.printException(() -> "Failed to setDefaultPlaybackSpeed", ex);
        }
    }

    public static float getPlaybackSpeedInShorts(final float playbackSpeed) {
        if (!Settings.ENABLE_DEFAULT_PLAYBACK_SPEED_SHORTS.get())
            return playbackSpeed;

        if (Settings.DISABLE_DEFAULT_PLAYBACK_SPEED_LIVE.get() && isLiveStream)
            return playbackSpeed;

        return Settings.DEFAULT_PLAYBACK_SPEED.get();
    }

    public static void userChangedSpeed(final float playbackSpeed) {
        currentPlaybackSpeed = playbackSpeed;

        if (!Settings.ENABLE_SAVE_PLAYBACK_SPEED.get())
            return;

        if (!PatchStatus.DefaultPlaybackSpeed())
            return;

        Settings.DEFAULT_PLAYBACK_SPEED.save(playbackSpeed);
        showToastShort(str("revanced_save_playback_speed", playbackSpeed + "x"));
    }

    public static void overrideSpeed(final float playbackSpeed) {
        if (playbackSpeed != currentPlaybackSpeed)
            currentPlaybackSpeed = playbackSpeed;
    }
}
