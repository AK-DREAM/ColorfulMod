package ColorfulMod.relics;

import ColorfulMod.actions.AddEmptyToHandAction;
import ColorfulMod.cards.AbstractColorCard;
import basemod.AutoAdd;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import ColorfulMod.DefaultMod;
import ColorfulMod.util.TextureLoader;
import com.megacrit.cardcrawl.powers.RetainCardPower;

import static ColorfulMod.DefaultMod.makeRelicOutlinePath;
import static ColorfulMod.DefaultMod.makeRelicPath;

public class WastepaperBasket extends CustomRelic {

    public static final String ID = DefaultMod.makeID("WastepaperBasket");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("WastepaperBasket.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("WastepaperBasket.png"));

    public WastepaperBasket() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.FLAT);
    }

    @Override
    public void atBattleStartPreDraw() {
        AbstractPlayer p = AbstractDungeon.player;
        this.flash();
        this.addToBot(new ApplyPowerAction(p, p, new RetainCardPower(p, 1)));
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
