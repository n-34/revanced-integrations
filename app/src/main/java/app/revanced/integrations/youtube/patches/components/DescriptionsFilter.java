package app.revanced.integrations.youtube.patches.components;

import androidx.annotation.Nullable;

import app.revanced.integrations.shared.patches.components.Filter;
import app.revanced.integrations.shared.patches.components.StringFilterGroup;
import app.revanced.integrations.shared.utils.StringTrieSearch;
import app.revanced.integrations.youtube.settings.Settings;

@SuppressWarnings("unused")
public final class DescriptionsFilter extends Filter {
    private final StringTrieSearch exceptions = new StringTrieSearch();
    private final StringFilterGroup shoppingLinks;

    public DescriptionsFilter() {
        exceptions.addPatterns(
                "compact_channel",
                "description",
                "grid_video",
                "inline_expander",
                "metadata"
        );

        final StringFilterGroup chapterSection = new StringFilterGroup(
                Settings.HIDE_CHAPTERS,
                "macro_markers_carousel."
        );

        final StringFilterGroup infoCardsSection = new StringFilterGroup(
                Settings.HIDE_INFO_CARDS_SECTION,
                "infocards_section"
        );

        final StringFilterGroup gameSection = new StringFilterGroup(
                Settings.HIDE_GAME_SECTION,
                "gaming_section"
        );

        final StringFilterGroup musicSection = new StringFilterGroup(
                Settings.HIDE_MUSIC_SECTION,
                "music_section",
                "video_attributes_section"
        );

        final StringFilterGroup placeSection = new StringFilterGroup(
                Settings.HIDE_PLACE_SECTION,
                "place_section"
        );

        final StringFilterGroup podcastSection = new StringFilterGroup(
                Settings.HIDE_PODCAST_SECTION,
                "playlist_section"
        );

        shoppingLinks = new StringFilterGroup(
                Settings.HIDE_SHOPPING_LINKS,
                "expandable_list."
        );

        final StringFilterGroup transcriptSection = new StringFilterGroup(
                Settings.HIDE_TRANSCRIPT_SECTION,
                "transcript_section"
        );


        addPathCallbacks(
                chapterSection,
                infoCardsSection,
                gameSection,
                musicSection,
                placeSection,
                podcastSection,
                shoppingLinks,
                transcriptSection
        );
    }

    @Override
    public boolean isFiltered(String path, @Nullable String identifier, String allValue, byte[] protobufBufferArray,
                       StringFilterGroup matchedGroup, FilterContentType contentType, int contentIndex) {
        if (exceptions.matches(path))
            return false;

        // Check for the index because of likelihood of false positives.
        if (matchedGroup == shoppingLinks && contentIndex != 0)
            return false;

        return super.isFiltered(path, identifier, allValue, protobufBufferArray, matchedGroup, contentType, contentIndex);
    }
}
