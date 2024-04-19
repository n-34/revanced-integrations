package app.revanced.integrations.youtube.patches.components;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import app.revanced.integrations.shared.patches.components.ByteArrayFilterGroup;
import app.revanced.integrations.shared.patches.components.Filter;
import app.revanced.integrations.shared.patches.components.StringFilterGroup;
import app.revanced.integrations.youtube.settings.Settings;

/**
 * @noinspection ALL
 */
public final class FeedVideoFilter extends Filter {
    private final ByteArrayFilterGroup feedRecommendations = new ByteArrayFilterGroup(
            Settings.HIDE_RECOMMENDED_VIDEO,
            "endorsement_header_footer" // videos with gray descriptions
    );
    private final ByteArrayFilterGroup homeFeedRecommendations = new ByteArrayFilterGroup(
            Settings.HIDE_RECOMMENDED_VIDEO,
            "g-highZ",  // videos with less than 1000 views
            "high-ptsZ" // videos for membership only
    );
    private final StringFilterGroup homeFeedVideo;
    private final StringFilterGroup feedVideo;

    public FeedVideoFilter() {
        // Paths.
        homeFeedVideo = new StringFilterGroup(
                null,
                "home_video_with_context.eml"
        );
        feedVideo = new StringFilterGroup(
                null,
                "video_with_context.eml"
        );

        addPathCallbacks(
                homeFeedVideo,
                feedVideo
        );
    }

    @Override
    public boolean isFiltered(String path, @Nullable String identifier, String allValue, byte[] protobufBufferArray,
                              StringFilterGroup matchedGroup, FilterContentType contentType, int contentIndex) {
        if (matchedGroup == feedVideo) {
            if (filterByViews(protobufBufferArray)) {
                return super.isFiltered(path, identifier, allValue, protobufBufferArray, matchedGroup, contentType, contentIndex);
            }
            if (feedRecommendations.check(protobufBufferArray).isFiltered()) {
                return super.isFiltered(path, identifier, allValue, protobufBufferArray, matchedGroup, contentType, contentIndex);
            }
        } else if (matchedGroup == homeFeedVideo && homeFeedRecommendations.check(protobufBufferArray).isFiltered()) {
            return super.isFiltered(path, identifier, allValue, protobufBufferArray, matchedGroup, contentType, contentIndex);
        }

        return false;
    }

    private static Pattern[] viewCountPatterns = null;
    private static long multiplierValue = -1L;

    private synchronized boolean filterByViews(byte[] protobufBufferArray) {
        if (!Settings.HIDE_VIDEO_BY_VIEW_COUNTS.get())
            return false;

        final long hideVideoViewCounts = Settings.HIDE_VIDEO_VIEW_COUNTS.get();
        final String[] parts = Settings.HIDE_VIDEO_VIEW_COUNTS_MULTIPLIER.get().split("\\n");

        final String protobufString = new String(protobufBufferArray);
        if (viewCountPatterns == null) {
            viewCountPatterns = getViewCountPatterns(parts);
        }

        for (Pattern pattern : viewCountPatterns) {
            final Matcher matcher = pattern.matcher(protobufString);
            if (matcher.find()) {
                String numString = Objects.requireNonNull(matcher.group(1));
                double num = parseNumber(numString);
                String multiplierKey = matcher.group(2);
                if (multiplierValue == -1L) {
                    multiplierValue = getMultiplierValue(parts, multiplierKey);
                }
                return num * multiplierValue < hideVideoViewCounts;
            }
        }

        return false;
    }

    private synchronized double parseNumber(String numString) {
        /**
         * Some languages have comma (,) as a decimal separator.
         * In order to detect those numbers as doubles in Java
         * we convert commas (,) to dots (.).
         * Unless we find a language that has commas used in
         * a different manner, it should work.
         */
        numString = numString.replace(",", ".");

        /**
         * Some languages have dot (.) as a kilo separator.
         * So we check with regex if there is a number with 3+
         * digits after dot (.), we replace it with nothing
         * to make Java understand the number as a whole.
         */
        if (numString.matches("\\d+\\.\\d{3,}")) {
            numString = numString.replace(".", "");
        }

        return Double.parseDouble(numString);
    }

    private synchronized Pattern[] getViewCountPatterns(String[] parts) {
        StringBuilder prefixPatternBuilder = new StringBuilder("(\\d+(?:[.,]\\d+)?)\\s?(");
        StringBuilder secondPatternBuilder = new StringBuilder();
        StringBuilder suffixBuilder = getSuffixBuilder(parts, prefixPatternBuilder, secondPatternBuilder);

        prefixPatternBuilder.deleteCharAt(prefixPatternBuilder.length() - 1); // Remove the trailing |
        prefixPatternBuilder.append(")?\\s*");
        prefixPatternBuilder.append(suffixBuilder.length() > 0 ? suffixBuilder.toString() : "views");

        secondPatternBuilder.deleteCharAt(secondPatternBuilder.length() - 1); // Remove the trailing |
        secondPatternBuilder.append(")?");

        final Pattern[] patterns = new Pattern[2];
        patterns[0] = Pattern.compile(prefixPatternBuilder.toString());
        patterns[1] = Pattern.compile(secondPatternBuilder.toString());

        return patterns;
    }

    @NonNull
    private synchronized StringBuilder getSuffixBuilder(String[] parts, StringBuilder prefixPatternBuilder, StringBuilder secondPatternBuilder) {
        StringBuilder suffixBuilder = new StringBuilder();

        for (String part : parts) {
            final String[] pair = part.split(" -> ");
            final String pair0 = pair[0].trim();
            final String pair1 = pair[1].trim();

            if (pair.length == 2 && !pair1.equals("views")) {
                prefixPatternBuilder.append(pair0).append("|");
            }

            if (pair.length == 2 && pair1.equals("views")) {
                suffixBuilder.append(pair0);
                secondPatternBuilder.append(pair0).append("\\s*").append(prefixPatternBuilder);
            }
        }
        return suffixBuilder;
    }

    private synchronized long getMultiplierValue(String[] parts, String multiplier) {
        for (String part : parts) {
            final String[] pair = part.split(" -> ");
            final String pair0 = pair[0].trim();
            final String pair1 = pair[1].trim();

            if (pair.length == 2 && pair0.equals(multiplier) && !pair1.equals("views")) {
                return Long.parseLong(pair[1].replaceAll("[^\\d]", ""));
            }
        }

        return 1L; // Default value if not found
    }
}
