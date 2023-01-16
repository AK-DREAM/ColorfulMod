package ColorfulMod.powers;

import ColorfulMod.actions.ColorAfterDrawnAction;
import ColorfulMod.cards.AbstractColorCard;
import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import ColorfulMod.DefaultMod;
import ColorfulMod.util.TextureLoader;

public class WellPreparedPower extends AbstractColorPower implements CloneablePowerInterface {
    public AbstractCreature source;

    public static final String POWER_ID = DefaultMod.makeID("WellPreparedPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    // We create 2 new textures *Using This Specific Texture Loader* - an 84x84 image and a 32x32 one.
    private static final Texture tex84 = TextureLoader.getTexture("ColorfulModResources/images/powers/placeholder_power84.png");
    private static final Texture tex32 = TextureLoader.getTexture("ColorfulModResources/images/powers/placeholder_power32.png");

    public WellPreparedPower(final AbstractCreature owner, final AbstractCreature source, final int amount) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;
        this.source = source;

        type = PowerType.BUFF;
        isTurnBased = false;

        // We load those textures here.
        this.loadRegion("carddraw");

        updateDescription();
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }

    @Override
    public void atStartOfTurnPostDraw() {
        this.flash();
        this.addToBot(new DrawCardAction(this.amount, new ColorAfterDrawnAction(this.amount, false, AbstractColorCard.MyCardColor.RED)));
        this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, "ColorfulMod:WellPreparedPower"));
    }

    @Override
    public AbstractPower makeCopy() {
        return new WellPreparedPower(owner, source, amount);
    }
}
