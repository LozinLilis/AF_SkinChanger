package org.lozin.af_skinchanger.Event;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;
import org.lozin.af_skinchanger.Cache.NBTCache;
import org.lozin.af_skinchanger.Dictionary;
import org.lozin.af_skinchanger.Processor.NBTReader;
import org.lozin.af_skinchanger.StringProcessor;

public class HeldEvent implements Listener, StringProcessor {
    @EventHandler
    public void onHeld(PlayerItemHeldEvent event) {
        Player player = event.getPlayer();
        ItemStack itemStack = player.getInventory().getItem(event.getNewSlot());
        if (itemStack == null || itemStack.getType() == Material.AIR) return;
        sendIfDebug(player, "&f[&eAFSC&f] &7当前读取路径: &e"+NBTCache.getPATH());
        if (NBTReader.getDetectKeyValue(itemStack) == null) return;
        sendIfDebug(player, "&f[&eAFSC&f] &7当前物品的字典: &e"+NBTReader.getDetectKeyValue(itemStack), Dictionary.getProperSkins(NBTReader.getDetectKeyValue(itemStack)).toString());
    }
}
