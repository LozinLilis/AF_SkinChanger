package org.lozin.af_skinchanger.GUI;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.lozin.af_skinchanger.Cache.PlayerCache;
import org.lozin.af_skinchanger.Dictionary;
import org.lozin.af_skinchanger.Processor.NBTReader;
import org.lozin.af_skinchanger.StringProcessor;

import java.util.LinkedList;

public class SkinGUI implements StringProcessor{
    private static SkinGUI instance;
    public static SkinGUI getInstance() {
        return instance == null ? instance = new SkinGUI() : instance;
    }
    public void render(PlayerCache pc) {
        ItemStack onSelectItem = pc.getOnSelectItem();
        if (onSelectItem == null) return;
        Player player = pc.getPlayer();
        LinkedList<String> skinList = Dictionary.getProperSkins(NBTReader.getDetectKeyValue(onSelectItem));
        Inventory inv = Bukkit.createInventory(player, 54, "§0§l皮肤选择");
        GUIService guiService = GUIManager.getInstance();
        guiService.InsertBackgroundItems(inv);
        guiService.insertSkins(inv, skinList, player);
        player.openInventory(inv);
    }
}