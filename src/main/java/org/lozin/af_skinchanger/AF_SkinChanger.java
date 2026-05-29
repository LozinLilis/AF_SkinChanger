package org.lozin.af_skinchanger;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.lozin.af_skinchanger.Cache.NBTCache;
import org.lozin.af_skinchanger.Event.GUIListener;
import org.lozin.af_skinchanger.Processor.Commands;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class AF_SkinChanger extends JavaPlugin {
    private static AF_SkinChanger plugin;
    List<String> Title = Stream.of(
            " ", " ",
            "&a====================", " ",
            "&fAF_SkinChanger", " ",
            "&f作者: &cLozin", " ",
            "&f联系方式: &e3531557655", " ",
            "&a====================",
            " ", " "
    ).map(s -> s.replace("&", "§")).collect(Collectors.toList());
    public AF_SkinChanger() {
        plugin = this;
    }

    public static AF_SkinChanger getInstance() {
        return plugin;
    }

    @Override
    public void onLoad() {
        saveAllConfigs();
    }

    public void saveAllConfigs() {
        saveDefaultConfig();
        saveResource("Dictionary.yml", false);
    }

    @Override
    public void onEnable() {
        NBTCache.init();
        for (String s : Title) { getLogger().info(s); }
        //Bukkit.getPluginManager().registerEvents(new HeldEvent(), this);
        Bukkit.getPluginCommand("af_skin_changer").setExecutor(new Commands());
        Bukkit.getPluginManager().registerEvents(new GUIListener(), this);
        plugin = this;
    }

    @Override
    public void onDisable() {

    }

    public void reload() {
        reloadConfig();
        saveAllConfigs();
        saveResource("Dictionary.yml", false);
        NBTCache.init();
    }
}