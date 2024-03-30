package app.revanced.integrations.youtube.patches.swipe;

import android.annotation.SuppressLint;
import android.view.View;

import app.revanced.integrations.youtube.settings.Settings;

@SuppressWarnings("unused")
public class SwipeControlsPatch {
    @SuppressLint("StaticFieldLeak")
    public static View engagementOverlay;

    /** @noinspection deprecation*/
    public static boolean disableHDRAutoBrightness() {
        return Settings.DISABLE_HDR_AUTO_BRIGHTNESS.get();
    }

    public static boolean isEngagementOverlayVisible() {
        return engagementOverlay != null && engagementOverlay.getVisibility() == View.VISIBLE;
    }

}
