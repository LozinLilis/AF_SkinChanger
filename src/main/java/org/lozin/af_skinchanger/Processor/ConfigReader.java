package org.lozin.af_skinchanger.Processor;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.lozin.af_skinchanger.AF_SkinChanger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ConfigReader {
    public static Map<String, List<String>> traverse(YamlConfiguration config){
        Map<String, List<String>> map = new HashMap<>();
        for (String key : config.getKeys(true)) {
            if (config.get(key) != null){
                map.put(key, Stream.of(config.getString(key).replaceFirst("^\\[", "").replaceFirst("]$", "").replace(" ", "").split(",")).map(String::trim).collect(Collectors.toList()));
            }
        }
        return map;
    }
    public static Object getConfig(String key){
        FileConfiguration config = AF_SkinChanger.getInstance().getConfig();
        switch (config.get(key).getClass().getSimpleName()){
            case "String":
                return config.getString(key);
            case "Integer":
                return config.getInt(key);
            case "Boolean":
                return config.getBoolean(key);
            case "Double":
                return config.getDouble(key);
            case "Long":
                return config.getLong(key);
        }
        return config.get(key);
    }
}