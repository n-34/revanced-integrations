package app.revanced.integrations.youtube.patches.components;

import androidx.annotation.Nullable;

import java.util.regex.Pattern;

import app.revanced.integrations.shared.patches.components.Filter;
import app.revanced.integrations.shared.patches.components.StringFilterGroup;
import app.revanced.integrations.shared.utils.StringTrieSearch;
import app.revanced.integrations.youtube.settings.Settings;

@SuppressWarnings("unused")
public final class CommentsFilter extends Filter {
    private static final String COMMENT_COMPOSER_PATH = "comment_composer";
    private static final String COMMENT_ENTRY_POINT_TEASER_PATH = "comments_entry_point_teaser";
    private static final Pattern COMMENT_PREVIEW_TEXT_PATTERN = Pattern.compile("comments_entry_point_teaser.+ContainerType");
    private static final String VIDEO_METADATA_CAROUSEL_PATH = "video_metadata_carousel.eml";

    private final StringFilterGroup commentsPreviewDots;
    private final StringFilterGroup createShorts;
    private final StringFilterGroup emojiPicker;
    private final StringFilterGroup previewCommentText;
    private final StringFilterGroup thanks;
    private final StringTrieSearch exceptions = new StringTrieSearch();

    public CommentsFilter() {
        exceptions.addPatterns("macro_markers_list_item");

        final StringFilterGroup channelGuidelines = new StringFilterGroup(
                Settings.HIDE_CHANNEL_GUIDELINES,
                "channel_guidelines_entry_banner",
                "community_guidelines",
                "sponsorships_comments_upsell"
        );

        final StringFilterGroup comments = new StringFilterGroup(
                Settings.HIDE_COMMENTS_SECTION,
                VIDEO_METADATA_CAROUSEL_PATH,
                "comments_"
        );

        commentsPreviewDots = new StringFilterGroup(
                Settings.HIDE_PREVIEW_COMMENT_OLD_METHOD,
                "|ContainerType|ContainerType|ContainerType|"
        );

        createShorts = new StringFilterGroup(
                Settings.HIDE_CREATE_SHORTS_BUTTON,
                "composer_short_creation_button"
        );

        emojiPicker = new StringFilterGroup(
                Settings.HIDE_EMOJI_PICKER,
                "|CellType|ContainerType|ContainerType|ContainerType|ContainerType|ContainerType|"
        );

        final StringFilterGroup membersBanner = new StringFilterGroup(
                Settings.HIDE_COMMENTS_BY_MEMBERS,
                "sponsorships_comments"
        );

        final StringFilterGroup previewComment = new StringFilterGroup(
                Settings.HIDE_PREVIEW_COMMENT_OLD_METHOD,
                "|carousel_item",
                "|carousel_listener",
                COMMENT_ENTRY_POINT_TEASER_PATH,
                "comments_entry_point_simplebox"
        );

        previewCommentText = new StringFilterGroup(
                Settings.HIDE_PREVIEW_COMMENT_NEW_METHOD,
                COMMENT_ENTRY_POINT_TEASER_PATH
        );

        thanks = new StringFilterGroup(
                Settings.HIDE_COMMENTS_THANKS_BUTTON,
                "|super_thanks_button.eml"
        );

        addIdentifierCallbacks(channelGuidelines);

        addPathCallbacks(
                comments,
                commentsPreviewDots,
                createShorts,
                emojiPicker,
                membersBanner,
                previewComment,
                previewCommentText,
                thanks
        );
    }

    @Override
    public boolean isFiltered(String path, @Nullable String identifier, String allValue, byte[] protobufBufferArray,
                       StringFilterGroup matchedGroup, FilterContentType contentType, int contentIndex) {
        if (exceptions.matches(path))
            return false;

        if (matchedGroup == createShorts || matchedGroup == emojiPicker || matchedGroup == thanks) {
            return path.startsWith(COMMENT_COMPOSER_PATH);
        } else if (matchedGroup == commentsPreviewDots) {
            return path.startsWith(VIDEO_METADATA_CAROUSEL_PATH);
        } else if (matchedGroup == previewCommentText) {
            return COMMENT_PREVIEW_TEXT_PATTERN.matcher(path).find();
        }

        return super.isFiltered(path, identifier, allValue, protobufBufferArray, matchedGroup, contentType, contentIndex);
    }
}
