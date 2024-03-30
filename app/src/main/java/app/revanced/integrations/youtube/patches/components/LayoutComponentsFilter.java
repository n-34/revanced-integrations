package app.revanced.integrations.youtube.patches.components;

import androidx.annotation.Nullable;

import app.revanced.integrations.shared.patches.components.ByteArrayFilterGroup;
import app.revanced.integrations.shared.patches.components.Filter;
import app.revanced.integrations.shared.patches.components.StringFilterGroup;
import app.revanced.integrations.shared.patches.components.StringFilterGroupList;
import app.revanced.integrations.youtube.settings.Settings;

@SuppressWarnings("unused")
public final class LayoutComponentsFilter extends Filter {
    private static final String ENDORSEMENT_HEADER_FOOTER_PATH = "endorsement_header_footer";
    private static final ByteArrayFilterGroup grayDescriptionIdentifier =
            new ByteArrayFilterGroup(
                    Settings.HIDE_VIDEO_WITH_GRAY_DESCRIPTION,
                    ENDORSEMENT_HEADER_FOOTER_PATH
            );
    private static final ByteArrayFilterGroup lowViewsVideoIdentifier =
            new ByteArrayFilterGroup(
                    Settings.HIDE_VIDEO_WITH_LOW_VIEW,
                    "g-highZ"
            );
    private final StringFilterGroup communityPosts;
    private final StringFilterGroupList communityPostsGroupList = new StringFilterGroupList();
    private final StringFilterGroup homeVideoWithContext;
    private final StringFilterGroup searchVideoWithContext;

    public LayoutComponentsFilter() {
        // Identifiers.

        final StringFilterGroup graySeparator = new StringFilterGroup(
                Settings.HIDE_GRAY_SEPARATOR,
                "cell_divider"
        );

        final StringFilterGroup chipsShelf = new StringFilterGroup(
                Settings.HIDE_CHIPS_SHELF,
                "chips_shelf"
        );

        final StringFilterGroup searchBar = new StringFilterGroup(
                Settings.HIDE_SEARCH_BAR,
                "search_bar_entry_point"
        );

        addIdentifierCallbacks(
                chipsShelf,
                graySeparator,
                searchBar
        );

        // Paths.

        final StringFilterGroup albumCard = new StringFilterGroup(
                Settings.HIDE_ALBUM_CARDS,
                "browsy_bar",
                "official_card"
        );

        final StringFilterGroup audioTrackButton = new StringFilterGroup(
                Settings.HIDE_AUDIO_TRACK_BUTTON,
                "multi_feed_icon_button"
        );

        communityPosts = new StringFilterGroup(
                null,
                "post_base_wrapper",
                "image_post_root"
        );

        final StringFilterGroup expandableMetadata = new StringFilterGroup(
                Settings.HIDE_EXPANDABLE_CHIP,
                "inline_expander"
        );

        final StringFilterGroup feedSurvey = new StringFilterGroup(
                Settings.HIDE_FEED_SURVEY,
                "feed_nudge",
                "infeed_survey",
                "in_feed_survey",
                "slimline_survey"
        );

        final StringFilterGroup grayDescription = new StringFilterGroup(
                Settings.HIDE_GRAY_DESCRIPTION,
                ENDORSEMENT_HEADER_FOOTER_PATH
        );

        homeVideoWithContext = new StringFilterGroup(
                Settings.HIDE_VIDEO_WITH_LOW_VIEW,
                "home_video_with_context.eml"
        );

        final StringFilterGroup infoPanel = new StringFilterGroup(
                Settings.HIDE_INFO_PANEL,
                "compact_banner",
                "publisher_transparency_panel",
                "single_item_information_panel"
        );

        final StringFilterGroup latestPosts = new StringFilterGroup(
                Settings.HIDE_LATEST_POSTS,
                "post_shelf"
        );

        final StringFilterGroup medicalPanel = new StringFilterGroup(
                Settings.HIDE_MEDICAL_PANEL,
                "emergency_onebox",
                "medical_panel"
        );

        final StringFilterGroup movieShelf = new StringFilterGroup(
                Settings.HIDE_MOVIE_SHELF,
                "compact_movie",
                "horizontal_movie_shelf",
                "movie_and_show_upsell_card",
                "compact_tvfilm_item",
                "offer_module"
        );

        final StringFilterGroup notifyMe = new StringFilterGroup(
                Settings.HIDE_NOTIFY_ME_BUTTON,
                "set_reminder_button"
        );

        searchVideoWithContext = new StringFilterGroup(
                Settings.HIDE_VIDEO_WITH_GRAY_DESCRIPTION,
                "search_video_with_context.eml"
        );

        final StringFilterGroup ticketShelf = new StringFilterGroup(
                Settings.HIDE_TICKET_SHELF,
                "ticket_horizontal_shelf",
                "ticket_shelf"
        );

        final StringFilterGroup timedReactions = new StringFilterGroup(
                Settings.HIDE_TIMED_REACTIONS,
                "emoji_control_panel",
                "timed_reaction"
        );

        addPathCallbacks(
                albumCard,
                audioTrackButton,
                communityPosts,
                expandableMetadata,
                feedSurvey,
                grayDescription,
                homeVideoWithContext,
                infoPanel,
                latestPosts,
                medicalPanel,
                movieShelf,
                notifyMe,
                searchVideoWithContext,
                ticketShelf,
                timedReactions
        );

        communityPostsGroupList.addAll(
                new StringFilterGroup(
                        Settings.HIDE_COMMUNITY_POSTS_HOME,
                        "horizontalCollectionSwipeProtector=null"
                ),
                new StringFilterGroup(
                        Settings.HIDE_COMMUNITY_POSTS_SUBSCRIPTIONS,
                        "heightConstraint=null"
                )
        );
    }

    @Override
    public boolean isFiltered(String path, @Nullable String identifier, String allValue, byte[] protobufBufferArray,
                       StringFilterGroup matchedGroup, FilterContentType contentType, int contentIndex) {
        if (matchedGroup == homeVideoWithContext) {
            return lowViewsVideoIdentifier.check(protobufBufferArray).isFiltered();
        } else if (matchedGroup == searchVideoWithContext) {
            return grayDescriptionIdentifier.check(protobufBufferArray).isFiltered();
        } else if (matchedGroup == communityPosts) {
            return communityPostsGroupList.check(allValue).isFiltered();
        }

        return super.isFiltered(path, identifier, allValue, protobufBufferArray, matchedGroup, contentType, contentIndex);
    }
}
