package ColorfulMod.actions;

import ColorfulMod.cards.AbstractColorCard;
import ColorfulMod.util.GetRandomColor;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import ColorfulMod.cards.AbstractColorCard.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.cardRandomRng;

public class AddColorAction extends AbstractGameAction {
    private AbstractPlayer p;
    private boolean randomChoose = false;
    private boolean randomColor = false;
    private int cnt = 0;
    private MyCardColor myColor;
    private ArrayList<AbstractCard> cannotAddColor = new ArrayList();
    private ArrayList canAddColor = new ArrayList();

    public AddColorAction(boolean _randomChoose, boolean _randomColor, int _cnt, MyCardColor _myColor) {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.p = AbstractDungeon.player;
        this.duration = Settings.ACTION_DUR_XFAST;
        this.randomChoose = _randomChoose;
        this.randomColor = _randomColor;
        this.myColor = _myColor;
        this.cnt = _cnt;
    }
    public void DoAddColor(AbstractCard c) {
        if (this.randomColor) {
            ((AbstractColorCard)c).setMyColor(GetRandomColor.getColor(), true);
        } else {
            ((AbstractColorCard)c).setMyColor(myColor, true);
        }
    }
    public void update() {
        if (this.duration == Settings.ACTION_DUR_XFAST) {
            if (this.randomChoose) {
                for (int i = 0; i < this.p.hand.group.size(); i++) {
                    AbstractCard c = this.p.hand.group.get(i);
                    if (c instanceof AbstractColorCard && !((AbstractColorCard) c).cannotColor) {
                        this.canAddColor.add(i);
                    }
                }
                if (this.canAddColor.size() > 0) {
                    cnt = Math.min(cnt, this.canAddColor.size());
                    Collections.shuffle(this.canAddColor, new java.util.Random(AbstractDungeon.shuffleRng.randomLong()));
                    for (int i = 0; i < cnt; i++) DoAddColor(this.p.hand.group.get((int)this.canAddColor.get(i)));
                }
                this.isDone = true;
                return;
            } else {
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
                } else {
                    AbstractDungeon.handCardSelectScreen.open("Color", cnt, true, true, false, false);
                    this.tickDuration();
                    return;
                }
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
