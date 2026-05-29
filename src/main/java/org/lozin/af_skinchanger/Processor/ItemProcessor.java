package org.lozin.af_skinchanger.Processor;

import de.tr7zw.nbtapi.NBTCompound;
import de.tr7zw.nbtapi.NBTItem;
import io.rokuko.azureflow.api.AzureFlowAPI;
import io.rokuko.azureflow.api.item.Item;
import lombok.NonNull;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.lozin.af_skinchanger.AF_SkinChanger;
import org.lozin.af_skinchanger.Cache.NBTCache;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ItemProcessor {
    public static ItemProcessor instance;
    public static ItemProcessor getInstance() { return instance == null ? instance = new ItemProcessor() : instance;}

    public static <T> void itemUpdater(@NonNull Player player, @NonNull ItemStack item, Map<String, T> map) {
        Item<ItemStack, Player, Location> _i = AzureFlowAPI.INSTANCE.toItem(item);
        if (_i != null) {
            for (Map.Entry<String, T> entry : map.entrySet()) {
                _i.set(entry.getKey(), entry.getValue());
            }
            _i.update(player, item);
        }
    }

    public static boolean isProperItem(@NonNull ItemStack item) {
        String path = NBTCache.getPATH();
        NBTItem nbtItem = new NBTItem(item);
        List<String> paths = NBTReader.split(path);
        NBTCompound currentCompound = nbtItem.getCompound(paths.get(0)) == null ? null : nbtItem.getCompound(paths.get(0));
        for (int i = 1; i < paths.size(); i++) {
            String segment = paths.get(i);
            if (currentCompound != null && !currentCompound.hasTag(segment)) {
                AF_SkinChanger.getInstance().getLogger().warning("路径不存在: " + String.join(".", paths.subList(0, i + 1)));
                return false;
            }
            if (i == paths.size() -1) {
                if (currentCompound != null) {
                    return currentCompound.getString(segment) != null;
                }
            }
            if (currentCompound != null) {
                currentCompound = currentCompound.getCompound(segment);
            }
        }
        return false;
    }

    public static boolean hasChangePerm(@NonNull ItemStack item, @NonNull Player player){
        Item<ItemStack, Player, Location> _i = AzureFlowAPI.INSTANCE.toItem(item);
        if (_i != null) {
            String perm = _i.get(ConfigReader.getConfig("permKey").toString());
            //System.out.println(perm);
            if (perm != null) {
                return player.hasPermission(perm);
            }
        }
        return false;
    }

    public ItemStack build(@Nullable String name, @Nullable Material material, @Nullable Byte damage, @Nullable String ...lore){
        if (material == null) material = Material.valueOf(AF_SkinChanger.getInstance().getConfig().getString("default_material").toUpperCase());
        if (Arrays.stream(Material.values()).noneMatch(material::equals)) return null;
        if (name == null) name = material.name();
        if (damage == null) damage = 0;
        if (lore == null) lore = new String[]{};
        ItemStack item = new ItemStack(material, 1, damage);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore(Arrays.asList(lore));
        item.setItemMeta(meta);
        return item;
    }

    public void test(ItemStack item){
        Player player = AF_SkinChanger.getInstance().getServer().getPlayer("lozin");
        player.getItemOnCursor().setAmount(0);
    }
}