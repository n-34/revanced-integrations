package app.revanced.integrations.youtube.patches.video;

import static app.revanced.integrations.shared.utils.StringRef.str;
import static app.revanced.integrations.youtube.utils.VideoUtils.getCurrentQuality;

import androidx.annotation.Nullable;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import app.revanced.integrations.shared.settings.IntegerSetting;
import app.revanced.integrations.shared.utils.Logger;
import app.revanced.integrations.shared.utils.Utils;
import app.revanced.integrations.youtube.settings.Settings;

@SuppressWarnings("unused")
public class VideoQualityPatch {
    private static final IntegerSetting mobileQualitySetting = Settings.DEFAULT_VIDEO_QUALITY_MOBILE;
    private static final IntegerSetting wifiQualitySetting = Settings.DEFAULT_VIDEO_QUALITY_WIFI;

    /**
     * The available qualities of the current video in human readable form: [1080, 720, 480]
     */
    @Nullable
    private static List<Integer> videoQualities;

    private static void changeDefaultQuality(final int defaultQuality) {
        if (!Settings.ENABLE_SAVE_VIDEO_QUALITY.get())
            return;

        final Utils.NetworkType networkType = Utils.getNetworkType();

        switch (networkType) {
            case NONE -> {
                Utils.showToastShort(str("revanced_save_video_quality_none"));
                return;
            }
            case MOBILE -> mobileQualitySetting.save(defaultQuality);
            default -> wifiQualitySetting.save(defaultQuality);
        }

        Utils.showToastShort(str("revanced_save_video_quality_" + networkType.getName(), defaultQuality + "p"));
    }

    public static void overrideQuality(final int qualityValue) {
        Logger.printDebug(() -> "Quality changed to: " + qualityValue);
        // Rest of the implementation added by patch.
    }

    /**
     * Injection point.
     *
     * @param qualities Video qualities available, ordered from largest to smallest, with index 0 being the 'automatic' value of -2
     */
    public static void setVideoQualityList(Object[] qualities) {
        try {
            if (videoQualities == null || videoQualities.size() != qualities.length) {
                videoQualities = new ArrayList<>(qualities.length);
                for (Object streamQuality : qualities) {
                    for (Field field : streamQuality.getClass().getFields()) {
                        if (field.getType().isAssignableFrom(Integer.TYPE)
                                && field.getName().length() <= 2) {
                            videoQualities.add(field.getInt(streamQuality));
                        }
                    }
                }
                Logger.printDebug(() -> "videoQualities: " + videoQualities);
            }
        } catch (Exception ex) {
            Logger.printException(() -> "Failed to set quality list", ex);
        }
    }

    private static void setVideoQuality(int preferredQuality) {
        if (videoQualities != null) {
            int qualityToUse = videoQualities.get(0); // first element is automatic mode
            for (Integer quality : videoQualities) {
                if (quality <= preferredQuality && qualityToUse < quality) {
                    qualityToUse = quality;
                }
            }
            preferredQuality = qualityToUse;
        }
        overrideQuality(preferredQuality);
    }

    /**
     * Injection point.
     */
    public static void newVideoStarted(final String ignoredVideoId) {
        final int preferredQuality =
                Utils.getNetworkType() == Utils.NetworkType.MOBILE
                        ? mobileQualitySetting.get()
                        : wifiQualitySetting.get();

        if (preferredQuality == -2)
            return;

        Utils.runOnMainThreadDelayed(() -> setVideoQuality(preferredQuality), 500);
    }

    /**
     * Injection point. New quality menu.
     *
     * @param selectedQuality user selected quality
     */
    public static void userChangedQuality(final int selectedQuality) {
        Utils.runOnMainThreadDelayed(() ->
                        changeDefaultQuality(getCurrentQuality(selectedQuality)),
                300
        );
    }

    /**
     * Injection point. Old quality menu.
     *
     * @param selectedQualityIndex user selected quality index
     */
    public static void userChangedQualityIndex(final int selectedQualityIndex) {
        if (videoQualities == null)
            return;

        changeDefaultQuality(videoQualities.get(selectedQualityIndex));
    }
}