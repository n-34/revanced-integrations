package app.revanced.integrations.youtube.patches.player;

import static app.revanced.integrations.shared.utils.ResourceUtils.getIdIdentifier;
import static app.revanced.integrations.shared.utils.StringRef.str;
import static app.revanced.integrations.shared.utils.Utils.hideViewBy0dpUnderCondition;
import static app.revanced.integrations.shared.utils.Utils.hideViewUnderCondition;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import java.lang.ref.WeakReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import app.revanced.integrations.shared.settings.BooleanSetting;
import app.revanced.integrations.shared.settings.IntegerSetting;
import app.revanced.integrations.shared.utils.Logger;
import app.revanced.integrations.shared.utils.ResourceUtils;
import app.revanced.integrations.shared.utils.Utils;
import app.revanced.integrations.youtube.patches.utils.PatchStatus;
import app.revanced.integrations.youtube.settings.Settings;
import app.revanced.integrations.youtube.shared.RootView;
import app.revanced.integrations.youtube.utils.VideoUtils;

@SuppressWarnings("unused")
public class PlayerPatch {
    private static final IntegerSetting quickActionsMarginTopSetting = Settings.QUICK_ACTIONS_TOP_MARGIN;


    // region [Ambient mode control] patch

    public static boolean bypassAmbientModeRestrictions(boolean original) {
        return (!Settings.BYPASS_AMBIENT_MODE_RESTRICTIONS.get() && original) || Settings.DISABLE_AMBIENT_MODE.get();
    }

    public static boolean disableAmbientModeInFullscreen() {
        return !Settings.DISABLE_AMBIENT_MODE_IN_FULLSCREEN.get();
    }

    // endregion

    // region [Change player flyout menu toggles] patch

    public static boolean changeSwitchToggle(boolean original) {
        return !Settings.CHANGE_PLAYER_FLYOUT_MENU_TOGGLE.get() && original;
    }

    public static String getToggleString(String str) {
        return ResourceUtils.getString(str);
    }

    // endregion

    // region [Description components] patch

    /**
     * view id R.id.content
     */
    private static final int contentId = getIdIdentifier("content");

    /**
     * engagementSubHeaderActive is updated every time the engagement panel is opened.
     * <p>
     * If engagementSubHeaderActive is true, the player's comments panel has been opened.
     * If engagementSubHeaderActive is false, the video description panel has been opened, or the comments panel for Shorts or community posts has been opened.
     */
    private static boolean engagementSubHeaderActive = false;

    /**
     * The last time the clickDescriptionView method was called.
     */
    private static long lastTimeDescriptionViewInvoked;

    public static void engagementPanelSubHeaderViewLoaded(@Nullable View engagementPanelView) {
        engagementSubHeaderActive = engagementPanelView != null;
    }

    public static void onVideoDescriptionCreate(RecyclerView recyclerView) {
        if (!Settings.EXPAND_VIDEO_DESCRIPTION.get())
            return;

        recyclerView.getViewTreeObserver().addOnDrawListener(() -> {
            try {
                // Video description's recyclerView is a child view of [contentId].
                if (!(recyclerView.getParent().getParent() instanceof View contentView)) {
                    return;
                }
                if (contentView.getId() != contentId) {
                    return;
                }
                // Video description panel is only open when the player is active.
                if (!RootView.isPlayerActive()) {
                    return;
                }
                // Ignored when the player comments panel opens.
                if (engagementSubHeaderActive) {
                    return;
                }
                // Typically, descriptionView is placed as the second child of recyclerView.
                if (recyclerView.getChildAt(1) instanceof ViewGroup viewGroup) {
                    clickDescriptionView(viewGroup);
                }
                // In some videos, descriptionView is placed as the third child of recyclerView.
                if (recyclerView.getChildAt(2) instanceof ViewGroup viewGroup) {
                    clickDescriptionView(viewGroup);
                }
                // Even if both methods are performed, there is no major issue with the operation of the patch.
            } catch (Exception ex) {
                Logger.printException(() -> "onVideoDescriptionCreate failed.", ex);
            }
        });
    }

    private static void clickDescriptionView(@NonNull ViewGroup descriptionViewGroup) {
        final View descriptionView = descriptionViewGroup.getChildAt(0);
        if (descriptionView == null) {
            return;
        }
        // This method is sometimes used multiple times.
        // To prevent this, ignore method reuse within 1 second.
        final long now = System.currentTimeMillis();
        if (now - lastTimeDescriptionViewInvoked < 1000) {
            return;
        }
        lastTimeDescriptionViewInvoked = now;

        // The type of descriptionView can be either ViewGroup or TextView. (A/B tests)
        // If the type of descriptionView is TextView, longer delay is required.
        final long delayMillis = descriptionView instanceof TextView
                ? 500
                : 100;

        Utils.runOnMainThreadDelayed(() -> {
            descriptionView.setSoundEffectsEnabled(false);
            descriptionView.performClick();
        }, delayMillis);
    }

    /**
     * This method is invoked only when the view type of descriptionView is {@link TextView}. (A/B tests)
     *
     * @param textView descriptionView.
     * @param original Whether to apply {@link TextView#setTextIsSelectable}.
     *                 Patch replaces the {@link TextView#setTextIsSelectable} method invoke.
     */
    public static void disableVideoDescriptionInteraction(TextView textView, boolean original) {
        if (textView != null) {
            textView.setTextIsSelectable(
                    !Settings.DISABLE_VIDEO_DESCRIPTION_INTERACTION.get() && original
            );
        }
    }

    // endregion

    // region [Disable haptic feedback] patch

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

    // endregion

    // region [Fullscreen components] patch

    public static void disableEngagementPanels(CoordinatorLayout coordinatorLayout) {
        if (!Settings.DISABLE_ENGAGEMENT_PANEL.get()) return;
        coordinatorLayout.setVisibility(View.GONE);
    }

    public static void showVideoTitleSection(FrameLayout frameLayout, View view) {
        final boolean isEnabled = Settings.SHOW_VIDEO_TITLE_SECTION.get() || !Settings.DISABLE_ENGAGEMENT_PANEL.get();

        if (isEnabled) {
            frameLayout.addView(view);
        }
    }

    public static boolean hideAutoPlayPreview() {
        return Settings.HIDE_AUTOPLAY_PREVIEW.get();
    }

    public static boolean hideRelatedVideoOverlay() {
        return Settings.HIDE_RELATED_VIDEO_OVERLAY.get();
    }

    public static void hideQuickActions(View view) {
        final boolean isEnabled = Settings.DISABLE_ENGAGEMENT_PANEL.get() || Settings.HIDE_QUICK_ACTIONS.get();

        Utils.hideViewBy0dpUnderCondition(
                isEnabled,
                view
        );
    }

    public static void setQuickActionMargin(FrameLayout frameLayout) {
        int topMargin = quickActionsMarginTopSetting.get();

        if (topMargin < 0 || topMargin > 32) {
            Utils.showToastShort(str("revanced_quick_actions_top_margin_warning"));
            quickActionsMarginTopSetting.resetToDefault();
            topMargin = quickActionsMarginTopSetting.defaultValue;
        }
        if (topMargin == 0) {
            return;
        }

        final int topMarginPx =
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, (float) topMargin, Utils.getResources().getDisplayMetrics());

        if (!(frameLayout.getLayoutParams() instanceof FrameLayout.MarginLayoutParams marginLayoutParams))
            return;
        marginLayoutParams.setMargins(
                marginLayoutParams.leftMargin,
                topMarginPx,
                marginLayoutParams.rightMargin,
                marginLayoutParams.bottomMargin
        );
        frameLayout.requestLayout();
    }

    public static boolean enableCompactControlsOverlay(boolean original) {
        return Settings.ENABLE_COMPACT_CONTROLS_OVERLAY.get() || original;
    }

    public static boolean disableLandScapeMode(boolean original) {
        return Settings.DISABLE_LANDSCAPE_MODE.get() || original;
    }

    private static volatile boolean isScreenOn;

    public static boolean keepFullscreen(boolean original) {
        if (!Settings.KEEP_LANDSCAPE_MODE.get())
            return original;

        return isScreenOn;
    }

    public static void setScreenOn() {
        if (!Settings.KEEP_LANDSCAPE_MODE.get())
            return;

        isScreenOn = true;
        Utils.runOnMainThreadDelayed(() -> isScreenOn = false, Settings.KEEP_LANDSCAPE_MODE_TIMEOUT.get());
    }

    private static WeakReference<Activity> watchDescriptorActivityRef = new WeakReference<>(null);
    private static volatile boolean isLandScapeVideo = true;

    public static void setWatchDescriptorActivity(Activity activity) {
        watchDescriptorActivityRef = new WeakReference<>(activity);
    }

    public static boolean forceFullscreen(boolean original) {
        if (!Settings.FORCE_FULLSCREEN.get())
            return original;

        Utils.runOnMainThreadDelayed(PlayerPatch::setOrientation, 1000);
        return true;
    }

    private static void setOrientation() {
        final Activity watchDescriptorActivity = watchDescriptorActivityRef.get();
        final int requestedOrientation = isLandScapeVideo
                ? ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                : watchDescriptorActivity.getRequestedOrientation();

        watchDescriptorActivity.setRequestedOrientation(requestedOrientation);
    }

    public static void setVideoPortrait(int width, int height) {
        if (!Settings.FORCE_FULLSCREEN.get())
            return;

        isLandScapeVideo = width > height;
    }

    // endregion

    // region [Hide comments component] patch

    private static final int inlineExtraButtonId = getIdIdentifier("inline_extra_buttons");

    public static void changeEmojiPickerOpacity(ImageView imageView) {
        if (!Settings.HIDE_COMMENT_TIMESTAMP_AND_EMOJI_BUTTONS.get())
            return;

        imageView.setImageAlpha(0);
    }

    @Nullable
    public static Object disableEmojiPickerOnClickListener(@Nullable Object object) {
        return Settings.HIDE_COMMENT_TIMESTAMP_AND_EMOJI_BUTTONS.get() ? null : object;
    }

    public static int hideThanksButton(View view, int visibility) {
        if (!Settings.HIDE_COMMENT_THANKS_BUTTON.get())
            return visibility;

        if (view.getId() != inlineExtraButtonId)
            return visibility;

        return View.GONE;
    }

    // endregion

    // region [Hide player buttons] patch

    private static final int collapseButtonId = getIdIdentifier("player_collapse_button");

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

    public static void hideCollapseButton(@NonNull View view, int visibility) {
        if (Settings.HIDE_COLLAPSE_BUTTON.get() && view.getId() == collapseButtonId) {
            visibility = View.GONE;
        }
        view.setVisibility(visibility);
    }

    public static ImageView hideFullscreenButton(ImageView imageView) {
        final boolean hideView = Settings.HIDE_FULLSCREEN_BUTTON.get();

        Utils.hideViewUnderCondition(hideView, imageView);
        return hideView ? null : imageView;
    }

    public static boolean hidePreviousNextButton(boolean previousOrNextButtonVisible) {
        return !Settings.HIDE_PREVIOUS_NEXT_BUTTON.get() && previousOrNextButtonVisible;
    }

    public static boolean hideMusicButton() {
        return Settings.HIDE_YOUTUBE_MUSIC_BUTTON.get();
    }

    // endregion

    // region [Player components] patch

    public static void changePlayerOpacity(ImageView imageView) {
        final IntegerSetting customPlayerOverlayOpacity = Settings.CUSTOM_PLAYER_OVERLAY_OPACITY;
        int opacity = customPlayerOverlayOpacity.get();

        if (opacity < 0 || opacity > 100) {
            Utils.showToastShort(str("revanced_custom_player_overlay_opacity_warning"));
            customPlayerOverlayOpacity.resetToDefault();
            opacity = customPlayerOverlayOpacity.defaultValue;
        }

        imageView.setImageAlpha((opacity * 255) / 100);
    }

    public static boolean disableAutoPlayerPopupPanels() {
        return Settings.DISABLE_AUTO_PLAYER_POPUP_PANELS.get();
    }

    public static boolean disableSpeedOverlay(boolean original) {
        return !Settings.DISABLE_SPEED_OVERLAY.get() && original;
    }

    public static boolean hideChannelWatermark(boolean original) {
        return !Settings.HIDE_CHANNEL_WATERMARK.get() && original;
    }

    public static void hideCrowdfundingBox(View view) {
        hideViewBy0dpUnderCondition(Settings.HIDE_CROWDFUNDING_BOX.get(), view);
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

    public static boolean hideSeekMessage() {
        return Settings.HIDE_SEEK_MESSAGE.get();
    }

    public static boolean hideSeekUndoMessage() {
        return Settings.HIDE_SEEK_UNDO_MESSAGE.get();
    }

    public static void hideSuggestedActions(View view) {
        hideViewUnderCondition(Settings.HIDE_SUGGESTED_ACTION.get(), view);
    }

    // endregion

    // region [Hide player flyout menu] patch

    public static void hideFooterCaptions(View view) {
        Utils.hideViewUnderCondition(
                Settings.HIDE_PLAYER_FLYOUT_MENU_CAPTIONS_FOOTER.get(),
                view
        );
    }

    public static void hideFooterQuality(View view) {
        Utils.hideViewUnderCondition(
                Settings.HIDE_PLAYER_FLYOUT_MENU_QUALITY_FOOTER.get(),
                view
        );
    }

    // endregion

    // region [Seekbar components] patch

    public static final int ORIGINAL_SEEKBAR_COLOR = 0xFFFF0000;

    public static String appendTimeStampInformation(String original) {
        if (!Settings.APPEND_TIME_STAMP_INFORMATION.get())
            return original;

        final String regex = "\\((.*?)\\)";
        final Matcher matcher = Pattern.compile(regex).matcher(original);

        if (matcher.find()) {
            String matcherGroup = matcher.group(1);
            String appendString = String.format(
                    "\u2009(%s)",
                    Settings.APPEND_TIME_STAMP_INFORMATION_TYPE.get()
                            ? VideoUtils.getFormattedQualityString(matcherGroup)
                            : VideoUtils.getFormattedSpeedString(matcherGroup)
            );
            return original.replaceAll(regex, "") + appendString;
        } else {
            String appendString = String.format(
                    "\u2009(%s)",
                    Settings.APPEND_TIME_STAMP_INFORMATION_TYPE.get()
                            ? VideoUtils.getFormattedQualityString(null)
                            : VideoUtils.getFormattedSpeedString(null)
            );
            return original + appendString;
        }
    }

    public static void setContainerClickListener(View view) {
        if (!Settings.APPEND_TIME_STAMP_INFORMATION.get())
            return;

        if (!(view.getParent() instanceof View containerView))
            return;

        final BooleanSetting appendTypeSetting = Settings.APPEND_TIME_STAMP_INFORMATION_TYPE;
        final boolean previousBoolean = appendTypeSetting.get();

        containerView.setOnLongClickListener(timeStampContainerView -> {
                    appendTypeSetting.save(!previousBoolean);
                    return true;
                }
        );
    }

    public static int getSeekbarClickedColorValue(final int colorValue) {
        return colorValue == ORIGINAL_SEEKBAR_COLOR
                ? overrideSeekbarColor(colorValue)
                : colorValue;
    }

    public static int resumedProgressBarColor(final int colorValue) {
        return Settings.ENABLE_CUSTOM_SEEKBAR_COLOR.get()
                ? getSeekbarClickedColorValue(colorValue)
                : colorValue;
    }

    /**
     * Overrides all drawable color that use the YouTube seekbar color.
     * Used only for the video thumbnails seekbar.
     * <p>
     * If {@link Settings#HIDE_SEEKBAR_THUMBNAIL} is enabled, this returns a fully transparent color.
     */
    public static int getColor(int colorValue) {
        if (colorValue == ORIGINAL_SEEKBAR_COLOR) {
            if (Settings.HIDE_SEEKBAR_THUMBNAIL.get()) {
                return 0x00000000;
            }
            return overrideSeekbarColor(ORIGINAL_SEEKBAR_COLOR);
        }
        return colorValue;
    }

    /**
     * Points where errors occur when playing videos on the PlayStore (ROOT Build)
     */
    public static int overrideSeekbarColor(final int colorValue) {
        try {
            return Settings.ENABLE_CUSTOM_SEEKBAR_COLOR.get()
                    ? Color.parseColor(Settings.ENABLE_CUSTOM_SEEKBAR_COLOR_VALUE.get())
                    : colorValue;
        } catch (Exception ignored) {
            Settings.ENABLE_CUSTOM_SEEKBAR_COLOR_VALUE.resetToDefault();
        }
        return colorValue;
    }

    public static boolean enableSeekbarTapping() {
        return Settings.ENABLE_SEEKBAR_TAPPING.get();
    }

    public static boolean hideSeekbar() {
        return Settings.HIDE_SEEKBAR.get();
    }

    public static boolean hideTimeStamp() {
        return Settings.HIDE_TIME_STAMP.get();
    }

    public static boolean restoreOldSeekbarThumbnails() {
        return !Settings.RESTORE_OLD_SEEKBAR_THUMBNAILS.get();
    }

    // endregion

    public static int getQuickActionsTopMargin() {
        if (!PatchStatus.QuickActions()) {
            return 0;
        }
        int topMargin = quickActionsMarginTopSetting.get();
        if (topMargin < 0 || topMargin > 32) {
            return 0;
        }
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, (float) topMargin, Utils.getResources().getDisplayMetrics());
    }

}
