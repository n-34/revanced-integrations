package app.revanced.integrations.youtube.patches.components;

import androidx.annotation.Nullable;

import app.revanced.integrations.shared.patches.components.Filter;
import app.revanced.integrations.shared.patches.components.StringFilterGroup;
import app.revanced.integrations.youtube.shared.PlayerType;
import app.revanced.integrations.youtube.shared.RootView;

import android.view.View;

/**
 * When the player is minimized, the layout it contains is never called.
 * If the layout included in the player is called even though the player is minimized,
 * it means that {@link View#invalidate} was detected.
 */
@SuppressWarnings("unused")
public final class InvalidateStateFilter extends Filter {

    public InvalidateStateFilter() {
        // As an example of the layout included in the player, a video action bar and relative video context was used.
        // Even if the components are hidden by another litho filter patch, this filter can still detect it.
        addIdentifierCallbacks(new StringFilterGroup(null, "video_action_bar.eml"));
    }

    @Override
    public boolean isFiltered(String path, @Nullable String identifier, String allValue, byte[] protobufBufferArray,
                       StringFilterGroup matchedGroup, FilterContentType contentType, int contentIndex) {
        // Checks whether the player is minimized.
        // If this filter is called when the player is minimized, it means {@link View#invalidate} was detected.
        final boolean playerIsMinimized =
                PlayerType.getCurrent() == PlayerType.WATCH_WHILE_MINIMIZED;
        RootView.onInvalidateStateChanged(playerIsMinimized);

        return false;
    }
}
