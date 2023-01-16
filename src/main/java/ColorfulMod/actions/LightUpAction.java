package ColorfulMod.actions;

import ColorfulMod.cards.AbstractColorCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import ColorfulMod.cards.AbstractColorCard.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.cardRandomRng;

public class LightUpAction extends AbstractGameAction {
    private AbstractPlayer p;
    private MyCardColor myColor;
    private ArrayList<AbstractCard> cannotAddColor = new ArrayList();
    private ArrayList<AbstractCard> AddColor = new ArrayList();

    public LightUpAction() {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.p = AbstractDungeon.player;
        this.duration = Settings.ACTION_DUR_FAST;
        this.myColor = MyCardColor.RED;
    }
    public void DoAddColor(AbstractCard c) {
        if (((AbstractColorCard)c).myColor != MyCardColor.NO_COLOR)
            this.addToBot(new GainEnergyAction(1));
        ((AbstractColorCard)c).setMyColor(myColor, true);
    }
    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            for (AbstractCard c : this.p.hand.group) {
                if (!(c instanceof AbstractColorCard) || ((AbstractColorCard) c).cannotColor) {
                    this.cannotAddColor.add(c);
                }
            }
            this.p.hand.group.removeAll(this.cannotAddColor);
            if (this.p.hand.group.size() == 0) {
                this.returnCards();
                this.isDone = true;
                return;
            } else if (this.p.hand.group.size() == 1) {
                DoAddColor(this.p.hand.group.get(0));
                this.returnCards();
                this.isDone = true;
                return;
            } else {
                AbstractDungeon.handCardSelectScreen.open("Color", 1, false, false, false, false);
                this.tickDuration();
                return;
            }
        }
        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
            for (AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group) {
                DoAddColor(c); this.p.hand.addToTop(c);
            }
            this.returnCards();
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            AbstractDungeon.handCardSelectScreen.selectedCards.group.clear();
            this.isDone = true;
        }
        this.tickDuration();
    }

    private void returnCards() {
        Iterator var1 = this.cannotAddColor.iterator();
        while(var1.hasNext()) {
            AbstractCard c = (AbstractCard)var1.next();
            this.p.hand.addToTop(c);
        }
        this.p.hand.refreshHandLayout();
    }
}
