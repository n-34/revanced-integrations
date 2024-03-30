package app.revanced.integrations.youtube.patches.components;

import app.revanced.integrations.shared.patches.components.Filter;
import app.revanced.integrations.shared.patches.components.StringFilterGroup;
import app.revanced.integrations.youtube.settings.Settings;

@SuppressWarnings("unused")
public final class WaterMarkFilter extends Filter {

    public WaterMarkFilter() {
        addPathCallbacks(
                new StringFilterGroup(
                        Settings.HIDE_CHANNEL_WATERMARK,
                        "featured_channel_watermark_overlay.eml"
                )
        );
    }
}
