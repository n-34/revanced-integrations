package app.revanced.integrations.youtube.patches.flyoutpanel;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import app.revanced.integrations.shared.utils.Logger;
import app.revanced.integrations.shared.utils.ResourceUtils;
import app.revanced.integrations.shared.utils.StringRef;
import app.revanced.integrations.shared.utils.Utils;
import app.revanced.integrations.youtube.patches.components.VideoQualityMenuFilter;
import app.revanced.integrations.youtube.settings.Settings;

@SuppressWarnings("unused")
public class FlyoutPanelPatch {

    public static boolean changeSwitchToggle(boolean original) {
        return !Settings.CHANGE_PLAYER_FLYOUT_PANEL_TOGGLE.get() && original;
    }

    public static boolean enableOldQualityMenu() {
        return Settings.ENABLE_OLD_QUALITY_LAYOUT.get();
    }

    public static void enableOldQualityMenu(ListView listView) {
        if (!Settings.ENABLE_OLD_QUALITY_LAYOUT.get())
            return;

        listView.setVisibility(View.GONE);

        Utils.runOnMainThreadDelayed(() -> {
                    listView.setSoundEffectsEnabled(false);
                    listView.performItemClick(null, 2, 0);
                },
                1
        );
    }

    /**
     * The toggle's strings must follow the language set in the YouTube app.
     * So use {@link ResourceUtils#string(String)} instead of {@link StringRef#str(String)}.
     *
     * @param str identifier.
     * @return toggle's strings.
     */
    public static String getToggleString(String str) {
        return ResourceUtils.string(str);
    }

    /**
     * hide feed flyout panel for phone
     *
     * @param charSequence raw text
     */
    public static CharSequence hideFeedFlyoutPanel(CharSequence charSequence) {
        if (charSequence == null || !Settings.HIDE_FEED_FLYOUT_PANEL.get())
            return charSequence;

        String[] blockList = Settings.HIDE_FEED_FLYOUT_PANEL_FILTER_STRINGS.get().split("\\n");
        String targetString = charSequence.toString();

        for (String filter : blockList) {
            if (targetString.equals(filter) && !filter.isEmpty())
                return null;
        }

        return charSequence;
    }

    /**
     * hide feed flyout panel for tablet
     *
     * @param textView     flyout text view
     * @param charSequence raw text
     */
    public static void hideFeedFlyoutPanel(TextView textView, CharSequence charSequence) {
        if (charSequence == null || !Settings.HIDE_FEED_FLYOUT_PANEL.get())
            return;

        if (textView.getParent() == null || !(textView.getParent() instanceof View parentView))
            return;

        String[] blockList = Settings.HIDE_FEED_FLYOUT_PANEL_FILTER_STRINGS.get().split("\\n");
        String targetString = charSequence.toString();

        for (String filter : blockList) {
            if (targetString.equals(filter) && !filter.isEmpty())
                Utils.hideViewByLayoutParams(parentView);
        }
    }

    public static void hideFooterCaptions(View view) {
        Utils.hideViewUnderCondition(
                Settings.HIDE_PLAYER_FLYOUT_PANEL_CAPTIONS_FOOTER.get(),
                view
        );
    }

    public static void hideFooterQuality(View view) {
        Utils.hideViewUnderCondition(
                Settings.HIDE_PLAYER_FLYOUT_PANEL_QUALITY_FOOTER.get(),
                view
        );
    }

    public static void onFlyoutMenuCreate(final RecyclerView recyclerView) {
        if (!Settings.ENABLE_OLD_QUALITY_LAYOUT.get())
            return;

        recyclerView.getViewTreeObserver().addOnDrawListener(() -> {
            try {
                // Check if the current view is the quality menu.
                if (!VideoQualityMenuFilter.isVideoQualityMenuVisible || recyclerView.getChildCount() == 0)
                    return;

                final ViewGroup AdvancedQualityParentView = (ViewGroup) recyclerView.getChildAt(0);
                if (AdvancedQualityParentView.getChildCount() < 4)
                    return;

                final View AdvancedQualityView = AdvancedQualityParentView.getChildAt(3);
                final View QuickQualityView = (View) recyclerView.getParent().getParent().getParent();
                if (AdvancedQualityView != null && QuickQualityView != null) {
                    QuickQualityView.setVisibility(View.GONE);
                    AdvancedQualityView.setSoundEffectsEnabled(false);
                    AdvancedQualityView.performClick();
                    VideoQualityMenuFilter.isVideoQualityMenuVisible = false;
                }
            } catch (Exception ex) {
                Logger.printException(() -> "onFlyoutMenuCreate failure", ex);
            }
        });
    }
}
