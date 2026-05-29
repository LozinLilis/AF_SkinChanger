package org.lozin.af_skinchanger.Processor;

import de.tr7zw.nbtapi.NBTCompound;
import de.tr7zw.nbtapi.NBTItem;
import lombok.NonNull;
import org.bukkit.inventory.ItemStack;
import org.lozin.af_skinchanger.AF_SkinChanger;
import org.lozin.af_skinchanger.Cache.NBTCache;

import java.util.Arrays;
import java.util.List;


public class NBTReader {
    /**
     * 获取物品 nbt 在 config.yml 下的 detectKey 对应的位置的值
     * @param item 用于检测的物品
     * @return 返回的 字典列表的 value
     */
    public static String getDetectKeyValue(@NonNull ItemStack item) {
        String thisPath = NBTCache.getPATH();
        NBTItem nbtItem = new NBTItem(item);
        List<String> paths = split(thisPath);
        NBTCompound currentCompound = nbtItem.getCompound(paths.get(0)) == null ? null : nbtItem.getCompound(paths.get(0));
        if (currentCompound == null) {
            AF_SkinChanger.getInstance().getLogger().warning("路径不存在: " + paths.get(0));
            return null;
        }
        for (int i = 1; i < paths.size(); i++) {
            //System.out.println(currentCompound);
            String segment = paths.get(i);
            if (currentCompound != null && !currentCompound.hasTag(segment)) {
                AF_SkinChanger.getInstance().getLogger().warning("路径不存在: " + String.join(".", paths.subList(0, i + 1)));
                return null;
            }
            if (i == paths.size() - 1) {
                if (currentCompound != null) {
                    return currentCompound.getString(segment);
                }
            };
            if (currentCompound != null) {
                currentCompound = currentCompound.getCompound(segment);
            }
        }
        return null;
    }
    public static List<String> split(String full_path){
        return Arrays.asList(full_path.split("\\."));
    }
}