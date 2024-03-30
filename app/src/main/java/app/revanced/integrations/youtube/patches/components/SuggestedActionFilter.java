package app.revanced.integrations.youtube.patches.components;

import static app.revanced.integrations.shared.utils.Utils.hideViewUnderCondition;

import android.view.View;

import androidx.annotation.Nullable;

import app.revanced.integrations.shared.patches.components.Filter;
import app.revanced.integrations.shared.patches.components.StringFilterGroup;
import app.revanced.integrations.shared.utils.StringTrieSearch;
import app.revanced.integrations.youtube.settings.Settings;
import app.revanced.integrations.youtube.shared.PlayerType;

@SuppressWarnings("unused")
public final class SuggestedActionFilter extends Filter {
    private final StringTrieSearch exceptions = new StringTrieSearch();

    public SuggestedActionFilter() {
        exceptions.addPatterns(
                "channel_bar",
                "lock_mode_suggested_action",
                "shorts"
        );

        addAllValueCallbacks(
                new StringFilterGroup(
                        Settings.HIDE_SUGGESTED_ACTION,
                        "suggested_action"
                )
        );
    }

    public static void hideSuggestedActions(View view) {
        hideViewUnderCondition(Settings.HIDE_SUGGESTED_ACTION.get(), view);
    }

    @Override
    public boolean isFiltered(String path, @Nullable String identifier, String allValue, byte[] protobufBufferArray,
                       StringFilterGroup matchedGroup, FilterContentType contentType, int contentIndex) {
        if (exceptions.matches(path) || PlayerType.getCurrent().isNoneOrHidden())
            return false;

        return super.isFiltered(path, identifier, allValue, protobufBufferArray, matchedGroup, contentType, contentIndex);
    }
}
