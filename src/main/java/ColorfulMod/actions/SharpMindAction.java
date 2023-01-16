package ColorfulMod.actions;

import ColorfulMod.cards.AbstractColorCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import ColorfulMod.cards.AbstractColorCard.*;

public class SharpMindAction extends AbstractGameAction {
    private int cnt;
    private MyCardColor col;
    public SharpMindAction(int _cnt, MyCardColor _col) {
        this.duration = 0.001F;
        this.cnt = _cnt; this.col = _col;
    }

    public void update() {
        AbstractDungeon.actionManager.addToTop(new WaitAction(0.05F));
        this.tickDuration();
        if (this.isDone) {
            for (AbstractCard c : DrawCardAction.drawnCards) {
                if (c instanceof AbstractColorCard && c.type == AbstractCard.CardType.SKILL && this.cnt > 0) {
                    ((AbstractColorCard)c).setMyColor(this.col, true);
                    --this.cnt;
                }
            }
        }
    }
}
