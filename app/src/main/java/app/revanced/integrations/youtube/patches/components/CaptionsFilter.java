package app.revanced.integrations.youtube.patches.components;

import app.revanced.integrations.shared.patches.components.Filter;
import app.revanced.integrations.shared.patches.components.StringFilterGroup;
import app.revanced.integrations.youtube.settings.Settings;

@SuppressWarnings("unused")
public final class CaptionsFilter extends Filter {

    public CaptionsFilter() {
        addPathCallbacks(
                new StringFilterGroup(
                        Settings.HIDE_CAPTIONS_BUTTON,
                        "captions_button.eml"
                )
        );
    }
}
