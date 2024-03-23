package app.revanced.integrations.youtube.patches.utils;

import android.widget.ImageView;

import app.revanced.integrations.youtube.settings.SettingsEnum;
import app.revanced.integrations.youtube.utils.ReVancedUtils;

@SuppressWarnings("unused")
public class FullscreenButtonPatch {

    public static ImageView hideFullscreenButton(ImageView imageView) {
        final boolean hideView = SettingsEnum.HIDE_FULLSCREEN_BUTTON.getBoolean();

        ReVancedUtils.hideViewUnderCondition(hideView, imageView);
        return hideView ? null : imageView;
    }
}
