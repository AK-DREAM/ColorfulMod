package ColorfulMod.cards;

import ColorfulMod.DefaultMod;
import ColorfulMod.characters.ColorfulBrush;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

import static ColorfulMod.DefaultMod.makeCardPath;
import static ColorfulMod.characters.ColorfulBrush.Enums.GOLD_CARD;
import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.*;

public class GoldTouch extends AbstractDynamicCard {
    // TEXT DECLARATION

    public static final String ID = DefaultMod.makeID(GoldTouch.class.getSimpleName());
    public static final String IMG = makeCardPath("GoldTouch.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.NONE;  //   since they don't change much.
    private static final CardType TYPE = CardType.SKILL;       //
    public static final CardColor COLOR = ColorfulBrush.Enums.COLOR_COLORFUL;

    private static final int COST = 1;
    private static final int UPGRADED_COST = 0;

    // /STAT DECLARATION/


    public GoldTouch() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.exhaust = true;
    }

    public AbstractCard getGoldCard() {
        ArrayList<AbstractCard> list = new ArrayList();
        for (AbstractCard c : srcCommonCardPool.group)
            if (c.hasTag(GOLD_CARD)) list.add(c);
        for (AbstractCard c : srcUncommonCardPool.group)
            if (c.hasTag(GOLD_CARD)) list.add(c);
        for (AbstractCard c : srcRareCardPool.group)
            if (c.hasTag(GOLD_CARD)) list.add(c);
        return list.get(cardRandomRng.random(list.size() - 1));
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractCard c = getGoldCard();
        c.setCostForTurn(0);
        this.addToBot(new MakeTempCardInHandAction(c, true));
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(UPGRADED_COST);
            initializeDescription();
        }
    }
}
