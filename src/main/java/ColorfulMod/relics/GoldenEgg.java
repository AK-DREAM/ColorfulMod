package ColorfulMod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import ColorfulMod.DefaultMod;
import ColorfulMod.util.TextureLoader;
import com.megacrit.cardcrawl.rewards.RewardItem;

import static ColorfulMod.DefaultMod.makeRelicOutlinePath;
import static ColorfulMod.DefaultMod.makeRelicPath;
import static ColorfulMod.characters.ColorfulBrush.Enums.GOLD_CARD;

public class GoldenEgg extends CustomRelic {

    public static final String ID = DefaultMod.makeID("GoldenEgg");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("GoldenEgg.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("GoldenEgg.png"));

    public GoldenEgg() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.MAGICAL);
    }

    public void onEquip() {
        for (RewardItem reward : AbstractDungeon.combatRewardScreen.rewards) {
            if (reward.cards != null)
                for (AbstractCard c : reward.cards) onPreviewObtainCard(c);
        }
    }

    public void onPreviewObtainCard(AbstractCard c) {
        this.onObtainCard(c);
    }

    public void onObtainCard(AbstractCard c) {
        if (c.hasTag(GOLD_CARD) && c.canUpgrade() && !c.upgraded) c.upgrade();
    }

    public boolean canSpawn() {
        return Settings.isEndless || AbstractDungeon.floorNum <= 48;
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
