package app.revanced.integrations.youtube.shared;

import android.view.View;

import java.lang.ref.WeakReference;

import app.revanced.integrations.shared.utils.Utils;
import app.revanced.integrations.youtube.patches.components.InvalidateStateFilter;

/**
 * @noinspection ALL
 */
public final class RootView {
    private static volatile WeakReference<View> searchBarResultsRef = new WeakReference<>(null);

    private static volatile WeakReference<View> playerViewRef = new WeakReference<>(null);

    private static volatile WeakReference<View> shortsViewRef = new WeakReference<>(null);

    /**
     * This value is whether the screen was turned off while minimized,
     * and a request was made to re-draw the view after the screen came back on.
     */
    private static volatile boolean invalidateCalled = false;

    /**
     * Injection point.
     */
    public static void searchBarResultsViewLoaded(View searchbarResults) {
        searchBarResultsRef = new WeakReference<>(searchbarResults);
    }

    /**
     * Injection point.
     *
     * @param view Shorts player overlay (R.id.reel_watch_player)
     */
    public static void onShortsCreate(View view) {
        shortsViewRef = new WeakReference<>(view);
    }

    /**
     * Injection point.
     *
     * @param view controls overlay (R.layout.size_adjustable_youtube_controls_overlay)
     */
    public static void onPlayerCreate(View view) {
        playerViewRef = new WeakReference<>(view);
    }

    /**
     * This method is only accessible by {@link InvalidateStateFilter}.
     *
     * @param invalidate whether {@link View#invalidate} was detected.
     */
    public static void onInvalidateStateChanged(boolean invalidate) {
        invalidateCalled = invalidate;
    }

    /**
     * @return If the search bar is on screen.  This includes if the player
     *         is on screen and the search results are behind the player (and not visible).
     *         Detecting the search is covered by the player can be done by checking {@link PlayerType#isMaximizedOrFullscreenOrSliding()}.
     */
    public static boolean isSearchBarActive() {
        View searchbarResults = searchBarResultsRef.get();
        return searchbarResults != null && searchbarResults.getParent() != null;
    }

    /**
     * @return user is in shorts.
     */
    public static boolean isShortsActive() {
        View shortsView = shortsViewRef.get();
        return shortsView != null && shortsView.isAttachedToWindow();
    }

    /**
     * @return user is in player.
     */
    public static boolean isPlayerActive() {
        // Check PlayerType first.
        if (PlayerType.getCurrent().isMaximizedOrFullscreenOrSliding()) {
            return true;
        }

        if (invalidateCalled) {
            // If {@link View#invalidate} is detected, returns true.
            // At the point when this method is called, we can check whether the playerView is attached to Windows.
            // Overrides the value of {@link RootView#invalidateCalled}.
            View playerView = playerViewRef.get();
            if (playerView != null) {
                Utils.runOnMainThreadDelayed(() -> invalidateCalled = playerView.isAttachedToWindow(), 250);
            }
            return true;
        }

        return false;
    }
}
