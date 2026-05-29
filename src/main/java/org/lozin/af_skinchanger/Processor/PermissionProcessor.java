package org.lozin.af_skinchanger.Processor;

import de.tr7zw.nbtapi.NBTItem;
import lombok.NonNull;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

public class PermissionProcessor {
    private static final Map<String, String> MAPPER = new ConcurrentHashMap<>();
    private static List<String> LIST = new ArrayList<>();

    public static void loadMapper(List<String> list){
        for (String s : list) {
            toMap(s);
            MAPPER.putAll(toMap(s));
        }
    }

    public static Map<String, String> toMap(@NonNull String obj){
        if (obj.startsWith("{") && obj.endsWith("}")){
            obj = obj.substring(1, obj.length() - 1);
            return Stream.of(obj)
                    .map(s -> s.split("="))
                    .collect(ConcurrentHashMap::new, (m, v) -> m.put(v[0], v[1]), ConcurrentHashMap::putAll);
        }
        return Collections.emptyMap();
    }

    public static boolean hasPermission(@NonNull ItemStack item, @NonNull Player player){
        NBTItem nbt = new NBTItem(item);
        String key = "req_perm";
        //System.out.println(nbt.getString(key));
        if (!nbt.hasTag(key)) return true;
        return player.hasPermission(nbt.getString(key));
    }
    public static void getDefaultPerms(){
        Object obj = ConfigReader.getConfig("default_permission");
        System.out.println(obj);
    }
}