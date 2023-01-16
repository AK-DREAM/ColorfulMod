package ColorfulMod.actions;

import ColorfulMod.cards.AbstractColorCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class ColorUpgradeAction extends AbstractGameAction {
    private AbstractPlayer p;

    public ColorUpgradeAction() {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.p = AbstractDungeon.player;
        this.duration = Settings.ACTION_DUR_FAST;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            for (AbstractCard c : this.p.hand.group) {
                if (c instanceof AbstractColorCard) {
                    if (((AbstractColorCard)c).myColor == AbstractColorCard.MyCardColor.RED) {
                        ((AbstractColorCard)c).ColorNumUpgCnt++;
                        c.superFlash(); c.applyPowers();
                    } else if (((AbstractColorCard)c).myColor == AbstractColorCard.MyCardColor.GREEN) {
                        ((AbstractColorCard)c).ColorNumUpgCnt++;
                        c.superFlash(); c.applyPowers();
                    }
                }
            }
        }
        this.tickDuration();
    }
}
