package app.revanced.integrations.youtube.patches.ads;

import app.revanced.integrations.youtube.settings.Settings;

@SuppressWarnings("unused")
public class VideoAdsPatch {

    public static boolean hideVideoAds() {
        return !Settings.HIDE_VIDEO_ADS.get();
    }

    public static boolean hideVideoAds(boolean original) {
        return !Settings.HIDE_VIDEO_ADS.get() && original;
    }
}
