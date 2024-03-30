package app.revanced.integrations.youtube.patches.shorts;

import static app.revanced.integrations.shared.utils.Utils.hideViewBy0dpUnderCondition;
import static app.revanced.integrations.shared.utils.Utils.hideViewUnderCondition;

import android.annotation.SuppressLint;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.HorizontalScrollView;

import app.revanced.integrations.youtube.settings.Settings;

@SuppressWarnings("unused")
public class ShortsPatch {
    public static Enum<?> repeat;
    public static Enum<?> singlePlay;
    public static Enum<?> endScreen;
    @SuppressLint("StaticFieldLeak")
    public static Object pivotBar;

    public static Enum<?> changeShortsRepeatState(Enum<?> currentState) {
        switch (Settings.CHANGE_SHORTS_REPEAT_STATE.get()) {
            case 1 -> currentState = repeat;
            case 2 -> currentState = singlePlay;
            case 3 -> currentState = endScreen;
        }

        return currentState;
    }

    public static boolean disableResumingStartupShortsPlayer() {
        return Settings.DISABLE_RESUMING_SHORTS_PLAYER.get();
    }

    public static View hideShortsPlayerNavigationBar(View view) {
        return Settings.HIDE_SHORTS_PLAYER_NAVIGATION_BAR.get() ? null : view;
    }

    public static void hideShortsPlayerNavigationBar() {
        if (!Settings.HIDE_SHORTS_PLAYER_NAVIGATION_BAR.get())
            return;

        if (!(pivotBar instanceof HorizontalScrollView horizontalScrollView))
            return;

        horizontalScrollView.setVisibility(View.GONE);
    }

    public static void hideShortsPlayerCommentsButton(View view) {
        hideViewUnderCondition(Settings.HIDE_SHORTS_PLAYER_COMMENTS_BUTTON.get(), view);
    }

    public static boolean hideShortsPlayerDislikeButton() {
        return Settings.HIDE_SHORTS_PLAYER_DISLIKE_BUTTON.get();
    }

    public static ViewGroup hideShortsPlayerInfoPanel(ViewGroup viewGroup) {
        return Settings.HIDE_SHORTS_PLAYER_INFO_PANEL.get() ? null : viewGroup;
    }

    public static boolean hideShortsPlayerLikeButton() {
        return Settings.HIDE_SHORTS_PLAYER_LIKE_BUTTON.get();
    }

    public static ViewStub hideShortsPlayerPaidPromotionBanner(ViewStub viewStub) {
        return Settings.HIDE_SHORTS_PLAYER_PAID_PROMOTION.get() ? null : viewStub;
    }

    public static boolean hideShortsPlayerPivotButton() {
        return Settings.HIDE_SHORTS_PLAYER_PIVOT_BUTTON.get();
    }

    public static Object hideShortsPlayerPivotButton(Object object) {
        return Settings.HIDE_SHORTS_PLAYER_PIVOT_BUTTON.get() ? null : object;
    }

    public static void hideShortsPlayerRemixButton(View view) {
        hideViewUnderCondition(Settings.HIDE_SHORTS_PLAYER_REMIX_BUTTON.get(), view);
    }

    public static void hideShortsPlayerShareButton(View view) {
        hideViewUnderCondition(Settings.HIDE_SHORTS_PLAYER_SHARE_BUTTON.get(), view);
    }

    public static void hideShortsPlayerSubscriptionsButton(View view) {
        hideViewBy0dpUnderCondition(Settings.HIDE_SHORTS_PLAYER_SUBSCRIPTIONS_BUTTON.get(), view);
    }

    public static int hideShortsPlayerSubscriptionsButton(int original) {
        return Settings.HIDE_SHORTS_PLAYER_SUBSCRIPTIONS_BUTTON.get() ? 0 : original;
    }

    public static boolean hideShortsToolBarBanner() {
        return Settings.HIDE_SHORTS_TOOLBAR_BANNER.get();
    }


    public static void hideShortsToolBarButton(String enumString, View view) {
        for (ToolBarButton button : ToolBarButton.values()) {
            if (enumString.equals(button.name)) {
                hideViewUnderCondition(button.enabled, view);
                break;
            }
        }
    }

    private enum ToolBarButton {
        SEARCH("SEARCH_BOLD", Settings.HIDE_SHORTS_TOOLBAR_SEARCH_BUTTON.get()),
        SEARCH_OLD_LAYOUT("SEARCH_FILLED", Settings.HIDE_SHORTS_TOOLBAR_SEARCH_BUTTON.get()),
        CAMERA("SHORTS_HEADER_CAMERA_BOLD", Settings.HIDE_SHORTS_TOOLBAR_CAMERA_BUTTON.get()),
        CAMERA_OLD_LAYOUT("SHORTS_HEADER_CAMERA", Settings.HIDE_SHORTS_TOOLBAR_CAMERA_BUTTON.get()),
        MENU("MORE_VERT_BOLD", Settings.HIDE_SHORTS_TOOLBAR_MENU_BUTTON.get()),
        MENU_TABLET("MORE_VERT", Settings.HIDE_SHORTS_TOOLBAR_MENU_BUTTON.get());

        private final boolean enabled;
        private final String name;

        ToolBarButton(String name, boolean enabled) {
            this.enabled = enabled;
            this.name = name;
        }
    }
}
