package app.revanced.integrations.music.patches.components;

import app.revanced.integrations.music.settings.Settings;
import app.revanced.integrations.shared.patches.components.Filter;
import app.revanced.integrations.shared.patches.components.StringFilterGroup;

@SuppressWarnings("unused")
public final class AdsFilter extends Filter {

    public AdsFilter() {
        final StringFilterGroup paidPromotionBanner = new StringFilterGroup(
                Settings.HIDE_PAID_PROMOTION,
                "paid_content_overlay"
        );
        final StringFilterGroup statementBanner = new StringFilterGroup(
                Settings.HIDE_GENERAL_ADS,
                "statement_banner"
        );

        addPathCallbacks(
                paidPromotionBanner,
                statementBanner
        );
    }
}
