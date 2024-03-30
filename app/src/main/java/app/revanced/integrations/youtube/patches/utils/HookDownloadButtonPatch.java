package app.revanced.integrations.youtube.patches.utils;

import static app.revanced.integrations.youtube.utils.VideoUtils.downloadVideo;

import app.revanced.integrations.shared.utils.Utils;
import app.revanced.integrations.youtube.settings.Settings;

@SuppressWarnings("unused")
public class HookDownloadButtonPatch {

    public static boolean shouldHookDownloadButton() {
        return Settings.HOOK_DOWNLOAD_BUTTON.get();
    }

    public static void startDownloadActivity() {
        downloadVideo(Utils.getContext());
    }
}