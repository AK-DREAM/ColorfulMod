package ColorfulMod.actions;

import ColorfulMod.cards.AbstractColorCard;
import ColorfulMod.cards.GoldenRare;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;
import java.util.Iterator;

public class GoldEvokeAction extends AbstractGameAction {
    private AbstractPlayer p;

    private boolean isRandom;
    public GoldEvokeAction(boolean isRandom) {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.p = AbstractDungeon.player;
        this.duration = Settings.ACTION_DUR_FAST;
        this.isRandom = isRandom;
    }

    private ArrayList<AbstractCard> tmp = new ArrayList(), tmp2 = new ArrayList<>();

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {

            for (AbstractCard c : this.p.hand.group) {
                if (c instanceof AbstractColorCard && Math.max(0, c.costForTurn-((AbstractColorCard) c).goldCnt) > 0 &&
                        (((AbstractColorCard) c).myColor != AbstractColorCard.MyCardColor.NO_COLOR)) {
                    tmp2.add(c);
                } else tmp.add(c);
            }
            if (this.isRandom) {
                if (tmp2.size() > 0) {
                    AbstractCard c = null;
                    for (AbstractCard c2 : tmp2) {
                        if (c2 instanceof GoldenRare) { c = c2; break; }
                    }
                    if (c == null) c = tmp2.get(AbstractDungeon.cardRng.random(tmp2.size()-1));
                    ((AbstractColorCard)c).setCostForTurnGold(1);
                    c.superFlash();
                }
                this.isDone = true;
                return;
            }
            this.p.hand.group.removeAll(this.tmp);
            if (this.p.hand.group.size() == 0) {
                this.returnCards();
                this.isDone = true;
                return;
            } else if (this.p.hand.group.size() == 1) {
                AbstractCard c = this.p.hand.group.get(0);
                ((AbstractColorCard)c).setCostForTurnGold(1);
                c.superFlash();
                this.returnCards();
                this.isDone = true;
                return;
            }
            AbstractDungeon.handCardSelectScreen.open("-1C", 1, false, false, false, false);
            this.tickDuration();
            return;
        }
        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
            for (AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group) {
                this.p.hand.addToTop(c);
                ((AbstractColorCard)c).setCostForTurnGold(1);
                c.superFlash();
            }
            this.returnCards();
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            AbstractDungeon.handCardSelectScreen.selectedCards.group.clear();
            this.isDone = true;
        }
        this.tickDuration();
    }

    private void returnCards() {
        for (AbstractCard c : tmp) this.p.hand.addToTop(c);
        this.p.hand.refreshHandLayout();
    }
}
