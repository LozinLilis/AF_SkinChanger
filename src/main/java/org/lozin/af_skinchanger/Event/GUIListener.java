package org.lozin.af_skinchanger.Event;

import com.google.common.collect.ImmutableMap;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.lozin.af_skinchanger.Cache.GUICache;
import org.lozin.af_skinchanger.Cache.PlayerCache;
import org.lozin.af_skinchanger.Enums.GUI_BACKGROUND;
import org.lozin.af_skinchanger.GUI.GUIManager;
import org.lozin.af_skinchanger.GUI.GUIService;
import org.lozin.af_skinchanger.Processor.ItemProcessor;
import org.lozin.af_skinchanger.Processor.PermissionProcessor;
import org.lozin.af_skinchanger.StringProcessor;

public class GUIListener implements Listener, GUIService, StringProcessor {
    @Getter
    @Setter
    private static boolean enable = false;
    @EventHandler
    public void applyingSkinChanger(InventoryClickEvent e){
        if (!enable) return;
        Player player = (Player) e.getWhoClicked();
        PlayerCache pc = PlayerCache.getInstance(player);
        InventoryType inv_t = e.getInventory().getType();
        ItemStack item = e.getCurrentItem();
        if (item == null || item.getType() == Material.AIR) return;
        switch (pc.getGuiType()){
            case SELECT_ITEM:
                e.setCancelled(true);
                if (inv_t != InventoryType.PLAYER) return;
                if (!ItemProcessor.isProperItem(item)) {
                    sendProperMessage(player, new String[]{"&f[&bSKIN&f] &c你选择的物品没有任何幻化皮肤!"});
                    return;
                }
                if (!ItemProcessor.hasChangePerm(item, player)){
                    sendProperMessage(player, new String[]{"&f[&bSKIN&f] &c你无法更换此物品的皮肤!"});
                    return;
                }
                pc.setOnSelectItem(item);
                pc.setGuiType(PlayerCache.GUI_TYPE.SKIN);
                pc.setPositiveClose(true);
                GUIManager.getInstance().renderGUI(player);
                pc.setPositiveClose(false);
            case LIB:
                break;
            case SKIN:
                e.setCancelled(true);
                if (GUICache.getBACKGROUND_ITEMS().containsKey(e.getRawSlot())){
                    int slot = e.getRawSlot();
                    int pre = GUI_BACKGROUND.PRE.getSlot();
                    int next = GUI_BACKGROUND.NEXT.getSlot();
                    if (slot == pre){
                        if (pc.getItemPage() > 1){
                            pc.setItemPage(pc.getItemPage() - 1);
                            pc.setPositiveClose(true);
                            GUIManager.getInstance().renderGUI(player);
                            pc.setPositiveClose(false);
                            return;
                        }
                        player.playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, 1, 1);
                    }
                    if (slot == next){
                        if (pc.getItemPage() < GUICache.getMaxPage(pc.getOnSelectItem())){
                            pc.setItemPage(pc.getItemPage() + 1);
                            pc.setPositiveClose(true);
                            GUIManager.getInstance().renderGUI(player);
                            pc.setPositiveClose(false);
                            return;
                        }
                        player.playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, 1, 1);
                    }
                };
                String selectedSkin = item.getItemMeta().getDisplayName().replaceAll("§.", "");
                if (PermissionProcessor.hasPermission(item, player)){
                    ItemProcessor.itemUpdater(player, pc.getOnSelectItem(), ImmutableMap.of("skin", selectedSkin));
                    sendProperMessage(player, new String[]{"&f[&bSKIN&f] &7当前物品皮肤已更换为: &e" + selectedSkin});
                    player.closeInventory();
                    break;
                }
                sendProperMessage(player, new String[]{"&f[&bSKIN&f] &c无权限更换为此皮肤!"});
                break;
            case SHUTDOWN:
                setEnable(false);
                break;
        }
    }
    @EventHandler
    public void onInventoryClose(InventoryCloseEvent e){
        if (!enable) return;
        Player player = (Player) e.getPlayer();
        PlayerCache pc = PlayerCache.getInstance(player);
        if (!pc.isPositiveClose()) {
            pc.setGuiType(PlayerCache.GUI_TYPE.SHUTDOWN);
            setEnable(false);
        }
    }
}