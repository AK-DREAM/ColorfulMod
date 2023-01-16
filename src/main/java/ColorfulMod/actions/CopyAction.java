//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package ColorfulMod.actions;

import ColorfulMod.DefaultMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class CopyAction extends AbstractGameAction {
    private AbstractCard card;
    AbstractPlayer p;

    public CopyAction(AbstractCard card) {
        this.duration = Settings.ACTION_DUR_FAST;
        this.p = AbstractDungeon.player;
        this.card = card;
    }

    public void update() {
        AbstractMonster target = AbstractDungeon.getMonsters().getRandomMonster(true);
        AbstractDungeon.player.limbo.group.add(this.card);
        this.card.current_x = (float)Settings.WIDTH / 2.0F;
        this.card.current_y = (float)Settings.HEIGHT / 2.0F;
        this.card.target_x = (float)Settings.WIDTH / 2.0F - 300.0F * Settings.scale;
        this.card.target_y = (float)Settings.HEIGHT / 2.0F;
        this.card.freeToPlayOnce = true;
        this.card.purgeOnUse = true;
        this.card.targetAngle = 0.0F;
        this.card.drawScale = 0.12F;
        this.card.applyPowers();
        AbstractDungeon.actionManager.currentAction = null;
        AbstractDungeon.actionManager.addToTop(this);
        AbstractDungeon.actionManager.cardQueue.add(new CardQueueItem(this.card, target));
        if (!Settings.FAST_MODE) {
            AbstractDungeon.actionManager.addToTop(new WaitAction(Settings.ACTION_DUR_MED));
        } else {
            AbstractDungeon.actionManager.addToTop(new WaitAction(Settings.ACTION_DUR_FASTER));
        }
        this.isDone = true;
    }
}
