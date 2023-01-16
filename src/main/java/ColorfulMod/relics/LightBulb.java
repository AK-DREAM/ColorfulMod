package ColorfulMod.relics;

import ColorfulMod.actions.AddEmptyToHandAction;
import ColorfulMod.cards.AbstractColorCard;
import ColorfulMod.powers.InspirationPower;
import basemod.AutoAdd;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import ColorfulMod.DefaultMod;
import ColorfulMod.util.TextureLoader;
import static ColorfulMod.DefaultMod.makeRelicOutlinePath;
import static ColorfulMod.DefaultMod.makeRelicPath;

public class LightBulb extends CustomRelic {

    public static final String ID = DefaultMod.makeID("LightBulb");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("LightBulb.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("LightBulb.png"));

    public LightBulb() {
        super(ID, IMG, OUTLINE, RelicTier.COMMON, LandingSound.CLINK);
    }

    @Override
    public void atBattleStartPreDraw() {
        AbstractPlayer p = AbstractDungeon.player;
        this.addToBot(new RelicAboveCreatureAction(p, this));
        this.addToBot(new ApplyPowerAction(p, p, new InspirationPower(p, p, 1)));
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
