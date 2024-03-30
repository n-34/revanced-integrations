package app.revanced.integrations.youtube.patches.components;

import android.annotation.SuppressLint;
import android.view.View;

import androidx.annotation.Nullable;

import app.revanced.integrations.shared.patches.components.ByteArrayFilterGroup;
import app.revanced.integrations.shared.patches.components.Filter;
import app.revanced.integrations.shared.patches.components.StringFilterGroup;
import app.revanced.integrations.shared.utils.Utils;
import app.revanced.integrations.youtube.settings.Settings;

@SuppressWarnings("unused")
public final class ChannelProfileFilter extends Filter {
    private static final String BROWSE_STORE_BUTTON_PATH = "|ContainerType|button.eml|";
    private static final String CHANNEL_PROFILE_LINKS_PATH = "|ContainerType|ContainerType|ContainerType|ContainerType|TextType|";
    @SuppressLint("StaticFieldLeak")
    public static View channelTabView;
    /**
     * Last time the method was used
     */
    private static long lastTimeUsed = 0;
    private final StringFilterGroup channelProfileButtonRule;
    private static final ByteArrayFilterGroup browseStoreButton =
            new ByteArrayFilterGroup(
                    null,
                    "header_store_button"
            );

    public ChannelProfileFilter() {
        channelProfileButtonRule = new StringFilterGroup(
                null,
                "|channel_profile_"
        );

        final StringFilterGroup channelMemberShelf = new StringFilterGroup(
                Settings.HIDE_CHANNEL_MEMBER_SHELF,
                "member_recognition_shelf"
        );

        final StringFilterGroup channelProfileLinks = new StringFilterGroup(
                Settings.HIDE_CHANNEL_PROFILE_LINKS,
                "channel_header_links"
        );

        final StringFilterGroup forYouShelf = new StringFilterGroup(
                Settings.HIDE_FOR_YOU_SHELF,
                "mixed_content_shelf"
        );

        addPathCallbacks(
                channelProfileButtonRule,
                channelMemberShelf,
                channelProfileLinks,
                forYouShelf
        );
    }

    private static void hideStoreTab(boolean isBrowseStoreButtonShown) {
        if (!isBrowseStoreButtonShown || !Settings.HIDE_STORE_TAB.get())
            return;

        final long currentTime = System.currentTimeMillis();

        // Ignores method reuse in less than 3 second.
        if (lastTimeUsed != 0 && currentTime - lastTimeUsed < 3000)
            return;
        lastTimeUsed = currentTime;

        // This method is called before the channel tab is created.
        // Add a delay to hide after the channel tab is created.
        Utils.runOnMainThreadDelayed(() -> {
                    if (channelTabView != null) {
                        channelTabView.setVisibility(View.GONE);
                    }
                },
                0
        );
    }

    @Override
    public boolean isFiltered(String path, @Nullable String identifier, String allValue, byte[] protobufBufferArray,
                       StringFilterGroup matchedGroup, FilterContentType contentType, int contentIndex) {
        if (matchedGroup == channelProfileButtonRule) {
            final boolean isBrowseStoreButtonShown = path.contains(BROWSE_STORE_BUTTON_PATH) && browseStoreButton.check(protobufBufferArray).isFiltered();
            final boolean isChannelProfileLinkShown = path.contains(CHANNEL_PROFILE_LINKS_PATH);
            if (isChannelProfileLinkShown && Settings.HIDE_CHANNEL_PROFILE_LINKS.get()) {
                return super.isFiltered(path, identifier, allValue, protobufBufferArray, matchedGroup, contentType, contentIndex);
            }
            hideStoreTab(isBrowseStoreButtonShown);
            if (!isBrowseStoreButtonShown || !Settings.HIDE_BROWSE_STORE_BUTTON.get()) {
                return false;
            }
        }

        return super.isFiltered(path, identifier, allValue, protobufBufferArray, matchedGroup, contentType, contentIndex);
    }
}
