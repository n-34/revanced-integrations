package app.revanced.integrations.music.settings;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static app.revanced.integrations.music.sponsorblock.objects.CategoryBehaviour.SKIP_AUTOMATICALLY;

import androidx.annotation.NonNull;

import app.revanced.integrations.shared.settings.BaseSettings;
import app.revanced.integrations.shared.settings.BooleanSetting;
import app.revanced.integrations.shared.settings.FloatSetting;
import app.revanced.integrations.shared.settings.IntegerSetting;
import app.revanced.integrations.shared.settings.LongSetting;
import app.revanced.integrations.shared.settings.StringSetting;
import app.revanced.integrations.shared.utils.Utils;


@SuppressWarnings("unused")
public class Settings extends BaseSettings {
    // Account
    public static final BooleanSetting HIDE_ACCOUNT_MENU = new BooleanSetting("revanced_hide_account_menu", FALSE);
    public static final StringSetting HIDE_ACCOUNT_MENU_FILTER_STRINGS = new StringSetting("revanced_hide_account_menu_filter_strings", "");
    public static final BooleanSetting HIDE_ACCOUNT_MENU_EMPTY_COMPONENT = new BooleanSetting("revanced_hide_account_menu_empty_component", FALSE);
    public static final BooleanSetting HIDE_HANDLE = new BooleanSetting("revanced_hide_handle", TRUE, true);
    public static final BooleanSetting HIDE_TERMS_CONTAINER = new BooleanSetting("revanced_hide_terms_container", FALSE);


    // Action Bar
    public static final StringSetting EXTERNAL_DOWNLOADER_PACKAGE_NAME = new StringSetting("revanced_external_downloader_package_name", "com.deniscerri.ytdl", true);
    public static final BooleanSetting HIDE_ACTION_BUTTON_ADD_TO_PLAYLIST = new BooleanSetting("revanced_hide_action_button_add_to_playlist", FALSE, true);
    public static final BooleanSetting HIDE_ACTION_BUTTON_COMMENT = new BooleanSetting("revanced_hide_action_button_comment", FALSE, true);
    public static final BooleanSetting HIDE_ACTION_BUTTON_DOWNLOAD = new BooleanSetting("revanced_hide_action_button_download", FALSE, true);
    public static final BooleanSetting HIDE_ACTION_BUTTON_LABEL = new BooleanSetting("revanced_hide_action_button_label", FALSE, true);
    public static final BooleanSetting HIDE_ACTION_BUTTON_LIKE_DISLIKE = new BooleanSetting("revanced_hide_action_button_like_dislike", FALSE, true);
    public static final BooleanSetting HIDE_ACTION_BUTTON_RADIO = new BooleanSetting("revanced_hide_action_button_radio", FALSE, true);
    public static final BooleanSetting HIDE_ACTION_BUTTON_SHARE = new BooleanSetting("revanced_hide_action_button_share", FALSE, true);
    public static final BooleanSetting HOOK_ACTION_BUTTON_DOWNLOAD = new BooleanSetting("revanced_hook_action_button_download", FALSE, true);


    // Ads
    public static final BooleanSetting HIDE_FULLSCREEN_ADS = new BooleanSetting("revanced_hide_fullscreen_ads", TRUE, true);
    public static final BooleanSetting HIDE_GENERAL_ADS = new BooleanSetting("revanced_hide_general_ads", TRUE, true);
    public static final BooleanSetting HIDE_MUSIC_ADS = new BooleanSetting("revanced_hide_music_ads", TRUE, true);
    public static final BooleanSetting HIDE_PAID_PROMOTION = new BooleanSetting("revanced_hide_paid_promotion", TRUE, true);
    public static final BooleanSetting HIDE_PREMIUM_PROMOTION = new BooleanSetting("revanced_hide_premium_promotion", TRUE, true);
    public static final BooleanSetting HIDE_PREMIUM_RENEWAL = new BooleanSetting("revanced_hide_premium_renewal", TRUE, true);


    // Flyout
    public static final BooleanSetting ENABLE_COMPACT_DIALOG = new BooleanSetting("revanced_enable_compact_dialog", TRUE);

    public static final BooleanSetting HIDE_FLYOUT_PANEL_3_COLUMN_COMPONENT = new BooleanSetting("revanced_hide_flyout_panel_3_column_component", FALSE, true);
    public static final BooleanSetting HIDE_FLYOUT_PANEL_ADD_TO_QUEUE = new BooleanSetting("revanced_hide_flyout_panel_add_to_queue", FALSE, true);
    public static final BooleanSetting HIDE_FLYOUT_PANEL_CAPTIONS = new BooleanSetting("revanced_hide_flyout_panel_captions", FALSE, true);
    public static final BooleanSetting HIDE_FLYOUT_PANEL_DELETE_PLAYLIST = new BooleanSetting("revanced_hide_flyout_panel_delete_playlist", FALSE, true);
    public static final BooleanSetting HIDE_FLYOUT_PANEL_DISMISS_QUEUE = new BooleanSetting("revanced_hide_flyout_panel_dismiss_queue", FALSE, true);
    public static final BooleanSetting HIDE_FLYOUT_PANEL_DOWNLOAD = new BooleanSetting("revanced_hide_flyout_panel_download", FALSE, true);
    public static final BooleanSetting HIDE_FLYOUT_PANEL_EDIT_PLAYLIST = new BooleanSetting("revanced_hide_flyout_panel_edit_playlist", FALSE, true);
    public static final BooleanSetting HIDE_FLYOUT_PANEL_GO_TO_ALBUM = new BooleanSetting("revanced_hide_flyout_panel_go_to_album", FALSE, true);
    public static final BooleanSetting HIDE_FLYOUT_PANEL_GO_TO_ARTIST = new BooleanSetting("revanced_hide_flyout_panel_go_to_artist", FALSE, true);
    public static final BooleanSetting HIDE_FLYOUT_PANEL_GO_TO_EPISODE = new BooleanSetting("revanced_hide_flyout_panel_go_to_episode", FALSE, true);
    public static final BooleanSetting HIDE_FLYOUT_PANEL_GO_TO_PODCAST = new BooleanSetting("revanced_hide_flyout_panel_go_to_podcast", FALSE, true);
    public static final BooleanSetting HIDE_FLYOUT_PANEL_HELP = new BooleanSetting("revanced_hide_flyout_panel_help", FALSE, true);
    public static final BooleanSetting HIDE_FLYOUT_PANEL_LIKE_DISLIKE = new BooleanSetting("revanced_hide_flyout_panel_like_dislike", FALSE, true);
    public static final BooleanSetting HIDE_FLYOUT_PANEL_PLAY_NEXT = new BooleanSetting("revanced_hide_flyout_panel_play_next", FALSE, true);
    public static final BooleanSetting HIDE_FLYOUT_PANEL_QUALITY = new BooleanSetting("revanced_hide_flyout_panel_quality", FALSE, true);
    public static final BooleanSetting HIDE_FLYOUT_PANEL_REMOVE_FROM_LIBRARY = new BooleanSetting("revanced_hide_flyout_panel_remove_from_library", FALSE, true);
    public static final BooleanSetting HIDE_FLYOUT_PANEL_REMOVE_FROM_PLAYLIST = new BooleanSetting("revanced_hide_flyout_panel_remove_from_playlist", FALSE, true);
    public static final BooleanSetting HIDE_FLYOUT_PANEL_REPORT = new BooleanSetting("revanced_hide_flyout_panel_report", FALSE, true);
    public static final BooleanSetting HIDE_FLYOUT_PANEL_SAVE_EPISODE_FOR_LATER = new BooleanSetting("revanced_hide_flyout_panel_save_episode_for_later", FALSE, true);
    public static final BooleanSetting HIDE_FLYOUT_PANEL_SAVE_TO_LIBRARY = new BooleanSetting("revanced_hide_flyout_panel_save_to_library", FALSE, true);
    public static final BooleanSetting HIDE_FLYOUT_PANEL_SAVE_TO_PLAYLIST = new BooleanSetting("revanced_hide_flyout_panel_save_to_playlist", FALSE, true);
    public static final BooleanSetting HIDE_FLYOUT_PANEL_SHARE = new BooleanSetting("revanced_hide_flyout_panel_share", FALSE, true);
    public static final BooleanSetting HIDE_FLYOUT_PANEL_SHUFFLE_PLAY = new BooleanSetting("revanced_hide_flyout_panel_shuffle_play", FALSE, true);
    public static final BooleanSetting HIDE_FLYOUT_PANEL_SLEEP_TIMER = new BooleanSetting("revanced_hide_flyout_panel_sleep_timer", FALSE, true);
    public static final BooleanSetting HIDE_FLYOUT_PANEL_START_RADIO = new BooleanSetting("revanced_hide_flyout_panel_start_radio", FALSE, true);
    public static final BooleanSetting HIDE_FLYOUT_PANEL_STATS_FOR_NERDS = new BooleanSetting("revanced_hide_flyout_panel_stats_for_nerds", FALSE, true);
    public static final BooleanSetting HIDE_FLYOUT_PANEL_SUBSCRIBE = new BooleanSetting("revanced_hide_flyout_panel_subscribe", FALSE, true);
    public static final BooleanSetting HIDE_FLYOUT_PANEL_VIEW_SONG_CREDIT = new BooleanSetting("revanced_hide_flyout_panel_view_song_credit", FALSE, true);
    public static final BooleanSetting REPLACE_FLYOUT_PANEL_DISMISS_QUEUE = new BooleanSetting("revanced_replace_flyout_panel_dismiss_queue", FALSE, true);
    public static final BooleanSetting REPLACE_FLYOUT_PANEL_DISMISS_QUEUE_CONTINUE_WATCH = new BooleanSetting("revanced_replace_flyout_panel_dismiss_queue_continue_watch", TRUE);
    public static final BooleanSetting REPLACE_FLYOUT_PANEL_REPORT = new BooleanSetting("revanced_replace_flyout_panel_report", TRUE, true);
    public static final BooleanSetting REPLACE_FLYOUT_PANEL_REPORT_ONLY_PLAYER = new BooleanSetting("revanced_replace_flyout_panel_report_only_player", TRUE, true);


    // General
    public static final StringSetting CHANGE_START_PAGE = new StringSetting("revanced_change_start_page", "FEmusic_home", true);
    public static final BooleanSetting CUSTOM_FILTER = new BooleanSetting("revanced_custom_filter", FALSE);
    public static final StringSetting CUSTOM_FILTER_STRINGS = new StringSetting("revanced_custom_filter_strings", "", true);
    public static final BooleanSetting DISABLE_AUTO_CAPTIONS = new BooleanSetting("revanced_disable_auto_captions", FALSE, true);
    public static final BooleanSetting DISABLE_DISLIKE_REDIRECTION = new BooleanSetting("revanced_disable_dislike_redirection", FALSE);
    public static final BooleanSetting ENABLE_LANDSCAPE_MODE = new BooleanSetting("revanced_enable_landscape_mode", TRUE, true);
    public static final BooleanSetting ENABLE_OLD_STYLE_LIBRARY_SHELF = new BooleanSetting("revanced_enable_old_style_library_shelf", FALSE, true);
    public static final BooleanSetting HIDE_BUTTON_SHELF = new BooleanSetting("revanced_hide_button_shelf", FALSE, true);
    public static final BooleanSetting HIDE_CAROUSEL_SHELF = new BooleanSetting("revanced_hide_carousel_shelf", FALSE, true);
    public static final BooleanSetting HIDE_CAST_BUTTON = new BooleanSetting("revanced_hide_cast_button", TRUE);
    public static final BooleanSetting HIDE_CATEGORY_BAR = new BooleanSetting("revanced_hide_category_bar", FALSE, true);
    public static final BooleanSetting HIDE_CHANNEL_GUIDELINES = new BooleanSetting("revanced_hide_channel_guidelines", TRUE);
    public static final BooleanSetting HIDE_EMOJI_PICKER = new BooleanSetting("revanced_hide_emoji_picker", FALSE);
    public static final BooleanSetting HIDE_HISTORY_BUTTON = new BooleanSetting("revanced_hide_history_button", FALSE);
    public static final BooleanSetting HIDE_NEW_PLAYLIST_BUTTON = new BooleanSetting("revanced_hide_new_playlist_button", FALSE);
    public static final BooleanSetting HIDE_PLAYLIST_CARD = new BooleanSetting("revanced_hide_playlist_card", FALSE, true);
    public static final BooleanSetting HIDE_SAMPLE_SHELF = new BooleanSetting("revanced_hide_samples_shelf", FALSE, true);
    public static final BooleanSetting HIDE_TAP_TO_UPDATE_BUTTON = new BooleanSetting("revanced_hide_tap_to_update_button", FALSE, true);
    public static final BooleanSetting REMOVE_VIEWER_DISCRETION_DIALOG = new BooleanSetting("revanced_remove_viewer_discretion_dialog", FALSE);


    // Misc
    public static final BooleanSetting ENABLE_OPUS_CODEC = new BooleanSetting("revanced_enable_opus_codec", TRUE, true);
    public static final BooleanSetting SANITIZE_SHARING_LINKS = new BooleanSetting("revanced_sanitize_sharing_links", TRUE, true);
    public static final BooleanSetting SETTINGS_IMPORT_EXPORT = new BooleanSetting("revanced_extended_settings_import_export", FALSE, false);
    public static final BooleanSetting SPOOF_APP_VERSION = new BooleanSetting("revanced_spoof_app_version", FALSE, true);
    public static final StringSetting SPOOF_APP_VERSION_TARGET = new StringSetting("revanced_spoof_app_version_target", "4.27.53", true);


    // Navigation
    public static final BooleanSetting ENABLE_BLACK_NAVIGATION_BAR = new BooleanSetting("revanced_enable_black_navigation_bar", TRUE);
    public static final BooleanSetting HIDE_EXPLORE_BUTTON = new BooleanSetting("revanced_hide_explore_button", FALSE, true);
    public static final BooleanSetting HIDE_HOME_BUTTON = new BooleanSetting("revanced_hide_home_button", FALSE, true);
    public static final BooleanSetting HIDE_LIBRARY_BUTTON = new BooleanSetting("revanced_hide_library_button", FALSE, true);
    public static final BooleanSetting HIDE_NAVIGATION_BAR = new BooleanSetting("revanced_hide_navigation_bar", FALSE, true);
    public static final BooleanSetting HIDE_NAVIGATION_LABEL = new BooleanSetting("revanced_hide_navigation_label", FALSE, true);
    public static final BooleanSetting HIDE_SAMPLES_BUTTON = new BooleanSetting("revanced_hide_samples_button", FALSE, true);
    public static final BooleanSetting HIDE_UPGRADE_BUTTON = new BooleanSetting("revanced_hide_upgrade_button", TRUE, true);


    // Player
    public static final BooleanSetting ENABLE_COLOR_MATCH_PLAYER = new BooleanSetting("revanced_enable_color_match_player", TRUE);
    public static final BooleanSetting ENABLE_FORCE_MINIMIZED_PLAYER = new BooleanSetting("revanced_enable_force_minimized_player", TRUE);
    public static final BooleanSetting ENABLE_MINI_PLAYER_NEXT_BUTTON = new BooleanSetting("revanced_enable_mini_player_next_button", TRUE, true);
    public static final BooleanSetting ENABLE_MINI_PLAYER_PREVIOUS_BUTTON = new BooleanSetting("revanced_enable_mini_player_previous_button", FALSE, true);
    public static final BooleanSetting ENABLE_OLD_PLAYER_BACKGROUND = new BooleanSetting("revanced_enable_old_player_background", FALSE, true);
    public static final BooleanSetting ENABLE_OLD_PLAYER_LAYOUT = new BooleanSetting("revanced_enable_old_player_layout", FALSE, true);
    public static final BooleanSetting ENABLE_SWIPE_TO_DISMISS_MINI_PLAYER = new BooleanSetting("revanced_enable_swipe_to_dismiss_mini_player", TRUE, true);
    public static final BooleanSetting ENABLE_ZEN_MODE = new BooleanSetting("revanced_enable_zen_mode", FALSE);
    public static final BooleanSetting ENABLE_ZEN_MODE_PODCAST = new BooleanSetting("revanced_enable_zen_mode_podcast", FALSE);
    public static final BooleanSetting HIDE_FULLSCREEN_SHARE_BUTTON = new BooleanSetting("revanced_hide_fullscreen_share_button", FALSE, true);
    public static final BooleanSetting REMEMBER_REPEAT_SATE = new BooleanSetting("revanced_remember_repeat_state", TRUE);
    public static final BooleanSetting REMEMBER_SHUFFLE_SATE = new BooleanSetting("revanced_remember_shuffle_state", TRUE);
    public static final IntegerSetting SHUFFLE_SATE = new IntegerSetting("revanced_shuffle_state", 1);
    public static final BooleanSetting REPLACE_PLAYER_CAST_BUTTON = new BooleanSetting("revanced_replace_player_cast_button", FALSE, true);


    // Video
    public static final StringSetting CUSTOM_PLAYBACK_SPEEDS = new StringSetting("revanced_custom_playback_speeds", "0.5\n0.8\n1.0\n1.2\n1.5\n1.8\n2.0", true);
    public static final BooleanSetting ENABLE_SAVE_PLAYBACK_SPEED = new BooleanSetting("revanced_enable_save_playback_speed", TRUE);
    public static final BooleanSetting ENABLE_SAVE_VIDEO_QUALITY = new BooleanSetting("revanced_enable_save_video_quality", TRUE);
    public static final FloatSetting DEFAULT_PLAYBACK_SPEED = new FloatSetting("revanced_default_playback_speed", 1.0f);
    public static final IntegerSetting DEFAULT_VIDEO_QUALITY_MOBILE = new IntegerSetting("revanced_default_video_quality_mobile", -2);
    public static final IntegerSetting DEFAULT_VIDEO_QUALITY_WIFI = new IntegerSetting("revanced_default_video_quality_wifi", -2);

    // Return YouTube Dislike
    public static final BooleanSetting RYD_ENABLED = new BooleanSetting("revanced_ryd_enabled", TRUE);
    public static final StringSetting RYD_USER_ID = new StringSetting("revanced_ryd_user_id", "", false, false);
    public static final BooleanSetting RYD_DISLIKE_PERCENTAGE = new BooleanSetting("revanced_ryd_dislike_percentage", FALSE);
    public static final BooleanSetting RYD_COMPACT_LAYOUT = new BooleanSetting("revanced_ryd_compact_layout", FALSE);
    public static final BooleanSetting RYD_TOAST_ON_CONNECTION_ERROR = new BooleanSetting("revanced_ryd_toast_on_connection_error", FALSE);


    // SponsorBlock
    public static final BooleanSetting SB_ENABLED = new BooleanSetting("sb_enabled", TRUE);
    public static final BooleanSetting SB_TOAST_ON_CONNECTION_ERROR = new BooleanSetting("sb_toast_on_connection_error", FALSE);
    public static final BooleanSetting SB_TOAST_ON_SKIP = new BooleanSetting("sb_toast_on_skip", TRUE);
    public static final StringSetting SB_API_URL = new StringSetting("sb_api_url", "https://sponsor.ajay.app");
    public static final StringSetting SB_PRIVATE_USER_ID = new StringSetting("sb_private_user_id", "");
    public static final BooleanSetting SB_USER_IS_VIP = new BooleanSetting("sb_user_is_vip", FALSE);

    public static final StringSetting SB_CATEGORY_SPONSOR = new StringSetting("sb_sponsor", SKIP_AUTOMATICALLY.reVancedKeyValue);
    public static final StringSetting SB_CATEGORY_SPONSOR_COLOR = new StringSetting("sb_sponsor_color","#00D400");
    public static final StringSetting SB_CATEGORY_SELF_PROMO = new StringSetting("sb_selfpromo", SKIP_AUTOMATICALLY.reVancedKeyValue);
    public static final StringSetting SB_CATEGORY_SELF_PROMO_COLOR = new StringSetting("sb_selfpromo_color","#FFFF00");
    public static final StringSetting SB_CATEGORY_INTERACTION = new StringSetting("sb_interaction", SKIP_AUTOMATICALLY.reVancedKeyValue);
    public static final StringSetting SB_CATEGORY_INTERACTION_COLOR = new StringSetting("sb_interaction_color","#CC00FF");
    public static final StringSetting SB_CATEGORY_INTRO = new StringSetting("sb_intro", SKIP_AUTOMATICALLY.reVancedKeyValue);
    public static final StringSetting SB_CATEGORY_INTRO_COLOR = new StringSetting("sb_intro_color","#00FFFF");
    public static final StringSetting SB_CATEGORY_OUTRO = new StringSetting("sb_outro", SKIP_AUTOMATICALLY.reVancedKeyValue);
    public static final StringSetting SB_CATEGORY_OUTRO_COLOR = new StringSetting("sb_outro_color","#0202ED");
    public static final StringSetting SB_CATEGORY_PREVIEW = new StringSetting("sb_preview", SKIP_AUTOMATICALLY.reVancedKeyValue);
    public static final StringSetting SB_CATEGORY_PREVIEW_COLOR = new StringSetting("sb_preview_color","#008FD6");
    public static final StringSetting SB_CATEGORY_FILLER = new StringSetting("sb_filler", SKIP_AUTOMATICALLY.reVancedKeyValue);
    public static final StringSetting SB_CATEGORY_FILLER_COLOR = new StringSetting("sb_filler_color","#7300FF");
    public static final StringSetting SB_CATEGORY_MUSIC_OFFTOPIC = new StringSetting("sb_music_offtopic", SKIP_AUTOMATICALLY.reVancedKeyValue);
    public static final StringSetting SB_CATEGORY_MUSIC_OFFTOPIC_COLOR = new StringSetting("sb_music_offtopic_color","#FF9900");

    // SB settings not exported
    public static final LongSetting SB_LAST_VIP_CHECK = new LongSetting("sb_last_vip_check", 0L, false, false);

    /**
     * If a setting path has this prefix, then remove it.
     */
    public static final String OPTIONAL_SPONSOR_BLOCK_SETTINGS_PREFIX = "sb_segments_";

    /**
     * Array of settings using intent
     */
    private static final String[] intentSettingArray = new String[]{
            CHANGE_START_PAGE.key,
            CUSTOM_FILTER_STRINGS.key,
            CUSTOM_PLAYBACK_SPEEDS.key,
            EXTERNAL_DOWNLOADER_PACKAGE_NAME.key,
            HIDE_ACCOUNT_MENU_FILTER_STRINGS.key,
            SB_API_URL.key,
            SETTINGS_IMPORT_EXPORT.key,
            SPOOF_APP_VERSION_TARGET.key,
            OPTIONAL_SPONSOR_BLOCK_SETTINGS_PREFIX
    };

    /**
     * @return whether dataString contains settings that use Intent
     */
    public static boolean includeWithIntent(@NonNull String dataString) {
        return Utils.containsAny(dataString, intentSettingArray);
    }
}
