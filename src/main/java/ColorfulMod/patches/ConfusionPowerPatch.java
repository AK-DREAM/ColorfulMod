package ColorfulMod.patches;

import ColorfulMod.cards.AbstractColorCard;
import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.ConfusionPower;

public class ConfusionPowerPatch {
    public ConfusionPowerPatch() {
    }

    @SpirePatch(
            clz = ConfusionPower.class,
            method = "onCardDraw",
            paramtypez = {
                    AbstractCard.class
            }
    )
    public static class ConfusionPowerPatchPatch {
        @SpireInsertPatch(
                rloc = 4,
                localvars = {"newCost"}
        )
        public static void Insert(ConfusionPower foo, @ByRef AbstractCard c[], int newCost) {

        }
    }

}
