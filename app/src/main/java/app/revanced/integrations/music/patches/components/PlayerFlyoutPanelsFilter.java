package app.revanced.integrations.music.patches.components;

import app.revanced.integrations.music.settings.Settings;
import app.revanced.integrations.shared.patches.components.Filter;
import app.revanced.integrations.shared.patches.components.StringFilterGroup;

@SuppressWarnings("unused")
public final class PlayerFlyoutPanelsFilter extends Filter {

    public PlayerFlyoutPanelsFilter() {
        addIdentifierCallbacks(
                new StringFilterGroup(
                        Settings.HIDE_FLYOUT_PANEL_3_COLUMN_COMPONENT,
                        "music_highlight_menu_item_carousel.eml"
                )
        );
    }
}
