package ColorfulMod.powers;

import ColorfulMod.actions.AddColorAction;
import ColorfulMod.actions.AddColorAction2;
import ColorfulMod.actions.ColorAfterDrawnAction;
import ColorfulMod.cards.AbstractColorCard;
import ColorfulMod.cards.FireSpread;
import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import ColorfulMod.DefaultMod;
import ColorfulMod.util.TextureLoader;

public class ColorfulFormPower2 extends AbstractColorPower implements CloneablePowerInterface {
    public AbstractCreature source;

    public static final String POWER_ID = DefaultMod.makeID("ColorfulFormPower2");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    // We create 2 new textures *Using This Specific Texture Loader* - an 84x84 image and a 32x32 one.
    private static final Texture tex84 = TextureLoader.getTexture("ColorfulModResources/images/powers/ColorfulFormPower_84.png");
    private static final Texture tex32 = TextureLoader.getTexture("ColorfulModResources/images/powers/ColorfulFormPower_32.png");

    public ColorfulFormPower2(final AbstractCreature owner, final AbstractCreature source) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.source = source;

        type = PowerType.BUFF;
        isTurnBased = false;

        // We load those textures here.
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0];
    }

    @Override
    public void onCardDraw(AbstractCard c) {
        if (c instanceof AbstractColorCard && !((AbstractColorCard) c).cannotColor && ((AbstractColorCard) c).myColor == AbstractColorCard.MyCardColor.NO_COLOR) {
            if (c.type == AbstractCard.CardType.ATTACK) {
                ((AbstractColorCard) c).setMyColor(AbstractColorCard.MyCardColor.RED, true);
            } else if (c.type == AbstractCard.CardType.SKILL) {
                ((AbstractColorCard) c).setMyColor(AbstractColorCard.MyCardColor.GREEN, true);
            } else if (c.type == AbstractCard.CardType.POWER) {
                ((AbstractColorCard) c).setMyColor(AbstractColorCard.MyCardColor.GOLD, true);
            }
        }
    }

    @Override
    public AbstractPower makeCopy() {
        return new ColorfulFormPower(owner, source);
    }
}
