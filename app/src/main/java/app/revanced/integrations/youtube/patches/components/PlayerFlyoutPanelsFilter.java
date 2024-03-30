package app.revanced.integrations.youtube.patches.components;

import androidx.annotation.Nullable;

import app.revanced.integrations.shared.patches.components.ByteArrayFilterGroup;
import app.revanced.integrations.shared.patches.components.ByteArrayFilterGroupList;
import app.revanced.integrations.shared.patches.components.Filter;
import app.revanced.integrations.shared.patches.components.StringFilterGroup;
import app.revanced.integrations.youtube.settings.Settings;
import app.revanced.integrations.youtube.shared.PlayerType;

@SuppressWarnings("unused")
public final class PlayerFlyoutPanelsFilter extends Filter {
    // Search the buffer only if the flyout menu identifier is found.
    // Handle the searching in this class instead of adding to the global filter group (which searches all the time)
    private final ByteArrayFilterGroupList flyoutFilterGroupList = new ByteArrayFilterGroupList();

    private final ByteArrayFilterGroupList exceptionFilterGroup = new ByteArrayFilterGroupList();

    public PlayerFlyoutPanelsFilter() {
        exceptionFilterGroup.addAll(new ByteArrayFilterGroup(null, "quality_sheet"));
        addPathCallbacks(new StringFilterGroup(null, "overflow_menu_item.eml|")); // Using pathCallbacks due to new flyout panel(A/B)

        flyoutFilterGroupList.addAll(
                new ByteArrayFilterGroup(
                        Settings.HIDE_PLAYER_FLYOUT_PANEL_AMBIENT,
                        "yt_outline_screen_light"
                ),
                new ByteArrayFilterGroup(
                        Settings.HIDE_PLAYER_FLYOUT_PANEL_AUDIO_TRACK,
                        "yt_outline_person_radar"
                ),
                new ByteArrayFilterGroup(
                        Settings.HIDE_PLAYER_FLYOUT_PANEL_CAPTIONS,
                        "closed_caption"
                ),
                new ByteArrayFilterGroup(
                        Settings.HIDE_PLAYER_FLYOUT_PANEL_HELP,
                        "yt_outline_question_circle"
                ),
                new ByteArrayFilterGroup(
                        Settings.HIDE_PLAYER_FLYOUT_PANEL_LOCK_SCREEN,
                        "yt_outline_lock"
                ),
                new ByteArrayFilterGroup(
                        Settings.HIDE_PLAYER_FLYOUT_PANEL_LOOP,
                        "yt_outline_arrow_repeat_1_"
                ),
                new ByteArrayFilterGroup(
                        Settings.HIDE_PLAYER_FLYOUT_PANEL_MORE,
                        "yt_outline_info_circle"
                ),
                new ByteArrayFilterGroup(
                        Settings.HIDE_PLAYER_FLYOUT_PANEL_PLAYBACK_SPEED,
                        "yt_outline_play_arrow_half_circle"
                ),
                new ByteArrayFilterGroup(
                        Settings.HIDE_PLAYER_FLYOUT_PANEL_PREMIUM_CONTROLS,
                        "yt_outline_adjust"
                ),
                new ByteArrayFilterGroup(
                        Settings.HIDE_PLAYER_FLYOUT_PANEL_ADDITIONAL_SETTINGS,
                        "yt_outline_gear"
                ),
                new ByteArrayFilterGroup(
                        Settings.HIDE_PLAYER_FLYOUT_PANEL_REPORT,
                        "yt_outline_flag"
                ),
                new ByteArrayFilterGroup(
                        Settings.HIDE_PLAYER_FLYOUT_PANEL_STABLE_VOLUME,
                        "volume_stable"
                ),
                new ByteArrayFilterGroup(
                        Settings.HIDE_PLAYER_FLYOUT_PANEL_STATS_FOR_NERDS,
                        "yt_outline_statistics_graph"
                ),
                new ByteArrayFilterGroup(
                        Settings.HIDE_PLAYER_FLYOUT_PANEL_WATCH_IN_VR,
                        "yt_outline_vr"
                ),
                new ByteArrayFilterGroup(
                        Settings.HIDE_PLAYER_FLYOUT_PANEL_YT_MUSIC,
                        "yt_outline_open_new"
                )
        );
    }

    @Override
    public boolean isFiltered(String path, @Nullable String identifier, String allValue, byte[] protobufBufferArray,
                       StringFilterGroup matchedGroup, FilterContentType contentType, int contentIndex) {
        // In YouTube v18.33.xx+, Shorts also use the player flyout panel
        if (PlayerType.getCurrent().isNoneOrHidden() || exceptionFilterGroup.check(protobufBufferArray).isFiltered())
            return false;
        // Only 1 group is added to the parent class, so the matched group must be the overflow menu.
        if (contentIndex == 0 && flyoutFilterGroupList.check(protobufBufferArray).isFiltered()) {
            // Super class handles logging.
            return super.isFiltered(path, identifier, allValue, protobufBufferArray, matchedGroup, contentType, contentIndex);
        }
        return false;
    }
}
