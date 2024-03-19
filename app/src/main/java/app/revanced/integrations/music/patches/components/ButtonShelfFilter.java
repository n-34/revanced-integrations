package app.revanced.integrations.music.patches.components;

import app.revanced.integrations.music.settings.SettingsEnum;

@SuppressWarnings("unused")
public final class ButtonShelfFilter extends Filter {

    public ButtonShelfFilter() {
        identifierFilterGroupList.addAll(
                new StringFilterGroup(
                        SettingsEnum.HIDE_BUTTON_SHELF,
                        "entry_point_button_shelf"
                )
        );
    }
}
