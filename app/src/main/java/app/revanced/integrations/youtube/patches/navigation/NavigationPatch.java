package app.revanced.integrations.youtube.patches.navigation;

import static app.revanced.integrations.shared.utils.Utils.hideViewUnderCondition;

import android.view.View;
import android.widget.TextView;

import app.revanced.integrations.youtube.settings.Settings;

@SuppressWarnings("unused")
public class NavigationPatch {
    public static Enum<?> lastPivotTab;

    public static boolean switchCreateNotification(boolean original) {
        return Settings.SWITCH_CREATE_NOTIFICATION.get() || original;
    }

    public static void hideCreateButton(View view) {
        hideViewUnderCondition(Settings.HIDE_CREATE_BUTTON.get(), view);
    }

    public static void hideNavigationButton(View view) {
        if (lastPivotTab == null)
            return;

        for (NavigationButton button : NavigationButton.values())
            if (button.name.equals(lastPivotTab.name()))
                hideViewUnderCondition(button.enabled, view);
    }

    public static void hideNavigationLabel(TextView view) {
        hideViewUnderCondition(Settings.HIDE_NAVIGATION_LABEL.get(), view);
    }

    public static boolean enableTabletNavBar(boolean original) {
        return Settings.ENABLE_TABLET_NAVIGATION_BAR.get() || original;
    }

    private enum NavigationButton {
        HOME("PIVOT_HOME", Settings.HIDE_HOME_BUTTON.get()),
        SHORTS("TAB_SHORTS", Settings.HIDE_SHORTS_BUTTON.get()),
        SUBSCRIPTIONS("PIVOT_SUBSCRIPTIONS", Settings.HIDE_SUBSCRIPTIONS_BUTTON.get()),
        NOTIFICATIONS("TAB_ACTIVITY", Settings.HIDE_NOTIFICATIONS_BUTTON.get()),
        LIBRARY("VIDEO_LIBRARY_WHITE", Settings.HIDE_LIBRARY_BUTTON.get());

        private final boolean enabled;
        private final String name;

        NavigationButton(String name, boolean enabled) {
            this.enabled = enabled;
            this.name = name;
        }
    }
}
