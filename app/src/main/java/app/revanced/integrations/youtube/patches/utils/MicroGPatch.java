package app.revanced.integrations.youtube.patches.utils;

import static app.revanced.integrations.shared.utils.StringRef.str;

import android.app.Activity;
import android.content.ContentProviderClient;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;

import androidx.annotation.NonNull;

import app.revanced.integrations.shared.utils.Logger;
import app.revanced.integrations.shared.utils.Utils;

@SuppressWarnings("unused")
public class MicroGPatch {
    private static final String DONT_KILL_MY_APP_LINK = "https://dontkillmyapp.com";
    private static final String MICROG_VENDOR = "com.mgoogle";
    private static final String MICROG_PACKAGE_NAME = MICROG_VENDOR + ".android.gms";
    private static final String MICROG_DOWNLOAD_LINK = "https://github.com/inotia00/VancedMicroG/releases/latest";
    private static final Uri MICROG_PROVIDER = Uri.parse("content://" + MICROG_VENDOR + ".android.gsf.gservices/prefix");

    private static void startIntent(Activity mActivity, String uriString, String... message) {
        for (String string : message) {
            Utils.showToastLong(string);
        }

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setData(Uri.parse(uriString));
        mActivity.startActivity(intent);
        System.exit(0);
    }

    public static void checkAvailability(@NonNull Activity mActivity) {
        try {
            mActivity.getPackageManager().getPackageInfo(MICROG_PACKAGE_NAME, PackageManager.GET_ACTIVITIES);
        } catch (PackageManager.NameNotFoundException exception) {
            Logger.printInfo(() -> "MicroG was not found", exception);
            startIntent(mActivity, MICROG_DOWNLOAD_LINK, str("microg_not_installed_warning"), str("microg_not_installed_notice"));
        }

        try (final ContentProviderClient client = mActivity.getContentResolver().acquireContentProviderClient(MICROG_PROVIDER)) {
            if (client != null)
                return;
            Logger.printInfo(() -> "MicroG is not running in the background");
            startIntent(mActivity, DONT_KILL_MY_APP_LINK, str("microg_not_running_warning"));
        }
    }
}
