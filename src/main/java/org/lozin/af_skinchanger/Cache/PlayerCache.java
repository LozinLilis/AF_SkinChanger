package org.lozin.af_skinchanger.Cache;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.concurrent.ConcurrentHashMap;
@Getter
@Setter
public class PlayerCache {
    public static PlayerCache instance;
    private static ConcurrentHashMap<Player, Integer> viewWhichPage = new ConcurrentHashMap<>();
    private final Player player;
    private Integer itemPage = 1;
    private GUI_TYPE guiType;
    private ItemStack onSelectItem;
    @Getter
    private boolean positiveClose = false;
    PlayerCache(Player player) {
        this.player = player;
    }

    public static PlayerCache getInstance(Player player) {
        if (instance == null) {
            instance = new PlayerCache(player);
        }
        return instance;
    }
    @Getter
    public enum GUI_TYPE {
        SKIN("皮肤总览"),
        LIB("幻化物品库"),
        SELECT_ITEM("选择物品"),
        SHUTDOWN("关闭");
        private String name;
        GUI_TYPE(String name) {
            this.name = name;
        }
    }
}