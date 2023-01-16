package ColorfulMod.relics;

import ColorfulMod.actions.AddEmptyToHandAction;
import ColorfulMod.cards.AbstractColorCard;
import basemod.AutoAdd;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import ColorfulMod.DefaultMod;
import ColorfulMod.util.TextureLoader;
import com.megacrit.cardcrawl.powers.StrengthPower;

import static ColorfulMod.DefaultMod.*;

public class MiniRainbow extends CustomRelic {

    public static final String ID = DefaultMod.makeID("MiniRainbow");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("MiniRainbow.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("MiniRainbow.png"));
    private boolean red = false;
    private boolean green = false;
    private boolean gold = false;
    private boolean usedThisTurn = false;

    public MiniRainbow() {
        super(ID, IMG, OUTLINE, RelicTier.RARE, LandingSound.SOLID);
    }

    @Override
    public void onUseCard(AbstractCard c, UseCardAction u) {
        if (c instanceof AbstractColorCard) {
            logger.info(((AbstractColorCard) c).myColor);
            switch (((AbstractColorCard) c).myColor) {
                case RED:
                    red = true; break;
                case GREEN:
                    green = true; break;
                case GOLD:
                    gold = true; break;
            }
            if (!usedThisTurn && red && green && gold) {
                this.flash();
                AbstractPlayer p = AbstractDungeon.player;
                this.addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, 2)));
                usedThisTurn = true; this.stopPulse();
            }
        }
    }
    @Override
    public void atPreBattle() {
        usedThisTurn = red = green = gold = false;
        this.beginPulse();
        this.pulse = true;
    }
    @Override
    public void atTurnStart() {
        usedThisTurn = red = green = gold = false;
        this.beginPulse();
        this.pulse = true;
    }
    @Override
    public void onVictory() {
        this.pulse = false;
    }
    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
