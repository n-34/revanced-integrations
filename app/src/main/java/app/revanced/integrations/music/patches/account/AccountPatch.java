package app.revanced.integrations.music.patches.account;

import android.view.View;
import android.widget.TextView;

import app.revanced.integrations.music.settings.Settings;

@SuppressWarnings("unused")
public class AccountPatch {

    public static void hideAccountMenu(CharSequence charSequence, View view) {
        if (!Settings.HIDE_ACCOUNT_MENU.get())
            return;

        if (charSequence == null) {
            if (Settings.HIDE_ACCOUNT_MENU_EMPTY_COMPONENT.get())
                view.setVisibility(View.GONE);

            return;
        }

        final String[] blockList = Settings.HIDE_ACCOUNT_MENU_FILTER_STRINGS.get().split("\\n");

        for (String filter : blockList) {
            if (charSequence.toString().equals(filter) && !filter.isEmpty())
                view.setVisibility(View.GONE);
        }
    }

    public static boolean hideHandle(boolean original) {
        return Settings.HIDE_HANDLE.get() || original;
    }

    public static void hideHandle(TextView textView, int visibility) {
        if (Settings.HIDE_HANDLE.get()) {
            textView.setVisibility(View.GONE);
        } else {
            textView.setVisibility(visibility);
        }
    }

    public static int hideTermsContainer() {
        return Settings.HIDE_TERMS_CONTAINER.get() ? View.GONE : View.VISIBLE;
    }
}
