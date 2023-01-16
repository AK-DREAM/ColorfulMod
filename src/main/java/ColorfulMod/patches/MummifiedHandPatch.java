package ColorfulMod.patches;

import ColorfulMod.cards.AbstractColorCard;
import ColorfulMod.cards.PaintBrush;
import ColorfulMod.powers.InspirationPower;
import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.MummifiedHand;

import java.util.ArrayList;

public class MummifiedHandPatch {
    public MummifiedHandPatch() {
    }

    @SpirePatch(
            clz = MummifiedHand.class,
            method = "onUseCard",
            paramtypez = {
                    AbstractCard.class,
                    UseCardAction.class
            }
    )
    public static class MummifiedHandPatchPatch {
        @SpireInsertPatch(
                rloc = 13,
                localvars = {"groupCopy"}
        )
        public static void Insert(MummifiedHand __instance, AbstractCard ccc, UseCardAction u, @ByRef ArrayList<AbstractCard> gg[]) {
            for (AbstractCard c : AbstractDungeon.player.hand.group) {
                if (c instanceof AbstractColorCard && ((AbstractColorCard) c).realCost() == 0) {
                    gg[0].remove(c);
                }
            }
        }
    }

}
