package ColorfulMod.actions;

import ColorfulMod.cards.AbstractColorCard;
import ColorfulMod.cards.Empty;
import ColorfulMod.util.GetRandomColor;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class AddEmptyToHandAction {
    public static void AddEmptyToHand(AbstractColorCard.MyCardColor col, int cnt, boolean isRandom, boolean upgrade) {
        if (!isRandom) {
            AbstractColorCard c = new Empty();
            if (upgrade || AbstractDungeon.player.hasPower("ColorfulMod:SimplicityPower")) c.upgrade();
            c.setMyColor(col, false);
            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(c, cnt));
        } else {
            for (int i = 1; i <= cnt; i++) {
                AbstractColorCard c = new Empty();
                if (upgrade || AbstractDungeon.player.hasPower("ColorfulMod:SimplicityPower")) c.upgrade();
                c.setMyColor(GetRandomColor.getColor(), false);
                AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(c, 1));
            }
        }
    }
}
