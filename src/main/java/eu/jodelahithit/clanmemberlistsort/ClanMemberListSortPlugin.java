package eu.jodelahithit.clanmemberlistsort;

import com.google.inject.Provides;

import javax.inject.Inject;

import net.runelite.api.*;
import net.runelite.api.events.ScriptPostFired;
import net.runelite.api.events.WidgetLoaded;
import net.runelite.api.widgets.*;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.ConfigChanged;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import sun.swing.BakedArrayList;

import java.util.*;
import java.util.List;

@PluginDescriptor(
        name = "Clan Member List Sorting",
        description = "Adds a sort button to the clan member list",
        tags = {"clan", "members", "list", "sorting", "alphabetically", "world", "rank", "role"}
)
public class ClanMemberListSortPlugin extends Plugin {
    static final String CONFIG_GROUP = "clanmemberlistsorting";

    final int WIDGET_HEIGHT = 15;
    final int UNK_CLAN_TAB_SCRIPT = 2859;

    private Widget clanMemberListHeaderWidget;
    private Widget clanMemberListsWidget;
    private Widget sortButton;

    @Inject
    Client client;
    @Inject
    ClientThread clientThread;
    @Inject
    ClanMemberListSortConfig config;

    @Provides
    ClanMemberListSortConfig getConfig(ConfigManager configManager) {
        return configManager.getConfig(ClanMemberListSortConfig.class);
    }

    @Subscribe
    public void onConfigChanged(ConfigChanged configChanged) {
        if (configChanged.getGroup().equals(CONFIG_GROUP)) {
            if (configChanged.getKey().equals("reverseSort")) {
                updateSortButtonSprite();
            }
        }
    }

    @Override
    public void startUp() {
        clientThread.invokeLater(this::initWidgets);
    }

    @Override
    public void shutDown() {
        if (clanMemberListHeaderWidget != null) clanMemberListHeaderWidget.deleteAllChildren();
    }

    @Subscribe
    public void onWidgetLoaded(WidgetLoaded event) {
        if (event.getGroupId() == WidgetInfo.CLAN_MEMBER_LIST.getGroupId()) {
            initWidgets();
        }
    }

    @Subscribe
    public void onScriptPostFired(ScriptPostFired event) {
        if (event.getScriptId() != UNK_CLAN_TAB_SCRIPT) return;
        if (clanMemberListsWidget == null) return;

        List<ClanMemberListEntry> widgets = new ArrayList<>();

        ArrayList<Widget> relevantWidgets = new ArrayList<>();

        Widget[] containerChildren = clanMemberListsWidget.getDynamicChildren();
        for (Widget widget : containerChildren) {
            if (widget.getFontId() != -1 || widget.getSpriteId() != -1) relevantWidgets.add(widget);
        }
        if(relevantWidgets.size() % 3 != 0) return;

        //Widgets are always in the same order: name, world, icon
        for (int i = 0; i < relevantWidgets.size(); i += 3) {
            widgets.add(new ClanMemberListEntry(relevantWidgets.get(i), relevantWidgets.get(i + 1), relevantWidgets.get(i + 2)));
        }

        Comparator<ClanMemberListEntry> comparator = null;
        switch (config.activeSortType()) {
            case SORT_BY_WORLD:
                comparator = Comparator.comparing(ClanMemberListEntry::getWorld);
                break;
            case SORT_BY_NAME:
                comparator = Comparator.comparing(ClanMemberListEntry::getPlayerName);
                break;
            case SORT_BY_RANK:
                widgets.forEach(entry -> entry.updateClanRank(client));
                comparator = Comparator.comparing(ClanMemberListEntry::getClanRank);
                break;
        }
        widgets.sort(config.reverseSort() ? comparator.reversed() : comparator);

        for (int i = 0; i < widgets.size(); i++) {
            widgets.get(i).setOriginalYAndRevalidate(WIDGET_HEIGHT * i);
        }
    }

    private void initWidgets() {
        clanMemberListsWidget = client.getWidget(WidgetInfo.CLAN_MEMBER_LIST);
        clanMemberListHeaderWidget = client.getWidget(WidgetInfo.CLAN_MEMBER_LIST.getGroupId(), 0);

        if (clanMemberListHeaderWidget == null) return;

        clanMemberListHeaderWidget.deleteAllChildren();

        sortButton = clanMemberListHeaderWidget.createChild(-1, WidgetType.GRAPHIC);
        reorderSortButton(config.activeSortType());
        sortButton.setOriginalY(2);
        sortButton.setOriginalX(2);
        sortButton.setOriginalHeight(16);
        sortButton.setOriginalWidth(16);
        sortButton.setOnClickListener((JavaScriptCallback) this::handleSortButtonClick);
        sortButton.setOnOpListener((JavaScriptCallback) this::handleSortButtonOp);
        sortButton.setHasListener(true);
        updateSortButtonSprite();
        sortButton.revalidate();
    }

    private void updateSortButtonSprite() {
        sortButton.setSpriteId(config.reverseSort() ? SpriteID.SCROLLBAR_ARROW_UP : SpriteID.SCROLLBAR_ARROW_DOWN);
    }

    private void handleSortButtonClick(ScriptEvent event) {
        config.reverseSort(!config.reverseSort());
        updateSortButtonSprite();
    }

    private void handleSortButtonOp(ScriptEvent event) {
        for (SortType type : SortType.values()) {
            if (type.actionIndex == event.getOp()) {
                config.activeSortType(type);
                reorderSortButton(type);
                return;
            }
        }
    }

    private void reorderSortButton(SortType firstType) {
        int index = 0;
        sortButton.setAction(index, firstType.name);
        firstType.actionIndex = 1;
        for (SortType type : SortType.values()) {
            if (type == firstType) continue;
            sortButton.setAction(++index, type.name);
            type.actionIndex = index + 1;
        }
    }
}