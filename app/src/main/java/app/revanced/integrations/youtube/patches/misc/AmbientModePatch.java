package app.revanced.integrations.youtube.patches.misc;

import app.revanced.integrations.youtube.settings.Settings;

@SuppressWarnings("unused")
public class AmbientModePatch {

    public static boolean bypassPowerSaveModeRestrictions(boolean original) {
        return (!Settings.BYPASS_AMBIENT_MODE_RESTRICTIONS.get() && original) || Settings.DISABLE_AMBIENT_MODE.get();
    }
}
