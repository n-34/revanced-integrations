package app.revanced.integrations.youtube.shared;

import android.view.View;

import androidx.annotation.Nullable;

import java.lang.ref.WeakReference;

import app.revanced.integrations.shared.utils.Logger;

/**
 * @noinspection ALL
 */
public final class RootView {
    private static volatile WeakReference<View> searchBarResultsRef = new WeakReference<>(null);

    /**
     * If this value is true, shorts opened.
     */
    private static volatile boolean shortsOpened = false;

    /**
     * Injection point.
     */
    public static void searchBarResultsViewLoaded(View searchbarResults) {
        searchBarResultsRef = new WeakReference<>(searchbarResults);
    }

    /**
     * Injection point.
     *
     * Add a listener to the shorts player overlay View.
     * Triggered when the shorts player overlay View is attached or detached to Windows.
     * It is triggered before the {@link PlayerType} hook.
     *
     * @param view Shorts player overlay (R.id.reel_watch_player)
     */
    public static void onShortsCreate(View view) {
        view.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
            @Override
            public void onViewAttachedToWindow(@Nullable View v) {
                Logger.printDebug(() -> "Shorts opened");
                shortsOpened = true;
            }

            @Override
            public void onViewDetachedFromWindow(@Nullable View v) {
                Logger.printDebug(() -> "Shorts closed");
                shortsOpened = false;
            }
        });
    }

    /**
     * @return If the search bar is on screen.  This includes if the player
     *         is on screen and the search results are behind the player (and not visible).
     *         Detecting the search is covered by the player can be done by checking {@link PlayerType#isMaximizedOrFullscreen()}.
     */
    public static boolean isSearchBarActive() {
        View searchbarResults = searchBarResultsRef.get();
        return searchbarResults != null && searchbarResults.getParent() != null;
    }

    /**
     * @return user is in shorts.
     */
    public static boolean isShorts() {
        return shortsOpened;
    }

}
