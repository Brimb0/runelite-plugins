package eu.jodelahithit;

import com.google.inject.Provides;

import javax.inject.Inject;

import lombok.extern.slf4j.Slf4j;
import net.runelite.api.*;
import net.runelite.api.coords.LocalPoint;
import net.runelite.api.coords.WorldPoint;
import net.runelite.api.events.*;
import net.runelite.api.worldmap.WorldMapRegion;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.ConfigChanged;
import net.runelite.client.game.ItemManager;
import net.runelite.client.input.KeyManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.ClientToolbar;
import net.runelite.client.ui.NavigationButton;
import net.runelite.client.ui.overlay.OverlayManager;
import net.runelite.client.util.ImageUtil;
import org.apache.commons.lang3.ArrayUtils;

import java.awt.event.ItemEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@PluginDescriptor(
        name = "Skilling Notifications",
        tags = {"notifications", "skilling"},
        description = "Provides visual notifications when no longer actively performing the selected skill"
)
public class SkillingNotificationsPlugin extends Plugin {
    private static final String WALK_HERE = "Walk here";
    private static final int MANIACAL_MONKEYS_REGION_ID = 11662;
    private LocalPoint lastPlayerLocation;
    private Session session;
    private NavigationButton navigationButton;
    private List<Skill> selectedSkills = new ArrayList<>();
    public Tile lastManiacalMonkeyRockTile = null;
    public SkillingNotificationsPanel panel;

    @Inject
    Client client;
    @Inject
    ConfigManager configManager;
    @Inject
    SkillingNotificationsConfig config;
    @Inject
    OverlayManager overlayManager;
    @Inject
    SkillingNotificationsOverlay overlay;
    @Inject
    SkillingNotificationsListener inputListener;
    @Inject
    ClientToolbar clientToolbar;
    @Inject
    ItemManager itemManager;
    @Inject
    KeyManager keyManager;

    public final BufferedImage ICON = ImageUtil.loadImageResource(SkillingNotificationsPlugin.class, "icon.png");

    @Override
    protected void startUp() throws Exception {
        keyManager.registerKeyListener(inputListener);
        updateSelectedSkills();
        panel = new SkillingNotificationsPanel(configManager);
        navigationButton = NavigationButton.builder()
                .tooltip("Skilling Notifications")
                .icon(ICON).priority(10).panel(panel)
                .build();

        clientToolbar.addNavigation(navigationButton);
        overlayManager.add(overlay);
        session = new Session(this);
    }

    @Override
    protected void shutDown() throws Exception {
        clientToolbar.removeNavigation(navigationButton);
        overlayManager.remove(overlay);
        keyManager.unregisterKeyListener(inputListener);
    }

    @Provides
    SkillingNotificationsConfig provideConfig(ConfigManager configManager) {
        return configManager.getConfig(SkillingNotificationsConfig.class);
    }

    @Subscribe
    public void onClientTick(ClientTick clientTick) {
        if (!config.enabled()) return;
        for (Skill skill : selectedSkills) {
            if (Utils.isInAnimation(skill, client)) session.updateInstant(skill);
        }
        Player player = client.getLocalPlayer();
        if (player != null) {
            LocalPoint playerLocation = player.getLocalLocation();
            if (!playerLocation.equals(lastPlayerLocation)) {
                session.updateWalkingInstant();
            }
            lastPlayerLocation = playerLocation;
        }

        boolean isInManiacalMonkeysArea = isInManiacalMonkeysArea();
        if(!isInManiacalMonkeysArea() || (isInManiacalMonkeysArea && lastManiacalMonkeyRockTile != null))
            session.updateInstant(Skill.MANIACALMONKEYS);
    }

    @Subscribe
    public void onConfigChanged(ConfigChanged configChanged) {
        if (configChanged.getGroup().equals("Skilling Notifications")) {
            updateSelectedSkills();
        }
    }

    @Subscribe
    public void onHitsplatApplied(HitsplatApplied hitsplatApplied) {
        if (!config.enabled()) return;
        Actor actor = hitsplatApplied.getActor();
        Hitsplat hitsplat = hitsplatApplied.getHitsplat();
        if (hitsplat.isMine()) {
            session.updateInstant(Skill.COMBAT);
        }
    }

    @Subscribe
    public void onGameObjectSpawned(GameObjectSpawned event) {
        final GameObject gameObject = event.getGameObject();
        final int id = gameObject.getId();
        final WorldPoint trapLocation = gameObject.getWorldLocation();

        if (id == ObjectID.MONKEY_TRAP || id == ObjectID.LARGE_BOULDER_28825) {
            if (client.getLocalPlayer().getWorldLocation().distanceTo(trapLocation) <= 2) {
                lastManiacalMonkeyRockTile = event.getTile();
            }
            return;
        }

        if (Constants.MONKEY_ROCKS.contains(id)) {
            if (lastManiacalMonkeyRockTile != null && event.getTile() == lastManiacalMonkeyRockTile)
                lastManiacalMonkeyRockTile = null;
        }
    }

    @Subscribe
    private void onWorldChanged(WorldChanged ev) {
        lastManiacalMonkeyRockTile = null;
    }

    @Subscribe
    public void onMenuOptionClicked(MenuOptionClicked event) {
        if (event.getMenuOption().equals(WALK_HERE)) session.updateWalkingInstant();
    }

    boolean areSelectedSkillsActive() {
        boolean isActive = false;
        for (Skill skill : selectedSkills) {
            isActive |= session.isSkillActive(skill);
        }
        return isActive;
    }

    boolean shouldRenderOverlay() {
        if (!config.enabled()) return false;
        final boolean skills = !selectedSkills.isEmpty() && !areSelectedSkillsActive();
        final boolean notWalking = !(config.disableWhenWalking() && session.isWalking(config.walkDelay()));
        return skills && notWalking;
    }

    public List<Skill> getSelectedSkills() {
        return selectedSkills;
    }

    void updateSelectedSkills() {
        selectedSkills.clear();
        for (Skill skill : Skill.values()) {
            if (Boolean.parseBoolean(configManager.getConfiguration("Skilling Notifications", skill.name().toUpperCase()))) {
                selectedSkills.add(skill);
            }
        }
    }

    int getExtraSkillDelay(Skill skill) {
        int delay = Integer.parseInt(configManager.getConfiguration("Skilling Notifications", skill.name() + "DELAYV2"));
        if (skill == Skill.COMBAT) return Utils.getAttackSpeed(client, itemManager) * 600 + delay;
        return delay;
    }

    void setSkillInConfig(Skill skill) {
        configManager.setConfiguration("Skilling Notifications", "selectedSkill", skill);
    }

    boolean isInManiacalMonkeysArea() {
        return ArrayUtils.contains(client.getMapRegions(), MANIACAL_MONKEYS_REGION_ID);
    }
}