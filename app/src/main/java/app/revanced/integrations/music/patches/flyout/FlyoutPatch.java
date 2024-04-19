package app.revanced.integrations.music.patches.flyout;

import static app.revanced.integrations.shared.utils.ResourceUtils.getIdentifier;
import static app.revanced.integrations.shared.utils.StringRef.str;
import static app.revanced.integrations.shared.utils.Utils.clickView;
import static app.revanced.integrations.shared.utils.Utils.runOnMainThreadDelayed;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import app.revanced.integrations.music.settings.Settings;
import app.revanced.integrations.music.utils.VideoUtils;
import app.revanced.integrations.shared.utils.Logger;
import app.revanced.integrations.shared.utils.ResourceUtils.ResourceType;
import app.revanced.integrations.shared.utils.Utils;

@SuppressWarnings("unused")
public class FlyoutPatch {
    private static boolean lastMenuWasDismissQueue = false;

    private static final ColorFilter cf = new PorterDuffColorFilter(Color.parseColor("#ffffffff"), PorterDuff.Mode.SRC_ATOP);

    @SuppressLint("StaticFieldLeak")
    public static View touchOutSideView;

    public static int enableCompactDialog(int original) {
        if (!Settings.ENABLE_COMPACT_DIALOG.get())
            return original;

        return Math.max(original, 600);
    }

    public static boolean hideComponents(@Nullable Enum<?> flyoutPanelEnum) {
        if (flyoutPanelEnum == null)
            return false;

        final String flyoutPanelName = flyoutPanelEnum.name();

        Logger.printDebug(() -> flyoutPanelName);

        for (FlyoutPanelComponent component : FlyoutPanelComponent.values())
            if (component.name.equals(flyoutPanelName) && component.enabled)
                return true;

        return false;
    }

    public static void hideLikeDislikeContainer(View view) {
        Utils.hideViewBy0dpUnderCondition(
                Settings.HIDE_FLYOUT_PANEL_LIKE_DISLIKE,
                view
        );
    }

    public static void replaceComponents(@Nullable Enum<?> flyoutPanelEnum, @NonNull TextView textView, @NonNull ImageView imageView) {
        if (flyoutPanelEnum == null)
            return;

        final String enumString = flyoutPanelEnum.name();
        final boolean isDismissQue = enumString.equals("DISMISS_QUEUE");
        final boolean isReport = enumString.equals("FLAG");

        if (isDismissQue) {
            replaceDismissQueue(textView, imageView);
        } else if (isReport) {
            replaceReport(textView, imageView, lastMenuWasDismissQueue);
        }
        lastMenuWasDismissQueue = isDismissQue;
    }

    private static void replaceDismissQueue(@NonNull TextView textView, @NonNull ImageView imageView) {
        if (!Settings.REPLACE_FLYOUT_PANEL_DISMISS_QUEUE.get())
            return;

        if (!(textView.getParent() instanceof ViewGroup clickAbleArea))
            return;

        runOnMainThreadDelayed(() -> {
                    textView.setText(str("revanced_flyout_panel_watch_on_youtube"));
                    imageView.setImageResource(getIdentifier("yt_outline_youtube_logo_icon_vd_theme_24", ResourceType.DRAWABLE, clickAbleArea.getContext()));
                    clickAbleArea.setOnClickListener(viewGroup -> VideoUtils.openInYouTube());
                }, 0L
        );
    }

    private static void replaceReport(@NonNull TextView textView, @NonNull ImageView imageView, boolean wasDismissQueue) {
        if (!Settings.REPLACE_FLYOUT_PANEL_REPORT.get())
            return;

        if (Settings.REPLACE_FLYOUT_PANEL_REPORT_ONLY_PLAYER.get() && !wasDismissQueue)
            return;

        if (!(textView.getParent() instanceof ViewGroup clickAbleArea))
            return;

        runOnMainThreadDelayed(() -> {
                    textView.setText(str("playback_rate_title"));
                    imageView.setImageResource(getIdentifier("yt_outline_play_arrow_half_circle_black_24", ResourceType.DRAWABLE, clickAbleArea.getContext()));
                    imageView.setColorFilter(cf);
                    clickAbleArea.setOnClickListener(view -> {
                        clickView(touchOutSideView);
                        VideoUtils.showPlaybackSpeedFlyoutMenu();
                    });
                }, 0L
        );
    }

    private enum FlyoutPanelComponent {
        SAVE_EPISODE_FOR_LATER("BOOKMARK_BORDER", Settings.HIDE_FLYOUT_PANEL_SAVE_EPISODE_FOR_LATER.get()),
        SHUFFLE_PLAY("SHUFFLE", Settings.HIDE_FLYOUT_PANEL_SHUFFLE_PLAY.get()),
        RADIO("MIX", Settings.HIDE_FLYOUT_PANEL_START_RADIO.get()),
        SUBSCRIBE("SUBSCRIBE", Settings.HIDE_FLYOUT_PANEL_SUBSCRIBE.get()),
        EDIT_PLAYLIST("EDIT", Settings.HIDE_FLYOUT_PANEL_EDIT_PLAYLIST.get()),
        DELETE_PLAYLIST("DELETE", Settings.HIDE_FLYOUT_PANEL_DELETE_PLAYLIST.get()),
        PLAY_NEXT("QUEUE_PLAY_NEXT", Settings.HIDE_FLYOUT_PANEL_PLAY_NEXT.get()),
        ADD_TO_QUEUE("QUEUE_MUSIC", Settings.HIDE_FLYOUT_PANEL_ADD_TO_QUEUE.get()),
        SAVE_TO_LIBRARY("LIBRARY_ADD", Settings.HIDE_FLYOUT_PANEL_SAVE_TO_LIBRARY.get()),
        REMOVE_FROM_LIBRARY("LIBRARY_REMOVE", Settings.HIDE_FLYOUT_PANEL_REMOVE_FROM_LIBRARY.get()),
        REMOVE_FROM_PLAYLIST("REMOVE_FROM_PLAYLIST", Settings.HIDE_FLYOUT_PANEL_REMOVE_FROM_PLAYLIST.get()),
        DOWNLOAD("OFFLINE_DOWNLOAD", Settings.HIDE_FLYOUT_PANEL_DOWNLOAD.get()),
        SAVE_TO_PLAYLIST("ADD_TO_PLAYLIST", Settings.HIDE_FLYOUT_PANEL_SAVE_TO_PLAYLIST.get()),
        GO_TO_EPISODE("INFO", Settings.HIDE_FLYOUT_PANEL_GO_TO_EPISODE.get()),
        GO_TO_PODCAST("BROADCAST", Settings.HIDE_FLYOUT_PANEL_GO_TO_PODCAST.get()),
        GO_TO_ALBUM("ALBUM", Settings.HIDE_FLYOUT_PANEL_GO_TO_ALBUM.get()),
        GO_TO_ARTIST("ARTIST", Settings.HIDE_FLYOUT_PANEL_GO_TO_ARTIST.get()),
        VIEW_SONG_CREDIT("PEOPLE_GROUP", Settings.HIDE_FLYOUT_PANEL_VIEW_SONG_CREDIT.get()),
        SHARE("SHARE", Settings.HIDE_FLYOUT_PANEL_SHARE.get()),
        DISMISS_QUEUE("DISMISS_QUEUE", Settings.HIDE_FLYOUT_PANEL_DISMISS_QUEUE.get()),
        HELP("HELP_OUTLINE", Settings.HIDE_FLYOUT_PANEL_HELP.get()),
        REPORT("FLAG", Settings.HIDE_FLYOUT_PANEL_REPORT.get()),
        QUALITY("SETTINGS_MATERIAL", Settings.HIDE_FLYOUT_PANEL_QUALITY.get()),
        CAPTIONS("CAPTIONS", Settings.HIDE_FLYOUT_PANEL_CAPTIONS.get()),
        STATS_FOR_NERDS("PLANNER_REVIEW", Settings.HIDE_FLYOUT_PANEL_STATS_FOR_NERDS.get()),
        SLEEP_TIMER("MOON_Z", Settings.HIDE_FLYOUT_PANEL_SLEEP_TIMER.get());

        private final boolean enabled;
        private final String name;

        FlyoutPanelComponent(String name, boolean enabled) {
            this.enabled = enabled;
            this.name = name;
        }
    }
}
