package ColorfulMod.patches;

import ColorfulMod.cards.AbstractColorCard;
import ColorfulMod.cards.PaintBrush;
import ColorfulMod.powers.InspirationPower;
import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class AddToHandPatch {
    public AddToHandPatch() {
    }

    @SpirePatch(
            clz = CardGroup.class,
            method = "addToHand",
            paramtypez = {
                    AbstractCard.class
            }
    )
    public static class AddToHandPatchPatch {
        @SpireInsertPatch(
                rloc = 0,
                localvars = {}
        )
        public static void Insert(CardGroup cg, @ByRef AbstractCard c[]) {
            if (cg.type == CardGroup.CardGroupType.HAND && c[0] instanceof AbstractColorCard) {
                AbstractPlayer p = AbstractDungeon.player;
                if (c[0] instanceof PaintBrush) {
                    AbstractDungeon.actionManager.addToBottom(
                            new ApplyPowerAction(p, p, new InspirationPower(p, p, c[0].magicNumber)));
                }
                if (((AbstractColorCard) c[0]).defaultColor != AbstractColorCard.MyCardColor.NO_COLOR) {
                    ((AbstractColorCard) c[0]).setMyColor(((AbstractColorCard) c[0]).defaultColor, true);
                }
            }
        }
    }

}
