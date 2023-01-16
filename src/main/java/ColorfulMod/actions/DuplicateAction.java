package ColorfulMod.actions;

import ColorfulMod.cards.AbstractColorCard;
import ColorfulMod.cards.Empty;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainGoldAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.vfx.RainingGoldEffect;

import java.util.Iterator;

public class DuplicateAction extends AbstractGameAction {
    private static final UIStrings uiStrings;
    public static final String[] TEXT;
    private AbstractPlayer p;
    public static int numExhausted;
    private int cnt;

    public DuplicateAction(int amount, int gg) {
        this.p = AbstractDungeon.player;
        this.amount = amount;
        this.duration = this.startDuration = Settings.ACTION_DUR_FAST;
        this.actionType = ActionType.EXHAUST;
        this.cnt = gg;
    }

    public void chkCard(AbstractCard c) {
        AbstractColorCard tmp = new Empty();
        if (c instanceof AbstractColorCard) {
            tmp.myColor = ((AbstractColorCard) c).myColor;
            tmp.BaseColorNumber = ((AbstractColorCard) c).BaseColorNumber;
            tmp.ColorNumber = ((AbstractColorCard) c).ColorNumber;
            tmp.ColorNumUpgCnt = ((AbstractColorCard) c).ColorNumUpgCnt;
        }
        this.addToBot(new MakeTempCardInHandAction(tmp, this.cnt));
    }

    public void update() {
        if (this.duration == this.startDuration) {
            if (this.p.hand.size() == 0) {
                this.isDone = true;
                return;
            } else if (this.p.hand.size() == 1) {
                AbstractCard c = this.p.hand.group.get(0);
                chkCard(c);
                this.p.hand.moveToExhaustPile(c);
                this.isDone = true;
                return;
            }
            numExhausted = this.amount;
            AbstractDungeon.handCardSelectScreen.open(TEXT[0], this.amount, false, false);
            this.tickDuration();
            return;
        }

        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
            for (AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group) {
                chkCard(c);
                this.p.hand.moveToExhaustPile(c);
            }
            CardCrawlGame.dungeon.checkForPactAchievement();
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
        }

        this.tickDuration();
    }

    static {
        uiStrings = CardCrawlGame.languagePack.getUIString("ExhaustAction");
        TEXT = uiStrings.TEXT;
    }
}