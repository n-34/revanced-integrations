package app.revanced.integrations.youtube.patches.general;

import static app.revanced.integrations.shared.utils.StringRef.str;
import static app.revanced.integrations.shared.utils.Utils.hideViewUnderCondition;
import static app.revanced.integrations.youtube.shared.NavigationBar.NavigationButton;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;

import app.revanced.integrations.shared.utils.Logger;
import app.revanced.integrations.shared.utils.Utils;
import app.revanced.integrations.youtube.patches.utils.ViewGroupMarginLayoutParamsPatch;
import app.revanced.integrations.youtube.settings.Settings;

/**
 * @noinspection ALL
 */
@SuppressWarnings("unused")
public class GeneralPatch {

    // region [Change start page] patch

    private static final String MAIN_ACTIONS = "android.intent.action.MAIN";

    /**
     * Change the start page only when the user starts the app on the launcher.
     * <p>
     * If the app starts with a widget or the app starts through a shortcut,
     * Action of intent is not {@link #MAIN_ACTIONS}.
     *
     * @param intent original intent
     */
    public static void changeStartPage(@NonNull Intent intent) {
        if (!Objects.equals(intent.getAction(), MAIN_ACTIONS))
            return;

        final String startPage = Settings.CHANGE_START_PAGE.get();
        if (startPage.isEmpty())
            return;

        if (startPage.startsWith("open.")) {
            intent.setAction("com.google.android.youtube.action." + startPage);
        } else if (startPage.startsWith("www.youtube.com")) {
            intent.setData(Uri.parse(startPage));
        } else {
            Utils.showToastShort(str("revanced_change_start_page_warning"));
            Settings.CHANGE_START_PAGE.resetToDefault();
            return;
        }
        Logger.printDebug(() -> "Changing start page to " + startPage);
    }

    // endregion

    // region [Disable auto audio tracks] patch

    private static final String DEFAULT_AUDIO_TRACKS_IDENTIFIER = "original";
    private static ArrayList<Object> formatStreamModelArray;

    /**
     * Find the stream format containing the parameter {@link DEFAULT_AUDIO_TRACKS_IDENTIFIER}, and save to the array.
     *
     * @param formatStreamModel stream format model including audio tracks.
     */
    public static void setFormatStreamModelArray(final Object formatStreamModel) {
        if (!Settings.DISABLE_AUTO_AUDIO_TRACKS.get()) {
            return;
        }

        // Ignoring, as the stream format model array has already been added.
        if (formatStreamModelArray != null) {
            return;
        }

        // Ignoring, as it is not an original audio track.
        if (!formatStreamModel.toString().contains(DEFAULT_AUDIO_TRACKS_IDENTIFIER)) {
            return;
        }

        // For some reason, when YouTube handles formatStreamModelArray,
        // it uses an array with duplicate values at the first and second indices.
        formatStreamModelArray = new ArrayList<>();
        formatStreamModelArray.add(formatStreamModel);
        formatStreamModelArray.add(formatStreamModel);
    }

    /**
     * Returns an array of stream format models containing the default audio tracks.
     *
     * @param localizedFormatStreamModelArray   stream format model array consisting of audio tracks in the system's language.
     * @return                                  stream format model array consisting of original audio tracks.
     */
    public static ArrayList<Object> getFormatStreamModelArray(final ArrayList<Object> localizedFormatStreamModelArray) {
        if (!Settings.DISABLE_AUTO_AUDIO_TRACKS.get()) {
            return localizedFormatStreamModelArray;
        }

        // Ignoring, as the stream format model array is empty.
        if (formatStreamModelArray == null || formatStreamModelArray.isEmpty()) {
            return localizedFormatStreamModelArray;
        }

        // Initialize the array before returning it.
        ArrayList<Object> defaultFormatStreamModelArray = formatStreamModelArray;
        formatStreamModelArray = null;
        return defaultFormatStreamModelArray;
    }

    // endregion

    // region [Disable auto captions] patch

    private static boolean subtitlePrefetched = true;
    @NonNull
    private static String videoId = "";

    public static boolean disableAutoCaptions(boolean original) {
        if (!Settings.DISABLE_AUTO_CAPTIONS.get())
            return original;

        return subtitlePrefetched;
    }

    public static void newVideoStarted(@NonNull String newlyLoadedVideoId) {
        if (Objects.equals(newlyLoadedVideoId, videoId)) {
            return;
        }
        videoId = newlyLoadedVideoId;
        subtitlePrefetched = false;
    }

    public static void prefetchSubtitleTrack() {
        subtitlePrefetched = true;
    }

    // endregion

    // region [Disable splash animation] patch

    public static boolean disableSplashAnimation(boolean original) {
        try {
            return !Settings.DISABLE_SPLASH_ANIMATION.get() && original;
        } catch (Exception ex) {
            Logger.printException(() -> "Failed to load disableSplashAnimation", ex);
        }
        return original;
    }

    // endregion

    // region [Enable gradient loading screen] patch

    public static boolean enableGradientLoadingScreen() {
        return Settings.ENABLE_GRADIENT_LOADING_SCREEN.get();
    }

    // endregion

    // region [Enable tablet mini player] patch

    public static boolean enableTabletMiniPlayer(boolean original) {
        return Settings.ENABLE_TABLET_MINI_PLAYER.get() || original;
    }

    // endregion

    // region [Hide layout components] patch

    /**
     * hide account menu in you tab
     *
     * @param menuTitleCharSequence menu title
     */
    public static void hideAccountList(View view, CharSequence menuTitleCharSequence) {
        if (!Settings.HIDE_ACCOUNT_MENU.get())
            return;
        if (menuTitleCharSequence == null)
            return;
        if (!(view.getParent().getParent().getParent() instanceof ViewGroup viewGroup))
            return;

        hideAccountMenu(viewGroup, menuTitleCharSequence.toString());
    }

    /**
     * hide account menu for tablet and old clients
     *
     * @param menuTitleCharSequence menu title
     */
    public static void hideAccountMenu(View view, CharSequence menuTitleCharSequence) {
        if (!Settings.HIDE_ACCOUNT_MENU.get())
            return;
        if (menuTitleCharSequence == null)
            return;
        if (!(view.getParent().getParent() instanceof ViewGroup viewGroup))
            return;

        hideAccountMenu(viewGroup, menuTitleCharSequence.toString());
    }

    private static void hideAccountMenu(ViewGroup viewGroup, String menuTitleString) {
        String[] blockList = Settings.HIDE_ACCOUNT_MENU_FILTER_STRINGS.get().split("\\n");

        for (String filter : blockList) {
            if (menuTitleString.equals(filter) && !filter.isEmpty()) {
                if (viewGroup.getLayoutParams() instanceof MarginLayoutParams)
                    ViewGroupMarginLayoutParamsPatch.hideViewGroupByMarginLayoutParams(viewGroup);
                else
                    viewGroup.setLayoutParams(new LayoutParams(0, 0));
            }
        }
    }

    public static boolean hideFloatingMicrophone(boolean original) {
        return Settings.HIDE_FLOATING_MICROPHONE.get() || original;
    }

    public static int hideHandle(int originalValue) {
        return Settings.HIDE_HANDLE.get() ? 8 : originalValue;
    }

    public static boolean hideSnackBar() {
        return Settings.HIDE_SNACK_BAR.get();
    }

    // endregion

    // region [Hide navigation bar components] patch

    private static final Map<NavigationButton, Boolean> shouldHideMap = new EnumMap<>(NavigationButton.class) {
        {
            put(NavigationButton.HOME, Settings.HIDE_NAVIGATION_HOME_BUTTON.get());
            put(NavigationButton.SHORTS, Settings.HIDE_NAVIGATION_SHORTS_BUTTON.get());
            put(NavigationButton.SUBSCRIPTIONS, Settings.HIDE_NAVIGATION_SUBSCRIPTIONS_BUTTON.get());
            put(NavigationButton.CREATE, Settings.HIDE_NAVIGATION_CREATE_BUTTON.get());
            put(NavigationButton.NOTIFICATIONS, Settings.HIDE_NAVIGATION_NOTIFICATIONS_BUTTON.get());

            put(NavigationButton.LIBRARY_LOGGED_OUT, Settings.HIDE_NAVIGATION_LIBRARY_BUTTON.get());
            put(NavigationButton.LIBRARY_INCOGNITO, Settings.HIDE_NAVIGATION_LIBRARY_BUTTON.get());
            put(NavigationButton.LIBRARY_OLD_UI, Settings.HIDE_NAVIGATION_LIBRARY_BUTTON.get());
            put(NavigationButton.LIBRARY_PIVOT_UNKNOWN, Settings.HIDE_NAVIGATION_LIBRARY_BUTTON.get());
            put(NavigationButton.LIBRARY_YOU, Settings.HIDE_NAVIGATION_LIBRARY_BUTTON.get());
        }
    };

    public static boolean enableNarrowNavigationButton(boolean original) {
        return Settings.ENABLE_NARROW_NAVIGATION_BUTTONS.get() || original;
    }

    public static boolean switchCreateWithNotificationButton(boolean original) {
        return Settings.SWITCH_CREATE_WITH_NOTIFICATIONS_BUTTON.get() || original;
    }

    public static void navigationTabCreated(NavigationButton button, View tabView) {
        if (BooleanUtils.isTrue(shouldHideMap.get(button))) {
            tabView.setVisibility(View.GONE);
        }
    }

    public static void hideNavigationLabel(TextView view) {
        hideViewUnderCondition(Settings.HIDE_NAVIGATION_LABEL.get(), view);
    }

    // endregion

    // region [Layout switch] patch

    public static boolean enableTabletLayout() {
        try {
            return Settings.ENABLE_TABLET_LAYOUT.get();
        } catch (Exception ex) {
            Logger.printException(() -> "enableTabletLayout failed", ex);
        }
        return false;
    }

    public static int enablePhoneLayout(int original) {
        try {
            return Settings.ENABLE_PHONE_LAYOUT.get() ? 480 : original;
        } catch (Exception ex) {
            Logger.printException(() -> "getLayoutOverride failed", ex);
        }
        return original;
    }

    // endregion

    // region [Remove viewer discretion dialog] patch

    /**
     * Injection point.
     * <p>
     * The {@link AlertDialog#getButton(int)} method must be used after {@link AlertDialog#show()} is called.
     * Otherwise {@link AlertDialog#getButton(int)} method will always return null.
     * https://stackoverflow.com/a/4604145
     * <p>
     * That's why {@link AlertDialog#show()} is absolutely necessary.
     * Instead, use two tricks to hide Alertdialog.
     * <p>
     * 1. Change the size of AlertDialog to 0.
     * 2. Disable AlertDialog's background dim.
     * <p>
     * This way, AlertDialog will be completely hidden,
     * and {@link AlertDialog#getButton(int)} method can be used without issue.
     */
    public static void confirmDialog(final AlertDialog dialog) {
        if (!Settings.REMOVE_VIEWER_DISCRETION_DIALOG.get()) {
            return;
        }

        // This method is called after AlertDialog#show(),
        // So we need to hide the AlertDialog before pressing the possitive button.
        final Window window = dialog.getWindow();
        final Button button = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        if (window != null && button != null) {
            WindowManager.LayoutParams params = window.getAttributes();
            params.height = 0;
            params.width = 0;

            // Change the size of AlertDialog to 0.
            window.setAttributes(params);

            // Disable AlertDialog's background dim.
            window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            Utils.clickView(button);
        }
    }

    public static void confirmDialogAgeVerified(final AlertDialog dialog) {
        final Button button = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        if (!button.getText().toString().equals(str("og_continue")))
            return;

        confirmDialog(dialog);
    }

    // endregion

    // region [Spoof app version] patch

    public static String getVersionOverride(String appVersion) {
        if (!Settings.SPOOF_APP_VERSION.get())
            return appVersion;

        return Settings.SPOOF_APP_VERSION_TARGET.get();
    }

    // endregion

    // region [Toolbar components] patch

    public static boolean enableWideSearchBar(boolean original) {
        return Settings.ENABLE_WIDE_SEARCH_BAR.get() || original;
    }

    public static boolean enableWideSearchBarInYouTab(boolean original) {
        if (!Settings.ENABLE_WIDE_SEARCH_BAR.get())
            return original;
        else
            return !Settings.ENABLE_WIDE_SEARCH_BAR_IN_YOU_TAB.get() && original;
    }

    public static boolean hideCastButton(boolean original) {
        return !Settings.HIDE_TOOLBAR_CAST_BUTTON.get() && original;
    }

    public static void hideCastButton(MenuItem menuItem) {
        if (!Settings.HIDE_TOOLBAR_CAST_BUTTON.get())
            return;

        menuItem.setVisible(false);
        menuItem.setEnabled(false);
    }

    public static void hideCreateButton(String enumString, View view) {
        if (!Settings.HIDE_TOOLBAR_CREATE_BUTTON.get())
            return;

        final boolean condition = StringUtils.equalsAny(
                enumString,
                "CREATION_ENTRY", // Create button for Phone layout
                "FAB_CAMERA" // Create button for Tablet layout
        );

        hideViewUnderCondition(condition, view);
    }

    public static void hideNotificationButton(String enumString, View view) {
        if (!Settings.HIDE_TOOLBAR_NOTIFICATION_BUTTON.get())
            return;

        final boolean condition = StringUtils.equalsAny(
                enumString,
                "TAB_ACTIVITY" // Notification button
        );

        hideViewUnderCondition(condition, view);
    }

    public static boolean hideSearchTermThumbnail() {
        return Settings.HIDE_SEARCH_TERM_THUMBNAIL.get();
    }

    public static boolean hideTrendingSearches(boolean original) {
        return Settings.HIDE_TRENDING_SEARCHES.get() || original;
    }

    public static void hideVoiceSearchButton(View view) {
        hideViewUnderCondition(
                Settings.HIDE_VOICE_SEARCH_BUTTON.get(),
                view
        );
    }

    public static void hideVoiceSearchButton(View view, int visibility) {
        view.setVisibility(
                Settings.HIDE_VOICE_SEARCH_BUTTON.get()
                        ? View.GONE : visibility
        );
    }

    // endregion

}
