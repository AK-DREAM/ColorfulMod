package ColorfulMod.cards;
import ColorfulMod.DefaultMod;
import ColorfulMod.actions.AddColorAction;
import ColorfulMod.actions.GoldEvokeAction;
import ColorfulMod.actions.ColorUpgradeAction;
import ColorfulMod.powers.AbstractColorPower;
import ColorfulMod.powers.MagicPaintPlusPower;
import ColorfulMod.powers.MagicPaintPower;
import ColorfulMod.util.GetRandomColor;
import basemod.abstracts.CustomCard;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import java.lang.reflect.InvocationTargetException;

public abstract class AbstractColorCard extends CustomCard {

    // Custom Abstract Cards can be a bit confusing. While this is a simple base for simply adding a second magic number,
    // if you're new to modding I suggest you skip this file until you know what unique things that aren't provided
    // by default, that you need in your own cards.

    // In this example, we use a custom Abstract Card in order to define a new magic number. From here on out, we can
    // simply use that in our cards, so long as we put "extends AbstractDynamicCard" instead of "extends CustomCard" at the start.
    // In simple terms, it's for things that we don't want to define again and again in every single card we make.
    public enum MyCardColor { NO_COLOR, RED, GREEN, GOLD }

    public static final int RED_BASE = 3;
    public static final int GREEN_BASE = 2;
    public int ColorNumber;        // Just like magic number, or any number for that matter, we want our regular, modifiable stat
    public int BaseColorNumber;    // And our base stat - the number in it's base state. It will reset to that by default.
    public boolean upgradedColorNumber; // A boolean to check whether the number has been upgraded or not.
    public boolean isColorNumberModified; // A boolean to check whether the number has been modified or not, for coloring purposes. (red/green)
    public MyCardColor myColor = MyCardColor.NO_COLOR;
    public MyCardColor defaultColor = MyCardColor.NO_COLOR;
    public int goldCnt, ColorNumUpgCnt;
    public boolean cannotColor;
    public boolean autoEvoke;
    private static final CardStrings redString = CardCrawlGame.languagePack.getCardStrings("ColorfulMod:RedCard");
    private static final CardStrings greenString = CardCrawlGame.languagePack.getCardStrings("ColorfulMod:GreenCard");
    private static final CardStrings goldString = CardCrawlGame.languagePack.getCardStrings("ColorfulMod:GoldCard");

    public AbstractColorCard(final String id,
                             final String name,
                             final String img,
                             final int cost,
                             final String rawDescription,
                             final CardType type,
                             final CardColor color,
                             final CardRarity rarity,
                             final CardTarget target) {

        super(id, name, img, cost, rawDescription, type, color, rarity, target);

        // Set all the things to their default values.
        isCostModified = false;
        isCostModifiedForTurn = false;
        isDamageModified = false;
        isBlockModified = false;
        isMagicNumberModified = false;
        isColorNumberModified = false;
        cannotColor = false;
        autoEvoke = true;
        goldCnt = ColorNumUpgCnt = 0;
    }

    public void displayUpgrades() { // Display the upgrade - when you click a card to upgrade it
        super.displayUpgrades();
        if (upgradedColorNumber) { // If we set upgradedDefaultSecondMagicNumber = true in our card.
            ColorNumber = BaseColorNumber; // Show how the number changes, as out of combat, the base number of a card is shown.
            isColorNumberModified = true; // Modified = true, color it green to highlight that the number is being changed.
        }
    }
    public void upgradeColorNumber(int amount) { // If we're upgrading (read: changing) the number. Note "upgrade" and NOT "upgraded" - 2 different things. One is a boolean, and then this one is what you will usually use - change the integer by how much you want to upgrade.
        BaseColorNumber += amount; // Upgrade the number by the amount you provide in your card.
        ColorNumber = BaseColorNumber; // Set the number to be equal to the base value.
        upgradedColorNumber = true; // Upgraded = true - which does what the above method does.
    }
    public void EvokeColor(MyCardColor _color) {
        AbstractPlayer p = AbstractDungeon.player;
        if (_color == MyCardColor.RED) {
            if (p.hasPower("ColorfulMod:FireSpreadPower")) {
                p.getPower("ColorfulMod:FireSpreadPower").flash();
                this.addToBot(new DamageAllEnemiesAction(p, DamageInfo.createDamageMatrix(ColorNumber, true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.FIRE));
            } else {
                this.addToBot(new DamageRandomEnemyAction(new DamageInfo(p, ColorNumber, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.FIRE));
            }
        } else if (_color == MyCardColor.GREEN) {
            this.addToBot(new GainBlockAction(p, p, ColorNumber));
        } else if (_color == MyCardColor.GOLD) {
            this.addToBot(new GoldEvokeAction(true));
        }

        if (_color != MyCardColor.NO_COLOR) {
            // check Magic Star
            if (p.hasRelic("ColorfulMod:MagicStar") && _color == MyCardColor.GOLD) {
                this.addToBot(new GoldEvokeAction(true));
            }
            // check Magic Star OK
        }
    }
    public void EvokeColor2(MyCardColor _color) {
        if (_color != MyCardColor.NO_COLOR &&
                (_color != MyCardColor.GOLD || !AbstractDungeon.player.hasRelic("ColorfulMod:MagicStar"))) {
            this.addToBot(new ColorUpgradeAction());
        }
    }
    public void setMyColor(MyCardColor _color, boolean doEvoke) {
        if (_color != MyCardColor.NO_COLOR && AbstractDungeon.player.hasRelic("ColorfulMod:MarkOfChaos")) {
            _color = GetRandomColor.getColor();
        }

        if (doEvoke) this.EvokeColor(this.myColor);
        this.myColor = _color;
        if (this.myColor == MyCardColor.RED) this.BaseColorNumber = this.ColorNumber = RED_BASE;
        else if (this.myColor == MyCardColor.GREEN) this.BaseColorNumber = this.ColorNumber = GREEN_BASE;
        else this.BaseColorNumber = this.ColorNumber = 0;
        this.isColorNumberModified = false;
        this.ColorNumUpgCnt = 0;

        this.superFlash();
        this.applyPowers();
        this.beginGlowing();
    }

    public void onUseCard() {
        AbstractPlayer p = AbstractDungeon.player;
        if (this.myColor != MyCardColor.NO_COLOR && this.autoEvoke) {
            this.EvokeColor(this.myColor);
            this.EvokeColor2(this.myColor);
        }
        this.setMyColor(MyCardColor.NO_COLOR, false);
    }
    @Override
    public void triggerOnEndOfPlayerTurn() {
        if (this.myColor != MyCardColor.NO_COLOR) {
            this.setMyColor(MyCardColor.NO_COLOR, true);
        }
        super.triggerOnEndOfPlayerTurn();
    }

    @Override
    public void stopGlowing() {
        if (this.myColor == MyCardColor.NO_COLOR) super.stopGlowing();
    }
    @Override
    public void triggerOnGlowCheck() {
        if (this.myColor == MyCardColor.RED) this.glowColor = new Color(1.0F, 0.3F, 0.3F, 1.0F);
        else if (this.myColor == MyCardColor.GREEN) this.glowColor = Color.GREEN.cpy();
        else if (this.myColor == MyCardColor.GOLD) this.glowColor = Color.GOLD.cpy();
        else this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
    }

    public void setCostForTurnGold(int amt) {
        this.goldCnt += amt;
        this.isCostModified = (this.realCost() != this.cost);
    }
    @Override
    public void setCostForTurn(int cost) {
        if (this.costForTurn >= 0 && this.realCost() >= cost) {
            this.goldCnt = 0;
            super.setCostForTurn(cost);
        }
    }
    public int realCost() { return Math.max(0, this.costForTurn-this.goldCnt); }
    @Override
    public void onMoveToDiscard() {
        this.goldCnt = 0;
        this.isCostModified = (Math.max(0, this.costForTurn-this.goldCnt) != this.cost);
    }

    @Override
    public void applyPowers() {
        super.applyPowers();

        int RED_UPG = 2, GREED_UPG = 1;
        if (AbstractDungeon.player.hasRelic("ColorfulMod:FlamePaint")) {
            ++RED_UPG;
        }

        this.ColorNumber = this.BaseColorNumber;
        if (this.myColor == MyCardColor.RED) this.ColorNumber += this.ColorNumUpgCnt*RED_UPG;
        else if (this.myColor == MyCardColor.GREEN) this.ColorNumber += this.ColorNumUpgCnt*GREED_UPG;
        if (AbstractDungeon.player.getPower("ColorfulMod:InspirationPower") != null) {
            this.ColorNumber += AbstractDungeon.player.getPower("ColorfulMod:InspirationPower").amount;
        }
        if (this.ColorNumber != this.BaseColorNumber) this.isColorNumberModified = true;
        else this.isColorNumberModified = false;

        CardStrings tmp = CardCrawlGame.languagePack.getCardStrings(this.cardID);
        String baseString;
        if (this.upgraded) baseString = tmp.UPGRADE_DESCRIPTION == null ? tmp.DESCRIPTION : tmp.UPGRADE_DESCRIPTION;
        else baseString = tmp.DESCRIPTION;
        if (this.myColor == MyCardColor.RED) rawDescription = baseString + redString.DESCRIPTION;
        else if (this.myColor == MyCardColor.GREEN) rawDescription = baseString + greenString.DESCRIPTION;
        else if (this.myColor == MyCardColor.GOLD) rawDescription = baseString + goldString.DESCRIPTION;
        else rawDescription = baseString;
        this.initializeDescription();
    }

    @Override
    public AbstractColorCard makeCopy() {
        try {
            return this.getClass().getDeclaredConstructor().newInstance();
        } catch (IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
            throw new RuntimeException("BaseMod failed to auto-generate makeCopy for card: " + this.cardID);
        }
    }
    @Override
    public AbstractColorCard makeStatEquivalentCopy() {
//        AbstractColorCard card = this.makeCopy();
        AbstractColorCard card = (AbstractColorCard)super.makeStatEquivalentCopy();
//        for(int i = 0; i < this.timesUpgraded; ++i) {
//            card.upgrade();
//        }
//        card.name = this.name;
//        card.target = this.target;
//        card.upgraded = this.upgraded;
//        card.timesUpgraded = this.timesUpgraded;
//        card.baseDamage = this.baseDamage;
//        card.baseBlock = this.baseBlock;
//        card.baseMagicNumber = this.baseMagicNumber;
//        card.cost = this.cost;
//        card.costForTurn = this.costForTurn;
//        card.isCostModified = this.isCostModified;
//        card.isCostModifiedForTurn = this.isCostModifiedForTurn;
//        card.inBottleLightning = this.inBottleLightning;
//        card.inBottleFlame = this.inBottleFlame;
//        card.inBottleTornado = this.inBottleTornado;
//        card.isSeen = this.isSeen;
//        card.isLocked = this.isLocked;
//        card.misc = this.misc;
//        card.freeToPlayOnce = this.freeToPlayOnce;

        card.myColor = this.myColor;
        card.BaseColorNumber = this.BaseColorNumber;
        card.ColorNumUpgCnt = this.ColorNumUpgCnt;
        card.goldCnt = this.goldCnt;
        return card;
    }
}