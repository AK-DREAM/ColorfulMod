package ColorfulMod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import ColorfulMod.DefaultMod;
import ColorfulMod.util.TextureLoader;
import static ColorfulMod.DefaultMod.makeRelicOutlinePath;
import static ColorfulMod.DefaultMod.makeRelicPath;

public class FlamePaint extends CustomRelic {

    public static final String ID = DefaultMod.makeID("FlamePaint");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("FlamePaint.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("FlamePaint.png"));

    public FlamePaint() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.CLINK);
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
