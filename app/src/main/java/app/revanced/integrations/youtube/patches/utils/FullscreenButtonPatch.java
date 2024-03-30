package app.revanced.integrations.youtube.patches.utils;

import android.widget.ImageView;

import app.revanced.integrations.shared.utils.Utils;
import app.revanced.integrations.youtube.settings.Settings;

@SuppressWarnings("unused")
public class FullscreenButtonPatch {

    public static ImageView hideFullscreenButton(ImageView imageView) {
        final boolean hideView = Settings.HIDE_FULLSCREEN_BUTTON.get();

        Utils.hideViewUnderCondition(hideView, imageView);
        return hideView ? null : imageView;
    }
}
