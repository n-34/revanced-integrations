package app.revanced.integrations.youtube.patches.utils;

import static app.revanced.integrations.youtube.settings.SettingsUtils.showRestartDialog;
import static app.revanced.integrations.youtube.utils.ReVancedUtils.runOnMainThreadDelayed;

import android.app.Activity;

import androidx.annotation.NonNull;

import app.revanced.integrations.youtube.settings.SettingsEnum;
import app.revanced.integrations.youtube.utils.ReVancedHelper;
import app.revanced.integrations.youtube.utils.ReVancedUtils;

@SuppressWarnings("unused")
public class InitializationPatch {

    /**
     * Some layouts that depend on litho do not load when the app is first installed.
     * (Also reproduced on unPatched YouTube)
     * <p>
     * To fix this, show the restart dialog when the app is installed for the first time.
     */
    public static void onCreate(@NonNull Activity mActivity) {
        ReVancedHelper.setPlayerFlyoutPanelAdditionalSettings();
        if (SettingsEnum.INITIALIZED.getBoolean())
            return;

        runOnMainThreadDelayed(() -> showRestartDialog(mActivity, "revanced_restart_first_run", 500), 500);
        runOnMainThreadDelayed(() -> SettingsEnum.INITIALIZED.saveValue(true), 1000);
    }

    public static void setDeviceInformation(@NonNull Activity mActivity) {
        ReVancedHelper.setPackageName(mActivity);
        ReVancedHelper.setApplicationLabel(mActivity);
        ReVancedHelper.setIsTablet(mActivity);
        ReVancedHelper.setVersionName(mActivity);
    }

    public static void setMainActivity(@NonNull Activity mActivity) {
        ReVancedUtils.activity = mActivity;
    }
}