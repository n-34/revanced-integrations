package app.revanced.integrations.youtube.patches.bottomplayer;

import static app.revanced.integrations.shared.utils.ResourceUtils.getIdIdentifier;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import app.revanced.integrations.youtube.settings.Settings;

@SuppressWarnings("unused")
public class BottomPlayerPatch {
    private static final int inlineExtraButtonId = getIdIdentifier("inline_extra_buttons");

    public static void changeEmojiPickerOpacity(ImageView imageView) {
        if (!Settings.HIDE_EMOJI_PICKER.get())
            return;

        imageView.setImageAlpha(0);
    }

    @Nullable
    public static Object disableEmojiPickerOnClickListener(@Nullable Object object) {
        return Settings.HIDE_EMOJI_PICKER.get() ? null : object;
    }

    public static boolean enableBottomPlayerGestures() {
        return Settings.ENABLE_BOTTOM_PLAYER_GESTURES.get();
    }

    public static int hideThanksButton(View view, int visibility) {
        if (!Settings.HIDE_COMMENTS_THANKS_BUTTON.get())
            return visibility;

        if (view.getId() != inlineExtraButtonId)
            return visibility;

        return View.GONE;
    }

}