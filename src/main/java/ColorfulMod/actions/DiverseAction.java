package ColorfulMod.actions;

import ColorfulMod.cards.AbstractColorCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;

import java.util.ArrayList;
import java.util.Iterator;

public class DiverseAction extends AbstractGameAction {
    AbstractPlayer p;
    AbstractColorCard.MyCardColor col;

    public DiverseAction(AbstractPlayer source, int amount, AbstractColorCard.MyCardColor col) {
        this.setValues(this.target, source, amount);
        this.actionType = ActionType.WAIT;
        this.p = source;
        this.col = col;
    }

    public void update() {
        ArrayList<AbstractColorCard.MyCardColor> colorList = new ArrayList();
        if (this.col != AbstractColorCard.MyCardColor.NO_COLOR) colorList.add(this.col);
        for (AbstractCard c : this.p.hand.group) {
            if (c instanceof AbstractColorCard) {
                if (((AbstractColorCard) c).myColor != AbstractColorCard.MyCardColor.NO_COLOR &&
                !colorList.contains(((AbstractColorCard) c).myColor)) {
                    colorList.add(((AbstractColorCard) c).myColor);
                }
            }
        }
        int toDraw = colorList.size() * this.amount;
        if (toDraw > 0) {
            this.addToTop(new DrawCardAction(this.source, toDraw));
        }
        this.isDone = true;
    }
}