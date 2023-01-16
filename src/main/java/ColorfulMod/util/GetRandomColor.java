package ColorfulMod.util;

import ColorfulMod.cards.AbstractColorCard.*;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.cardRandomRng;

public class GetRandomColor {
    public static MyCardColor getColor() {
        int col = cardRandomRng.random(2);
        return col==0?MyCardColor.RED:col==1?MyCardColor.GREEN:MyCardColor.GOLD;
    }
}
