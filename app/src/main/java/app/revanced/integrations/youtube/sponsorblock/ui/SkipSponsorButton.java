package app.revanced.integrations.youtube.sponsorblock.ui;

import static app.revanced.integrations.shared.utils.ResourceUtils.identifier;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.Objects;

import app.revanced.integrations.shared.utils.ResourceType;
import app.revanced.integrations.youtube.sponsorblock.SegmentPlaybackController;
import app.revanced.integrations.youtube.sponsorblock.objects.SponsorSegment;

public class SkipSponsorButton extends FrameLayout {
    final int defaultBottomMargin;
    final int ctaBottomMargin;
    final int hiddenBottomMargin;
    private final TextView skipSponsorTextView;
    private SponsorSegment segment;

    public SkipSponsorButton(Context context) {
        this(context, null);
    }

    public SkipSponsorButton(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public SkipSponsorButton(Context context, AttributeSet attributeSet, int defStyleAttr) {
        this(context, attributeSet, defStyleAttr, 0);
    }

    public SkipSponsorButton(Context context, AttributeSet attributeSet, int defStyleAttr, int defStyleRes) {
        super(context, attributeSet, defStyleAttr, defStyleRes);

        LayoutInflater.from(context).inflate(identifier("revanced_sb_skip_sponsor_button", ResourceType.LAYOUT, context), this, true);  // layout:skip_ad_button
        Resources resources = context.getResources();
        setMinimumHeight(resources.getDimensionPixelSize(identifier("ad_skip_ad_button_min_height", ResourceType.DIMEN, context)));  // dimen:ad_skip_ad_button_min_height
        final LinearLayout skipSponsorBtnContainer = (LinearLayout) Objects.requireNonNull((View) findViewById(identifier("revanced_sb_skip_sponsor_button_container", ResourceType.ID, context)));  // id:skip_ad_button_container
        skipSponsorTextView = (TextView) Objects.requireNonNull((View) findViewById(identifier("revanced_sb_skip_sponsor_button_text", ResourceType.ID, context)));  // id:sb_skip_sponsor_button_text;
        defaultBottomMargin = resources.getDimensionPixelSize(identifier("skip_button_default_bottom_margin", ResourceType.DIMEN, context));  // dimen:skip_button_default_bottom_margin
        ctaBottomMargin = resources.getDimensionPixelSize(identifier("skip_button_cta_bottom_margin", ResourceType.DIMEN, context));  // dimen:skip_button_cta_bottom_margin
        hiddenBottomMargin = (int) Math.round((ctaBottomMargin) * 0.5);  // margin when the button container is hidden

        skipSponsorBtnContainer.setOnClickListener(v -> {
            // The view controller handles hiding this button, but hide it here as well just in case something goofs.
            setVisibility(View.GONE);
            SegmentPlaybackController.onSkipSegmentClicked(segment);
        });
    }

    /**
     * @return true, if this button state was changed
     */
    public boolean updateSkipButtonText(@NonNull SponsorSegment segment) {
        this.segment = segment;
        final String newText = segment.getSkipButtonText();
        if (newText.equals(skipSponsorTextView.getText().toString())) {
            return false;
        }
        skipSponsorTextView.setText(newText);
        return true;
    }
}
