package app.revanced.integrations.youtube.patches.overlaybutton;

import static app.revanced.integrations.shared.utils.StringRef.str;
import static app.revanced.integrations.shared.utils.Utils.showToastShort;
import static app.revanced.integrations.youtube.patches.video.PlaybackSpeedPatch.overrideSpeed;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import app.revanced.integrations.shared.utils.Logger;
import app.revanced.integrations.youtube.settings.Settings;
import app.revanced.integrations.youtube.utils.VideoUtils;

@SuppressWarnings("unused")
public class SpeedDialog extends BottomControlButton {
    @Nullable
    private static SpeedDialog instance;

    public SpeedDialog(ViewGroup bottomControlsViewGroup) {
        super(
                bottomControlsViewGroup,
                "speed_dialog_button",
                Settings.OVERLAY_BUTTON_SPEED_DIALOG,
                view -> VideoUtils.playbackSpeedDialogListener(view.getContext()),
                view -> {
                    overrideSpeed(1.0f);
                    showToastShort(str("revanced_overlay_button_speed_dialog_reset"));
                    return true;
                }
        );
    }

    /**
     * Injection point.
     */
    public static void initialize(View bottomControlsViewGroup) {
        try {
            if (bottomControlsViewGroup instanceof ViewGroup viewGroup) {
                instance = new SpeedDialog(viewGroup);
            }
        } catch (Exception ex) {
            Logger.printException(() -> "initialize failure", ex);
        }
    }

    /**
     * Injection point.
     */
    public static void changeVisibility(boolean showing) {
        if (instance != null) instance.setVisibility(showing);
    }

    /**
     * Injection point.
     */
    public static void changeVisibilityNegatedImmediate(boolean scrubbing) {
        if (instance != null && scrubbing) instance.setVisibilityNegatedImmediate();
    }

}