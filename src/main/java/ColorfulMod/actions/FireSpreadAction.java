package ColorfulMod.actions;

import ColorfulMod.cards.AbstractColorCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class FireSpreadAction extends AbstractGameAction {
    private AbstractPlayer p;
    private boolean upgraded;

    public FireSpreadAction(boolean _upgraded) {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.p = AbstractDungeon.player;
        this.duration = Settings.ACTION_DUR_FAST;
        this.upgraded = _upgraded;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            for (AbstractCard c : this.p.hand.group) {
                if (c instanceof AbstractColorCard && (this.upgraded || c.type == AbstractCard.CardType.ATTACK)) {
                    ((AbstractColorCard) c).setMyColor(AbstractColorCard.MyCardColor.RED, true);
                }
            }
        }
        this.tickDuration();
    }
}
