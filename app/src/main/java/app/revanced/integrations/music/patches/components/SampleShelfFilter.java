package app.revanced.integrations.music.patches.components;

import app.revanced.integrations.music.settings.SettingsEnum;

@SuppressWarnings("unused")
public final class SampleShelfFilter extends Filter {

    public SampleShelfFilter() {
        pathFilterGroupList.addAll(
                new StringFilterGroup(
                        SettingsEnum.HIDE_SAMPLE_SHELF,
                        "immersive_card_shelf"
                )
        );
    }
}
