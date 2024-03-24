package app.revanced.integrations.youtube.patches.components;

import androidx.annotation.Nullable;

import app.revanced.integrations.youtube.patches.utils.BrowseIdPatch;
import app.revanced.integrations.youtube.settings.SettingsEnum;
import app.revanced.integrations.youtube.shared.PlayerType;
import app.revanced.integrations.youtube.utils.LogHelper;

/**
 * @noinspection rawtypes
 */
@SuppressWarnings("unused")
public final class ShortsShelfFilter extends Filter {
    private static final String SHORTS_SHELF_HEADER_CONVERSION_CONTEXT = "horizontalCollectionSwipeProtector=null";
    private static final String SHELF_HEADER_PATH = "shelf_header.eml";
    private final StringFilterGroup shortsCompactFeedVideoPath;
    private final ByteArrayAsStringFilterGroup shortsCompactFeedVideoBuffer;
    private final StringFilterGroup shelfHeader;

    public ShortsShelfFilter() {
        // Feed Shorts shelf header.
        // Use a different filter group for this pattern, as it requires an additional check after matching.
        shelfHeader = new StringFilterGroup(
                SettingsEnum.HIDE_SHORTS_SHELF,
                SHELF_HEADER_PATH
        );

        final StringFilterGroup shorts = new StringFilterGroup(
                SettingsEnum.HIDE_SHORTS_SHELF,
                "shorts_shelf",
                "inline_shorts",
                "shorts_grid",
                "shorts_video_cell"
        );

        identifierFilterGroupList.addAll(shelfHeader, shorts);

        // Shorts that appear in the feed/search when the device is using tablet layout.
        shortsCompactFeedVideoPath = new StringFilterGroup(
                SettingsEnum.HIDE_SHORTS_SHELF,
                "compact_video.eml"
        );

        // Filter out items that use the 'frame0' thumbnail.
        // This is a valid thumbnail for both regular videos and Shorts,
        // but it appears these thumbnails are used only for Shorts.
        shortsCompactFeedVideoBuffer = new ByteArrayAsStringFilterGroup(
                SettingsEnum.HIDE_SHORTS_SHELF,
                "/frame0.jpg"
        );

        pathFilterGroupList.addAll(shortsCompactFeedVideoPath);
    }

    @Override
    boolean isFiltered(String path, @Nullable String identifier, String allValue, byte[] protobufBufferArray,
                       FilterGroupList matchedList, FilterGroup matchedGroup, int matchedIndex) {
        // 1. opened watch history
        // 2. video doesn't play or the video is minimized
        if (BrowseIdPatch.isHistory() && PlayerType.getCurrent().isNoneHiddenOrSliding()) {
            if (SettingsEnum.HIDE_SHORTS_SHELF_EXCEPTION_HISTORY.getBoolean()) {
                LogHelper.printDebug(() -> "Ignoring shorts shelf in watch history");
                return false;
            }
        } else if (matchedGroup == shortsCompactFeedVideoPath) {
            if (matchedIndex == 0 && shortsCompactFeedVideoBuffer.check(protobufBufferArray).isFiltered())
                return super.isFiltered(path, identifier, allValue, protobufBufferArray, matchedList, matchedGroup, matchedIndex);
            return false;
        } else if (matchedGroup == shelfHeader) {
            // Check ConversationContext to not hide shelf header in channel profile
            // This value does not exist in the shelf header in the channel profile
            if (!allValue.contains(SHORTS_SHELF_HEADER_CONVERSION_CONTEXT))
                return false;
        }

        // Super class handles logging.
        return super.isFiltered(path, identifier, allValue, protobufBufferArray, matchedList, matchedGroup, matchedIndex);
    }
}
