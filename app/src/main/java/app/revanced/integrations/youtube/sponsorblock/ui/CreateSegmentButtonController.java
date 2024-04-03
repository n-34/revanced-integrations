package app.revanced.integrations.youtube.sponsorblock.ui;

import static app.revanced.integrations.shared.utils.ResourceUtils.getAnimation;
import static app.revanced.integrations.shared.utils.ResourceUtils.getInteger;
import static app.revanced.integrations.shared.utils.Utils.getChildView;

import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageView;

import java.lang.ref.WeakReference;
import java.util.Objects;

import app.revanced.integrations.shared.utils.Logger;
import app.revanced.integrations.youtube.patches.video.VideoInformation;
import app.revanced.integrations.youtube.settings.Settings;

@SuppressWarnings("unused")
public class CreateSegmentButtonController {
    private static WeakReference<ImageView> buttonReference = new WeakReference<>(null);

    private static Animation fadeIn;
    private static Animation fadeOut;
    private static boolean isShowing;


    /**
     * injection point
     */
    public static void initialize(View youtubeControlsLayout) {
        try {
            ImageView imageView = Objects.requireNonNull(getChildView(youtubeControlsLayout,"revanced_sb_create_segment_button"));

            imageView.setOnClickListener(v -> SponsorBlockViewController.toggleNewSegmentLayoutVisibility());
            buttonReference = new WeakReference<>(imageView);

            // Animations
            if (fadeIn == null) {
                fadeIn = getAnimation("fade_in");
                fadeIn.setDuration(getInteger("fade_duration_fast"));
                fadeOut = getAnimation("fade_out");
                fadeOut.setDuration(getInteger("fade_duration_scheduled"));
            }
            isShowing = true;
            changeVisibilityImmediate(false);
        } catch (Exception ex) {
            Logger.printException(() -> "Unable to set RelativeLayout", ex);
        }
    }

    public static void changeVisibilityImmediate(boolean visible) {
        changeVisibility(visible, true);
    }

    /**
     * injection point
     */
    public static void changeVisibilityNegatedImmediate(boolean isUserScrubbing) {
        ImageView imageView = buttonReference.get();

        if (imageView == null || !isUserScrubbing) return;

        isShowing = false;
        imageView.setVisibility(View.GONE);
    }

    /**
     * injection point
     */
    public static void changeVisibility(boolean visible) {
        changeVisibility(visible, false);
    }

    public static void changeVisibility(boolean visible, boolean immediate) {
        try {
            if (isShowing == visible) return;
            isShowing = visible;

            ImageView iView = buttonReference.get();
            if (iView == null) return;

            if (visible) {
                iView.clearAnimation();
                if (!shouldBeShown()) {
                    return;
                }
                if (!immediate) {
                    iView.startAnimation(fadeIn);
                }
                iView.setVisibility(View.VISIBLE);
                return;
            }

            if (iView.getVisibility() == View.VISIBLE) {
                iView.clearAnimation();
                if (!immediate) {
                    iView.startAnimation(fadeOut);
                }
                iView.setVisibility(View.GONE);
            }
        } catch (Exception ex) {
            Logger.printException(() -> "changeVisibility failure", ex);
        }
    }

    private static boolean shouldBeShown() {
        return Settings.SB_ENABLED.get() && Settings.SB_CREATE_NEW_SEGMENT.get()
                && VideoInformation.isNotAtEndOfVideo();
    }
}
