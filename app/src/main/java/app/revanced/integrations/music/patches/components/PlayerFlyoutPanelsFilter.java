package app.revanced.integrations.music.patches.components;

import app.revanced.integrations.music.settings.SettingsEnum;

@SuppressWarnings("unused")
public final class PlayerFlyoutPanelsFilter extends Filter {

    public PlayerFlyoutPanelsFilter() {
        identifierFilterGroupList.addAll(
                new StringFilterGroup(
                        SettingsEnum.HIDE_FLYOUT_PANEL_3_COLUMN_COMPONENT,
                        "music_highlight_menu_item_carousel.eml"
                )
        );
    }
}
