package app.revanced.integrations.youtube.patches.utils;

import app.revanced.integrations.youtube.settings.Settings;

@SuppressWarnings("unused")
public class AlwaysRepeatPatch {

    public static boolean enableAlwaysRepeat(boolean original) {
        return !Settings.ALWAYS_REPEAT.get() && original;
    }
}
