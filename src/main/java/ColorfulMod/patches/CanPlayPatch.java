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
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

public class CanPlayPatch {
    public CanPlayPatch() {
    }

    @SpirePatch(
            clz = AbstractCard.class,
            method = "hasEnoughEnergy",
            paramtypez = {
            }
    )
    public static class CanPlayPatchPatch {
        @SpireInsertPatch(
                rloc = 35,
                localvars = {}
        )
        public static SpireReturn<Boolean> Insert(AbstractCard c) {
            if (c instanceof AbstractColorCard && EnergyPanel.totalCount >= ((AbstractColorCard) c).realCost()) {
                return SpireReturn.Return(true);
            } else {
                return SpireReturn.Continue();
            }
        }
    }

}
