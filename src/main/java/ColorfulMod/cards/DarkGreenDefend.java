package ColorfulMod.cards;

import ColorfulMod.DefaultMod;
import ColorfulMod.actions.AddEmptyToHandAction;
import ColorfulMod.characters.ColorfulBrush;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static ColorfulMod.DefaultMod.makeCardPath;

public class DarkGreenDefend extends AbstractDynamicCard {
    // TEXT DECLARATION

    public static final String ID = DefaultMod.makeID(DarkGreenDefend.class.getSimpleName());
    public static final String IMG = makeCardPath("DarkGreenDefend.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.SELF;  //   since they don't change much.
    private static final CardType TYPE = CardType.SKILL;       //
    public static final CardColor COLOR = ColorfulBrush.Enums.COLOR_COLORFUL;

    private static final int COST = 2;
    private static final int UPGRADED_COST = 2;
    private static final int BLOCK = 8;
    private static final int UPGRADE_BLOCK = 4;

    // /STAT DECLARATION/


    public DarkGreenDefend() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseBlock = block = BLOCK;
        this.cardsToPreview = new Empty();
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new GainBlockAction(p, p, block));
        AddEmptyToHandAction.AddEmptyToHand(MyCardColor.GREEN, 2, false, false);
    }


    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(UPGRADED_COST);
            upgradeBlock(UPGRADE_BLOCK);
            initializeDescription();
        }
    }
}
