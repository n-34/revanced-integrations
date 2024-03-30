package app.revanced.integrations.youtube.utils;

import static app.revanced.integrations.shared.utils.ResourceUtils.identifier;
import static app.revanced.integrations.shared.utils.Utils.getResources;

import android.widget.ImageButton;

import app.revanced.integrations.shared.utils.ResourceType;

@SuppressWarnings("unused")
public class ThemeUtils {
    private static int themeValue;

    public static int getThemeId() {
        final String themeName = isDarkTheme()
                ? "Theme.YouTube.Settings.Dark"
                : "Theme.YouTube.Settings";

        return identifier(themeName, ResourceType.STYLE);
    }

    public static boolean isDarkTheme() {
        return themeValue == 1;
    }

    /** @noinspection deprecation*/
    public static void setBackButtonDrawable(ImageButton imageButton) {
        final String drawableName = isDarkTheme()
                ? "yt_outline_arrow_left_white_24"
                : "yt_outline_arrow_left_black_24";

        imageButton.setImageDrawable(getResources().getDrawable(identifier(drawableName, ResourceType.DRAWABLE)));
    }

    /**
     * Injection point.
     */
    public static void setTheme(Enum<?> themeEnum) {
        themeValue = themeEnum.ordinal();
    }
}
