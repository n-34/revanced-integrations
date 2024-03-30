package app.revanced.integrations.youtube.patches.components;

import androidx.annotation.Nullable;

import app.revanced.integrations.shared.patches.components.ByteArrayFilterGroup;
import app.revanced.integrations.shared.patches.components.ByteArrayFilterGroupList;
import app.revanced.integrations.shared.patches.components.Filter;
import app.revanced.integrations.shared.patches.components.StringFilterGroup;
import app.revanced.integrations.shared.utils.StringTrieSearch;
import app.revanced.integrations.youtube.settings.Settings;

@SuppressWarnings("unused")
public final class ShortsButtonFilter extends Filter {
    private static final String REEL_CHANNEL_BAR_PATH = "reel_channel_bar.eml";
    private final StringTrieSearch exceptions = new StringTrieSearch();
    private final StringFilterGroup infoPanel;

    private final StringFilterGroup videoActionButton;
    private final ByteArrayFilterGroupList videoActionButtonGroupList = new ByteArrayFilterGroupList();


    public ShortsButtonFilter() {
        exceptions.addPatterns(
                "lock_mode_suggested_action"
        );

        final StringFilterGroup thanksButton = new StringFilterGroup(
                Settings.HIDE_SHORTS_PLAYER_THANKS_BUTTON,
                "suggested_action"
        );

        addIdentifierCallbacks(thanksButton);

        final StringFilterGroup joinButton = new StringFilterGroup(
                Settings.HIDE_SHORTS_PLAYER_JOIN_BUTTON,
                "sponsor_button"
        );

        final StringFilterGroup subscribeButton = new StringFilterGroup(
                Settings.HIDE_SHORTS_PLAYER_SUBSCRIPTIONS_BUTTON,
                "shorts_paused_state",
                "subscribe_button"
        );

        infoPanel = new StringFilterGroup(
                Settings.HIDE_SHORTS_PLAYER_INFO_PANEL,
                "reel_multi_format_link",
                "reel_sound_metadata",
                "shorts_info_panel_overview"
        );

        videoActionButton = new StringFilterGroup(
                null,
                "shorts_video_action_button"
        );

        addPathCallbacks(
                joinButton,
                subscribeButton,
                infoPanel,
                videoActionButton
        );

        final ByteArrayFilterGroup shortsDislikeButton =
                new ByteArrayFilterGroup(
                        Settings.HIDE_SHORTS_PLAYER_DISLIKE_BUTTON,
                        "reel_dislike_button",
                        "reel_dislike_toggled_button"
                );

        final ByteArrayFilterGroup shortsLikeButton =
                new ByteArrayFilterGroup(
                        Settings.HIDE_SHORTS_PLAYER_LIKE_BUTTON,
                        "reel_like_button",
                        "reel_like_toggled_button"
                );

        final ByteArrayFilterGroup shortsCommentButton =
                new ByteArrayFilterGroup(
                        Settings.HIDE_SHORTS_PLAYER_COMMENTS_BUTTON,
                        "reel_comment_button"
                );

        final ByteArrayFilterGroup shortsRemixButton =
                new ByteArrayFilterGroup(
                        Settings.HIDE_SHORTS_PLAYER_REMIX_BUTTON,
                        "reel_remix_button"
                );

        final ByteArrayFilterGroup shortsShareButton =
                new ByteArrayFilterGroup(
                        Settings.HIDE_SHORTS_PLAYER_SHARE_BUTTON,
                        "reel_share_button"
                );

        videoActionButtonGroupList.addAll(
                shortsCommentButton,
                shortsDislikeButton,
                shortsLikeButton,
                shortsRemixButton,
                shortsShareButton
        );
    }

    @Override
    public boolean isFiltered(String path, @Nullable String identifier, String allValue, byte[] protobufBufferArray,
                       StringFilterGroup matchedGroup, FilterContentType contentType, int contentIndex) {
        if (exceptions.matches(path))
            return false;

        if (contentType == FilterContentType.PATH) {
            if (matchedGroup == infoPanel) {
                // Always filter if matched.
                return super.isFiltered(path, identifier, allValue, protobufBufferArray, matchedGroup, contentType, contentIndex);
            } else if (matchedGroup == videoActionButton) {
                // Video action buttons have the same path.
                return videoActionButtonGroupList.check(protobufBufferArray).isFiltered();
            } else {
                // Filter other path groups from pathCallbacks, only when reelChannelBar is visible
                // to avoid false positives.
                return path.startsWith(REEL_CHANNEL_BAR_PATH);
            }
        }

        // Super class handles logging.
        return super.isFiltered(path, identifier, allValue, protobufBufferArray, matchedGroup, contentType, contentIndex);
    }
}
