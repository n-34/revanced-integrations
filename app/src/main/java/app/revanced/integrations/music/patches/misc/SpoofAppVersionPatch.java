package app.revanced.integrations.music.patches.misc;

import app.revanced.integrations.music.settings.Settings;

@SuppressWarnings("unused")
public class SpoofAppVersionPatch {

    public static String getVersionOverride(String version) {
        if (!Settings.SPOOF_APP_VERSION.get())
            return version;

        return Settings.SPOOF_APP_VERSION_TARGET.get();
    }
}
