package ColorfulMod.actions;

import ColorfulMod.cards.AbstractColorCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainGoldAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.vfx.RainingGoldEffect;

import java.util.Iterator;

public class SellAction extends AbstractGameAction {
    private static final UIStrings uiStrings;
    public static final String[] TEXT;
    private AbstractPlayer p;
    public static int numExhausted;
    private int goldgold;

    public SellAction(int amount, int gg) {
        this.p = AbstractDungeon.player;
        this.amount = amount;
        this.duration = this.startDuration = Settings.ACTION_DUR_FAST;
        this.actionType = ActionType.EXHAUST;
        this.goldgold = gg;
    }

    public void chkCard(AbstractCard c) {
        if (c instanceof AbstractColorCard) {
            int addGold = 0;
            if (((AbstractColorCard) c).myColor == AbstractColorCard.MyCardColor.RED ||
                    ((AbstractColorCard) c).myColor == AbstractColorCard.MyCardColor.GREEN) {
                addGold = this.goldgold;
            } else if (((AbstractColorCard) c).myColor == AbstractColorCard.MyCardColor.GOLD) {
                addGold = this.goldgold*2;
            }
            if (addGold > 0) {
                AbstractDungeon.effectList.add(new RainingGoldEffect(addGold * 2, true));
                this.addToBot(new GainGoldAction(addGold));
            }
        }
    }

    public void update() {
        if (this.duration == this.startDuration) {
            if (this.p.hand.size() == 0) {
                this.isDone = true;
                return;
            } else if (this.p.hand.size() == 1) {
                AbstractCard c = this.p.hand.group.get(0);
                chkCard(c);
                this.p.hand.moveToExhaustPile(c);
                this.isDone = true;
                return;
            }
            numExhausted = this.amount;
            AbstractDungeon.handCardSelectScreen.open(TEXT[0], this.amount, false, false);
            this.tickDuration();
            return;
        }

        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
            for (AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group) {
                chkCard(c);
                this.p.hand.moveToExhaustPile(c);
            }
            CardCrawlGame.dungeon.checkForPactAchievement();
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
        }

        this.tickDuration();
    }

    static {
        uiStrings = CardCrawlGame.languagePack.getUIString("ExhaustAction");
        TEXT = uiStrings.TEXT;
    }
}