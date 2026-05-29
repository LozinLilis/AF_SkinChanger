package org.lozin.af_skinchanger.GUI;

import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.lozin.af_skinchanger.Cache.GUICache;
import org.lozin.af_skinchanger.Cache.PlayerCache;
import org.lozin.af_skinchanger.Processor.ConfigReader;
import org.lozin.af_skinchanger.Processor.ItemProcessor;
import org.lozin.af_skinchanger.Processor.PermissionProcessor;
import org.lozin.af_skinchanger.StringProcessor;

import java.util.LinkedList;
import java.util.Map;

public interface GUIService extends StringProcessor {
    default void InsertBackgroundItems(Inventory inv){
        GUICache.renderBackgrounds();
        //System.out.println(GUICache.getBACKGROUND_ITEMS());
        for(Map.Entry<Integer, ItemStack> entry: GUICache.getBACKGROUND_ITEMS().entrySet()){
            inv.setItem(entry.getKey(), entry.getValue());
        }
    }
    default ItemStack getHeldItem(Player player){
        return player.getInventory().getItemInMainHand();
    }
    default void insertSkins(Inventory inv, LinkedList<String> skins, Player player){
        PlayerCache pc = PlayerCache.getInstance(player);
        int page = pc.getItemPage();
        int item_pre_page = 36;
        int totalPages = (int) Math.ceil((double) skins.size() / item_pre_page);
        page = Math.min(page, totalPages - 1);
        int start = page * item_pre_page;
        int end = Math.min(start + item_pre_page, skins.size());
        for(int i = start; i < end && inv.firstEmpty() != -1; i++){
            String skin = skins.get(i);
            if (isObjectMapper(skin)){
                Map<String, String> map = PermissionProcessor.toMap(skin);
                for (Map.Entry<String, String> entry: map.entrySet()){
                    if (entry.getValue() != null) {
                        boolean hp = player.hasPermission(entry.getValue());
                        ItemStack item = ItemProcessor.getInstance().build("&e"+entry.getKey(),
                                Material.valueOf(ConfigReader.getConfig("default_material").toString().toUpperCase()),
                                null,
                                " ","&e<&c需要的权限如下&e>"," ", (hp ? "&a✔" : "&c✘")+" &7"+entry.getValue());
                        if(item != null){
                            NBTItem nbt = new NBTItem(item);
                            nbt.setString("req_perm", entry.getValue());
                            inv.addItem(nbt.getItem());
                        };
                        continue;
                    }
                    ItemStack item = ItemProcessor.getInstance().build("&e"+entry.getKey(),
                            Material.valueOf(ConfigReader.getConfig("default_material").toString().toUpperCase()),
                            null);
                    if(item != null) inv.addItem(item);
                }
                continue;
            }
            ItemStack item = ItemProcessor.getInstance().build("&e"+skin,
                    Material.valueOf(ConfigReader.getConfig("default_material").toString().toUpperCase()),
                    null);
            if(item != null) inv.addItem(item);
        }
    }
}