package app.revanced.integrations.youtube.patches.misc;

import app.revanced.integrations.youtube.settings.Settings;

@SuppressWarnings("unused")
public class LanguageSelectorPatch {

    public static boolean enableLanguageSwitch() {
        return Settings.ENABLE_LANGUAGE_SWITCH.get();
    }
}
