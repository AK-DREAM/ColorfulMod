package ColorfulMod.actions;

import ColorfulMod.cards.AbstractColorCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import ColorfulMod.cards.AbstractColorCard.*;

public class ColorXDrawAction extends AbstractGameAction {
    public ColorXDrawAction() {
        this.duration = 0.001F;
    }

    public void update() {
        AbstractDungeon.actionManager.addToTop(new WaitAction(0.05F));
        this.tickDuration();
        if (this.isDone) {
            for (AbstractCard c : DrawCardAction.drawnCards) {
                if (c.type == AbstractCard.CardType.SKILL) {
                    this.addToBot(new GainEnergyAction(1));
                }
            }
        }
    }
}
