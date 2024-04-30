package app.revanced.integrations.youtube.patches.shorts;

import static app.revanced.integrations.shared.utils.Utils.hideViewUnderCondition;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.lang.ref.WeakReference;

import app.revanced.integrations.shared.utils.Logger;
import app.revanced.integrations.youtube.settings.Settings;

@SuppressWarnings("unused")
public class ShortsPatch {
    public static Enum<?> repeat;
    public static Enum<?> singlePlay;
    public static Enum<?> endScreen;

    public static Enum<?> changeShortsRepeatState(Enum<?> currentState) {
        switch (Settings.CHANGE_SHORTS_REPEAT_STATE.get()) {
            case 1 -> currentState = repeat;
            case 2 -> currentState = singlePlay;
            case 3 -> currentState = endScreen;
        }

        return currentState;
    }

    public static boolean disableResumingStartupShortsPlayer() {
        return Settings.DISABLE_RESUMING_SHORTS_PLAYER.get();
    }

    public static void hideShortsCommentsButton(View view) {
        hideViewUnderCondition(Settings.HIDE_SHORTS_COMMENTS_BUTTON.get(), view);
    }

    public static boolean hideShortsDislikeButton() {
        return Settings.HIDE_SHORTS_DISLIKE_BUTTON.get();
    }

    public static ViewGroup hideShortsInfoPanel(ViewGroup viewGroup) {
        return Settings.HIDE_SHORTS_INFO_PANEL.get() ? null : viewGroup;
    }

    public static boolean hideShortsLikeButton() {
        return Settings.HIDE_SHORTS_LIKE_BUTTON.get();
    }

    public static boolean hideShortsPaidPromotionLabel() {
        return Settings.HIDE_SHORTS_PAID_PROMOTION_LABEL.get();
    }
    public static void hideShortsPaidPromotionLabel(TextView textView) {
        hideViewUnderCondition(Settings.HIDE_SHORTS_PAID_PROMOTION_LABEL.get(), textView);
    }

    public static void hideShortsRemixButton(View view) {
        hideViewUnderCondition(Settings.HIDE_SHORTS_REMIX_BUTTON.get(), view);
    }

    public static void hideShortsShareButton(View view) {
        hideViewUnderCondition(Settings.HIDE_SHORTS_SHARE_BUTTON.get(), view);
    }

    public static boolean hideShortsSoundButton() {
        return Settings.HIDE_SHORTS_SOUND_BUTTON.get();
    }

    public static Object hideShortsSoundButton(Object object) {
        return Settings.HIDE_SHORTS_SOUND_BUTTON.get() ? null : object;
    }

    public static int hideShortsSubscribeButton(int original) {
        return Settings.HIDE_SHORTS_SUBSCRIBE_BUTTON.get() ? 0 : original;
    }

    public static boolean hideShortsToolBar(boolean original) {
        return !Settings.HIDE_SHORTS_TOOLBAR.get() && original;
    }

    private static WeakReference<View> pivotBarViewRef = new WeakReference<>(null);

    public static void setNavigationBar(Object pivotBar) {
        if (pivotBar instanceof View pivotBarView) {
            pivotBarViewRef = new WeakReference<>(pivotBarView);
        }
    }

    public static View hideShortsNavigationBar(View view) {
        return Settings.HIDE_SHORTS_NAVIGATION_BAR.get() ? null : view;
    }

    public static void hideShortsNavigationBar() {
        final View pivotBarView = pivotBarViewRef.get();
        if (pivotBarView != null) {
            hideViewUnderCondition(Settings.HIDE_SHORTS_NAVIGATION_BAR.get(), pivotBarView);
        }
    }

    /**
     * The last character of some handles is an official channel certification mark.
     * This was in the form of nonBreakSpaceCharacter before SpannableString was made.
     */
    private static final String NON_BREAK_SPACE_CHARACTER = "\u00A0";
    private static String channelName = "";

    /**
     * This method is only invoked on Shorts and is updated whenever the user swipes up or down on the Shorts.
     */
    public static void newShortsVideoStarted(@NonNull String newlyLoadedChannelId, @NonNull String newlyLoadedChannelName,
                                             @NonNull String newlyLoadedVideoId, @NonNull String newlyLoadedVideoTitle,
                                             final long newlyLoadedVideoLength, boolean newlyLoadedLiveStreamValue) {
        if (newlyLoadedChannelName.equals(channelName))
            return;

        Logger.printDebug(() -> "New channel name loaded: " + newlyLoadedChannelName);

        channelName = newlyLoadedChannelName;
    }

    public static CharSequence onCharSequenceLoaded(@NonNull Object conversionContext,
                                                    @NonNull CharSequence charSequence) {
        try {
            if (!Settings.RETURN_SHORTS_CHANNEL_NAME.get())
                return charSequence;

            final String conversionContextString = conversionContext.toString();
            final String originalString = charSequence.toString();

            if (!conversionContextString.contains("|reel_channel_bar_inner.eml|"))
                return charSequence;
            if (!originalString.startsWith("@"))
                return charSequence;
            if (channelName.isEmpty())
                return charSequence;

            String replacedChannelName = channelName;
            if (originalString.contains(NON_BREAK_SPACE_CHARACTER)) {
                replacedChannelName += NON_BREAK_SPACE_CHARACTER;
            }
            final String finalChannelName = replacedChannelName;
            Logger.printDebug(() -> "Replace Handle " + originalString + " to " + finalChannelName);
            return finalChannelName;
        } catch (Exception ex) {
            Logger.printException(() -> "onCharSequenceLoaded failed", ex);
        }
        return charSequence;
    }

}
