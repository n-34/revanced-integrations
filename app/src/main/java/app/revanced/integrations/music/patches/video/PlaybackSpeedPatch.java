package app.revanced.integrations.music.patches.video;

import static app.revanced.integrations.shared.utils.StringRef.str;
import static app.revanced.integrations.shared.utils.Utils.showToastShort;

import app.revanced.integrations.music.settings.Settings;

@SuppressWarnings("unused")
public class PlaybackSpeedPatch {

    public static float getPlaybackSpeed(final float playbackSpeed) {
        try {
            return Settings.DEFAULT_PLAYBACK_SPEED.get();
        } catch (Exception ignored) {
        }
        return playbackSpeed;
    }

    public static void userSelectedPlaybackSpeed(final float playbackSpeed) {
        if (!Settings.ENABLE_SAVE_PLAYBACK_SPEED.get())
            return;

        Settings.DEFAULT_PLAYBACK_SPEED.save(playbackSpeed);
        showToastShort(str("revanced_save_playback_speed", playbackSpeed + "x"));
    }
}
