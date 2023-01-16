package ColorfulMod.relics;

import ColorfulMod.actions.AddColorAction;
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

public class MarkOfChaos extends CustomRelic {

    public static final String ID = DefaultMod.makeID("MarkOfChaos");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("MarkOfChaos.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("MarkOfChaos.png"));

    public MarkOfChaos() {
        super(ID, IMG, OUTLINE, RelicTier.SPECIAL, LandingSound.MAGICAL);
    }

    @Override
    public void atTurnStartPostDraw() {
        this.addToBot(new AddColorAction(true, true, 2, AbstractColorCard.MyCardColor.NO_COLOR));
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
