package app.revanced.integrations.youtube.patches.navigation;

import static app.revanced.integrations.shared.utils.Utils.hideViewUnderCondition;
import static app.revanced.integrations.youtube.shared.NavigationBar.NavigationButton;

import android.view.View;
import android.widget.TextView;

import java.util.EnumMap;
import java.util.Map;

import app.revanced.integrations.youtube.settings.Settings;

@SuppressWarnings("unused")
public class NavigationPatch {
    private static final Map<NavigationButton, Boolean> shouldHideMap = new EnumMap<>(NavigationButton.class) {
        {
            put(NavigationButton.HOME, Settings.HIDE_HOME_BUTTON.get());
            put(NavigationButton.SHORTS, Settings.HIDE_SHORTS_BUTTON.get());
            put(NavigationButton.SUBSCRIPTIONS, Settings.HIDE_SUBSCRIPTIONS_BUTTON.get());
            put(NavigationButton.CREATE, Settings.HIDE_CREATE_BUTTON.get());
            put(NavigationButton.NOTIFICATIONS, Settings.HIDE_NOTIFICATIONS_BUTTON.get());

            put(NavigationButton.LIBRARY_LOGGED_OUT, Settings.HIDE_LIBRARY_BUTTON.get());
            put(NavigationButton.LIBRARY_INCOGNITO, Settings.HIDE_LIBRARY_BUTTON.get());
            put(NavigationButton.LIBRARY_OLD_UI, Settings.HIDE_LIBRARY_BUTTON.get());
            put(NavigationButton.LIBRARY_PIVOT_UNKNOWN, Settings.HIDE_LIBRARY_BUTTON.get());
            put(NavigationButton.LIBRARY_YOU, Settings.HIDE_LIBRARY_BUTTON.get());
        }
    };

    /**
     * Injection point.
     */
    public static boolean switchCreateWithNotificationButton(boolean original) {
        return Settings.SWITCH_CREATE_WITH_NOTIFICATIONS_BUTTON.get() || original;
    }

    /**
     * Injection point.
     */
    public static void navigationTabCreated(NavigationButton button, View tabView) {
        if (Boolean.TRUE.equals(shouldHideMap.get(button))) {
            tabView.setVisibility(View.GONE);
        }
    }

    public static void hideNavigationLabel(TextView view) {
        hideViewUnderCondition(Settings.HIDE_NAVIGATION_LABEL.get(), view);
    }

    public static boolean enableTabletNavBar(boolean original) {
        return Settings.ENABLE_TABLET_NAVIGATION_BAR.get() || original;
    }
}
