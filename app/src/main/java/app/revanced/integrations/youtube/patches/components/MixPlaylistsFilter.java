package app.revanced.integrations.youtube.patches.components;

import app.revanced.integrations.shared.patches.components.ByteArrayFilterGroup;
import app.revanced.integrations.shared.patches.components.Filter;
import app.revanced.integrations.shared.utils.StringTrieSearch;
import app.revanced.integrations.youtube.settings.Settings;

@SuppressWarnings("unused")
public final class MixPlaylistsFilter extends Filter {

    private static final StringTrieSearch exceptions = new StringTrieSearch();
    private static final StringTrieSearch mixPlaylistsExceptions = new StringTrieSearch();
    private static final ByteArrayFilterGroup mixPlaylistsExceptions2 =
            new ByteArrayFilterGroup(
                    null,
                    "cell_description_body"
            );

    private static final ByteArrayFilterGroup mixPlaylists =
            new ByteArrayFilterGroup(
                    Settings.HIDE_MIX_PLAYLISTS,
                    "&list="
            );

    public MixPlaylistsFilter() {
        mixPlaylistsExceptions.addPatterns(
                "V.ED", // playlist browse id
                "java.lang.ref.WeakReference"
        );
    }

    /**
     * Injection point.
     * <p>
     * Called from a different place then the other filters.
     */
    public static boolean filterMixPlaylists(final Object conversionContext, final byte[] bytes) {
        if (bytes == null)
            return false;

        return mixPlaylists.check(bytes).isFiltered()
                && !mixPlaylistsExceptions.matches(conversionContext.toString())
                && !mixPlaylistsExceptions2.check(bytes).isFiltered();
    }
}
