package app.revanced.integrations.youtube.patches.misc;

import app.revanced.integrations.shared.utils.Logger;
import app.revanced.integrations.youtube.settings.Settings;

@SuppressWarnings("unused")
public class SplashAnimationPatch {

    public static boolean enableNewSplashAnimationBoolean(boolean original) {
        try {
            return Settings.ENABLE_NEW_SPLASH_ANIMATION.get();
        } catch (Exception ex) {
            Logger.printException(() -> "Failed to load enableNewSplashAnimation", ex);
        }
        return original;
    }

    public static int enableNewSplashAnimationInt(int original) {
        try {
            if (original == 0) {
                return Settings.ENABLE_NEW_SPLASH_ANIMATION.get() ? 3 : 0;
            }
        } catch (Exception ex) {
            Logger.printException(() -> "Failed to load enableNewSplashAnimation", ex);
        }

        return original;
    }
}
