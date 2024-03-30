package app.revanced.integrations.music.patches.video;

import static app.revanced.integrations.shared.utils.StringRef.str;

import java.util.Arrays;

import app.revanced.integrations.music.settings.Settings;
import app.revanced.integrations.shared.utils.Logger;
import app.revanced.integrations.shared.utils.Utils;

@SuppressWarnings("unused")
public class CustomPlaybackSpeedPatch {
    /**
     * Maximum playback speed, exclusive value.  Custom speeds must be less than this value.
     */
    private static final float MAXIMUM_PLAYBACK_SPEED = 3;

    /**
     * Custom playback speeds.
     */
    private static float[] customPlaybackSpeeds;

    static {
        loadCustomSpeeds();
    }

    public static float[] getArray(float[] original) {
        return customPlaybackSpeeds;
    }

    public static int getLength(int original) {
        return customPlaybackSpeeds.length;
    }

    public static int getSize(int original) {
        return 0;
    }

    private static void resetCustomSpeeds(boolean shouldWarning) {
        if (shouldWarning) {
            Utils.showToastShort(getWarningMessage());
        }

        Utils.showToastShort(str("revanced_custom_playback_speeds_invalid"));
        Settings.CUSTOM_PLAYBACK_SPEEDS.resetToDefault();
    }

    public static void loadCustomSpeeds() {
        try {
            String[] speedStrings = Settings.CUSTOM_PLAYBACK_SPEEDS.get().split("\\s+");
            Arrays.sort(speedStrings);
            if (speedStrings.length == 0) {
                throw new IllegalArgumentException();
            }
            customPlaybackSpeeds = new float[speedStrings.length];
            for (int i = 0, length = speedStrings.length; i < length; i++) {
                final float speed = Float.parseFloat(speedStrings[i]);
                if (speed <= 0 || arrayContains(customPlaybackSpeeds, speed)) {
                    throw new IllegalArgumentException();
                }
                if (speed > MAXIMUM_PLAYBACK_SPEED) {
                    resetCustomSpeeds(true);
                    loadCustomSpeeds();
                    return;
                }
                customPlaybackSpeeds[i] = speed;
            }
        } catch (Exception ex) {
            Logger.printInfo(() -> "parse error", ex);
            resetCustomSpeeds(false);
            loadCustomSpeeds();
        }
    }

    private static boolean arrayContains(float[] array, float value) {
        for (float arrayValue : array) {
            if (arrayValue == value) return true;
        }
        return false;
    }

    public static String getWarningMessage() {
        return str("revanced_custom_playback_speeds_warning", MAXIMUM_PLAYBACK_SPEED + "");
    }

}
