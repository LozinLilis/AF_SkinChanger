package org.lozin.af_skinchanger.Cache;

import lombok.Getter;
import org.bukkit.inventory.ItemStack;
import org.lozin.af_skinchanger.Dictionary;

import java.util.concurrent.ConcurrentHashMap;

@Getter
public class ItemSkinCache {
    // location: -> itemStack
    private static ConcurrentHashMap<Dictionary, ItemStack> LocationCache = new ConcurrentHashMap<>();
    public static void init() {
        LocationCache.clear();
    }

}