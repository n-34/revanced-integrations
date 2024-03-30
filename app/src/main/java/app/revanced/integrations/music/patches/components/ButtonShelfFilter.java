package app.revanced.integrations.music.patches.components;

import app.revanced.integrations.music.settings.Settings;
import app.revanced.integrations.shared.patches.components.Filter;
import app.revanced.integrations.shared.patches.components.StringFilterGroup;

@SuppressWarnings("unused")
public final class ButtonShelfFilter extends Filter {

    public ButtonShelfFilter() {
        addIdentifierCallbacks(
                new StringFilterGroup(
                        Settings.HIDE_BUTTON_SHELF,
                        "entry_point_button_shelf"
                )
        );
    }
}
