package ColorfulMod.actions;


import ColorfulMod.cards.AbstractColorCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;
import java.util.Iterator;

public class GoldMagnetAction extends AbstractGameAction {
    public static final String[] TEXT;
    private AbstractPlayer p;
    private int numberOfCards;

    public GoldMagnetAction(int numberOfCards) {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = this.startDuration = Settings.ACTION_DUR_FAST;
        this.p = AbstractDungeon.player;
        this.numberOfCards = numberOfCards;
    }

    public void update() {
        if (this.duration == this.startDuration) {
            if (!this.p.discardPile.isEmpty() && this.numberOfCards > 0) {
                if (this.p.discardPile.size() <= this.numberOfCards) {
                    ArrayList<AbstractCard> cardsToMove = new ArrayList();
                    for (AbstractCard c : this.p.discardPile.group) {
                        cardsToMove.add(c);
                    }
                    for (AbstractCard c : cardsToMove) {
                        if (this.p.hand.size() < 10) {
                            if (c instanceof AbstractColorCard) {
                                ((AbstractColorCard) c).setMyColor(AbstractColorCard.MyCardColor.GOLD, false);
                            }
                            this.p.hand.addToHand(c);
                            this.p.discardPile.removeCard(c);
                        }
                        c.lighten(false);
                        c.applyPowers();
                    }
                    this.isDone = true;
                } else {
                    AbstractDungeon.gridSelectScreen.open(this.p.discardPile, this.numberOfCards, TEXT[0], false);
                    this.tickDuration();
                }
            } else {
                this.isDone = true;
            }
        } else {
            if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
                for (AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards) {
                    if (this.p.hand.size() < 10) {
                        if (c instanceof AbstractColorCard) {
                            ((AbstractColorCard) c).setMyColor(AbstractColorCard.MyCardColor.GOLD, false);
                        }
                        this.p.hand.addToHand(c);
                        this.p.discardPile.removeCard(c);
                    }
                    c.lighten(false);
                    c.unhover();
                    c.applyPowers();
                }
                for (AbstractCard c : this.p.discardPile.group) {
                    c.unhover();
                    c.target_x = (float) CardGroup.DISCARD_PILE_X;
                    c.target_y = 0.0F;
                }
                AbstractDungeon.gridSelectScreen.selectedCards.clear();
                AbstractDungeon.player.hand.refreshHandLayout();
            }
            this.tickDuration();
            if (this.isDone) {
                for (AbstractCard c : this.p.hand.group) {
                    c.applyPowers();
                }
            }

        }
    }

    static {
        TEXT = CardCrawlGame.languagePack.getUIString("BetterToHandAction").TEXT;
    }
}

