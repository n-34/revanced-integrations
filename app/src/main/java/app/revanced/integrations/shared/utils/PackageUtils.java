package app.revanced.integrations.shared.utils;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class PackageUtils extends Utils {
    private static String applicationLabel = "";
    private static int smallestScreenWidthDp = 0;
    private static String versionName = "";

    public static String getApplicationLabel() {
        return applicationLabel;
    }

    public static String getVersionName() {
        return versionName;
    }

    public static boolean isPackageEnabled(@NonNull String packageName) {
        try {
            return context.getPackageManager().getApplicationInfo(packageName, 0).enabled;
        } catch (PackageManager.NameNotFoundException ignored) {
        }

        return false;
    }

    public static boolean isTablet() {
        return smallestScreenWidthDp >= 600;
    }

    public static void setApplicationLabel() {
        final PackageInfo packageInfo = getPackageInfo();
        if (packageInfo != null) {
            applicationLabel = (String) packageInfo.applicationInfo.loadLabel(getPackageManager());
        }
    }

    public static void setSmallestScreenWidthDp() {
        smallestScreenWidthDp = context.getResources().getConfiguration().smallestScreenWidthDp;
    }

    public static void setVersionName() {
        final PackageInfo packageInfo = getPackageInfo();
        if (packageInfo != null) {
            versionName = packageInfo.versionName;
        }
    }

    // utils
    @Nullable
    private static PackageInfo getPackageInfo() {
        try {
            final PackageManager packageManager = getPackageManager();
            final String packageName = context.getPackageName();
            return Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU
                    ? packageManager.getPackageInfo(packageName, PackageManager.PackageInfoFlags.of(0))
                    : packageManager.getPackageInfo(packageName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            Logger.printException(() -> "Failed to get package Info!" + e);
        }
        return null;
    }

    @NonNull
    private static PackageManager getPackageManager() {
        return context.getPackageManager();
    }

}
