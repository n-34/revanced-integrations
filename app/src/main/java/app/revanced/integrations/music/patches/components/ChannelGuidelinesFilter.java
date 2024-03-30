package app.revanced.integrations.music.patches.components;

import app.revanced.integrations.music.settings.Settings;
import app.revanced.integrations.shared.patches.components.Filter;
import app.revanced.integrations.shared.patches.components.StringFilterGroup;

@SuppressWarnings("unused")
public final class ChannelGuidelinesFilter extends Filter {

    public ChannelGuidelinesFilter() {
        addPathCallbacks(
                new StringFilterGroup(
                        Settings.HIDE_CHANNEL_GUIDELINES,
                        "community_guidelines"
                )
        );
    }
}
