package app.revanced.integrations.music.patches.components;

import app.revanced.integrations.music.settings.Settings;
import app.revanced.integrations.shared.patches.components.Filter;
import app.revanced.integrations.shared.patches.components.StringFilterGroup;

@SuppressWarnings("unused")
public final class PlaylistCardFilter extends Filter {

    public PlaylistCardFilter() {
        addIdentifierCallbacks(
                new StringFilterGroup(
                        Settings.HIDE_PLAYLIST_CARD,
                        "music_container_card_shelf"
                )
        );
    }
}
