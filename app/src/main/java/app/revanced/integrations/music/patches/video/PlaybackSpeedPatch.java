package app.revanced.integrations.music.patches.video;

import static app.revanced.integrations.music.utils.ReVancedUtils.showToastShort;
import static app.revanced.integrations.music.utils.StringRef.str;

import app.revanced.integrations.music.settings.SettingsEnum;

@SuppressWarnings("unused")
public class PlaybackSpeedPatch {
    private static float selectedSpeed = 1.0f;

    public static float getPlaybackSpeed() {
        try {
            return SettingsEnum.DEFAULT_PLAYBACK_SPEED.getFloat();
        } catch (Exception ignored) {
        }
        return selectedSpeed;
    }

    /** @noinspection ResultOfMethodCallIgnored*/
    public static void showPlaybackSpeedMenu() {
        SettingsEnum.ENABLE_FLYOUT_PANEL_PLAYBACK_SPEED.getBoolean();
        // Rest of the implementation added by patch.
    }

    public static void userChangedSpeed(final float speed) {
        selectedSpeed = speed;

        if (!SettingsEnum.ENABLE_SAVE_PLAYBACK_SPEED.getBoolean())
            return;

        SettingsEnum.DEFAULT_PLAYBACK_SPEED.saveValue(speed);
        showToastShort(str("revanced_save_playback_speed", speed + "x"));
    }
}
