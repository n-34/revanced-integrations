package app.revanced.integrations.youtube.patches.components;

import static app.revanced.integrations.shared.utils.Utils.hideViewBy0dpUnderCondition;

import android.view.View;

import androidx.annotation.Nullable;

import app.revanced.integrations.shared.patches.components.Filter;
import app.revanced.integrations.shared.patches.components.StringFilterGroup;
import app.revanced.integrations.youtube.patches.utils.NavBarIndexPatch;
import app.revanced.integrations.youtube.settings.Settings;

@SuppressWarnings("unused")
public final class SuggestionsShelfFilter extends Filter {
    private final StringFilterGroup horizontalShelf;
    private final StringFilterGroup libraryShelf;
    private final StringFilterGroup searchResult;

    public SuggestionsShelfFilter() {
        horizontalShelf = new StringFilterGroup(
                Settings.HIDE_SUGGESTIONS_SHELF,
                "horizontal_shelf.eml",
                "horizontal_tile_shelf.eml",
                "horizontal_video_shelf.eml"
        );

        libraryShelf = new StringFilterGroup(
                Settings.HIDE_SUGGESTIONS_SHELF,
                "library_recent_shelf.eml"
        );

        searchResult = new StringFilterGroup(
                Settings.HIDE_SUGGESTIONS_SHELF,
                "compact_channel.eml",
                "search_video_with_context.eml"
        );

        addIdentifierCallbacks(libraryShelf, searchResult);
        addPathCallbacks(horizontalShelf);
    }

    /**
     * Injection point.
     * <p>
     * Only used to tablet layout.
     */
    public static void hideBreakingNewsShelf(View view) {
        hideViewBy0dpUnderCondition(
                Settings.HIDE_SUGGESTIONS_SHELF.get(),
                view
        );
    }

    @Override
    public boolean isFiltered(String path, @Nullable String identifier, String allValue, byte[] protobufBufferArray,
                       StringFilterGroup matchedGroup, FilterContentType contentType, int contentIndex) {
        // Even though [NavBarIndex] has not been set yet, but [LithoFilterPatch] can be called.
        // In this case, the patch may not work normally.
        // To prevent this, you need to detect a specific component that exists only in some [NavBarIndex],
        // And manually update the [NavBarIndex].
        if (matchedGroup == searchResult) {
            NavBarIndexPatch.setNavBarIndex(0);
        } else if (matchedGroup == libraryShelf) {
            NavBarIndexPatch.setNavBarIndex(4);
        } else if (matchedGroup == horizontalShelf) {
            return NavBarIndexPatch.isNotLibraryTab();
        }
        return false;
    }
}
