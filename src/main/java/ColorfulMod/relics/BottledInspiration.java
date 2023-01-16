package ColorfulMod.relics;

import ColorfulMod.powers.InspirationPower;
import ColorfulMod.powers.LoseInspirationPower;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import ColorfulMod.DefaultMod;
import ColorfulMod.util.TextureLoader;
import static ColorfulMod.DefaultMod.makeRelicOutlinePath;
import static ColorfulMod.DefaultMod.makeRelicPath;

public class BottledInspiration extends CustomRelic {

    public static final String ID = DefaultMod.makeID("BottledInspiration");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("BottledInspiration.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("BottledInspiration.png"));

    public BottledInspiration() {
        super(ID, IMG, OUTLINE, RelicTier.RARE, LandingSound.MAGICAL);
    }


    @Override
    public void onUseCard(AbstractCard c, UseCardAction u) {
        AbstractPlayer p = AbstractDungeon.player;
        if (c.costForTurn == 0 && this.counter < 3) {
            this.addToBot(new ApplyPowerAction(p, p, new InspirationPower(p, p, 1)));
            this.addToBot(new ApplyPowerAction(p, p, new LoseInspirationPower(p, p, 1)));
            ++this.counter;
        }
    }

    @Override
    public void atTurnStart() { this.counter = 0; }

    @Override
    public void atBattleStart() { this.counter = 0; }

    @Override
    public void onVictory() {
        this.counter = -1;
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
