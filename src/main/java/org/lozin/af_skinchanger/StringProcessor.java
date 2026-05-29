package org.lozin.af_skinchanger;

import org.bukkit.entity.Player;

public interface StringProcessor {
    default void sendIfDebug(Player player, String ...message){
        if(AF_SkinChanger.getInstance().getConfig().getBoolean("debug")){
            for(String msg : message){
                player.sendMessage(msg.replaceAll("&", "§"));
            }
        }
    }
    default void sendProperMessage(Player player, String[] message){
        for(String msg : message){
            player.sendMessage(msg.replaceAll("&", "§"));
        }
    }
    default boolean isObjectMapper(String obj){
        return obj.matches("^\\{.*}$");
    }
}