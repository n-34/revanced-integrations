package app.revanced.integrations.youtube.settings;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static app.revanced.integrations.shared.settings.Setting.migrateFromOldPreferences;
import static app.revanced.integrations.shared.settings.Setting.parent;
import static app.revanced.integrations.shared.settings.Setting.parentsAny;
import static app.revanced.integrations.youtube.sponsorblock.objects.CategoryBehaviour.MANUAL_SKIP;
import static app.revanced.integrations.youtube.sponsorblock.objects.CategoryBehaviour.SKIP_AUTOMATICALLY;
import static app.revanced.integrations.youtube.sponsorblock.objects.CategoryBehaviour.SKIP_AUTOMATICALLY_ONCE;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import app.revanced.integrations.shared.settings.BaseSettings;
import app.revanced.integrations.shared.settings.BooleanSetting;
import app.revanced.integrations.shared.settings.FloatSetting;
import app.revanced.integrations.shared.settings.IntegerSetting;
import app.revanced.integrations.shared.settings.LongSetting;
import app.revanced.integrations.shared.settings.Setting;
import app.revanced.integrations.shared.settings.StringSetting;
import app.revanced.integrations.shared.settings.preference.SharedPrefCategory;
import app.revanced.integrations.youtube.sponsorblock.SponsorBlockSettings;

@SuppressWarnings("unused")
public class Settings extends BaseSettings {
    // Ads
    public static final BooleanSetting HIDE_FULLSCREEN_ADS = new BooleanSetting("revanced_hide_fullscreen_ads", TRUE, true);
    public static final BooleanSetting HIDE_GENERAL_ADS = new BooleanSetting("revanced_hide_general_ads", TRUE);
    public static final BooleanSetting HIDE_GET_PREMIUM = new BooleanSetting("revanced_hide_get_premium", TRUE, true);
    public static final BooleanSetting HIDE_IMAGE_SHELF = new BooleanSetting("revanced_hide_image_shelf", TRUE);
    public static final BooleanSetting HIDE_MERCHANDISE_SHELF = new BooleanSetting("revanced_hide_merchandise_shelf", TRUE);
    public static final BooleanSetting HIDE_PAID_PROMOTION = new BooleanSetting("revanced_hide_paid_promotion_banner", TRUE);
    public static final BooleanSetting HIDE_SELF_SPONSOR_CARDS = new BooleanSetting("revanced_hide_self_sponsor_cards", TRUE);
    public static final BooleanSetting HIDE_VIDEO_ADS = new BooleanSetting("revanced_hide_video_ads", TRUE, true);
    public static final BooleanSetting HIDE_VIEW_PRODUCTS = new BooleanSetting("revanced_hide_view_products", TRUE);
    public static final BooleanSetting HIDE_WEB_SEARCH_RESULTS = new BooleanSetting("revanced_hide_web_search_results", TRUE);


    // Alternative Thumbnails
    public static final BooleanSetting ALT_THUMBNAIL_DEARROW = new BooleanSetting("revanced_alt_thumbnail_dearrow", FALSE);
    public static final StringSetting ALT_THUMBNAIL_DEARROW_API_URL = new StringSetting("revanced_alt_thumbnail_dearrow_api_url", "https://dearrow-thumb.ajay.app/api/v1/getThumbnail", true, parent(ALT_THUMBNAIL_DEARROW));
    public static final BooleanSetting ALT_THUMBNAIL_DEARROW_CONNECTION_TOAST = new BooleanSetting("revanced_alt_thumbnail_dearrow_connection_toast", FALSE, parent(ALT_THUMBNAIL_DEARROW));
    public static final BooleanSetting ALT_THUMBNAIL_STILLS = new BooleanSetting("revanced_alt_thumbnail_stills", FALSE);
    public static final BooleanSetting ALT_THUMBNAIL_STILLS_FAST = new BooleanSetting("revanced_alt_thumbnail_stills_fast", FALSE, parent(ALT_THUMBNAIL_STILLS));
    public static final IntegerSetting ALT_THUMBNAIL_STILLS_TIME = new IntegerSetting("revanced_alt_thumbnail_stills_time", 2, parent(ALT_THUMBNAIL_STILLS));


    // Bottom Player
    public static final BooleanSetting ENABLE_BOTTOM_PLAYER_GESTURES = new BooleanSetting("revanced_enable_bottom_player_gestures", TRUE, true);

    // Channel Bar
    public static final BooleanSetting HIDE_JOIN_BUTTON = new BooleanSetting("revanced_hide_join_button", TRUE);
    public static final BooleanSetting HIDE_START_TRIAL_BUTTON = new BooleanSetting("revanced_hide_start_trial_button", TRUE);

    // Button Container
    public static final BooleanSetting HIDE_CREATE_CLIP_BUTTON = new BooleanSetting("revanced_hide_button_create_clip", FALSE);
    public static final BooleanSetting HIDE_DOWNLOAD_BUTTON = new BooleanSetting("revanced_hide_button_download", FALSE);
    public static final BooleanSetting HIDE_LIKE_DISLIKE_BUTTON = new BooleanSetting("revanced_hide_button_like_dislike", FALSE);
    public static final BooleanSetting HIDE_REWARDS_BUTTON = new BooleanSetting("revanced_hide_button_rewards", FALSE);
    public static final BooleanSetting HIDE_REMIX_BUTTON = new BooleanSetting("revanced_hide_button_remix", FALSE);
    public static final BooleanSetting HIDE_REPORT_BUTTON = new BooleanSetting("revanced_hide_button_report", FALSE);
    public static final BooleanSetting HIDE_SAVE_TO_PLAYLIST_BUTTON = new BooleanSetting("revanced_hide_button_save_to_playlist", FALSE);
    public static final BooleanSetting HIDE_SHARE_BUTTON = new BooleanSetting("revanced_hide_button_share", FALSE);
    public static final BooleanSetting HIDE_SHOP_BUTTON = new BooleanSetting("revanced_hide_button_shop", FALSE);
    public static final BooleanSetting HIDE_THANKS_BUTTON = new BooleanSetting("revanced_hide_button_thanks", FALSE);

    // Comments
    public static final BooleanSetting HIDE_CHANNEL_GUIDELINES = new BooleanSetting("revanced_hide_channel_guidelines", TRUE);
    public static final BooleanSetting HIDE_COMMENTS_BY_MEMBERS = new BooleanSetting("revanced_hide_comments_by_members", FALSE);
    public static final BooleanSetting HIDE_COMMENTS_SECTION = new BooleanSetting("revanced_hide_comments_section", FALSE);
    public static final BooleanSetting HIDE_COMMENTS_THANKS_BUTTON = new BooleanSetting("revanced_hide_comments_thanks_button", FALSE);
    public static final BooleanSetting HIDE_CREATE_SHORTS_BUTTON = new BooleanSetting("revanced_hide_create_shorts_button", FALSE);
    public static final BooleanSetting HIDE_EMOJI_PICKER = new BooleanSetting("revanced_hide_emoji_picker", FALSE);
    public static final BooleanSetting HIDE_PREVIEW_COMMENT = new BooleanSetting("revanced_hide_preview_comment", FALSE);
    public static final BooleanSetting HIDE_PREVIEW_COMMENT_TYPE = new BooleanSetting("revanced_hide_preview_comment_type", FALSE);
    public static final BooleanSetting HIDE_PREVIEW_COMMENT_OLD_METHOD = new BooleanSetting("revanced_hide_preview_comment_old_method", FALSE);
    public static final BooleanSetting HIDE_PREVIEW_COMMENT_NEW_METHOD = new BooleanSetting("revanced_hide_preview_comment_new_method", FALSE);


    // Flyout Panel

    // Feed Flyout Panel
    public static final BooleanSetting HIDE_FEED_FLYOUT_PANEL = new BooleanSetting("revanced_hide_feed_flyout_panel", FALSE);
    public static final StringSetting HIDE_FEED_FLYOUT_PANEL_FILTER_STRINGS = new StringSetting("revanced_hide_feed_flyout_panel_filter_strings", "", true, parent(HIDE_FEED_FLYOUT_PANEL));

    // Player Flyout Panel
    public static final BooleanSetting CHANGE_PLAYER_FLYOUT_PANEL_TOGGLE = new BooleanSetting("revanced_change_player_flyout_panel_toggle", TRUE, true);
    public static final BooleanSetting ENABLE_OLD_QUALITY_LAYOUT = new BooleanSetting("revanced_enable_old_quality_layout", TRUE, true);
    public static final BooleanSetting HIDE_PLAYER_FLYOUT_PANEL_AUDIO_TRACK = new BooleanSetting("revanced_hide_player_flyout_panel_audio_track", FALSE);
    public static final BooleanSetting HIDE_PLAYER_FLYOUT_PANEL_CAPTIONS = new BooleanSetting("revanced_hide_player_flyout_panel_captions", FALSE);
    public static final BooleanSetting HIDE_PLAYER_FLYOUT_PANEL_CAPTIONS_FOOTER = new BooleanSetting("revanced_hide_player_flyout_panel_captions_footer", FALSE, true);
    public static final BooleanSetting HIDE_PLAYER_FLYOUT_PANEL_LOCK_SCREEN = new BooleanSetting("revanced_hide_player_flyout_panel_lock_screen", FALSE);
    public static final BooleanSetting HIDE_PLAYER_FLYOUT_PANEL_MORE = new BooleanSetting("revanced_hide_player_flyout_panel_more_info", FALSE);
    public static final BooleanSetting HIDE_PLAYER_FLYOUT_PANEL_PLAYBACK_SPEED = new BooleanSetting("revanced_hide_player_flyout_panel_playback_speed", FALSE);
    public static final BooleanSetting HIDE_PLAYER_FLYOUT_PANEL_QUALITY_FOOTER = new BooleanSetting("revanced_hide_player_flyout_panel_quality_footer", FALSE, true);
    public static final BooleanSetting HIDE_PLAYER_FLYOUT_PANEL_REPORT = new BooleanSetting("revanced_hide_player_flyout_panel_report", TRUE);

    // Player Flyout Panel (Additional settings)
    public static final BooleanSetting HIDE_PLAYER_FLYOUT_PANEL_ADDITIONAL_SETTINGS = new BooleanSetting("revanced_hide_player_flyout_panel_additional_settings", FALSE);
    public static final BooleanSetting HIDE_PLAYER_FLYOUT_PANEL_AMBIENT = new BooleanSetting("revanced_hide_player_flyout_panel_ambient_mode", FALSE);
    public static final BooleanSetting HIDE_PLAYER_FLYOUT_PANEL_HELP = new BooleanSetting("revanced_hide_player_flyout_panel_help", TRUE);
    public static final BooleanSetting HIDE_PLAYER_FLYOUT_PANEL_LOOP = new BooleanSetting("revanced_hide_player_flyout_panel_loop_video", FALSE);
    public static final BooleanSetting HIDE_PLAYER_FLYOUT_PANEL_PREMIUM_CONTROLS = new BooleanSetting("revanced_hide_player_flyout_panel_premium_controls", TRUE);
    public static final BooleanSetting HIDE_PLAYER_FLYOUT_PANEL_STABLE_VOLUME = new BooleanSetting("revanced_hide_player_flyout_panel_stable_volume", FALSE);
    public static final BooleanSetting HIDE_PLAYER_FLYOUT_PANEL_STATS_FOR_NERDS = new BooleanSetting("revanced_hide_player_flyout_panel_stats_for_nerds", TRUE);
    public static final BooleanSetting HIDE_PLAYER_FLYOUT_PANEL_WATCH_IN_VR = new BooleanSetting("revanced_hide_player_flyout_panel_watch_in_vr", TRUE);
    public static final BooleanSetting HIDE_PLAYER_FLYOUT_PANEL_YT_MUSIC = new BooleanSetting("revanced_hide_player_flyout_panel_listen_with_youtube_music", TRUE);


    // Fullscreen
    public static final BooleanSetting DISABLE_AMBIENT_MODE_IN_FULLSCREEN = new BooleanSetting("revanced_disable_ambient_mode_in_fullscreen", FALSE, true);
    public static final BooleanSetting HIDE_AUTOPLAY_PREVIEW = new BooleanSetting("revanced_hide_autoplay_preview", FALSE, true);
    public static final BooleanSetting HIDE_END_SCREEN_OVERLAY = new BooleanSetting("revanced_hide_end_screen_overlay", FALSE, true);
    public static final BooleanSetting HIDE_FULLSCREEN_PANELS = new BooleanSetting("revanced_hide_fullscreen_panels", FALSE, true);
    public static final BooleanSetting SHOW_FULLSCREEN_TITLE = new BooleanSetting("revanced_show_fullscreen_title", TRUE, true, parent(HIDE_FULLSCREEN_PANELS));

    // Quick Actions
    public static final BooleanSetting HIDE_QUICK_ACTIONS = new BooleanSetting("revanced_hide_quick_actions", FALSE, true);
    public static final BooleanSetting HIDE_QUICK_ACTIONS_COMMENT_BUTTON = new BooleanSetting("revanced_hide_quick_actions_comment", FALSE);
    public static final BooleanSetting HIDE_QUICK_ACTIONS_DISLIKE_BUTTON = new BooleanSetting("revanced_hide_quick_actions_dislike", FALSE);
    public static final BooleanSetting HIDE_QUICK_ACTIONS_LIKE_BUTTON = new BooleanSetting("revanced_hide_quick_actions_like", FALSE);
    public static final BooleanSetting HIDE_QUICK_ACTIONS_LIVE_CHAT_BUTTON = new BooleanSetting("revanced_hide_quick_actions_live_chat", FALSE);
    public static final BooleanSetting HIDE_QUICK_ACTIONS_MORE_BUTTON = new BooleanSetting("revanced_hide_quick_actions_more", FALSE);
    public static final BooleanSetting HIDE_QUICK_ACTIONS_OPEN_MIX_PLAYLIST_BUTTON = new BooleanSetting("revanced_hide_quick_actions_open_mix_playlist", FALSE);
    public static final BooleanSetting HIDE_QUICK_ACTIONS_OPEN_PLAYLIST_BUTTON = new BooleanSetting("revanced_hide_quick_actions_open_playlist", FALSE);
    public static final BooleanSetting HIDE_QUICK_ACTIONS_RELATED_VIDEO = new BooleanSetting("revanced_hide_quick_actions_related_videos", FALSE);
    public static final BooleanSetting HIDE_QUICK_ACTIONS_SAVE_TO_PLAYLIST_BUTTON = new BooleanSetting("revanced_hide_quick_actions_save_to_playlist", FALSE);
    public static final BooleanSetting HIDE_QUICK_ACTIONS_SHARE_BUTTON = new BooleanSetting("revanced_hide_quick_actions_share", FALSE);
    public static final IntegerSetting QUICK_ACTIONS_MARGIN_TOP = new IntegerSetting("revanced_quick_actions_margin_top", 12, true);

    // Experimental Flags
    public static final BooleanSetting DISABLE_LANDSCAPE_MODE = new BooleanSetting("revanced_disable_landscape_mode", FALSE, true);
    public static final BooleanSetting ENABLE_COMPACT_CONTROLS_OVERLAY = new BooleanSetting("revanced_enable_compact_controls_overlay", FALSE, true);
    public static final BooleanSetting FORCE_FULLSCREEN = new BooleanSetting("revanced_force_fullscreen", FALSE, true);
    public static final BooleanSetting KEEP_LANDSCAPE_MODE = new BooleanSetting("revanced_keep_landscape_mode", FALSE, true);
    public static final LongSetting KEEP_LANDSCAPE_MODE_TIMEOUT = new LongSetting("revanced_keep_landscape_mode_timeout", 3000L, true);


    // General
    public static final StringSetting CHANGE_START_PAGE = new StringSetting("revanced_change_start_page", "", true);
    public static final BooleanSetting DISABLE_AUTO_CAPTIONS = new BooleanSetting("revanced_disable_auto_captions", FALSE, true);
    public static final BooleanSetting ENABLE_GRADIENT_LOADING_SCREEN = new BooleanSetting("revanced_enable_gradient_loading_screen", FALSE, true);
    public static final BooleanSetting ENABLE_SONG_SEARCH = new BooleanSetting("revanced_enable_song_search", TRUE, true);
    public static final BooleanSetting ENABLE_TABLET_MINI_PLAYER = new BooleanSetting("revanced_enable_tablet_mini_player", FALSE, true);
    public static final BooleanSetting ENABLE_WIDE_SEARCH_BAR = new BooleanSetting("revanced_enable_wide_search_bar", FALSE, true);
    public static final BooleanSetting ENABLE_WIDE_SEARCH_BAR_IN_YOU_TAB = new BooleanSetting("revanced_enable_wide_search_bar_in_you_tab", FALSE, true);
    public static final BooleanSetting HIDE_ACCOUNT_MENU = new BooleanSetting("revanced_hide_account_menu", FALSE);
    public static final StringSetting HIDE_ACCOUNT_MENU_FILTER_STRINGS = new StringSetting("revanced_hide_account_menu_filter_strings", "", true, parent(HIDE_ACCOUNT_MENU));
    public static final BooleanSetting HIDE_AUTO_PLAYER_POPUP_PANELS = new BooleanSetting("revanced_hide_auto_player_popup_panels", TRUE, true);
    public static final BooleanSetting HIDE_CAST_BUTTON = new BooleanSetting("revanced_hide_cast_button", TRUE, true);
    public static final BooleanSetting HIDE_CATEGORY_BAR_IN_FEED = new BooleanSetting("revanced_hide_category_bar_in_feed", FALSE, true);
    public static final BooleanSetting HIDE_CATEGORY_BAR_IN_RELATED_VIDEO = new BooleanSetting("revanced_hide_category_bar_in_related_video", FALSE, true);
    public static final BooleanSetting HIDE_CATEGORY_BAR_IN_SEARCH_RESULTS = new BooleanSetting("revanced_hide_category_bar_in_search_results", FALSE, true);
    public static final BooleanSetting HIDE_CHANNEL_LIST_SUBMENU = new BooleanSetting("revanced_hide_channel_list_submenu", FALSE, true);
    public static final BooleanSetting HIDE_CROWDFUNDING_BOX = new BooleanSetting("revanced_hide_crowdfunding_box", TRUE, true);
    public static final BooleanSetting HIDE_FLOATING_MICROPHONE = new BooleanSetting("revanced_hide_floating_microphone", TRUE, true);
    public static final BooleanSetting HIDE_HANDLE = new BooleanSetting("revanced_hide_handle", TRUE, true);
    public static final BooleanSetting HIDE_LATEST_VIDEOS_BUTTON = new BooleanSetting("revanced_hide_latest_videos_button", TRUE);
    public static final BooleanSetting HIDE_LOAD_MORE_BUTTON = new BooleanSetting("revanced_hide_load_more_button", TRUE, true);
    public static final BooleanSetting HIDE_MIX_PLAYLISTS = new BooleanSetting("revanced_hide_mix_playlists", FALSE);
    public static final BooleanSetting HIDE_SEARCH_TERM_THUMBNAIL = new BooleanSetting("revanced_hide_search_term_thumbnail", FALSE);
    public static final BooleanSetting HIDE_SNACK_BAR = new BooleanSetting("revanced_hide_snack_bar", FALSE);
    public static final BooleanSetting HIDE_SUGGESTIONS_SHELF = new BooleanSetting("revanced_hide_suggestions_shelf", FALSE, true);
    public static final BooleanSetting HIDE_TOOLBAR_CREATE_NOTIFICATION_BUTTON = new BooleanSetting("revanced_hide_toolbar_create_notification_button", FALSE, true);
    public static final BooleanSetting HIDE_TRENDING_SEARCHES = new BooleanSetting("revanced_hide_trending_searches", TRUE);
    public static final BooleanSetting REMOVE_VIEWER_DISCRETION_DIALOG = new BooleanSetting("revanced_remove_viewer_discretion_dialog", FALSE);

    // Layout
    public static final BooleanSetting CUSTOM_FILTER = new BooleanSetting("revanced_custom_filter", FALSE);
    public static final StringSetting CUSTOM_FILTER_STRINGS = new StringSetting("revanced_custom_filter_strings", "", true, parent(CUSTOM_FILTER));
    public static final BooleanSetting HIDE_ALBUM_CARDS = new BooleanSetting("revanced_hide_album_card", TRUE);
    public static final BooleanSetting HIDE_CHIPS_SHELF = new BooleanSetting("revanced_hide_chips_shelf", TRUE);
    public static final BooleanSetting HIDE_COMMUNITY_POSTS_HOME = new BooleanSetting("revanced_hide_community_posts_home", TRUE);
    public static final BooleanSetting HIDE_COMMUNITY_POSTS_SUBSCRIPTIONS = new BooleanSetting("revanced_hide_community_posts_subscriptions", FALSE);
    public static final BooleanSetting HIDE_EXPANDABLE_CHIP = new BooleanSetting("revanced_hide_expandable_chip", TRUE);
    public static final BooleanSetting HIDE_FEED_SURVEY = new BooleanSetting("revanced_hide_feed_survey", TRUE);
    public static final BooleanSetting HIDE_GRAY_DESCRIPTION = new BooleanSetting("revanced_hide_gray_description", TRUE);
    public static final BooleanSetting HIDE_GRAY_SEPARATOR = new BooleanSetting("revanced_hide_gray_separator", TRUE);
    public static final BooleanSetting HIDE_INFO_PANEL = new BooleanSetting("revanced_hide_info_panel", TRUE);
    public static final BooleanSetting HIDE_NOTIFY_ME_BUTTON = new BooleanSetting("revanced_hide_notify_me_button", FALSE);
    public static final BooleanSetting HIDE_LATEST_POSTS = new BooleanSetting("revanced_hide_latest_posts", TRUE);
    public static final BooleanSetting HIDE_MEDICAL_PANEL = new BooleanSetting("revanced_hide_medical_panel", TRUE);
    public static final BooleanSetting HIDE_MOVIE_SHELF = new BooleanSetting("revanced_hide_movie_shelf", FALSE);
    public static final BooleanSetting HIDE_SEARCH_BAR = new BooleanSetting("revanced_hide_search_bar", FALSE);
    public static final BooleanSetting HIDE_TICKET_SHELF = new BooleanSetting("revanced_hide_ticket_shelf", TRUE);
    public static final BooleanSetting HIDE_TIMED_REACTIONS = new BooleanSetting("revanced_hide_timed_reactions", TRUE);
    // Experimental Flags
    public static final BooleanSetting HIDE_VIDEO_WITH_GRAY_DESCRIPTION = new BooleanSetting("revanced_hide_video_with_gray_description", FALSE, true);
    public static final BooleanSetting HIDE_VIDEO_WITH_LOW_VIEW = new BooleanSetting("revanced_hide_video_with_low_view", FALSE, true);

    // Channel Profile
    public static final BooleanSetting HIDE_BROWSE_STORE_BUTTON = new BooleanSetting("revanced_hide_browse_store_button", TRUE);
    public static final BooleanSetting HIDE_CHANNEL_MEMBER_SHELF = new BooleanSetting("revanced_hide_channel_member_shelf", TRUE);
    public static final BooleanSetting HIDE_CHANNEL_PROFILE_LINKS = new BooleanSetting("revanced_hide_channel_profile_links", TRUE);
    public static final BooleanSetting HIDE_FOR_YOU_SHELF = new BooleanSetting("revanced_hide_for_you_shelf", TRUE);
    public static final BooleanSetting HIDE_STORE_TAB = new BooleanSetting("revanced_hide_store_tab", TRUE);

    // Description
    public static final BooleanSetting ALWAYS_EXPAND_PANEL = new BooleanSetting("revanced_always_expand_panel", FALSE, true);
    public static final BooleanSetting DISABLE_DESCRIPTION_INTERACTION = new BooleanSetting("revanced_disable_description_interaction", FALSE, true);
    public static final BooleanSetting HIDE_CHAPTERS = new BooleanSetting("revanced_hide_chapters", FALSE);
    public static final BooleanSetting HIDE_INFO_CARDS_SECTION = new BooleanSetting("revanced_hide_info_cards_section", FALSE);
    public static final BooleanSetting HIDE_GAME_SECTION = new BooleanSetting("revanced_hide_game_section", FALSE);
    public static final BooleanSetting HIDE_MUSIC_SECTION = new BooleanSetting("revanced_hide_music_section", FALSE);
    public static final BooleanSetting HIDE_PLACE_SECTION = new BooleanSetting("revanced_hide_place_section", FALSE);
    public static final BooleanSetting HIDE_PODCAST_SECTION = new BooleanSetting("revanced_hide_podcast_section", FALSE);
    public static final BooleanSetting HIDE_SHOPPING_LINKS = new BooleanSetting("revanced_hide_shopping_links", TRUE);
    public static final BooleanSetting HIDE_TRANSCIPT_SECTION = new BooleanSetting("revanced_hide_transcript_section", FALSE);


    // Misc
    public static final BooleanSetting BYPASS_AMBIENT_MODE_RESTRICTIONS = new BooleanSetting("revanced_bypass_ambient_mode_restrictions", FALSE);
    public static final BooleanSetting DISABLE_AMBIENT_MODE = new BooleanSetting("revanced_disable_ambient_mode", FALSE, true);
    public static final BooleanSetting ENABLE_EXTERNAL_BROWSER = new BooleanSetting("revanced_enable_external_browser", TRUE, true);
    public static final BooleanSetting ENABLE_LANGUAGE_SWITCH = new BooleanSetting("revanced_enable_language_switch", TRUE, true);
    public static final BooleanSetting ENABLE_NEW_SPLASH_ANIMATION = new BooleanSetting("revanced_enable_new_splash_animation", TRUE, true);
    public static final BooleanSetting ENABLE_OPEN_LINKS_DIRECTLY = new BooleanSetting("revanced_enable_open_links_directly", TRUE);
    public static final IntegerSetting DOUBLE_BACK_TIMEOUT = new IntegerSetting("revanced_double_back_timeout", 2, true);
    public static final BooleanSetting SANITIZE_SHARING_LINKS = new BooleanSetting("revanced_sanitize_sharing_links", true, true);

    // Experimental Flags
    public static final BooleanSetting DISABLE_QUIC_PROTOCOL = new BooleanSetting("revanced_disable_quic_protocol", FALSE, true);
    public static final BooleanSetting ENABLE_OPUS_CODEC = new BooleanSetting("revanced_enable_opus_codec", FALSE, true);
    public static final BooleanSetting ENABLE_PHONE_LAYOUT = new BooleanSetting("revanced_enable_phone_layout", FALSE, true);
    public static final BooleanSetting ENABLE_TABLET_LAYOUT = new BooleanSetting("revanced_enable_tablet_layout", FALSE, true);
    public static final BooleanSetting ENABLE_VIDEO_CODEC = new BooleanSetting("revanced_enable_video_codec", FALSE, true);
    public static final BooleanSetting ENABLE_VIDEO_CODEC_TYPE = new BooleanSetting("revanced_enable_video_codec_type", FALSE, true);
    public static final BooleanSetting SPOOF_APP_VERSION = new BooleanSetting("revanced_spoof_app_version", FALSE, true);
    public static final StringSetting SPOOF_APP_VERSION_TARGET = new StringSetting("revanced_spoof_app_version_target", "18.17.43", true, parent(SPOOF_APP_VERSION));
    public static final BooleanSetting SPOOF_DEVICE_DIMENSIONS = new BooleanSetting("revanced_spoof_device_dimensions", FALSE, true);
    public static final BooleanSetting SPOOF_PLAYER_PARAMETER = new BooleanSetting("revanced_spoof_player_parameter", TRUE, true);
    public static final BooleanSetting SPOOF_PLAYER_PARAMETER_IN_FEED = new BooleanSetting("revanced_spoof_player_parameter_in_feed", FALSE, true);


    // Navigation
    public static final BooleanSetting ENABLE_TABLET_NAVIGATION_BAR = new BooleanSetting("revanced_enable_tablet_navigation_bar", FALSE, true);
    public static final BooleanSetting HIDE_CREATE_BUTTON = new BooleanSetting("revanced_hide_create_button", TRUE, true);
    public static final BooleanSetting HIDE_HOME_BUTTON = new BooleanSetting("revanced_hide_home_button", FALSE, true);
    public static final BooleanSetting HIDE_LIBRARY_BUTTON = new BooleanSetting("revanced_hide_library_button", FALSE, true);
    public static final BooleanSetting HIDE_NAVIGATION_LABEL = new BooleanSetting("revanced_hide_navigation_label", FALSE, true);
    public static final BooleanSetting HIDE_NOTIFICATIONS_BUTTON = new BooleanSetting("revanced_hide_notifications_button", FALSE, true);
    public static final BooleanSetting HIDE_SHORTS_BUTTON = new BooleanSetting("revanced_hide_shorts_button", FALSE, true);
    public static final BooleanSetting HIDE_SUBSCRIPTIONS_BUTTON = new BooleanSetting("revanced_hide_subscriptions_button", FALSE, true);
    public static final BooleanSetting SWITCH_CREATE_NOTIFICATION = new BooleanSetting("revanced_switching_create_notification", TRUE, true);


    // Overlay Button
    public static final BooleanSetting ALWAYS_REPEAT = new BooleanSetting("revanced_always_repeat", FALSE);
    public static final BooleanSetting HIDE_FULLSCREEN_BUTTON = new BooleanSetting("revanced_hide_fullscreen_button", FALSE, true);
    public static final BooleanSetting OVERLAY_BUTTON_ALWAYS_REPEAT = new BooleanSetting("revanced_overlay_button_always_repeat", FALSE);
    public static final BooleanSetting OVERLAY_BUTTON_COPY_VIDEO_URL = new BooleanSetting("revanced_overlay_button_copy_video_url", FALSE);
    public static final BooleanSetting OVERLAY_BUTTON_COPY_VIDEO_URL_TIMESTAMP = new BooleanSetting("revanced_overlay_button_copy_video_url_timestamp", FALSE);
    public static final BooleanSetting OVERLAY_BUTTON_EXTERNAL_DOWNLOADER = new BooleanSetting("revanced_overlay_button_external_downloader", FALSE);
    public static final BooleanSetting OVERLAY_BUTTON_SPEED_DIALOG = new BooleanSetting("revanced_overlay_button_speed_dialog", FALSE);
    public static final StringSetting EXTERNAL_DOWNLOADER_PACKAGE_NAME = new StringSetting("revanced_external_downloader_package_name", "com.deniscerri.ytdl", true);

    // Experimental Flags
    public static final BooleanSetting HOOK_DOWNLOAD_BUTTON = new BooleanSetting("revanced_hook_download_button", FALSE);


    // Player
    public static final IntegerSetting CUSTOM_PLAYER_OVERLAY_OPACITY = new IntegerSetting("revanced_custom_player_overlay_opacity", 100, true);
    public static final BooleanSetting DISABLE_SPEED_OVERLAY = new BooleanSetting("revanced_disable_speed_overlay", FALSE, true);
    public static final BooleanSetting HIDE_AUDIO_TRACK_BUTTON = new BooleanSetting("revanced_hide_audio_track_button", TRUE);
    public static final BooleanSetting HIDE_AUTOPLAY_BUTTON = new BooleanSetting("revanced_hide_autoplay_button", TRUE, true);
    public static final BooleanSetting HIDE_CAPTIONS_BUTTON = new BooleanSetting("revanced_hide_captions_button", FALSE, true);
    public static final BooleanSetting HIDE_CHANNEL_WATERMARK = new BooleanSetting("revanced_hide_channel_watermark", TRUE);
    public static final BooleanSetting HIDE_COLLAPSE_BUTTON = new BooleanSetting("revanced_hide_collapse_button", FALSE);
    public static final BooleanSetting HIDE_END_SCREEN_CARDS = new BooleanSetting("revanced_hide_end_screen_cards", FALSE, true);
    public static final BooleanSetting HIDE_INFO_CARDS = new BooleanSetting("revanced_hide_info_cards", FALSE, true);
    public static final BooleanSetting HIDE_PREVIOUS_NEXT_BUTTON = new BooleanSetting("revanced_hide_previous_next_button", FALSE);
    public static final BooleanSetting HIDE_SEEK_MESSAGE = new BooleanSetting("revanced_hide_seek_message", FALSE, true);
    public static final BooleanSetting HIDE_SEEK_UNDO_MESSAGE = new BooleanSetting("revanced_hide_seek_undo_message", FALSE, true);
    public static final BooleanSetting HIDE_SUGGESTED_ACTION = new BooleanSetting("revanced_hide_suggested_actions", TRUE, true);
    public static final BooleanSetting HIDE_YOUTUBE_MUSIC_BUTTON = new BooleanSetting("revanced_hide_youtube_music_button", FALSE);

    // Experimental Flags
    public static final BooleanSetting HIDE_FILMSTRIP_OVERLAY = new BooleanSetting("revanced_hide_filmstrip_overlay", FALSE, true);
    @Deprecated
    public static final BooleanSetting HIDE_SUGGESTED_VIDEO_END_SCREEN = new BooleanSetting("revanced_hide_suggested_video_end_screen", FALSE);

    // Haptic Feedback
    public static final BooleanSetting DISABLE_HAPTIC_FEEDBACK_CHAPTERS = new BooleanSetting("revanced_disable_haptic_feedback_chapters", FALSE);
    public static final BooleanSetting DISABLE_HAPTIC_FEEDBACK_SCRUBBING = new BooleanSetting("revanced_disable_haptic_feedback_scrubbing", FALSE);
    public static final BooleanSetting DISABLE_HAPTIC_FEEDBACK_SEEK = new BooleanSetting("revanced_disable_haptic_feedback_seek", FALSE);
    public static final BooleanSetting DISABLE_HAPTIC_FEEDBACK_SEEK_UNDO = new BooleanSetting("revanced_disable_haptic_feedback_seek_undo", FALSE);
    public static final BooleanSetting DISABLE_HAPTIC_FEEDBACK_ZOOM = new BooleanSetting("revanced_disable_haptic_feedback_zoom", FALSE);


    // Seekbar
    public static final BooleanSetting APPEND_TIME_STAMP_INFORMATION = new BooleanSetting("revanced_append_time_stamp_information", TRUE);
    public static final BooleanSetting APPEND_TIME_STAMP_INFORMATION_TYPE = new BooleanSetting("revanced_append_time_stamp_information_type", TRUE);
    public static final BooleanSetting ENABLE_CUSTOM_SEEKBAR_COLOR = new BooleanSetting("revanced_enable_custom_seekbar_color", FALSE, true);
    public static final StringSetting ENABLE_CUSTOM_SEEKBAR_COLOR_VALUE = new StringSetting("revanced_custom_seekbar_color_value", "#FF0000", true, parent(ENABLE_CUSTOM_SEEKBAR_COLOR));
    public static final BooleanSetting ENABLE_SEEKBAR_TAPPING = new BooleanSetting("revanced_enable_seekbar_tapping", TRUE);
    public static final BooleanSetting ENABLE_NEW_THUMBNAIL_PREVIEW = new BooleanSetting("revanced_enable_new_thumbnail_preview", FALSE, true);
    public static final BooleanSetting HIDE_SEEKBAR = new BooleanSetting("revanced_hide_seekbar", FALSE, true);
    public static final BooleanSetting HIDE_SEEKBAR_THUMBNAIL = new BooleanSetting("revanced_hide_seekbar_thumbnail", FALSE);
    public static final BooleanSetting HIDE_TIME_STAMP = new BooleanSetting("revanced_hide_time_stamp", FALSE, true);


    // Shorts
    public static final IntegerSetting CHANGE_SHORTS_REPEAT_STATE = new IntegerSetting("revanced_change_shorts_repeat_state", 0);
    public static final BooleanSetting DISABLE_RESUMING_SHORTS_PLAYER = new BooleanSetting("revanced_disable_resuming_shorts_player", TRUE);
    public static final BooleanSetting HIDE_SHORTS_PLAYER_COMMENTS_BUTTON = new BooleanSetting("revanced_hide_shorts_player_comments_button", FALSE);
    public static final BooleanSetting HIDE_SHORTS_PLAYER_DISLIKE_BUTTON = new BooleanSetting("revanced_hide_shorts_player_dislike_button", FALSE);
    public static final BooleanSetting HIDE_SHORTS_PLAYER_INFO_PANEL = new BooleanSetting("revanced_hide_shorts_player_info_panel", TRUE);
    public static final BooleanSetting HIDE_SHORTS_PLAYER_JOIN_BUTTON = new BooleanSetting("revanced_hide_shorts_player_join_button", TRUE);
    public static final BooleanSetting HIDE_SHORTS_PLAYER_LIKE_BUTTON = new BooleanSetting("revanced_hide_shorts_player_like_button", FALSE);
    public static final BooleanSetting HIDE_SHORTS_PLAYER_PAID_PROMOTION = new BooleanSetting("revanced_hide_shorts_player_paid_promotion_banner", TRUE);
    public static final BooleanSetting HIDE_SHORTS_PLAYER_REMIX_BUTTON = new BooleanSetting("revanced_hide_shorts_player_remix_button", TRUE);
    public static final BooleanSetting HIDE_SHORTS_PLAYER_SHARE_BUTTON = new BooleanSetting("revanced_hide_shorts_player_share_button", FALSE);
    public static final BooleanSetting HIDE_SHORTS_PLAYER_SUBSCRIPTIONS_BUTTON = new BooleanSetting("revanced_hide_shorts_player_subscriptions_button", TRUE);
    public static final BooleanSetting HIDE_SHORTS_PLAYER_THANKS_BUTTON = new BooleanSetting("revanced_hide_shorts_player_thanks_button", TRUE);
    public static final BooleanSetting HIDE_SHORTS_PLAYER_PIVOT_BUTTON = new BooleanSetting("revanced_hide_shorts_player_pivot_button", TRUE);
    public static final BooleanSetting HIDE_SHORTS_TOOLBAR_BANNER = new BooleanSetting("revanced_hide_shorts_toolbar_banner", FALSE, true);
    public static final BooleanSetting HIDE_SHORTS_TOOLBAR_CAMERA_BUTTON = new BooleanSetting("revanced_hide_shorts_toolbar_camera_button", FALSE, true);
    public static final BooleanSetting HIDE_SHORTS_TOOLBAR_MENU_BUTTON = new BooleanSetting("revanced_hide_shorts_toolbar_menu_button", FALSE, true);
    public static final BooleanSetting HIDE_SHORTS_TOOLBAR_SEARCH_BUTTON = new BooleanSetting("revanced_hide_shorts_toolbar_search_button", FALSE, true);
    public static final BooleanSetting HIDE_SHORTS_SHELF = new BooleanSetting("revanced_hide_shorts_shelf", TRUE);
    public static final BooleanSetting HIDE_SHORTS_SHELF_EXCEPTION_HISTORY = new BooleanSetting("revanced_hide_shorts_shelf_exception_history", FALSE);

    // Experimental Flags
    public static final BooleanSetting HIDE_SHORTS_PLAYER_NAVIGATION_BAR = new BooleanSetting("revanced_hide_shorts_player_navigation_bar", FALSE, true);


    // Swipe controls
    public static final BooleanSetting ENABLE_SWIPE_BRIGHTNESS = new BooleanSetting("revanced_enable_swipe_brightness", TRUE, true);
    public static final BooleanSetting ENABLE_SWIPE_VOLUME = new BooleanSetting("revanced_enable_swipe_volume", TRUE, true);
    public static final BooleanSetting ENABLE_SWIPE_AUTO_BRIGHTNESS = new BooleanSetting("revanced_enable_swipe_auto_brightness", FALSE, parent(ENABLE_SWIPE_BRIGHTNESS));
    public static final BooleanSetting ENABLE_SWIPE_PRESS_TO_ENGAGE = new BooleanSetting("revanced_enable_swipe_press_to_engage", FALSE, true, parentsAny(ENABLE_SWIPE_BRIGHTNESS, ENABLE_SWIPE_VOLUME));
    public static final BooleanSetting ENABLE_SWIPE_HAPTIC_FEEDBACK = new BooleanSetting("revanced_enable_swipe_haptic_feedback", TRUE, true, parentsAny(ENABLE_SWIPE_BRIGHTNESS, ENABLE_SWIPE_VOLUME));
    public static final BooleanSetting SWIPE_LOCK_MODE = new BooleanSetting("revanced_swipe_gestures_lock_mode", FALSE, true, parentsAny(ENABLE_SWIPE_BRIGHTNESS, ENABLE_SWIPE_VOLUME));
    public static final IntegerSetting SWIPE_MAGNITUDE_THRESHOLD = new IntegerSetting("revanced_swipe_magnitude_threshold", 0, true, parentsAny(ENABLE_SWIPE_BRIGHTNESS, ENABLE_SWIPE_VOLUME));
    public static final IntegerSetting SWIPE_OVERLAY_BACKGROUND_ALPHA = new IntegerSetting("revanced_swipe_overlay_background_alpha", 127, true, parentsAny(ENABLE_SWIPE_BRIGHTNESS, ENABLE_SWIPE_VOLUME));
    public static final IntegerSetting SWIPE_OVERLAY_TEXT_SIZE = new IntegerSetting("revanced_swipe_overlay_text_size", 27, true, parentsAny(ENABLE_SWIPE_BRIGHTNESS, ENABLE_SWIPE_VOLUME));
    public static final LongSetting SWIPE_OVERLAY_TIMEOUT = new LongSetting("revanced_swipe_overlay_timeout", 500L, true, parentsAny(ENABLE_SWIPE_BRIGHTNESS, ENABLE_SWIPE_VOLUME));

    // Experimental Flags
    /** @noinspection DeprecatedIsStillUsed*/
    @Deprecated // Patch is obsolete and no longer works with 19.09+
    public static final BooleanSetting DISABLE_HDR_AUTO_BRIGHTNESS = new BooleanSetting("revanced_disable_hdr_auto_brightness", TRUE, true, parent(ENABLE_SWIPE_BRIGHTNESS));
    public static final BooleanSetting SWIPE_BRIGHTNESS_AUTO = new BooleanSetting("revanced_swipe_brightness_auto", TRUE);
    public static final FloatSetting SWIPE_BRIGHTNESS_VALUE = new FloatSetting("revanced_swipe_brightness_value", -1.0f, false, false);


    // Video
    public static final FloatSetting DEFAULT_PLAYBACK_SPEED = new FloatSetting("revanced_default_playback_speed", -2.0f);
    public static final IntegerSetting DEFAULT_VIDEO_QUALITY_MOBILE = new IntegerSetting("revanced_default_video_quality_mobile", -2);
    public static final IntegerSetting DEFAULT_VIDEO_QUALITY_WIFI = new IntegerSetting("revanced_default_video_quality_wifi", -2);
    public static final BooleanSetting DISABLE_HDR_VIDEO = new BooleanSetting("revanced_disable_hdr_video", FALSE, true);
    public static final BooleanSetting DISABLE_DEFAULT_PLAYBACK_SPEED_LIVE = new BooleanSetting("revanced_disable_default_playback_speed_live", TRUE);
    public static final BooleanSetting ENABLE_CUSTOM_PLAYBACK_SPEED = new BooleanSetting("revanced_enable_custom_playback_speed", FALSE, true);
    public static final BooleanSetting CUSTOM_PLAYBACK_SPEED_PANEL_TYPE = new BooleanSetting("revanced_custom_playback_speed_panel_type", FALSE, parent(ENABLE_CUSTOM_PLAYBACK_SPEED));
    public static final StringSetting CUSTOM_PLAYBACK_SPEEDS = new StringSetting("revanced_custom_playback_speeds", "0.25\n0.5\n0.75\n1.0\n1.25\n1.5\n1.75\n2.0\n2.25\n2.5", true, parent(ENABLE_CUSTOM_PLAYBACK_SPEED));
    public static final BooleanSetting ENABLE_SAVE_PLAYBACK_SPEED = new BooleanSetting("revanced_enable_save_playback_speed", TRUE);
    public static final BooleanSetting ENABLE_SAVE_VIDEO_QUALITY = new BooleanSetting("revanced_enable_save_video_quality", TRUE);
    // Experimental Flags
    public static final BooleanSetting ENABLE_DEFAULT_PLAYBACK_SPEED_SHORTS = new BooleanSetting("revanced_enable_default_playback_speed_shorts", FALSE);
    public static final BooleanSetting SKIP_PRELOADED_BUFFER = new BooleanSetting("revanced_skip_preloaded_buffer", FALSE);
    public static final BooleanSetting SKIP_PRELOADED_BUFFER_TOAST = new BooleanSetting("revanced_skip_preloaded_buffer_toast", TRUE);


    // Return YouTube Dislike
    public static final BooleanSetting RYD_ENABLED = new BooleanSetting("ryd_enabled", TRUE);
    public static final StringSetting RYD_USER_ID = new StringSetting("ryd_user_id", "");
    public static final BooleanSetting RYD_SHORTS = new BooleanSetting("ryd_shorts", TRUE, parent(RYD_ENABLED));
    public static final BooleanSetting RYD_DISLIKE_PERCENTAGE = new BooleanSetting("ryd_dislike_percentage", FALSE, parent(RYD_ENABLED));
    public static final BooleanSetting RYD_COMPACT_LAYOUT = new BooleanSetting("ryd_compact_layout", FALSE, parent(RYD_ENABLED));
    public static final BooleanSetting RYD_TOAST_ON_CONNECTION_ERROR = new BooleanSetting("ryd_toast_on_connection_error", FALSE, parent(RYD_ENABLED));


    // SponsorBlock
    public static final BooleanSetting SB_ENABLED = new BooleanSetting("sb_enabled", TRUE);
    /**
     * Do not use directly, instead use {@link SponsorBlockSettings}
     */
    public static final StringSetting SB_PRIVATE_USER_ID = new StringSetting("sb_private_user_id_Do_Not_Share", "");
    public static final IntegerSetting SB_CREATE_NEW_SEGMENT_STEP = new IntegerSetting("sb_create_new_segment_step", 150, parent(SB_ENABLED));
    public static final BooleanSetting SB_VOTING_BUTTON = new BooleanSetting("sb_voting_button", FALSE, parent(SB_ENABLED));
    public static final BooleanSetting SB_CREATE_NEW_SEGMENT = new BooleanSetting("sb_create_new_segment", FALSE, parent(SB_ENABLED));
    public static final BooleanSetting SB_COMPACT_SKIP_BUTTON = new BooleanSetting("sb_compact_skip_button", FALSE, parent(SB_ENABLED));
    public static final BooleanSetting SB_AUTO_HIDE_SKIP_BUTTON = new BooleanSetting("sb_auto_hide_skip_button", TRUE, parent(SB_ENABLED));
    public static final BooleanSetting SB_TOAST_ON_SKIP = new BooleanSetting("sb_toast_on_skip", TRUE, parent(SB_ENABLED));
    public static final BooleanSetting SB_TOAST_ON_CONNECTION_ERROR = new BooleanSetting("sb_toast_on_connection_error", FALSE, parent(SB_ENABLED));
    public static final BooleanSetting SB_TRACK_SKIP_COUNT = new BooleanSetting("sb_track_skip_count", TRUE, parent(SB_ENABLED));
    public static final FloatSetting SB_SEGMENT_MIN_DURATION = new FloatSetting("sb_min_segment_duration", 0F, parent(SB_ENABLED));
    public static final BooleanSetting SB_VIDEO_LENGTH_WITHOUT_SEGMENTS = new BooleanSetting("sb_video_length_without_segments", FALSE, parent(SB_ENABLED));
    public static final StringSetting SB_API_URL = new StringSetting("sb_api_url","https://sponsor.ajay.app");
    public static final BooleanSetting SB_USER_IS_VIP = new BooleanSetting("sb_user_is_vip", FALSE);
    public static final IntegerSetting SB_LOCAL_TIME_SAVED_NUMBER_SEGMENTS = new IntegerSetting("sb_local_time_saved_number_segments", 0);
    public static final LongSetting SB_LOCAL_TIME_SAVED_MILLISECONDS = new LongSetting("sb_local_time_saved_milliseconds", 0L);

    public static final StringSetting SB_CATEGORY_SPONSOR = new StringSetting("sb_sponsor", SKIP_AUTOMATICALLY.reVancedKeyValue);
    public static final StringSetting SB_CATEGORY_SPONSOR_COLOR = new StringSetting("sb_sponsor_color","#00D400");
    public static final StringSetting SB_CATEGORY_SELF_PROMO = new StringSetting("sb_selfpromo", SKIP_AUTOMATICALLY.reVancedKeyValue);
    public static final StringSetting SB_CATEGORY_SELF_PROMO_COLOR = new StringSetting("sb_selfpromo_color","#FFFF00");
    public static final StringSetting SB_CATEGORY_INTERACTION = new StringSetting("sb_interaction", SKIP_AUTOMATICALLY_ONCE.reVancedKeyValue);
    public static final StringSetting SB_CATEGORY_INTERACTION_COLOR = new StringSetting("sb_interaction_color","#CC00FF");
    public static final StringSetting SB_CATEGORY_HIGHLIGHT = new StringSetting("sb_highlight", MANUAL_SKIP.reVancedKeyValue);
    public static final StringSetting SB_CATEGORY_HIGHLIGHT_COLOR = new StringSetting("sb_highlight_color","#FF1684");
    public static final StringSetting SB_CATEGORY_INTRO = new StringSetting("sb_intro", SKIP_AUTOMATICALLY_ONCE.reVancedKeyValue);
    public static final StringSetting SB_CATEGORY_INTRO_COLOR = new StringSetting("sb_intro_color","#00FFFF");
    public static final StringSetting SB_CATEGORY_OUTRO = new StringSetting("sb_outro", SKIP_AUTOMATICALLY_ONCE.reVancedKeyValue);
    public static final StringSetting SB_CATEGORY_OUTRO_COLOR = new StringSetting("sb_outro_color","#0202ED");
    public static final StringSetting SB_CATEGORY_PREVIEW = new StringSetting("sb_preview", SKIP_AUTOMATICALLY_ONCE.reVancedKeyValue);
    public static final StringSetting SB_CATEGORY_PREVIEW_COLOR = new StringSetting("sb_preview_color","#008FD6");
    public static final StringSetting SB_CATEGORY_FILLER = new StringSetting("sb_filler", SKIP_AUTOMATICALLY_ONCE.reVancedKeyValue);
    public static final StringSetting SB_CATEGORY_FILLER_COLOR = new StringSetting("sb_filler_color","#7300FF");
    public static final StringSetting SB_CATEGORY_MUSIC_OFFTOPIC = new StringSetting("sb_music_offtopic", MANUAL_SKIP.reVancedKeyValue);
    public static final StringSetting SB_CATEGORY_MUSIC_OFFTOPIC_COLOR = new StringSetting("sb_music_offtopic_color","#FF9900");
    public static final StringSetting SB_CATEGORY_UNSUBMITTED = new StringSetting("sb_unsubmitted", SKIP_AUTOMATICALLY.reVancedKeyValue);
    public static final StringSetting SB_CATEGORY_UNSUBMITTED_COLOR = new StringSetting("sb_unsubmitted_color","#FFFFFF");

    // SB Setting not exported
    public static final LongSetting SB_LAST_VIP_CHECK = new LongSetting("sb_last_vip_check", 0L, false, false);
    public static final BooleanSetting SB_HIDE_EXPORT_WARNING = new BooleanSetting("sb_hide_export_warning", FALSE, false, false);
    public static final BooleanSetting SB_SEEN_GUIDELINES = new BooleanSetting("sb_seen_guidelines", FALSE, false, false);

    static {
        // region Migrationinitialized"
        // Categories were previously saved without a 'sb_' key prefix, so they need an additional adjustment.
        Set<Setting<?>> sbCategories = new HashSet<>(Arrays.asList(
                SB_CATEGORY_SPONSOR,
                SB_CATEGORY_SPONSOR_COLOR,
                SB_CATEGORY_SELF_PROMO,
                SB_CATEGORY_SELF_PROMO_COLOR,
                SB_CATEGORY_INTERACTION,
                SB_CATEGORY_INTERACTION_COLOR,
                SB_CATEGORY_HIGHLIGHT,
                SB_CATEGORY_HIGHLIGHT_COLOR,
                SB_CATEGORY_INTRO,
                SB_CATEGORY_INTRO_COLOR,
                SB_CATEGORY_OUTRO,
                SB_CATEGORY_OUTRO_COLOR,
                SB_CATEGORY_PREVIEW,
                SB_CATEGORY_PREVIEW_COLOR,
                SB_CATEGORY_FILLER,
                SB_CATEGORY_FILLER_COLOR,
                SB_CATEGORY_MUSIC_OFFTOPIC,
                SB_CATEGORY_MUSIC_OFFTOPIC_COLOR,
                SB_CATEGORY_UNSUBMITTED,
                SB_CATEGORY_UNSUBMITTED_COLOR));

        SharedPrefCategory ytPrefs = new SharedPrefCategory("youtube");
        SharedPrefCategory rydPrefs = new SharedPrefCategory("ryd");
        SharedPrefCategory sbPrefs = new SharedPrefCategory("sponsor-block");
        for (Setting<?> setting : Setting.allLoadedSettings()) {
            String key = setting.key;
            if (setting.key.startsWith("sb_")) {
                if (sbCategories.contains(setting)) {
                    key = key.substring(3); // Remove the "sb_" prefix, as old categories are saved without it.
                }
                migrateFromOldPreferences(sbPrefs, setting, key);
            } else if (setting.key.startsWith("ryd_")) {
                migrateFromOldPreferences(rydPrefs, setting, key);
            } else {
                migrateFromOldPreferences(ytPrefs, setting, key);
            }
        }
        // endregion
    }
}
