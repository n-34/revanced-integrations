package app.revanced.integrations.youtube.patches.misc;

import app.revanced.integrations.shared.utils.Logger;
import app.revanced.integrations.youtube.settings.Settings;

@SuppressWarnings("unused")
public class LayoutOverridePatch {
    /**
     * Context is overridden when trying to play a YouTube video from the Google Play Store,
     * Which is speculated to affect LayoutOverridePatch
     */
    public static boolean enableTabletLayout() {
        try {
            return Settings.ENABLE_TABLET_LAYOUT.get();
        } catch (Exception ex) {
            Logger.printException(() -> "enableTabletLayout failed", ex);
        }
        return false;
    }

    public static int getLayoutOverride(int original) {
        try {
            return Settings.ENABLE_PHONE_LAYOUT.get() ? 480 : original;
        } catch (Exception ex) {
            Logger.printException(() -> "getLayoutOverride failed", ex);
        }
        return original;
    }
}
