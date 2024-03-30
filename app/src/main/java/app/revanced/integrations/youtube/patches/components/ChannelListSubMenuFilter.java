package app.revanced.integrations.youtube.patches.components;

import app.revanced.integrations.shared.patches.components.Filter;
import app.revanced.integrations.shared.patches.components.StringFilterGroup;
import app.revanced.integrations.youtube.settings.Settings;

@SuppressWarnings("unused")
public final class ChannelListSubMenuFilter extends Filter {

    public ChannelListSubMenuFilter() {
        addPathCallbacks(
                new StringFilterGroup(
                        Settings.HIDE_CHANNEL_LIST_SUBMENU,
                        "subscriptions_channel_bar.eml"
                )
        );
    }
}
