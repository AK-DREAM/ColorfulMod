package ColorfulMod.relics;

import ColorfulMod.actions.AddColorAction;
import ColorfulMod.actions.AddEmptyToHandAction;
import ColorfulMod.cards.AbstractColorCard;
import basemod.AutoAdd;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import ColorfulMod.DefaultMod;
import ColorfulMod.util.TextureLoader;
import static ColorfulMod.DefaultMod.makeRelicOutlinePath;
import static ColorfulMod.DefaultMod.makeRelicPath;

public class MimeticPaint extends CustomRelic {

    public static final String ID = DefaultMod.makeID("MimeticPaint");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("MimeticPaint.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("MimeticPaint.png"));

    public MimeticPaint() {
        super(ID, IMG, OUTLINE, RelicTier.RARE, LandingSound.HEAVY);
    }

    @Override
    public void onUseCard(AbstractCard c, UseCardAction u) {
        if (c instanceof AbstractColorCard && ((AbstractColorCard) c).myColor != AbstractColorCard.MyCardColor.NO_COLOR) {
            this.flash();
            AbstractPlayer p = AbstractDungeon.player;
            this.addToBot(new GainBlockAction(p, p, 1));
        }
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
