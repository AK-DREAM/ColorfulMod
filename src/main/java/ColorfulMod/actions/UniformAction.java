package ColorfulMod.actions;

import ColorfulMod.cards.AbstractColorCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;
import java.util.Iterator;

public class UniformAction extends AbstractGameAction {
    private AbstractPlayer p;

    public UniformAction() {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.p = AbstractDungeon.player;
        this.duration = Settings.ACTION_DUR_FAST;
    }

    private ArrayList<AbstractCard> tmp = new ArrayList();

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            for (AbstractCard c : this.p.hand.group) {
                if (c instanceof AbstractColorCard && ((AbstractColorCard) c).myColor != AbstractColorCard.MyCardColor.NO_COLOR) {
                    // do nothing
                } else tmp.add(c);
            }
            this.p.hand.group.removeAll(this.tmp);
            if (this.p.hand.group.size() == 0) {
                this.returnCards();
                this.isDone = true;
                return;
            } else if (this.p.hand.group.size() == 1) {
                AbstractCard c = this.p.hand.group.get(0);
                this.returnCards();
                this.addToBot(new AddColorAction(true, false, this.p.hand.group.size(), ((AbstractColorCard)c).myColor));
                this.isDone = true;
                return;
            }
            AbstractDungeon.handCardSelectScreen.open("Uniform", 1, false, false, false, false);
            this.tickDuration();
            return;
        }
        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
            AbstractCard c = AbstractDungeon.handCardSelectScreen.selectedCards.group.get(0);
            this.p.hand.addToTop(c);
            this.returnCards();
            this.addToBot(new AddColorAction(true, false, this.p.hand.group.size(), ((AbstractColorCard)c).myColor));
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
