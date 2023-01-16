package ColorfulMod.actions;

import ColorfulMod.cards.AbstractColorCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class GlitteringPowerAction extends AbstractGameAction {
    private int amount;

    public GlitteringPowerAction(int amt) {
        this.amount = amt;
    }

    public void update() {
        for (AbstractCard c : AbstractDungeon.player.hand.group) {
            if (c.selfRetain || c.retain) {
                if (c instanceof AbstractColorCard && ((AbstractColorCard) c).myColor != AbstractColorCard.MyCardColor.NO_COLOR) {
                    for (int i = 1; i <= this.amount; i++) {
                        ((AbstractColorCard) c).EvokeColor(((AbstractColorCard) c).myColor);
                    }
                }
            }
        }
        this.isDone = true;
    }
}