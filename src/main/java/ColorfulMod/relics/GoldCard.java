package ColorfulMod.relics;

import ColorfulMod.actions.AddEmptyToHandAction;
import ColorfulMod.cards.AbstractColorCard;
import basemod.AutoAdd;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import ColorfulMod.DefaultMod;
import ColorfulMod.util.TextureLoader;
import static ColorfulMod.DefaultMod.makeRelicOutlinePath;
import static ColorfulMod.DefaultMod.makeRelicPath;

public class GoldCard extends CustomRelic {

    public static final String ID = DefaultMod.makeID("GoldCard");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("GoldCard.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("GoldCard.png"));

    public GoldCard() {
        super(ID, IMG, OUTLINE, RelicTier.STARTER, LandingSound.FLAT);
    }

    @Override
    public void atBattleStartPreDraw() {
        this.addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        AddEmptyToHandAction.AddEmptyToHand(AbstractColorCard.MyCardColor.GOLD, 1, false, true);
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
