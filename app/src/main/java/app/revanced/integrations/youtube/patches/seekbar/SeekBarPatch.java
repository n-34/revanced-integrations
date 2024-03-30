package app.revanced.integrations.youtube.patches.seekbar;

import android.graphics.Color;
import android.view.View;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import app.revanced.integrations.shared.settings.BooleanSetting;
import app.revanced.integrations.youtube.settings.Settings;
import app.revanced.integrations.youtube.utils.VideoUtils;

@SuppressWarnings("unused")
public class SeekBarPatch {
    /**
     * Default color of seekbar.
     */
    public static final int ORIGINAL_SEEKBAR_COLOR = 0xFFFF0000;

    public static String appendTimeStampInformation(String original) {
        if (!Settings.APPEND_TIME_STAMP_INFORMATION.get())
            return original;

        final String regex = "\\((.*?)\\)";
        final Matcher matcher = Pattern.compile(regex).matcher(original);

        if (matcher.find()) {
            String matcherGroup = matcher.group(1);
            String appendString = String.format(
                    "\u2009(%s)",
                    Settings.APPEND_TIME_STAMP_INFORMATION_TYPE.get()
                            ? VideoUtils.getFormattedQualityString(matcherGroup)
                            : VideoUtils.getFormattedSpeedString(matcherGroup)
            );
            return original.replaceAll(regex, "") + appendString;
        } else {
            String appendString = String.format(
                    "\u2009(%s)",
                    Settings.APPEND_TIME_STAMP_INFORMATION_TYPE.get()
                            ? VideoUtils.getFormattedQualityString(null)
                            : VideoUtils.getFormattedSpeedString(null)
            );
            return original + appendString;
        }
    }

    public static boolean enableNewThumbnailPreview() {
        return Settings.ENABLE_NEW_THUMBNAIL_PREVIEW.get();
    }

    public static boolean enableSeekbarTapping() {
        return Settings.ENABLE_SEEKBAR_TAPPING.get();
    }

    public static boolean hideTimeStamp() {
        return Settings.HIDE_TIME_STAMP.get();
    }

    public static boolean hideSeekbar() {
        return Settings.HIDE_SEEKBAR.get();
    }

    public static void setContainerClickListener(View view) {
        if (!Settings.APPEND_TIME_STAMP_INFORMATION.get())
            return;

        if (!(view.getParent() instanceof View containerView))
            return;

        final BooleanSetting appendTypeSetting = Settings.APPEND_TIME_STAMP_INFORMATION_TYPE;
        final boolean previousBoolean = appendTypeSetting.get();

        containerView.setOnLongClickListener(timeStampContainerView -> {
                    appendTypeSetting.save(!previousBoolean);
                    return true;
                }
        );
    }

    /**
     * Injection point.
     */
    public static int getSeekbarClickedColorValue(final int colorValue) {
        return colorValue == ORIGINAL_SEEKBAR_COLOR
                ? overrideSeekbarColor(colorValue)
                : colorValue;
    }

    /**
     * Injection point.
     */
    public static int resumedProgressBarColor(final int colorValue) {
        return Settings.ENABLE_CUSTOM_SEEKBAR_COLOR.get()
                ? getSeekbarClickedColorValue(colorValue)
                : colorValue;
    }

    /**
     * Injection point.
     * <p>
     * Overrides all Litho components that use the YouTube seekbar color.
     * Used only for the video thumbnails seekbar.
     * <p>
     * If {@link Settings#HIDE_SEEKBAR_THUMBNAIL} is enabled, this returns a fully transparent color.
     */
    public static int getColor(int colorValue) {
        if (colorValue == ORIGINAL_SEEKBAR_COLOR) {
            if (Settings.HIDE_SEEKBAR_THUMBNAIL.get()) {
                return 0x00000000;
            }
            return overrideSeekbarColor(ORIGINAL_SEEKBAR_COLOR);
        }
        return colorValue;
    }

    /**
     * Points where errors occur when playing videos on the PlayStore (ROOT Build)
     */
    public static int overrideSeekbarColor(final int colorValue) {
        try {
            return Settings.ENABLE_CUSTOM_SEEKBAR_COLOR.get()
                    ? Color.parseColor(Settings.ENABLE_CUSTOM_SEEKBAR_COLOR_VALUE.get())
                    : colorValue;
        } catch (Exception ignored) {
        }
        return colorValue;
    }

}
