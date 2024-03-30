package app.revanced.integrations.music.settings;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import androidx.annotation.NonNull;

import app.revanced.integrations.music.settings.preference.ReVancedPreferenceFragment;
import app.revanced.integrations.shared.utils.Logger;

/**
 * @noinspection ALL
 */
public class ActivityHook {
    @SuppressLint("StaticFieldLeak")
    private static Activity activity;

    public static Activity getActivity() {
        return activity;
    }

    /**
     * Injection point.
     *
     * @param object object is usually Activity, but sometimes object cannot be cast to Activity.
     *               Check whether object can be cast as Activity for a safe hook.
     */
    public static void setActivity(@NonNull Object object) {
        if (object instanceof Activity mActivity)
            activity = mActivity;
    }

    /**
     * Injection point.
     *
     * @param baseActivity Activity containing intent data.
     *                     It should be finished immediately after obtaining the dataString.
     * @return Whether or not dataString is included.
     */
    public static boolean initialize(@NonNull Activity baseActivity) {
        try {
            final Intent baseActivityIntent = baseActivity.getIntent();
            if (baseActivityIntent == null)
                return false;

            // If we do not finish the activity immediately, the YT Music logo will remain on the screen.
            baseActivity.finish();

            String dataString = baseActivityIntent.getDataString();
            if (dataString == null || dataString.isEmpty())
                return false;

            // Checks whether dataString contains settings that use Intent.
            if (!Settings.includeWithIntent(dataString))
                return false;


            // Save intent data in settings activity.
            Intent intent = activity.getIntent();
            intent.setData(Uri.parse(dataString));
            activity.setIntent(intent);

            // Starts a new PreferenceFragment to handle activities freely.
            activity.getFragmentManager()
                    .beginTransaction()
                    .add(new ReVancedPreferenceFragment(), "")
                    .commit();

            return true;
        } catch (Exception ex) {
            Logger.printException(() -> "initializeSettings failure", ex);
        }
        return false;
    }

}
