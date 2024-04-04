package app.revanced.integrations.youtube.patches.overlaybutton;

import static app.revanced.integrations.shared.utils.ResourceUtils.getAnimation;
import static app.revanced.integrations.shared.utils.ResourceUtils.getInteger;
import static app.revanced.integrations.shared.utils.Utils.getChildView;

import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.lang.ref.WeakReference;
import java.util.Objects;

import app.revanced.integrations.shared.settings.BooleanSetting;
import app.revanced.integrations.shared.utils.Logger;

public abstract class BottomControlButton {
    private static final Animation fadeIn;
    private static final Animation fadeOut;

    private final WeakReference<ImageView> buttonRef;
    private final BooleanSetting setting;
    private final BooleanSetting interactionSetting;
    protected boolean isVisible;

    static {
        // TODO: check if these durations are correct.
        fadeIn = getAnimation("fade_in");
        fadeIn.setDuration(getInteger("fade_duration_fast"));

        fadeOut = getAnimation("fade_out");
        fadeOut.setDuration(getInteger("fade_overlay_fade_duration"));
    }

    @NonNull
    public static Animation getButtonFadeIn() {
        return fadeIn;
    }

    @NonNull
    public static Animation getButtonFadeOut() {
        return fadeOut;
    }

    public BottomControlButton(@NonNull ViewGroup bottomControlsViewGroup, @NonNull String imageViewButtonId,
                               @NonNull BooleanSetting booleanSetting, @NonNull View.OnClickListener onClickListener,
                               @Nullable View.OnLongClickListener longClickListener) {
        this(bottomControlsViewGroup, imageViewButtonId, booleanSetting, null, onClickListener, longClickListener);
    }

    public BottomControlButton(@NonNull ViewGroup bottomControlsViewGroup, @NonNull String imageViewButtonId,
                               @NonNull BooleanSetting booleanSetting, @Nullable BooleanSetting interactionSetting,
                               @NonNull View.OnClickListener onClickListener,
                               @Nullable View.OnLongClickListener longClickListener) {
        Logger.printDebug(() -> "Initializing button: " + imageViewButtonId);

        setting = booleanSetting;

        // Create the button.
        ImageView imageView = Objects.requireNonNull(getChildView(bottomControlsViewGroup, imageViewButtonId));
        if (interactionSetting != null) {
            this.interactionSetting = interactionSetting;
            imageView.setSelected(interactionSetting.get());
        } else {
            this.interactionSetting = null;
        }
        imageView.setOnClickListener(onClickListener);
        if (longClickListener != null) {
            imageView.setOnLongClickListener(longClickListener);
        }
        imageView.setVisibility(View.GONE);
        buttonRef = new WeakReference<>(imageView);
    }

    public void changeSelected(boolean selected, boolean onlyView) {
        ImageView imageView = buttonRef.get();
        if (imageView == null || interactionSetting == null)
            return;

        imageView.setSelected(selected);
        if (!onlyView) interactionSetting.save(selected);
    }

    public void setVisibility(boolean visible, boolean animation) {
        ImageView imageView = buttonRef.get();
        if (imageView == null || isVisible == visible) return;
        isVisible = visible;

        imageView.clearAnimation();
        if (visible && setting.get()) {
            imageView.setVisibility(View.VISIBLE);
            if (animation) imageView.startAnimation(fadeIn);
            return;
        }
        if (imageView.getVisibility() == View.VISIBLE) {
            if (animation) imageView.startAnimation(fadeOut);
            imageView.setVisibility(View.GONE);
        }
    }
}
