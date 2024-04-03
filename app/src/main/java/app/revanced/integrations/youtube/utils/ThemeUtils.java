package app.revanced.integrations.youtube.utils;

import static app.revanced.integrations.shared.utils.ResourceUtils.getDrawable;
import static app.revanced.integrations.shared.utils.ResourceUtils.getStyleIdentifier;

import android.widget.ImageButton;

@SuppressWarnings("unused")
public class ThemeUtils {
    private static int themeValue;

    public static int getThemeId() {
        final String themeName = isDarkTheme()
                ? "Theme.YouTube.Settings.Dark"
                : "Theme.YouTube.Settings";

        return getStyleIdentifier(themeName);
    }

    public static boolean isDarkTheme() {
        return themeValue == 1;
    }

    public static void setBackButtonDrawable(ImageButton imageButton) {
        final String drawableName = isDarkTheme()
                ? "yt_outline_arrow_left_white_24"
                : "yt_outline_arrow_left_black_24";

        imageButton.setImageDrawable(getDrawable(drawableName));
    }

    /**
     * Injection point.
     */
    public static void setTheme(Enum<?> themeEnum) {
        themeValue = themeEnum.ordinal();
    }
}
