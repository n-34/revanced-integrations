package app.revanced.integrations.youtube.patches.components;

import androidx.annotation.Nullable;

import app.revanced.integrations.youtube.settings.SettingsEnum;

/**
 * There are too many exceptions to integrate into one {@link LayoutComponentsFilter}, and the filter is too complicated.
 * That's why I separated it with a new filter.
 */
@SuppressWarnings("unused")
final class ChannelBarFilter extends Filter {
    private final StringFilterGroupList channelBarGroupList = new StringFilterGroupList();

    public ChannelBarFilter() {
        final StringFilterGroup channelBar = new StringFilterGroup(
                null,
                "channel_bar_inner"
        );

        final StringFilterGroup joinMembership = new StringFilterGroup(
                SettingsEnum.HIDE_JOIN_BUTTON,
                "compact_sponsor_button",
                "|ContainerType|button.eml|"
        );

        final StringFilterGroup startTrial = new StringFilterGroup(
                SettingsEnum.HIDE_START_TRIAL_BUTTON,
                "channel_purchase_button"
        );

        pathFilterGroupList.addAll(channelBar);
        channelBarGroupList.addAll(
                joinMembership,
                startTrial
        );
    }

    /** @noinspection rawtypes*/
    @Override
    boolean isFiltered(String path, @Nullable String identifier, String allValue, byte[] protobufBufferArray,
                       FilterGroupList matchedList, FilterGroup matchedGroup, int matchedIndex) {
        if (!channelBarGroupList.check(path).isFiltered()) {
            return false;
        }

        return super.isFiltered(path, identifier, allValue, protobufBufferArray, matchedList, matchedGroup, matchedIndex);
    }
}
