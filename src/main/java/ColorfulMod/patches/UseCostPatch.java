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
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import javassist.CannotCompileException;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;

public class UseCostPatch {
    public UseCostPatch() {
    }

    @SpirePatch(
            clz = AbstractPlayer.class,
            method = "useCard",
            paramtypez = {
                    AbstractCard.class,
                    AbstractMonster.class,
                    int.class
            }
    )
    public static class OnUseCardPatchPatch {
        public static ExprEditor Instrument() {
            return new ExprEditor() {
                public void edit(MethodCall m) throws CannotCompileException {
                    if (m.getClassName().equals(EnergyManager.class.getName()) && m.getMethodName().equals("use")) {
                        String colorCard = AbstractColorCard.class.getName();
                        m.replace("{ " +
                                "if (c instanceof " + colorCard + ")" +
                                "{ $1 = ((" + colorCard + ")c).realCost(); }" +
                                "$_ = $proceed($$); }");
                    }
                }
            };
        }

//        @SpireInsertPatch(
//                rloc = 29,
//                localvars = {}
//        )
//        public static void Insert(AbstractPlayer __instance, @ByRef AbstractCard c[], AbstractMonster mo, int fff) {
//            if (c[0] instanceof AbstractColorCard) {
//                c[0].costForTurn = ((AbstractColorCard) c[0]).realCost();
//            }
//        }
    }
}
