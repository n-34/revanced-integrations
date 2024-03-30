package app.revanced.integrations.music.patches.general;

import static app.revanced.integrations.shared.utils.Utils.hideViewBy0dpUnderCondition;

import android.app.AlertDialog;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import androidx.annotation.NonNull;

import java.util.Objects;

import app.revanced.integrations.music.settings.Settings;

/**
 * @noinspection ALL
 */
@SuppressWarnings("unused")
public class GeneralPatch {
    @NonNull
    private static String videoId = "";
    private static boolean subtitlePrefetched = true;

    public static String changeStartPage(final String browseId) {
        if (!browseId.equals("FEmusic_home"))
            return browseId;

        return Settings.CHANGE_START_PAGE.get();
    }

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

            button.setSoundEffectsEnabled(false);
            button.performClick();
        }
    }

    public static boolean disableAutoCaptions(boolean original) {
        if (!Settings.DISABLE_AUTO_CAPTIONS.get())
            return original;

        return subtitlePrefetched;
    }

    public static void disableDimBehind(Window window) {
        if (window != null) {
            // Disable AlertDialog's background dim.
            window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        }
    }

    public static boolean disableDislikeRedirection() {
        return Settings.DISABLE_DISLIKE_REDIRECTION.get();
    }

    public static boolean enableLandScapeMode(boolean original) {
        try {
            return Settings.ENABLE_LANDSCAPE_MODE.get() || original;
        } catch (Exception ignored) {
            return original;
        }
    }

    public static String enableOldStyleLibraryShelf(final String browseId) {
        return Settings.ENABLE_OLD_STYLE_LIBRARY_SHELF.get()
                && browseId.equals("FEmusic_library_landing")
                ? "FEmusic_liked"
                : browseId;
    }

    public static int hideCastButton(int original) {
        return Settings.HIDE_CAST_BUTTON.get() ? View.GONE : original;
    }

    public static void hideCastButton(View view) {
        hideViewBy0dpUnderCondition(Settings.HIDE_CAST_BUTTON.get(), view);
    }

    public static void hideCategoryBar(View view) {
        hideViewBy0dpUnderCondition(Settings.HIDE_CATEGORY_BAR.get(), view);
    }

    public static boolean hideHistoryButton(boolean original) {
        return !Settings.HIDE_HISTORY_BUTTON.get() && original;
    }

    public static boolean hideNewPlaylistButton() {
        return Settings.HIDE_NEW_PLAYLIST_BUTTON.get();
    }

    public static boolean hideTapToUpdateButton() {
        return Settings.HIDE_TAP_TO_UPDATE_BUTTON.get();
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
}
