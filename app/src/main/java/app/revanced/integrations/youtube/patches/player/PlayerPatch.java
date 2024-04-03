package app.revanced.integrations.youtube.patches.player;

import static app.revanced.integrations.shared.utils.ResourceUtils.getIdIdentifier;
import static app.revanced.integrations.shared.utils.StringRef.str;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.ImageView;

import app.revanced.integrations.shared.utils.Utils;
import app.revanced.integrations.youtube.settings.Settings;

@SuppressWarnings("unused")
public class PlayerPatch {
    private static final int collapseButtonId = getIdIdentifier("player_collapse_button");
    @SuppressLint("StaticFieldLeak")
    private static ImageView lastView;
    public static void changePlayerOpacity(ImageView imageView) {
        int opacity = Settings.CUSTOM_PLAYER_OVERLAY_OPACITY.get();

        if (opacity < 0 || opacity > 100) {
            Utils.showToastShort(str("revanced_custom_player_overlay_opacity_warning"));
            Settings.CUSTOM_PLAYER_OVERLAY_OPACITY.resetToDefault();
            opacity = Settings.CUSTOM_PLAYER_OVERLAY_OPACITY.defaultValue;
        }

        imageView.setImageAlpha((opacity * 255) / 100);
    }

    public static boolean disableSpeedOverlay(boolean original) {
        return !Settings.DISABLE_SPEED_OVERLAY.get() && original;
    }

    public static boolean disableChapterVibrate() {
        return Settings.DISABLE_HAPTIC_FEEDBACK_CHAPTERS.get();
    }

    public static boolean disableSeekVibrate() {
        return Settings.DISABLE_HAPTIC_FEEDBACK_SEEK.get();
    }

    public static boolean disableSeekUndoVibrate() {
        return Settings.DISABLE_HAPTIC_FEEDBACK_SEEK_UNDO.get();
    }

    public static boolean disableScrubbingVibrate() {
        return Settings.DISABLE_HAPTIC_FEEDBACK_SCRUBBING.get();
    }

    public static boolean disableZoomVibrate() {
        return Settings.DISABLE_HAPTIC_FEEDBACK_ZOOM.get();
    }

    public static boolean hideAutoPlayButton() {
        return Settings.HIDE_AUTOPLAY_BUTTON.get();
    }

    public static boolean hideCaptionsButton(boolean original) {
        return !Settings.HIDE_CAPTIONS_BUTTON.get() && original;
    }

    public static void hideCaptionsButton(View view) {
        if (!Settings.HIDE_CAPTIONS_BUTTON.get())
            return;

        view.setVisibility(View.GONE);
        Utils.hideViewByLayoutParams(view);
    }

    public static boolean hideChannelWatermark(boolean original) {
        return !Settings.HIDE_CHANNEL_WATERMARK.get() && original;
    }

    public static void hideEndScreenCards(View view) {
        if (Settings.HIDE_END_SCREEN_CARDS.get()) {
            view.setVisibility(View.GONE);
        }
    }

    public static boolean hideFilmstripOverlay() {
        return Settings.HIDE_FILMSTRIP_OVERLAY.get();
    }

    public static boolean hideInfoCard(boolean original) {
        return !Settings.HIDE_INFO_CARDS.get() && original;
    }

    public static boolean hideMusicButton() {
        return Settings.HIDE_YOUTUBE_MUSIC_BUTTON.get();
    }

    public static int hidePlayerButton(View view, int visibility) {
        return Settings.HIDE_COLLAPSE_BUTTON.get() && view.getId() == collapseButtonId
                ? View.GONE
                : visibility;
    }

    public static boolean hidePreviousNextButton(boolean previousOrNextButtonVisible) {
        return !Settings.HIDE_PREVIOUS_NEXT_BUTTON.get() && previousOrNextButtonVisible;
    }

    public static boolean hideSeekMessage() {
        return Settings.HIDE_SEEK_MESSAGE.get();
    }

    public static boolean hideSeekUndoMessage() {
        return Settings.HIDE_SEEK_UNDO_MESSAGE.get();
    }
    public static void hideSuggestedVideoOverlay(final ImageView imageView) {
        if (!Settings.HIDE_SUGGESTED_VIDEO_OVERLAY.get())
            return;

        // Prevent adding the listener multiple times.
        if (lastView == imageView)
            return;

        lastView = imageView;

        imageView.getViewTreeObserver().addOnGlobalLayoutListener(() -> {
            if (imageView.isShown()) imageView.callOnClick();
        });
    }

    public static boolean hideSuggestedVideoOverlay() {
        return Settings.HIDE_SUGGESTED_VIDEO_OVERLAY.get()
                && !Settings.HIDE_SUGGESTED_VIDEO_OVERLAY_AUTO_PLAY.get();
    }

    public static void hideSuggestedVideoOverlayAutoPlay(View view) {
        if (!Settings.HIDE_SUGGESTED_VIDEO_OVERLAY.get())
            return;

        if (!Settings.HIDE_SUGGESTED_VIDEO_OVERLAY_AUTO_PLAY.get())
            return;

        if (view != null) {
            view.setSoundEffectsEnabled(false);
            view.performClick();
        }
    }
}
