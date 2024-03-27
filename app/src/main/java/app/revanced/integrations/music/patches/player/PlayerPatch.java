package app.revanced.integrations.music.patches.player;

import static app.revanced.integrations.music.utils.ReVancedUtils.hideViewUnderCondition;
import static app.revanced.integrations.music.utils.ReVancedUtils.runOnBackgroundThread;
import static app.revanced.integrations.music.utils.ReVancedUtils.runOnMainThreadDelayed;
import static app.revanced.integrations.music.utils.ReVancedUtils.showToastShort;
import static app.revanced.integrations.music.utils.ResourceUtils.identifier;
import static app.revanced.integrations.music.utils.StringRef.str;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;

import java.util.Arrays;

import app.revanced.integrations.music.patches.utils.CheckMusicVideoPatch;
import app.revanced.integrations.music.settings.SettingsEnum;
import app.revanced.integrations.music.shared.PlayerType;
import app.revanced.integrations.music.shared.VideoType;
import app.revanced.integrations.music.utils.ResourceType;
import app.revanced.integrations.music.utils.VideoHelpers;

@SuppressWarnings("unused")
public class PlayerPatch {
    private static final int MUSIC_VIDEO_GREY_BACKGROUND_COLOR = -12566464;
    private static final int MUSIC_VIDEO_ORIGINAL_BACKGROUND_COLOR = -16579837;
    private static final Instrumentation instrumentation = new Instrumentation();
    @SuppressLint("StaticFieldLeak")
    public static View previousButton;
    @SuppressLint("StaticFieldLeak")
    public static View nextButton;

    public static boolean enableColorMatchPlayer() {
        return SettingsEnum.ENABLE_COLOR_MATCH_PLAYER.getBoolean();
    }

    public static boolean enableForceMinimizedPlayer(boolean original) {
        return SettingsEnum.ENABLE_FORCE_MINIMIZED_PLAYER.getBoolean() || original;
    }

    public static boolean enableMiniPlayerNextButton(boolean original) {
        return !SettingsEnum.ENABLE_MINI_PLAYER_NEXT_BUTTON.getBoolean() && original;
    }

    public static boolean enableOldPlayerBackground(boolean original) {
        if (!SettingsEnum.SETTINGS_INITIALIZED.getBoolean()) {
            return original;
        }
        return !SettingsEnum.ENABLE_OLD_PLAYER_BACKGROUND.getBoolean();
    }

    public static boolean enableOldPlayerLayout(boolean original) {
        if (!SettingsEnum.SETTINGS_INITIALIZED.getBoolean()) {
            return original;
        }
        return !SettingsEnum.ENABLE_OLD_PLAYER_LAYOUT.getBoolean();
    }

    public static boolean enableSwipeToDismissMiniPlayer() {
        return SettingsEnum.ENABLE_SWIPE_TO_DISMISS_MINI_PLAYER.getBoolean();
    }

    public static boolean enableSwipeToDismissMiniPlayer(boolean original) {
        return !SettingsEnum.ENABLE_SWIPE_TO_DISMISS_MINI_PLAYER.getBoolean() && original;
    }

    public static Object enableSwipeToDismissMiniPlayer(Object object) {
        return SettingsEnum.ENABLE_SWIPE_TO_DISMISS_MINI_PLAYER.getBoolean() ? null : object;
    }

    public static int enableZenMode(int originalColor) {
        if (SettingsEnum.ENABLE_ZEN_MODE.getBoolean() && originalColor == MUSIC_VIDEO_ORIGINAL_BACKGROUND_COLOR) {
            if (SettingsEnum.ENABLE_ZEN_MODE_PODCAST.getBoolean() || !VideoType.getCurrent().isPodCast()) {
                return MUSIC_VIDEO_GREY_BACKGROUND_COLOR;
            }
        }
        return originalColor;
    }

    public static int getShuffleState() {
        return SettingsEnum.SHUFFLE_SATE.getInt();
    }

    public static View[] getViewArray(View[] oldViewArray) {
        if (previousButton != null) {
            if (nextButton != null) {
                return getViewArray(getViewArray(oldViewArray, previousButton), nextButton);
            } else {
                return getViewArray(oldViewArray, previousButton);
            }
        } else {
            return oldViewArray;
        }
    }

    private static View[] getViewArray(View[] oldViewArray, View newView) {
        final int oldViewArrayLength = oldViewArray.length;

        View[] newViewArray = Arrays.copyOf(oldViewArray, oldViewArrayLength + 1);
        newViewArray[oldViewArrayLength] = newView;
        return newViewArray;
    }

    public static int hideFullscreenShareButton(int original) {
        return SettingsEnum.HIDE_FULLSCREEN_SHARE_BUTTON.getBoolean() ? 0 : original;
    }

    private static void prepareOpenMusic(@NonNull Context context) {
        if (!VideoType.getCurrent().isMusicVideo()) {
            showToastShort(str("revanced_playlist_dismiss"));
            return;
        }
        final String songId = CheckMusicVideoPatch.getSongId();
        if (songId.isEmpty()) {
            showToastShort(str("revanced_playlist_error"));
            return;
        }
        VideoHelpers.openInMusic(context, songId);
    }

    public static boolean rememberRepeatState(boolean original) {
        return SettingsEnum.REMEMBER_REPEAT_SATE.getBoolean() || original;
    }

    public static boolean rememberShuffleState() {
        return SettingsEnum.REMEMBER_SHUFFLE_SATE.getBoolean();
    }

    public static void replaceCastButton(Activity activity, ViewGroup viewGroup, View originalView) {
        if (!SettingsEnum.REPLACE_PLAYER_CAST_BUTTON.getBoolean()) {
            viewGroup.addView(originalView);
            return;
        }

        LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(activity).inflate(identifier("open_music_button", ResourceType.LAYOUT), null);
        ImageView musicButtonView = (ImageView) linearLayout.getChildAt(0);

        musicButtonView.setOnClickListener(imageView -> prepareOpenMusic(imageView.getContext()));

        viewGroup.addView(linearLayout);
    }

    public static void setNextButtonOnClickListener(View nextButtonView) {
        if (nextButtonView == null)
            return;

        hideViewUnderCondition(
                !SettingsEnum.ENABLE_MINI_PLAYER_NEXT_BUTTON.getBoolean(),
                nextButtonView
        );

        nextButtonView.setOnClickListener(view -> runOnMainThreadDelayed(() -> runOnBackgroundThread(() -> instrumentation.sendKeyDownUpSync(KeyEvent.KEYCODE_MEDIA_NEXT)), 0));
    }

    public static void setPreviousButtonOnClickListener(View previousButtonView) {
        if (previousButtonView == null)
            return;

        hideViewUnderCondition(
                !SettingsEnum.ENABLE_MINI_PLAYER_PREVIOUS_BUTTON.getBoolean(),
                previousButtonView
        );

        previousButtonView.setOnClickListener(view -> runOnMainThreadDelayed(() -> runOnBackgroundThread(() -> instrumentation.sendKeyDownUpSync(KeyEvent.KEYCODE_MEDIA_PREVIOUS)), 0));
    }

    public static void setShuffleState(int buttonState) {
        if (!SettingsEnum.REMEMBER_SHUFFLE_SATE.getBoolean())
            return;
        SettingsEnum.SHUFFLE_SATE.saveValue(buttonState);
    }
}
