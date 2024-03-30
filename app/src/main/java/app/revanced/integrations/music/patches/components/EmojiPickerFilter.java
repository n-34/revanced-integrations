package app.revanced.integrations.music.patches.components;

import app.revanced.integrations.music.settings.Settings;
import app.revanced.integrations.shared.patches.components.Filter;
import app.revanced.integrations.shared.patches.components.StringFilterGroup;

@SuppressWarnings("unused")
public final class EmojiPickerFilter extends Filter {

    public EmojiPickerFilter() {
        addPathCallbacks(
                new StringFilterGroup(
                        Settings.HIDE_EMOJI_PICKER,
                        "|CellType|ContainerType|ContainerType|ContainerType|ContainerType|ContainerType|"
                )
        );
    }
}
