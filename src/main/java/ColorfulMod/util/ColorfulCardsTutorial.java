

package ColorfulMod.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon.CurrentScreen;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.localization.TutorialStrings;
import com.megacrit.cardcrawl.ui.FtueTip;
import com.megacrit.cardcrawl.vfx.combat.BattleStartEffect;

// 不要乱改，需要改的地方我已经全部写好注释
public class ColorfulCardsTutorial extends FtueTip { // 类名改一下
    private static final TutorialStrings tutorialStrings;
    public static final String[] MSG;
    public static final String[] LABEL;
    private static final int W = 760;
    private static final int H = 580;
    private Texture img[] = new Texture[10];
    private Color screen = Color.valueOf("1c262a00"); // 你应该不需要改颜色
    private float x;
    private float xx[] = new float[10];
    private float targetX;
    private float startX;
    private float scrollTimer = 0.0F;
    private static final float SCROLL_TIME = 0.3F;
    private int currentSlot = 0;
    private int maxn = 5; // 你教程的页数+1 = maxn，我这里maxn=5所以有6页，自己改

    public ColorfulCardsTutorial() {
        AbstractDungeon.player.releaseCard();
        if (AbstractDungeon.isScreenUp) {
            AbstractDungeon.dynamicBanner.hide();
            AbstractDungeon.previousScreen = AbstractDungeon.screen;
        }

        AbstractDungeon.isScreenUp = true;
        AbstractDungeon.screen = CurrentScreen.FTUE;
        AbstractDungeon.overlayMenu.showBlackScreen();
        this.x = 0.0F;
        this.xx[0] = 567.0F * Settings.scale;
        for (int i = 1; i <= maxn; i++) this.xx[i] = this.xx[i-1]+(float)Settings.WIDTH;

        for (int i = 0; i <= maxn; i++) {
            this.img[i] = ImageMaster.loadImage("ColorfulModResources/images/ui/tip"+i+".png");
            // 在这里导入教程插图，自己改路径
        }

        AbstractDungeon.overlayMenu.proceedButton.show();
        AbstractDungeon.overlayMenu.proceedButton.setLabel(LABEL[0]);
    }

    public void update() {
        if (this.screen.a != 0.8F) {
            Color var10000 = this.screen;
            var10000.a += Gdx.graphics.getDeltaTime();
            if (this.screen.a > 0.8F) {
                this.screen.a = 0.8F;
            }
        }

        if (AbstractDungeon.overlayMenu.proceedButton.isHovered && InputHelper.justClickedLeft || CInputActionSet.proceed.isJustPressed()) {
            CInputActionSet.proceed.unpress();
            if (this.currentSlot == -maxn) {
                CardCrawlGame.sound.play("DECK_CLOSE");
                AbstractDungeon.closeCurrentScreen();
                AbstractDungeon.overlayMenu.proceedButton.hide();
                AbstractDungeon.effectList.clear();
                return;
            }

            AbstractDungeon.overlayMenu.proceedButton.hideInstantly();
            AbstractDungeon.overlayMenu.proceedButton.show();
            CardCrawlGame.sound.play("DECK_CLOSE");
            --this.currentSlot;
            this.startX = this.x;
            this.targetX = (float)(this.currentSlot * Settings.WIDTH);
            this.scrollTimer = 0.3F;
            if (this.currentSlot == -maxn) {
                AbstractDungeon.overlayMenu.proceedButton.setLabel(LABEL[1]);
            }
        }

        if (this.scrollTimer != 0.0F) {
            this.scrollTimer -= Gdx.graphics.getDeltaTime();
            if (this.scrollTimer < 0.0F) {
                this.scrollTimer = 0.0F;
            }
        }

        this.x = Interpolation.fade.apply(this.targetX, this.startX, this.scrollTimer / 0.3F);
    }

    public void render(SpriteBatch sb) {
        sb.setColor(this.screen);
        sb.draw(ImageMaster.WHITE_SQUARE_IMG, 0.0F, 0.0F, (float)Settings.WIDTH, (float)Settings.HEIGHT);
        sb.setColor(Color.WHITE);
        for (int i = 0; i <= maxn; i++) {
            sb.draw(this.img[i], this.x + this.xx[i] - 380.0F, (float) Settings.HEIGHT / 2.0F - 290.0F, 380.0F, 290.0F, 760.0F, 580.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 760, 580, false, false);
        }
        float offsetY = 0.0F;
        if (Settings.BIG_TEXT_MODE) {
            offsetY = 110.0F * Settings.scale;
        }

        for (int i = 0; i <= maxn; i++) {
            FontHelper.renderSmartText(sb, FontHelper.panelNameFont, MSG[i], this.x + this.xx[i] + 400.0F * Settings.scale, (float)Settings.HEIGHT / 2.0F - FontHelper.getSmartHeight(FontHelper.panelNameFont, MSG[i], 700.0F * Settings.scale, 40.0F * Settings.scale) / 2.0F + offsetY, 700.0F * Settings.scale, 40.0F * Settings.scale, Settings.CREAM_COLOR);
        }
        FontHelper.renderFontCenteredWidth(sb, FontHelper.panelNameFont, LABEL[2], (float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F - 360.0F * Settings.scale, Settings.GOLD_COLOR);
        FontHelper.renderFontCenteredWidth(sb, FontHelper.tipBodyFont, LABEL[3] + Integer.toString(Math.abs(this.currentSlot - 1)) + LABEL[4], (float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F - 400.0F * Settings.scale, Settings.CREAM_COLOR);
        AbstractDungeon.overlayMenu.proceedButton.render(sb);
    }

    static {
        tutorialStrings = CardCrawlGame.languagePack.getTutorialString("ColorfulMod:ColorfulCardsTutorials");
        MSG = tutorialStrings.TEXT;
        LABEL = tutorialStrings.LABEL;
        // 文本呢？文本在这里
        // 在你放各种文本的目录下新建一个 xxxxxxx-tutorial-strings.json 然后写下下面的内容
    }
}

/*
文本的json怎么写？示例：
{
  "ColorfulMod:ColorfulCardsTutorials": { // 自己改成自己mod的名字
    "TEXT": [ // 按顺序写下每一页教程的文本
      "给你的手牌添加 #y颜色 来触发额外效果！ NL 为了良好的游玩体验 , 请 #y仔细阅读 本教程！",
      " #r红色 : NL #y主动效果 : 对随机敌人造成 #b3 点伤害 。 NL #y强化效果 : 伤害 + #b2 。",
      " #g绿色 : NL #y主动效果 : 获得 #b2 点格挡 。 NL #y强化效果 : 格挡 + #b1 。",
      " #y金色 : NL #y主动效果 : 使手牌中随机 #b1 张 #y有颜色 的牌本回合下 #b1 次打出耗能- #b1 。 NL #y强化效果 : 无 。",
      " #y强激发 : 若有颜色 , 触发此牌的 #y颜色主动效果 并触发手牌中其他牌的 #y颜色强化效果 。 NL #y弱激发 : 仅触发此牌的 #y颜色主动效果 。 NL NL 打出一张牌时 , 触发 #y强激发 效果并失去颜色。 NL 一张牌颜色被覆盖时 , 触发 #y弱激发 效果。 NL 回合结束时 , 若 #y未被保留 , 触发 #y弱激发 效果并失去颜色。 NL NL 仅本角色的牌可以被添加颜色！",
      "没有看懂？玩就行了！ NL NL 如果在游玩过程中有疑问 , 可以在 #g战斗中 #y右键点击 第二个初始遗物: NL #y迷你教程 来查看本教程！ NL 祝大家游戏愉快！"
    ],
    "LABEL": [ // 改一下第3个，是教程的标题
      "下一条",
      "我准备好了！",
      "给你的牌添加颜色！",
      "(页数: ",
      ")"
    ]
  }
}

写完这个之后，打开你的主文件(就是你在那里面注册你的角色,卡牌,遗物,文本等的那个主文件)
找到 receiveEditStrings 函数，加入
BaseMod.loadCustomStringsFile(TutorialStrings.class, "xxxx/xxx-tutorial-strings.json");
文件路径自己改

然后到开头检查一下那个大类是否有 implements OnStartBattleSubscriber
如果没有就给它加上
在类的开头定义这3个东西
public static Properties xxxDefaultSettings = new Properties(); // xxx 可以改成你想要的名字
public static final String RENDER_TIPS = "renderTips";
public static boolean renderTips = true;

找到类的构造函数，在末尾加入
xxxDefaultSettings.setProperty(RENDER_TIPS, "TRUE");
try {
    SpireConfig config = new SpireConfig("xxxMod", "xxxConfig", xxxDefaultSettings);
    config.load();
    renderTips = config.getBool(RENDER_TIPS);

} catch (Exception e) {
    e.printStackTrace();
}
该改的xxx自己改

然后在主类里面找个地方加入这个函数
public void receiveOnBattleStart(AbstractRoom room) {
// 开始战斗时，如果renderTips为true就调出教程并把renderTips永久设置为false
    try {
        SpireConfig config = new SpireConfig("xxxMod", "xxxConfig", xxxDefaultSettings); // 和上面保持一致
        if (AbstractDungeon.player instanceof (你的角色类名) && renderTips) {
            AbstractDungeon.ftue = new xxxTutorial(); // 你的教程的类名
            renderTips = false;
            config.setBool(RENDER_TIPS, false);
            config.save();
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
}
相信大家都知道哪些地方需要改成自己mod的名称

有可能漏了什么 如果无法运行可以告诉我
*/