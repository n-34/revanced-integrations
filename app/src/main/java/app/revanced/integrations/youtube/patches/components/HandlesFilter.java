package app.revanced.integrations.youtube.patches.components;

import androidx.annotation.Nullable;

import app.revanced.integrations.shared.patches.components.Filter;
import app.revanced.integrations.shared.patches.components.StringFilterGroup;
import app.revanced.integrations.youtube.settings.Settings;

@SuppressWarnings("unused")
public final class HandlesFilter extends Filter {
    private static final String ACCOUNT_HEADER_PATH = "account_header.eml";

    public HandlesFilter() {
        addPathCallbacks(
                new StringFilterGroup(
                        Settings.HIDE_HANDLE,
                        "|CellType|ContainerType|ContainerType|ContainerType|TextType|"
                )
        );
    }

    @Override
    public boolean isFiltered(String path, @Nullable String identifier, String allValue, byte[] protobufBufferArray,
                       StringFilterGroup matchedGroup, FilterContentType contentType, int contentIndex) {
        if (!path.startsWith(ACCOUNT_HEADER_PATH)) {
            return false;
        }

        return super.isFiltered(path, identifier, allValue, protobufBufferArray, matchedGroup, contentType, contentIndex);
    }
}
