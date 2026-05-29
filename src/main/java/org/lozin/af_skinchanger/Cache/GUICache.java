package org.lozin.af_skinchanger.Cache;

import lombok.Data;
import lombok.Getter;
import lombok.NonNull;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.lozin.af_skinchanger.Dictionary;
import org.lozin.af_skinchanger.Enums.GUI_BACKGROUND;
import org.lozin.af_skinchanger.Processor.ItemProcessor;
import org.lozin.af_skinchanger.Processor.NBTReader;

import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class GUICache {
    @Getter
    // slot: -> itemStack
    private static Map<Integer, ItemStack> BACKGROUND_ITEMS = new ConcurrentHashMap<>();

    public static void renderBackgrounds(){
        for (GUI_BACKGROUND type : GUI_BACKGROUND.values()){
            for (int slot : type.getSlots()){
                BACKGROUND_ITEMS.put(slot, buildInitItem(type));
            }
        }
    }

    private static ItemStack buildInitItem(GUI_BACKGROUND type){
        switch (type){
            case FRAME:
                return ItemProcessor.getInstance().build(" ", Material.STAINED_GLASS_PANE, (byte) 0);
            case PRE:
                return ItemProcessor.getInstance().build("&e<> &f上一页", Material.ARROW, null);
            case NEXT:
                return ItemProcessor.getInstance().build("&f下一页 &e<>", Material.ARROW, null);
        }
        return null;
    }

    public static Integer getMaxPage(@NonNull ItemStack item){
        if (ItemProcessor.isProperItem(item)){
            LinkedList<String> skinList = Dictionary.getProperSkins(NBTReader.getDetectKeyValue(item));
            return skinList.size() / 36 + 1;
        }
        return 1;
    }

    @Data
    public static class Location{
        private int page, slot;
        Location(int page, int slot){
            this.page = page;
            this.slot = slot;
        }
    }
}