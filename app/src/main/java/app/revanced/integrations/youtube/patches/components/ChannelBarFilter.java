package app.revanced.integrations.youtube.patches.components;

import androidx.annotation.Nullable;

import app.revanced.integrations.shared.patches.components.Filter;
import app.revanced.integrations.shared.patches.components.StringFilterGroup;
import app.revanced.integrations.shared.patches.components.StringFilterGroupList;
import app.revanced.integrations.youtube.settings.Settings;

/**
 * There are too many exceptions to integrate into one {@link LayoutComponentsFilter}, and the filter is too complicated.
 * That's why I separated it with a new filter.
 */
@SuppressWarnings("unused")
public final class ChannelBarFilter extends Filter {
    private final StringFilterGroupList channelBarGroupList = new StringFilterGroupList();

    public ChannelBarFilter() {
        final StringFilterGroup channelBar = new StringFilterGroup(
                null,
                "channel_bar_inner"
        );

        final StringFilterGroup joinMembership = new StringFilterGroup(
                Settings.HIDE_JOIN_BUTTON,
                "compact_sponsor_button",
                "|ContainerType|button.eml|"
        );

        final StringFilterGroup startTrial = new StringFilterGroup(
                Settings.HIDE_START_TRIAL_BUTTON,
                "channel_purchase_button"
        );

        addPathCallbacks(channelBar);
        channelBarGroupList.addAll(
                joinMembership,
                startTrial
        );
    }

    @Override
    public boolean isFiltered(String path, @Nullable String identifier, String allValue, byte[] protobufBufferArray,
                       StringFilterGroup matchedGroup, FilterContentType contentType, int contentIndex) {
        if (!channelBarGroupList.check(path).isFiltered()) {
            return false;
        }

        return super.isFiltered(path, identifier, allValue, protobufBufferArray, matchedGroup, contentType, contentIndex);
    }
}
