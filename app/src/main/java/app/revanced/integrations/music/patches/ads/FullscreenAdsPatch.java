package app.revanced.integrations.music.patches.ads;

import static app.revanced.integrations.music.utils.ReVancedUtils.hideViewBy0dpUnderCondition;

import android.view.View;

import app.revanced.integrations.music.settings.SettingsEnum;

@SuppressWarnings("unused")
public class FullscreenAdsPatch {

    public static boolean hideFullscreenAds() {
        return SettingsEnum.HIDE_FULLSCREEN_ADS.getBoolean();
    }

    public static void hideFullscreenAds(View view) {
        hideViewBy0dpUnderCondition(
                SettingsEnum.HIDE_FULLSCREEN_ADS.getBoolean(),
                view
        );
    }

    /*
    public static void confirmDialog(final Button button) {
        if (SettingsEnum.HIDE_FULLSCREEN_ADS.getBoolean() && button != null) {
            button.setSoundEffectsEnabled(false);
            button.performClick();
        }
    }
     */

}