package app.revanced.integrations.music.patches.player;

import static app.revanced.integrations.shared.utils.ResourceUtils.identifier;
import static app.revanced.integrations.shared.utils.StringRef.str;
import static app.revanced.integrations.shared.utils.Utils.hideViewUnderCondition;
import static app.revanced.integrations.shared.utils.Utils.showToastShort;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;

import java.util.Arrays;

import app.revanced.integrations.music.patches.utils.CheckMusicVideoPatch;
import app.revanced.integrations.music.settings.Settings;
import app.revanced.integrations.music.shared.VideoType;
import app.revanced.integrations.music.utils.VideoUtils;
import app.revanced.integrations.shared.utils.ResourceType;

@SuppressWarnings("unused")
public class PlayerPatch {
    private static final int MUSIC_VIDEO_GREY_BACKGROUND_COLOR = -12566464;
    private static final int MUSIC_VIDEO_ORIGINAL_BACKGROUND_COLOR = -16579837;
    @SuppressLint("StaticFieldLeak")
    public static View previousButton;
    @SuppressLint("StaticFieldLeak")
    public static View nextButton;

    public static boolean enableColorMatchPlayer() {
        return Settings.ENABLE_COLOR_MATCH_PLAYER.get();
    }

    public static boolean enableForceMinimizedPlayer(boolean original) {
        return Settings.ENABLE_FORCE_MINIMIZED_PLAYER.get() || original;
    }

    public static boolean enableMiniPlayerNextButton(boolean original) {
        return !Settings.ENABLE_MINI_PLAYER_NEXT_BUTTON.get() && original;
    }

    public static boolean enableOldPlayerBackground(boolean original) {
        if (!Settings.SETTINGS_INITIALIZED.get()) {
            return original;
        }
        return !Settings.ENABLE_OLD_PLAYER_BACKGROUND.get();
    }

    public static boolean enableOldPlayerLayout(boolean original) {
        if (!Settings.SETTINGS_INITIALIZED.get()) {
            return original;
        }
        return !Settings.ENABLE_OLD_PLAYER_LAYOUT.get();
    }

    public static boolean enableSwipeToDismissMiniPlayer() {
        return Settings.ENABLE_SWIPE_TO_DISMISS_MINI_PLAYER.get();
    }

    public static boolean enableSwipeToDismissMiniPlayer(boolean original) {
        return !Settings.ENABLE_SWIPE_TO_DISMISS_MINI_PLAYER.get() && original;
    }

    public static Object enableSwipeToDismissMiniPlayer(Object object) {
        return Settings.ENABLE_SWIPE_TO_DISMISS_MINI_PLAYER.get() ? null : object;
    }

    public static int enableZenMode(int originalColor) {
        if (Settings.ENABLE_ZEN_MODE.get() && originalColor == MUSIC_VIDEO_ORIGINAL_BACKGROUND_COLOR) {
            if (Settings.ENABLE_ZEN_MODE_PODCAST.get() || !VideoType.getCurrent().isPodCast()) {
                return MUSIC_VIDEO_GREY_BACKGROUND_COLOR;
            }
        }
        return originalColor;
    }

    public static int getShuffleState() {
        return Settings.SHUFFLE_SATE.get();
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
        return Settings.HIDE_FULLSCREEN_SHARE_BUTTON.get() ? 0 : original;
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
        VideoUtils.openInMusic(context, songId);
    }

    public static boolean rememberRepeatState(boolean original) {
        return Settings.REMEMBER_REPEAT_SATE.get() || original;
    }

    public static boolean rememberShuffleState() {
        return Settings.REMEMBER_SHUFFLE_SATE.get();
    }

    public static void replaceCastButton(Activity activity, ViewGroup viewGroup, View originalView) {
        if (!Settings.REPLACE_PLAYER_CAST_BUTTON.get()) {
            viewGroup.addView(originalView);
            return;
        }

        LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(activity).inflate(identifier("open_music_button", ResourceType.LAYOUT), null);
        ImageView musicButtonView = (ImageView) linearLayout.getChildAt(0);

        musicButtonView.setOnClickListener(imageView -> prepareOpenMusic(imageView.getContext()));

        viewGroup.addView(linearLayout);
    }

    public static void setNextButton(View nextButtonView) {
        if (nextButtonView == null)
            return;

        hideViewUnderCondition(
                !Settings.ENABLE_MINI_PLAYER_NEXT_BUTTON.get(),
                nextButtonView
        );

        nextButtonView.setOnClickListener(PlayerPatch::setNextButtonOnClickListener);
    }

    // rest of the implementation added by patch.
    private static void setNextButtonOnClickListener(View view) {
        if (Settings.ENABLE_MINI_PLAYER_NEXT_BUTTON.get())
            view.getClass();
    }

    public static void setPreviousButton(View previousButtonView) {
        if (previousButtonView == null)
            return;

        hideViewUnderCondition(
                !Settings.ENABLE_MINI_PLAYER_PREVIOUS_BUTTON.get(),
                previousButtonView
        );

        previousButtonView.setOnClickListener(PlayerPatch::setPreviousButtonOnClickListener);
    }

    // rest of the implementation added by patch.
    private static void setPreviousButtonOnClickListener(View view) {
        if (Settings.ENABLE_MINI_PLAYER_PREVIOUS_BUTTON.get())
            view.getClass();
    }

    public static void setShuffleState(int buttonState) {
        if (!Settings.REMEMBER_SHUFFLE_SATE.get())
            return;
        Settings.SHUFFLE_SATE.save(buttonState);
    }
}
