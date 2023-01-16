package ColorfulMod.potions;

import ColorfulMod.actions.AddColorAction;
import ColorfulMod.cards.AbstractColorCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;

import basemod.abstracts.CustomPotion;

public class GoldenPotion extends CustomPotion {

    public static final String POTION_ID = ColorfulMod.DefaultMod.makeID("GoldenPotion");
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);

    public static final String NAME = potionStrings.NAME;
    public static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;

    public GoldenPotion() {
        // The bottle shape and inside is determined by potion size and color. The actual colors are the main DefaultMod.java
        super(NAME, POTION_ID, PotionRarity.RARE, PotionSize.FAIRY, PotionColor.SWIFT);
        potency = getPotency();
        description = DESCRIPTIONS[0];
        isThrown = false;
        // Initialize the on-hover name + description
        tips.add(new PowerTip(name, description));
    }
    @Override
    public void use(AbstractCreature target) {
        this.addToBot(new AddColorAction(true, false, 99, AbstractColorCard.MyCardColor.GOLD));
    }

    @Override
    public AbstractPotion makeCopy() {
        return new GoldenPotion();
    }

    // This is your potency.
    @Override
    public int getPotency(final int potency) {
        return 1;
    }
}
