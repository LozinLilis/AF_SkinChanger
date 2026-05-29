package org.lozin.af_skinchanger;

import lombok.Getter;
import org.bukkit.configuration.file.YamlConfiguration;
import org.lozin.af_skinchanger.Cache.GUICache;
import org.lozin.af_skinchanger.Processor.ConfigReader;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Getter
public class Dictionary {
    private static Map<String, LinkedList<String>> dicCache = new ConcurrentHashMap<>();
    private String key;
    private List<String> skinList;
    private GUICache.Location location;
    public Dictionary(GUICache.Location location, String key, List<String> skinList) {
        this.location = location;
        this.key = key;
        this.skinList = skinList;
    }
    public Dictionary(String key, List<String> skinList) {
        this.key = key;
        this.skinList = skinList;
    }

    /**
     * @param key Dictionary detectKey
     * @return 皮肤列表
     */
    public static LinkedList<String> getProperSkins(String key) {
        Map<String, List<String>> map = ConfigReader.traverse(YamlConfiguration.loadConfiguration(new File(AF_SkinChanger.getInstance().getDataFolder(), "Dictionary.yml")));
        return new LinkedList<>(map.get(key));
    }
}