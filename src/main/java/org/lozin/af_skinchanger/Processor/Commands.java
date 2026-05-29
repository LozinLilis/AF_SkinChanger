package org.lozin.af_skinchanger.Processor;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.lozin.af_skinchanger.AF_SkinChanger;
import org.lozin.af_skinchanger.Cache.PlayerCache;
import org.lozin.af_skinchanger.Enums.LISTENERS;
import org.lozin.af_skinchanger.Event.GUIListener;
import org.lozin.af_skinchanger.StringProcessor;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Commands implements CommandExecutor ,TabCompleter, StringProcessor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        Player player = (Player) commandSender;
        if (command.getName().equalsIgnoreCase("af_skin_changer")) {
            if (strings.length == 0 || (strings.length == 1 && strings[0].equalsIgnoreCase("help"))) {
                sendProperMessage(player, new String[]{
                        " ", " ",
                        "&f&lAF_SkinChanger &7ver "+AF_SkinChanger.getInstance().getDescription().getVersion(),
                        " ",
                        " &f|-- &7afs",
                        " &f|---- &7skin &e(&7s&e)           &f进入物品选择状态",
                        " &f|---- &7reload &e(&7r&e)         &f重载所有配置",
                        " &f|---- &7toggle <listener>        &f开关<listener>监听器",
                        " &f|---- &7help                     &f就是这个提示框",
                        " ", " "
                });
                return true;
            }
            if (strings.length == 1){
                if (strings[0].equalsIgnoreCase("reload") || strings[0].equalsIgnoreCase("r")
                && player.isOp()) {
                    AF_SkinChanger.getInstance().reload();
                    commandSender.sendMessage("§e已重载配置文件");
                    PermissionProcessor.getDefaultPerms();
                    return true;
                }
                if ((strings[0].equalsIgnoreCase("skin") || strings[0].equalsIgnoreCase("s"))
                && player.hasPermission("afsc.gui.open")){
                    GUIListener.setEnable(true);
                    PlayerCache pc = PlayerCache.getInstance(player);
                    sendProperMessage(player, new String[]{
                            "&f[&bSKIN&f] &e请选择需要更换皮肤的物品",
                    });
                    Bukkit.getScheduler().runTaskLater(AF_SkinChanger.getInstance(),() -> {
                        player.openInventory(player.getInventory());
                    }, 10);
                    pc.setGuiType(PlayerCache.GUI_TYPE.SELECT_ITEM);
                }
            }
            if (strings.length == 2){
                if(strings[0].equalsIgnoreCase("toggle") && player.isOp()){
                    String listener = strings[1];
                    LISTENERS l = LISTENERS.getListener(listener);
                    if (l != null) {
                        switch (l){
                            case GUI:
                                GUIListener.setEnable(!GUIListener.isEnable());
                                sendProperMessage(player, new String[]{
                                        "&f[&eAFS&f] " + (GUIListener.isEnable() ? "&aON" : "&cOFF") + "&7 GUI监听器",
                                });
                                break;
                        }
                    }
                }
            }
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        if (command.getName().equalsIgnoreCase("af_skin_changer")) {
            if (strings.length == 1) {
                return Arrays.asList("reload", "r", "skin", "s", "toggle");
            }
            if (strings.length == 2) {
                if (strings[0].equalsIgnoreCase("toggle")) {
                    return Stream.of(LISTENERS.values()).map(LISTENERS::getName).collect(Collectors.toList());
                }
            }
        }
        return Collections.emptyList();
    }
}