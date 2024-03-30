package app.revanced.integrations.music.patches.components;

import app.revanced.integrations.music.settings.Settings;
import app.revanced.integrations.shared.patches.components.Filter;
import app.revanced.integrations.shared.patches.components.StringFilterGroup;

@SuppressWarnings("unused")
public final class CarouselShelfFilter extends Filter {

    public CarouselShelfFilter() {
        addIdentifierCallbacks(
                new StringFilterGroup(
                        Settings.HIDE_CAROUSEL_SHELF,
                        "music_grid_item_carousel"
                )
        );
    }
}
