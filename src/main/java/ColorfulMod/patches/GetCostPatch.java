package ColorfulMod.patches;

import ColorfulMod.cards.AbstractColorCard;
import ColorfulMod.cards.PaintBrush;
import ColorfulMod.powers.InspirationPower;
import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class GetCostPatch {
    public GetCostPatch() {
    }

    @SpirePatch(
            clz = AbstractCard.class,
            method = "getCost",
            paramtypez = {
            }
    )
    public static class GetCostPatchPatch {
        @SpireInsertPatch(
                rloc = 5,
                localvars = {}
        )
        public static SpireReturn<String> Insert(AbstractCard c) {
            if (c instanceof AbstractColorCard) {
                return SpireReturn.Return(Integer.toString(((AbstractColorCard) c).realCost()));
            } else {
                return SpireReturn.Continue();
            }
        }
    }

}
