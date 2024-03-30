package app.revanced.integrations.music.patches.video;

import static app.revanced.integrations.shared.utils.StringRef.str;
import static app.revanced.integrations.shared.utils.Utils.showToastShort;

import app.revanced.integrations.music.settings.Settings;

@SuppressWarnings("unused")
public class PlaybackSpeedPatch {
    private static float selectedSpeed = 1.0f;

    public static float getPlaybackSpeed() {
        try {
            return Settings.DEFAULT_PLAYBACK_SPEED.get();
        } catch (Exception ignored) {
        }
        return selectedSpeed;
    }

    /** @noinspection ResultOfMethodCallIgnored*/
    public static void showPlaybackSpeedMenu() {
        Settings.REPLACE_FLYOUT_PANEL_REPORT.get();
        // Rest of the implementation added by patch.
    }

    public static void userChangedSpeed(final float speed) {
        selectedSpeed = speed;

        if (!Settings.ENABLE_SAVE_PLAYBACK_SPEED.get())
            return;

        Settings.DEFAULT_PLAYBACK_SPEED.save(speed);
        showToastShort(str("revanced_save_playback_speed", speed + "x"));
    }
}
