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

public class AddColorAction2 extends AbstractGameAction {
    private AbstractPlayer p;
    private ArrayList canAddColor = new ArrayList();

    public AddColorAction2() {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.p = AbstractDungeon.player;
        this.duration = Settings.ACTION_DUR_XFAST;
    }
    public void DoAddColor(AbstractCard c) {
        ((AbstractColorCard)c).setMyColor(GetRandomColor.getColor(), true);
    }
    public void update() {
        if (this.duration == Settings.ACTION_DUR_XFAST) {
            for (int i = 0; i < this.p.hand.group.size(); i++) {
                AbstractCard c = this.p.hand.group.get(i);
                if (c instanceof AbstractColorCard && !((AbstractColorCard) c).cannotColor && ((AbstractColorCard) c).myColor == MyCardColor.NO_COLOR) {
                    this.canAddColor.add(i);
                }
            }
            if (this.canAddColor.size() != 0) {
                int id = (int)this.canAddColor.get(cardRandomRng.random(this.canAddColor.size()-1));
                DoAddColor(this.p.hand.group.get(id));
            }
            this.isDone = true;
            return;
        }
        this.tickDuration();
    }
}
