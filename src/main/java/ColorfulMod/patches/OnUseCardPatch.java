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
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class OnUseCardPatch {
    public OnUseCardPatch() {
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
        @SpireInsertPatch(
                rloc = 38,
                localvars = {}
        )
        public static void Insert(AbstractPlayer __instance, AbstractCard c, AbstractMonster mo, int fff) {
            if (c instanceof AbstractColorCard) {
                ((AbstractColorCard) c).onUseCard();
            }
        }
    }
}
