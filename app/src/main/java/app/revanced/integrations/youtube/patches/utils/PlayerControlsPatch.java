package app.revanced.integrations.youtube.patches.utils;

import static app.revanced.integrations.shared.utils.ResourceUtils.getIdIdentifier;

import android.annotation.SuppressLint;
import android.view.View;

import app.revanced.integrations.shared.utils.Utils;

/**
 * @noinspection ALL
 */
public class PlayerControlsPatch {
    @SuppressLint("StaticFieldLeak")
    private static View playButtonView;
    private static final int playButtonId = getIdIdentifier("player_control_play_pause_replay_button");

    /**
     * Injection point.
     */
    public static void initializeOverlayButtons(View bottomControlsViewGroup) {
        // AlwaysRepeat.initialize(bottomControlsViewGroup);
        // CopyVideoUrl.initialize(bottomControlsViewGroup);
        // CopyVideoUrlTimestamp.initialize(bottomControlsViewGroup);
        // ExternalDownload.initialize(bottomControlsViewGroup);
        // SpeedDialog.initialize(bottomControlsViewGroup);
    }

    /**
     * Injection point.
     */
    public static void initializeSponsorBlockButtons(View youtubeControlsLayout) {
        // CreateSegmentButtonController.initialize(youtubeControlsLayout);
        // VotingButtonController.initialize(youtubeControlsLayout);
    }

    /**
     * Injection point.
     * Legacy method.
     * <p>
     * Play button view does not attach to windows immediately after cold start.
     * Play button view is not attached to the windows until the user touches the player at least once, and the overlay buttons are hidden until then.
     * To prevent this, uses the legacy method to show the overlay button until the play button view is attached to the windows.
     */
    public static void changeVisibility(boolean showing) {
        if (playButtonView == null) {
            changeVisibility(showing, false);
        }
    }

    private static void changeVisibility(boolean showing, boolean animation) {
        // AlwaysRepeat.changeVisibility(showing, animation);
        // CopyVideoUrl.changeVisibility(showing, animation);
        // CopyVideoUrlTimestamp.changeVisibility(showing, animation);
        // ExternalDownload.changeVisibility(showing, animation);
        // SpeedDialog.changeVisibility(showing, animation);

        // CreateSegmentButtonController.changeVisibility(showing, animation);
        // VotingButtonController.changeVisibility(showing, animation);
    }

    /**
     * Injection point.
     * New method.
     * <p>
     * Show or hide the overlay button when the play button view is visible and hidden, respectively.
     * <p>
     * Inject the current view into {@link PlayerControlsPatch#playButtonView} to check that the play button view is attached to the window.
     * From this point on, the legacy method is deprecated.
     */
    public static void changeVisibility(boolean showing, boolean animation, View view) {
        if (view != null && view.getId() == playButtonId) {
            if (playButtonView == null) {
                Utils.runOnMainThreadDelayed(() -> playButtonView = view, 1400);
            }
            changeVisibility(showing, animation);
        }
    }
}