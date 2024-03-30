package app.revanced.integrations.youtube.patches.components;

import androidx.annotation.Nullable;

import app.revanced.integrations.shared.patches.components.Filter;
import app.revanced.integrations.shared.patches.components.StringFilterGroup;
import app.revanced.integrations.shared.utils.StringTrieSearch;
import app.revanced.integrations.youtube.settings.Settings;

@SuppressWarnings("unused")
public final class PlayerFlyoutPanelsFooterFilter extends Filter {

    private final StringTrieSearch exceptions = new StringTrieSearch();
    private final StringTrieSearch targetPath = new StringTrieSearch();

    public PlayerFlyoutPanelsFooterFilter() {
        exceptions.addPattern(
                "bottom_sheet_list_option"
        );

        targetPath.addPatterns(
                "captions_sheet_content.eml",
                "quality_sheet_content.eml"
        );

        addPathCallbacks(
                new StringFilterGroup(
                        Settings.HIDE_PLAYER_FLYOUT_PANEL_QUALITY_FOOTER,
                        "quality_sheet_footer.eml",
                        "|divider.eml|"
                ),
                new StringFilterGroup(
                        Settings.HIDE_PLAYER_FLYOUT_PANEL_CAPTIONS_FOOTER,
                        "|ContainerType|ContainerType|ContainerType|TextType|",
                        "|divider.eml|"
                )
        );
    }

    @Override
    public boolean isFiltered(String path, @Nullable String identifier, String allValue, byte[] protobufBufferArray,
                       StringFilterGroup matchedGroup, FilterContentType contentType, int contentIndex) {
        if (exceptions.matches(path) || !targetPath.matches(path))
            return false;

        return super.isFiltered(path, identifier, allValue, protobufBufferArray, matchedGroup, contentType, contentIndex);
    }
}
