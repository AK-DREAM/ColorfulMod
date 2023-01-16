package ColorfulMod.cards;

import ColorfulMod.DefaultMod;
import ColorfulMod.characters.ColorfulBrush;
import ColorfulMod.powers.InspirationPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import static ColorfulMod.DefaultMod.makeCardPath;

public class DeepDarkFantasy extends AbstractDynamicCard {
    // TEXT DECLARATION

    public static final String ID = DefaultMod.makeID(DeepDarkFantasy.class.getSimpleName());
    public static final String IMG = makeCardPath("DeepDarkFantasy.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.NONE;  //   since they don't change much.
    private static final CardType TYPE = CardType.SKILL;       //
    public static final CardColor COLOR = ColorfulBrush.Enums.COLOR_COLORFUL;

    private static final int COST = 2;
    private static final int MAGIC = 4;
    private static final int UPGRADE_MAGIC = 2;
    // /STAT DECLARATION/


    public DeepDarkFantasy() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.exhaust = true;
        this.magicNumber = this.baseMagicNumber = MAGIC;
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractPower tmp = p.getPower("ColorfulMod:InspirationPower");
        int amt = Math.min(tmp==null?0:tmp.amount, magicNumber);
        if (amt > 0) {
            this.addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, amt)));
        }
        tmp = p.getPower("Strength");
        amt = Math.min(amt+(tmp==null?0:tmp.amount), magicNumber);
        if (amt > 0) {
            this.addToBot(new ApplyPowerAction(p, p, new InspirationPower(p, p, amt)));
        }
    }


    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_MAGIC);
            initializeDescription();
        }
    }
}
