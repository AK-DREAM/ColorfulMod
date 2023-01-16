package ColorfulMod.events;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.curses.Clumsy;
import com.megacrit.cardcrawl.cards.curses.Normality;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import ColorfulMod.DefaultMod;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;

public class ShrinkEnlargeMachineEvent extends AbstractImageEvent {

    public static final String ID = DefaultMod.makeID("ShrinkEnlargeMachineEvent");
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);

    private static final String NAME = eventStrings.NAME;
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;
    public static final String IMG = DefaultMod.makeEventPath("ShrinkEnlargeMachineEvent.png");

    private int screenNum = 0; // The initial screen we will see when encountering the event - screen 0;

    public ShrinkEnlargeMachineEvent() {
        super(NAME, DESCRIPTIONS[0], IMG);
        imageEventText.setDialogOption(OPTIONS[0], new Clumsy());
        imageEventText.setDialogOption(OPTIONS[1]);
        imageEventText.setDialogOption(OPTIONS[2]);
    }

    @Override
    protected void buttonEffect(int i) { // This is the event:
        switch (screenNum) {
            case 0:
                switch (i) {
                    case 0:
                        this.imageEventText.updateBodyText(DESCRIPTIONS[1]);
                        this.imageEventText.updateDialogOption(0, OPTIONS[2]);
                        AbstractDungeon.player.increaseMaxHp(MathUtils.round((float)AbstractDungeon.player.maxHealth * 0.5F), false);
                        CardCrawlGame.sound.play("HEAL_3");
                        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new Clumsy(), (float)Settings.WIDTH * 0.6F, (float)Settings.HEIGHT / 2.0F));
                        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new Clumsy(), (float)Settings.WIDTH * 0.3F, (float)Settings.HEIGHT / 2.0F));
                        break;
                    case 1:
                        this.imageEventText.updateBodyText(DESCRIPTIONS[2]);
                        this.imageEventText.updateDialogOption(0, OPTIONS[2]);
                        AbstractDungeon.player.decreaseMaxHealth(MathUtils.round((float)AbstractDungeon.player.maxHealth * 0.20F));
                        ++AbstractDungeon.player.masterHandSize;
                        break;
                    case 2:
                        this.imageEventText.updateBodyText(DESCRIPTIONS[2]);
                        this.imageEventText.updateDialogOption(0, OPTIONS[2]);
                        break;
                }
                this.imageEventText.clearRemainingOptions();
                screenNum = 1;
                break;
            case 1:
                switch (i) {
                    case 0: // If you press the first (and this should be the only) button,
                        openMap(); // You'll open the map and end the event.
                        break;
                }
                break;
        }
    }
}
