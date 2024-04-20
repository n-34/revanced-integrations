package app.revanced.integrations.youtube.patches.misc;

import app.revanced.integrations.youtube.shared.RootView;

@SuppressWarnings("unused")
public class MinimizedPlaybackPatch {

    public static boolean isPlaybackNotShort() {
        return !RootView.isShortsActive();
    }

}
