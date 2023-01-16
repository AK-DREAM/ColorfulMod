package ColorfulMod.powers;

import ColorfulMod.cards.AbstractColorCard;
import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import ColorfulMod.DefaultMod;
import ColorfulMod.util.TextureLoader;

public class MagicPaintPlusPower extends AbstractColorPower implements CloneablePowerInterface {
    public AbstractCreature source;

    public static final String POWER_ID = DefaultMod.makeID("MagicPaintPlusPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    // We create 2 new textures *Using This Specific Texture Loader* - an 84x84 image and a 32x32 one.
    private static final Texture tex84 = TextureLoader.getTexture("ColorfulModResources/images/powers/MagicPaintPower_84.png");
    private static final Texture tex32 = TextureLoader.getTexture("ColorfulModResources/images/powers/MagicPaintPower_32.png");

    public MagicPaintPlusPower(final AbstractCreature owner, final AbstractCreature source, final int amount) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;
        this.source = source;

        type = PowerType.BUFF;
        isTurnBased = false;

        // We load those textures here.
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    public int calc() {
        AbstractPower tmp = this.owner.getPower("Strength");
        return Math.max(0, (tmp == null ? 0 : tmp.amount) + 4);
    }

    @Override
    public void onUseCard(AbstractCard c, UseCardAction u) {
        AbstractPlayer p = AbstractDungeon.player;
        if (c instanceof AbstractColorCard && ((AbstractColorCard) c).myColor != AbstractColorCard.MyCardColor.NO_COLOR) {
            this.flash();
            for (int i = 1; i <= this.amount; i++) {
                this.addToBot(new DamageRandomEnemyAction(new DamageInfo(p, this.calc(), DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.NONE));
            }
        }
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + 4 + DESCRIPTIONS[1] + amount + DESCRIPTIONS[2];
    }

    @Override
    public AbstractPower makeCopy() {
        return new MagicPaintPlusPower(owner, source, amount);
    }
}
