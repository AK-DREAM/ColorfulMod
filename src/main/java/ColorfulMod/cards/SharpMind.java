package ColorfulMod.cards;

import ColorfulMod.DefaultMod;
import ColorfulMod.actions.ColorAfterDrawnAction;
import ColorfulMod.actions.SharpMindAction;
import ColorfulMod.characters.ColorfulBrush;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static ColorfulMod.DefaultMod.makeCardPath;

public class SharpMind extends AbstractDynamicCard {
    // TEXT DECLARATION

    public static final String ID = DefaultMod.makeID(SharpMind.class.getSimpleName());
    public static final String IMG = makeCardPath("SharpMind.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.SELF;  //   since they don't change much.
    private static final CardType TYPE = CardType.SKILL;       //
    public static final CardColor COLOR = ColorfulBrush.Enums.COLOR_COLORFUL;

    private static final int COST = 0;
    private static final int UPGRADED_COST = 0;

    // /STAT DECLARATION/


    public SharpMind() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (!this.upgraded) {
            this.addToBot(new DrawCardAction(1, new SharpMindAction(1, MyCardColor.GREEN)));
        } else {
            this.addToBot(new DrawCardAction(1, new ColorAfterDrawnAction(1, false, MyCardColor.GREEN)));
        }
    }


    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(UPGRADED_COST);
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
