package app.revanced.integrations.youtube.patches.fullscreen;

import static app.revanced.integrations.shared.utils.StringRef.str;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.view.View;
import android.widget.FrameLayout;

import androidx.coordinatorlayout.widget.CoordinatorLayout;

import app.revanced.integrations.shared.utils.Utils;
import app.revanced.integrations.youtube.settings.Settings;

@SuppressWarnings("unused")
public class FullscreenPatch {
    private static final int DEFAULT_MARGIN_TOP = (int) Settings.QUICK_ACTIONS_MARGIN_TOP.defaultValue;
    @SuppressLint("StaticFieldLeak")
    public static Activity watchDescriptorActivity;
    private static boolean isLandScapeVideo = true;
    private static volatile boolean isScreenOn;

    public static boolean disableAmbientMode() {
        return !Settings.DISABLE_AMBIENT_MODE_IN_FULLSCREEN.get();
    }

    public static boolean disableLandScapeMode(boolean original) {
        return Settings.DISABLE_LANDSCAPE_MODE.get() || original;
    }

    public static boolean enableCompactControlsOverlay(boolean original) {
        return Settings.ENABLE_COMPACT_CONTROLS_OVERLAY.get() || original;
    }

    public static boolean forceFullscreen(boolean original) {
        if (!Settings.FORCE_FULLSCREEN.get())
            return original;

        Utils.runOnMainThreadDelayed(FullscreenPatch::setOrientation, 1000);
        return true;
    }

    public static boolean keepFullscreen(boolean original) {
        if (!Settings.KEEP_LANDSCAPE_MODE.get())
            return original;

        return isScreenOn;
    }

    public static void setScreenStatus() {
        if (!Settings.KEEP_LANDSCAPE_MODE.get())
            return;

        isScreenOn = true;
        Utils.runOnMainThreadDelayed(() -> isScreenOn = false, Settings.KEEP_LANDSCAPE_MODE_TIMEOUT.get());
    }

    public static boolean hideAutoPlayPreview() {
        return Settings.HIDE_AUTOPLAY_PREVIEW.get() || Settings.HIDE_AUTOPLAY_BUTTON.get();
    }

    public static boolean hideEndScreenOverlay() {
        return Settings.HIDE_END_SCREEN_OVERLAY.get();
    }

    public static int hideFullscreenPanels() {
        return Settings.HIDE_FULLSCREEN_PANELS.get() ? 8 : 0;
    }

    public static void hideFullscreenPanels(CoordinatorLayout coordinatorLayout) {
        if (!Settings.HIDE_FULLSCREEN_PANELS.get()) return;
        coordinatorLayout.setVisibility(View.GONE);
    }

    public static void hideQuickActions(View view) {
        Utils.hideViewBy0dpUnderCondition(
                Settings.HIDE_FULLSCREEN_PANELS.get() || Settings.HIDE_QUICK_ACTIONS.get(),
                view
        );
    }

    private static void setOrientation() {
        final int requestedOrientation = isLandScapeVideo
                ? ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                : watchDescriptorActivity.getRequestedOrientation();

        watchDescriptorActivity.setRequestedOrientation(requestedOrientation);
    }

    public static void setQuickActionMargin(FrameLayout frameLayout) {
        int marginTop = Settings.QUICK_ACTIONS_MARGIN_TOP.get();

        if (marginTop < 0 || marginTop > 64) {
            Utils.showToastShort(str("revanced_quick_actions_margin_top_warning"));
            Settings.QUICK_ACTIONS_MARGIN_TOP.save(DEFAULT_MARGIN_TOP);
            marginTop = DEFAULT_MARGIN_TOP;
        }

        if (!(frameLayout.getLayoutParams() instanceof FrameLayout.MarginLayoutParams marginLayoutParams))
            return;
        marginLayoutParams.setMargins(
                marginLayoutParams.leftMargin,
                marginTop,
                marginLayoutParams.rightMargin,
                marginLayoutParams.bottomMargin
        );
        frameLayout.requestLayout();
    }

    public static void setVideoPortrait(int width, int height) {
        if (!Settings.FORCE_FULLSCREEN.get())
            return;

        isLandScapeVideo = width > height;
    }

    public static boolean showFullscreenTitle() {
        return Settings.SHOW_FULLSCREEN_TITLE.get() || !Settings.HIDE_FULLSCREEN_PANELS.get();
    }
}
