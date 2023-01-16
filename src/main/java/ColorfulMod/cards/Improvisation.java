package ColorfulMod.cards;

import ColorfulMod.DefaultMod;
import ColorfulMod.actions.AddEmptyToHandAction;
import ColorfulMod.characters.ColorfulBrush;
import com.megacrit.cardcrawl.actions.watcher.ChooseOneAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

import static ColorfulMod.DefaultMod.makeCardPath;

public class Improvisation extends AbstractDynamicCard {
    // TEXT DECLARATION

    public static final String ID = DefaultMod.makeID(Improvisation.class.getSimpleName());
    public static final String IMG = makeCardPath("Improvisation.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.NONE;  //   since they don't change much.
    private static final CardType TYPE = CardType.SKILL;       //
    public static final CardColor COLOR = ColorfulBrush.Enums.COLOR_COLORFUL;

    private static final int COST = 1;
    private static final int UPGRADED_COST = 1;
    private static final int MAGIC = 2;
    private static final int UPGRADE_MAGIC = 1;
    // /STAT DECLARATION/


    public Improvisation() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = magicNumber = MAGIC;
        this.cardsToPreview = new Empty();
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        ArrayList<AbstractCard> Choices = new ArrayList();
        Choices.add(new Improvisation1());
        Choices.add(new Improvisation2());
        if (this.upgraded) {
            for (AbstractCard c : Choices) c.upgrade();
        }
        this.addToBot(new ChooseOneAction(Choices));
    }


    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(UPGRADED_COST);
            upgradeMagicNumber(UPGRADE_MAGIC);
            initializeDescription();
        }
    }
}
