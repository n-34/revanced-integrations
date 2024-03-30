package app.revanced.integrations.music.patches.components;

import app.revanced.integrations.music.settings.Settings;
import app.revanced.integrations.shared.patches.components.Filter;
import app.revanced.integrations.shared.patches.components.StringFilterGroup;

@SuppressWarnings("unused")
public final class SampleShelfFilter extends Filter {

    public SampleShelfFilter() {
        addPathCallbacks(
                new StringFilterGroup(
                        Settings.HIDE_SAMPLE_SHELF,
                        "immersive_card_shelf"
                )
        );
    }
}
