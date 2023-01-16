package ColorfulMod.actions;

import ColorfulMod.cards.AbstractColorCard;
import ColorfulMod.util.GetRandomColor;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import ColorfulMod.cards.AbstractColorCard.*;

public class ColorAfterDrawnAction extends AbstractGameAction {
    private int cnt;
    private MyCardColor col;
    private boolean random;
    public ColorAfterDrawnAction(int _cnt, boolean isRandom, MyCardColor _col) {
        this.duration = 0.001F;
        this.cnt = _cnt; this.col = _col;
        this.random = isRandom;
    }

    public void update() {
        AbstractDungeon.actionManager.addToTop(new WaitAction(0.05F));
        this.tickDuration();
        if (this.isDone) {
            for (AbstractCard c : DrawCardAction.drawnCards) {
                if (c instanceof AbstractColorCard && this.cnt > 0) {
                    if (this.random) ((AbstractColorCard) c).setMyColor(GetRandomColor.getColor(), true);
                    else ((AbstractColorCard)c).setMyColor(this.col, true);
                    --this.cnt;
                }
            }
        }
    }
}
