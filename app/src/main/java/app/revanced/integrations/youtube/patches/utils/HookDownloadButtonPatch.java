package app.revanced.integrations.youtube.patches.utils;

import app.revanced.integrations.shared.utils.Logger;
import app.revanced.integrations.youtube.settings.Settings;
import app.revanced.integrations.youtube.utils.VideoUtils;

@SuppressWarnings("unused")
public class HookDownloadButtonPatch {

    /**
     * Injection point.
     * <p>
     * Called from the in app download hook,
     * for both the player action button (below the video)
     * and the 'Download video' flyout option for feed videos.
     * <p>
     * Appears to always be called from the main thread.
     */
    public static boolean inAppDownloadButtonOnClick(String videoId) {
        try {
            if (!Settings.HOOK_DOWNLOAD_BUTTON.get()) {
                return false;
            }
            if (videoId == null || videoId.isEmpty()) {
                return false;
            }
            VideoUtils.launchExternalDownloader(videoId);

            return true;
        } catch (Exception ex) {
            Logger.printException(() -> "inAppDownloadButtonOnClick failure", ex);
        }
        return false;
    }
}