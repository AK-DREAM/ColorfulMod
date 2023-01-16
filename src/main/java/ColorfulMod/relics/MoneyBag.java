package ColorfulMod.relics;

import ColorfulMod.actions.AddColorAction;
import ColorfulMod.cards.AbstractColorCard;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import ColorfulMod.DefaultMod;
import ColorfulMod.util.TextureLoader;
import com.megacrit.cardcrawl.actions.common.GainGoldAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;

import static ColorfulMod.DefaultMod.makeRelicOutlinePath;
import static ColorfulMod.DefaultMod.makeRelicPath;

public class MoneyBag extends CustomRelic {

    public static final String ID = DefaultMod.makeID("MoneyBag");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("MoneyBag.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("MoneyBag.png"));

    public MoneyBag() {
        super(ID, IMG, OUTLINE, RelicTier.SHOP, LandingSound.CLINK);
    }

    @Override
    public void onUseCard(AbstractCard c, UseCardAction u) {
        if (c instanceof AbstractColorCard && ((AbstractColorCard) c).myColor == AbstractColorCard.MyCardColor.GOLD) {
            this.flash();
            this.addToBot(new GainGoldAction(7));
        }
    }
    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
