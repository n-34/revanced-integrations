package app.revanced.integrations.youtube.patches.misc;

import app.revanced.integrations.youtube.settings.Settings;

@SuppressWarnings("unused")
public class SpoofAppVersionPatch {

    public static String getVersionOverride(String appVersion) {
        if (!Settings.SPOOF_APP_VERSION.get())
            return appVersion;

        return Settings.SPOOF_APP_VERSION_TARGET.get();
    }
}
