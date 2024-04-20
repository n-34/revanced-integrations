package app.revanced.integrations.youtube.patches.components;

import androidx.annotation.Nullable;

import app.revanced.integrations.shared.patches.components.ByteArrayFilterGroup;
import app.revanced.integrations.shared.patches.components.Filter;
import app.revanced.integrations.shared.patches.components.StringFilterGroup;
import app.revanced.integrations.shared.patches.components.StringFilterGroupList;
import app.revanced.integrations.shared.utils.StringTrieSearch;
import app.revanced.integrations.youtube.patches.feed.FeedPatch;
import app.revanced.integrations.youtube.settings.Settings;
import app.revanced.integrations.youtube.shared.BrowseId;
import app.revanced.integrations.youtube.shared.RootView;

@SuppressWarnings("unused")
public final class FeedComponentsFilter extends Filter {
    private static final String BROWSE_STORE_BUTTON_PATH = "|ContainerType|button.eml|";
    private static final String CONVERSATION_CONTEXT_FEED_IDENTIFIER =
            "horizontalCollectionSwipeProtector=null";
    private static final String CONVERSATION_CONTEXT_SUBSCRIPTIONS_IDENTIFIER =
            "heightConstraint=null";
    private static final ByteArrayFilterGroup browseStoreButton =
            new ByteArrayFilterGroup(
                    null,
                    "header_store_button"
            );
    private static final ByteArrayFilterGroup mixPlaylists =
            new ByteArrayFilterGroup(
                    Settings.HIDE_MIX_PLAYLISTS,
                    "&list="
            );
    private static final ByteArrayFilterGroup mixPlaylistsBufferExceptions =
            new ByteArrayFilterGroup(
                    null,
                    "cell_description_body"
            );
    private static final StringTrieSearch mixPlaylistsContextExceptions = new StringTrieSearch();

    private final StringFilterGroup carouselShelf;
    private final StringFilterGroup channelProfileButtonRule;
    private final StringFilterGroup communityPosts;

    private final StringFilterGroupList communityPostsGroupListWithOutSettings = new StringFilterGroupList();
    private final StringFilterGroupList communityPostsGroupListWithSettings = new StringFilterGroupList();


    public FeedComponentsFilter() {
        mixPlaylistsContextExceptions.addPatterns(
                "V.ED", // playlist browse id
                "java.lang.ref.WeakReference"
        );

        // Identifiers.

        final StringFilterGroup chipsShelf = new StringFilterGroup(
                Settings.HIDE_CHIPS_SHELF,
                "chips_shelf"
        );

        final StringFilterGroup feedSearchBar = new StringFilterGroup(
                Settings.HIDE_FEED_SEARCH_BAR,
                "search_bar_entry_point"
        );

        addIdentifierCallbacks(
                chipsShelf,
                feedSearchBar
        );

        // Paths.
        final StringFilterGroup albumCard = new StringFilterGroup(
                Settings.HIDE_ALBUM_CARDS,
                "browsy_bar",
                "official_card"
        );

        carouselShelf = new StringFilterGroup(
                Settings.HIDE_CAROUSEL_SHELF,
                "horizontal_shelf.eml",
                "horizontal_tile_shelf.eml",
                "horizontal_video_shelf.eml"
        );

        channelProfileButtonRule = new StringFilterGroup(
                null,
                "|channel_profile_"
        );

        final StringFilterGroup channelMemberShelf = new StringFilterGroup(
                Settings.HIDE_CHANNEL_MEMBER_SHELF,
                "member_recognition_shelf"
        );

        final StringFilterGroup channelProfileLinks = new StringFilterGroup(
                Settings.HIDE_CHANNEL_PROFILE_LINKS,
                "channel_header_links"
        );

        communityPosts = new StringFilterGroup(
                null,
                "post_base_wrapper",
                "image_post_root"
        );

        final StringFilterGroup expandableChip = new StringFilterGroup(
                Settings.HIDE_EXPANDABLE_CHIP,
                "inline_expansion"
        );

        final StringFilterGroup feedSurvey = new StringFilterGroup(
                Settings.HIDE_FEED_SURVEY,
                "feed_nudge",
                "_survey"
        );

        final StringFilterGroup forYouShelf = new StringFilterGroup(
                Settings.HIDE_FOR_YOU_SHELF,
                "mixed_content_shelf"
        );

        final StringFilterGroup imageShelf = new StringFilterGroup(
                Settings.HIDE_IMAGE_SHELF,
                "image_shelf"
        );

        final StringFilterGroup latestPosts = new StringFilterGroup(
                Settings.HIDE_LATEST_POSTS,
                "post_shelf"
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

        final StringFilterGroup subscriptionsChannelBar = new StringFilterGroup(
                Settings.HIDE_SUBSCRIPTIONS_CHANNEL_SECTION,
                "subscriptions_channel_bar"
        );

        final StringFilterGroup ticketShelf = new StringFilterGroup(
                Settings.HIDE_TICKET_SHELF,
                "ticket_horizontal_shelf",
                "ticket_shelf"
        );

        addPathCallbacks(
                albumCard,
                carouselShelf,
                channelProfileButtonRule,
                channelMemberShelf,
                channelProfileLinks,
                communityPosts,
                expandableChip,
                feedSurvey,
                forYouShelf,
                imageShelf,
                latestPosts,
                movieShelf,
                notifyMe,
                subscriptionsChannelBar,
                ticketShelf
        );

        final StringFilterGroup communityPostsFeed = new StringFilterGroup(
                null,
                CONVERSATION_CONTEXT_FEED_IDENTIFIER,
                CONVERSATION_CONTEXT_SUBSCRIPTIONS_IDENTIFIER
        );

        final StringFilterGroup communityPostsHomeAndRelatedVideos =
                new StringFilterGroup(
                        Settings.HIDE_COMMUNITY_POSTS_HOME_RELATED_VIDEOS,
                        CONVERSATION_CONTEXT_FEED_IDENTIFIER
                );

        final StringFilterGroup communityPostsSubscriptions =
                new StringFilterGroup(
                        Settings.HIDE_COMMUNITY_POSTS_SUBSCRIPTIONS,
                        CONVERSATION_CONTEXT_SUBSCRIPTIONS_IDENTIFIER
                );

        communityPostsGroupListWithOutSettings.addAll(communityPostsFeed);
        communityPostsGroupListWithSettings.addAll(communityPostsHomeAndRelatedVideos, communityPostsSubscriptions);
    }

    /**
     * Injection point.
     * <p>
     * Called from a different place then the other filters.
     */
    public static boolean filterMixPlaylists(final Object conversionContext, final byte[] bytes) {
        return bytes != null
                && mixPlaylists.check(bytes).isFiltered()
                && !mixPlaylistsBufferExceptions.check(bytes).isFiltered()
                && !mixPlaylistsContextExceptions.matches(conversionContext.toString());
    }

    @Override
    public boolean isFiltered(String path, @Nullable String identifier, String allValue, byte[] protobufBufferArray,
                       StringFilterGroup matchedGroup, FilterContentType contentType, int contentIndex) {
        if (matchedGroup == carouselShelf) {
            if (BrowseId.isLibrary() && !RootView.isSearchBarActive())
                return false;
        } else if (matchedGroup == channelProfileButtonRule) {
            final boolean isBrowseStoreButtonShown = path.contains(BROWSE_STORE_BUTTON_PATH) && browseStoreButton.check(protobufBufferArray).isFiltered();
            FeedPatch.hideStoreTab(isBrowseStoreButtonShown);
            if (!isBrowseStoreButtonShown || !Settings.HIDE_BROWSE_STORE_BUTTON.get()) {
                return false;
            }
        } else if (matchedGroup == communityPosts) {
            if (!communityPostsGroupListWithOutSettings.check(allValue).isFiltered() && Settings.HIDE_COMMUNITY_POSTS_CHANNEL.get()) {
                return super.isFiltered(path, identifier, allValue, protobufBufferArray, matchedGroup, contentType, contentIndex);
            }
            if (!communityPostsGroupListWithSettings.check(allValue).isFiltered()) {
                return false;
            }
        }

        return super.isFiltered(path, identifier, allValue, protobufBufferArray, matchedGroup, contentType, contentIndex);
    }
}
