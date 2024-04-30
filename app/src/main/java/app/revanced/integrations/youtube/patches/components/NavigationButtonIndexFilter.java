package app.revanced.integrations.youtube.patches.components;

import static app.revanced.integrations.youtube.shared.NavigationBar.NavigationButton;

import androidx.annotation.Nullable;

import app.revanced.integrations.shared.patches.components.Filter;
import app.revanced.integrations.shared.patches.components.StringFilterGroup;
import app.revanced.integrations.youtube.shared.NavigationBar;

/**
 * Sometimes litho components are invoked before {@link NavigationButton} is updated.
 * In this case, some filters that check {@link NavigationButton} may not work correctly.
 * In particular, in the case of {@link FeedComponentsFilter#carouselShelf}, it is sometimes hidden in situations where it should not be hidden.
 * To prevent these issues, use Navigation Index.
 * If a component that exists only in a specific NavigationIndex is detected, manually update the Navigation Index.
 */
@SuppressWarnings("unused")
public final class NavigationButtonIndexFilter extends Filter {

    public NavigationButtonIndexFilter() {
        addIdentifierCallbacks(
                new StringFilterGroup(
                        null,
                        "library_recent_shelf.eml"
                )
        );
    }

    @Override
    public boolean isFiltered(String path, @Nullable String identifier, String allValue, byte[] protobufBufferArray,
                       StringFilterGroup matchedGroup, FilterContentType contentType, int contentIndex) {
        NavigationBar.setNavButtonIndex(4);

        return false;
    }
}
