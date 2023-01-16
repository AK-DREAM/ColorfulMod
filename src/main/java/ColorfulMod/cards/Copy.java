package ColorfulMod.cards;

import ColorfulMod.DefaultMod;
import ColorfulMod.actions.CopyAction;
import ColorfulMod.characters.ColorfulBrush;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static ColorfulMod.DefaultMod.makeCardPath;

public class Copy extends AbstractDynamicCard {
    // TEXT DECLARATION

    public static final String ID = DefaultMod.makeID(Copy.class.getSimpleName());
    public static final String IMG = makeCardPath("Copy.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final String EXTENDED_DESCRIPTION[] = cardStrings.EXTENDED_DESCRIPTION;

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.NONE;  //   since they don't change much.
    private static final CardType TYPE = CardType.SKILL;       //
    public static final CardColor COLOR = ColorfulBrush.Enums.COLOR_COLORFUL;

    private static final int COST = 1;
    private static final int UPGRADED_COST = 1;
    private static AbstractCard lastCard;

    // /STAT DECLARATION/


    public Copy() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        lastCard = null;
        if (AbstractDungeon.actionManager.cardsPlayedThisTurn.size() > 0) {
            lastCard = AbstractDungeon.actionManager.cardsPlayedThisTurn.get(AbstractDungeon.actionManager.cardsPlayedThisTurn.size()-1);
        }
        if (lastCard == null) {
            this.rawDescription = DESCRIPTION + EXTENDED_DESCRIPTION[2];
            this.initializeDescription();
        } else {
            this.rawDescription = DESCRIPTION + EXTENDED_DESCRIPTION[0] + lastCard.name + EXTENDED_DESCRIPTION[1];
            this.initializeDescription();
        }
    }

    @Override
    public void onMoveToDiscard() {
        super.onMoveToDiscard();
        this.rawDescription = upgraded?UPGRADE_DESCRIPTION:DESCRIPTION;
        this.initializeDescription();
    }
    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (lastCard != null) {
            AbstractCard card = lastCard.makeStatEquivalentCopy();
            if (card.costForTurn >= 0) {
                card.setCostForTurn(0);
            }
            AbstractDungeon.actionManager.addToBottom(new CopyAction(card));
        }
    }


    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(UPGRADED_COST);
            this.selfRetain = true;
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
