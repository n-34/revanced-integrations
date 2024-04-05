package app.revanced.integrations.youtube.patches.video;

import static app.revanced.integrations.shared.utils.StringRef.str;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.lang.ref.WeakReference;
import java.lang.reflect.Method;
import java.util.Objects;

import app.revanced.integrations.shared.utils.Logger;
import app.revanced.integrations.shared.utils.Utils;
import app.revanced.integrations.youtube.settings.Settings;
import app.revanced.integrations.youtube.shared.VideoState;

/**
 * Hooking class for the current playing video.
 * @noinspection unused
 */
public final class VideoInformation {
    private static final float DEFAULT_YOUTUBE_PLAYBACK_SPEED = 1.0f;
    private static final String SEEK_METHOD_NAME = "seekTo";
    /**
     * Prefix present in all Short player parameters signature.
     */
    private static final String SHORTS_PLAYER_PARAMETERS = "8AEB";

    private static WeakReference<Object> playerControllerRef;
    private static Method seekMethod;

    @NonNull
    private static String videoId = "";

    private static long videoLength = 0;
    private static long videoTime = -1;

    @NonNull
    private static volatile String playerResponseVideoId = "";
    private static volatile boolean playerResponseVideoIdIsShort;
    private static volatile boolean videoIsLiveStream;
    private static volatile boolean videoIdIsShort;

    /**
     * The current playback speed
     */
    private static float playbackSpeed = DEFAULT_YOUTUBE_PLAYBACK_SPEED;

    /**
     * Injection point.
     *
     * @param playerController player controller object.
     */
    public static void initialize(@NonNull Object playerController) {
        try {
            playerControllerRef = new WeakReference<>(Objects.requireNonNull(playerController));
            videoLength = 0;
            videoTime = -1;

            seekMethod = playerController.getClass().getMethod(SEEK_METHOD_NAME, Long.TYPE);
            seekMethod.setAccessible(true);
        } catch (Exception ex) {
            Logger.printException(() -> "Failed to initialize", ex);
        }
    }

    /**
     * Seek on the current video.
     * Does not function for playback of Shorts.
     * <p>
     * Caution: If called from a videoTimeHook() callback,
     * this will cause a recursive call into the same videoTimeHook() callback.
     *
     * @param seekTime The seekTime to seek the video to.
     * @return true if the seek was successful.
     */
    public static boolean seekTo(final long seekTime) {
        Utils.verifyOnMainThread();
        try {
            final long videoTime = getVideoTime();
            final long videoLength = getVideoLength();

            // Prevent issues such as play/ pause button or autoplay not working.
            final long adjustedSeekTime = Math.min(seekTime, videoLength - 250);
            if (videoTime <= seekTime && videoTime >= adjustedSeekTime) {
                // Both the current video time and the seekTo are in the last 250ms of the video.
                // Ignore this seek call, otherwise if a video ends with multiple closely timed segments
                // then seeking here can create an infinite loop of skip attempts.
                Logger.printDebug(() -> "Ignoring seekTo call as video playback is almost finished. "
                        + " videoTime: " + videoTime + " videoLength: " + videoLength + " seekTo: " + seekTime);
                return false;
            }

            Logger.printDebug(() -> "Seeking to " + adjustedSeekTime);
            //noinspection DataFlowIssue
            return (Boolean) seekMethod.invoke(playerControllerRef.get(), adjustedSeekTime);
        } catch (Exception ex) {
            Logger.printException(() -> "Failed to seek", ex);
        }
        return false;
    }

    public static void seekToRelative(long millisecondsRelative) {
        seekTo(videoTime + millisecondsRelative);
    }

    public static void reloadVideo() {
        if (videoLength < 10000 || videoIsLiveStream)
            return;

        final long lastVideoTime = videoTime;
        final float playbackSpeed = getPlaybackSpeed();
        final long speedAdjustedTimeThreshold = (long) (playbackSpeed * 1000);
        seekTo(10000);
        seekTo(lastVideoTime + speedAdjustedTimeThreshold);

        if (!Settings.SKIP_PRELOADED_BUFFER_TOAST.get())
            return;

        Utils.showToastShort(str("revanced_skipped_preloaded_buffer"));
    }

    public static boolean videoEnded() {
        if (!Settings.ALWAYS_REPEAT.get())
            return false;

        Utils.runOnMainThreadDelayed(() -> seekTo(0), 0);

        return true;
    }

    /**
     * Id of the last video opened.  Includes Shorts.
     *
     * @return The id of the video, or an empty string if no videos have been opened yet.
     */
    @NonNull
    public static String getVideoId() {
        return videoId;
    }

    /**
     * Injection point.
     *
     * @param newlyLoadedVideoId id of the current video
     */
    public static void setVideoId(@NonNull String newlyLoadedVideoId) {
        if (videoId.equals(newlyLoadedVideoId))
            return;

        videoId = newlyLoadedVideoId;
    }

    public static boolean getLiveStreamState() {
        return videoIsLiveStream;
    }

    public static void setLiveStreamState(final String newlyLoadedVideoCpn, boolean newlyLoadedValue) {
        videoIsLiveStream = newlyLoadedValue;
    }

    /**
     * Differs from {@link #videoId} as this is the video id for the
     * last player response received, which may not be the last video opened.
     * <p>
     * If Shorts are loading the background, this commonly will be
     * different from the Short that is currently on screen.
     * <p>
     * For most use cases, you should instead use {@link #getVideoId()}.
     *
     * @return The id of the last video loaded, or an empty string if no videos have been loaded yet.
     */
    @NonNull
    public static String getPlayerResponseVideoId() {
        return playerResponseVideoId;
    }


    /**
     * @return If the last player response video id was a Short.
     * Includes Shorts shelf items appearing in the feed that are not opened.
     * @see #lastVideoIdIsShort()
     */
    public static boolean lastPlayerResponseIsShort() {
        return playerResponseVideoIdIsShort;
    }

    /**
     * @return If the last player response video id _that was opened_ was a Short.
     */
    public static boolean lastVideoIdIsShort() {
        return videoIdIsShort;
    }

    /**
     * @return If the player parameters are for a Short.
     */
    public static boolean playerParametersAreShort(@Nullable String playerParameter) {
        return playerParameter != null && playerParameter.startsWith(SHORTS_PLAYER_PARAMETERS);
    }

    /**
     * Injection point.
     */
    @Nullable
    public static String newPlayerResponseParameter(@NonNull String videoId, @Nullable String playerParameter, boolean isShortAndOpeningOrPlaying) {
        final boolean isShort = playerParametersAreShort(playerParameter);
        playerResponseVideoIdIsShort = isShort;
        if (!isShort || isShortAndOpeningOrPlaying) {
            if (videoIdIsShort != isShort) {
                videoIdIsShort = isShort;
            }
        }
        return playerParameter; // Return the original value since we are observing and not modifying.
    }

    /**
     * Injection point.  Called off the main thread.
     *
     * @param videoId The id of the last video loaded.
     */
    public static void setPlayerResponseVideoId(@NonNull String videoId, boolean isShortAndOpeningOrPlaying) {
        if (!playerResponseVideoId.equals(videoId)) {
            playerResponseVideoId = videoId;
        }
    }

    /**
     * Overrides the current playback speed.
     * <p>
     * <b> Used exclusively by {@link PlaybackSpeedPatch} </b>
     */
    public static void overridePlaybackSpeed(float speedOverride) {
        if (playbackSpeed != speedOverride) {
            Logger.printDebug(() -> "Overriding playback speed to: " + speedOverride);
            playbackSpeed = speedOverride;
        }
    }

    /**
     * @return The current playback speed.
     */
    public static float getPlaybackSpeed() {
        return playbackSpeed;
    }

    /**
     * Overrides the current playback speed.
     */
    public static void setPlaybackSpeed(float speedOverride) {
        playbackSpeed = speedOverride;
    }

    /**
     * Length of the current video playing.  Includes Shorts.
     *
     * @return The length of the video in milliseconds.
     * If the video is not yet loaded, or if the video is playing in the background with no video visible,
     * then this returns zero.
     */
    public static long getVideoLength() {
        return videoLength;
    }

    /**
     * Injection point.
     *
     * @param length The length of the video in milliseconds.
     */
    public static void setVideoLength(final long length) {
        videoLength = length;
    }

    /**
     * Playback time of the current video playing.  Includes Shorts.
     * <p>
     * Value will lag behind the actual playback time by a variable amount based on the playback speed.
     * <p>
     * If playback speed is 2.0x, this value may be up to 2000ms behind the actual playback time.
     * If playback speed is 1.0x, this value may be up to 1000ms behind the actual playback time.
     * If playback speed is 0.5x, this value may be up to 500ms behind the actual playback time.
     * Etc.
     *
     * @return The time of the video in milliseconds. -1 if not set yet.
     */
    public static long getVideoTime() {
        return videoTime;
    }

    /**
     * Injection point.
     * Called on the main thread every 1000ms.
     *
     * @param time The current playback time of the video in milliseconds.
     */
    public static void setVideoTime(final long time) {
        videoTime = time;
    }

    /**
     * @return If the playback is not at the end of the video.
     * <p>
     * If video is playing in the background with no video visible,
     * this always returns false (even if the video is not actually at the end).
     * <p>
     * This is equivalent to checking for {@link VideoState#ENDED},
     * but can give a more up to date result for code calling from some hooks.
     * @see VideoState
     */
    public static boolean isNotAtEndOfVideo() {
        return videoTime < videoLength && videoLength > 0;
    }
}
