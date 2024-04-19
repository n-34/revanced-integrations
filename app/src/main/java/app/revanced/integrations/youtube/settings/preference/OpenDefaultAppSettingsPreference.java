package app.revanced.integrations.youtube.settings.preference;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.preference.Preference;
import android.util.AttributeSet;

 /**
 * @noinspection ALL
 */
public class OpenDefaultAppSettingsPreference extends Preference {
    {
        setOnPreferenceClickListener(pref -> {
            Context context = pref.getContext();
            final Uri uri = Uri.parse("package:" + context.getPackageName());
            final Intent intent = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S
                    ? new Intent(android.provider.Settings.ACTION_APP_OPEN_BY_DEFAULT_SETTINGS, uri)
                    : new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS, uri);
            context.startActivity(intent);
            return false;
        });
    }

    public OpenDefaultAppSettingsPreference(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
    public OpenDefaultAppSettingsPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    public OpenDefaultAppSettingsPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public OpenDefaultAppSettingsPreference(Context context) {
        super(context);
    }
}
