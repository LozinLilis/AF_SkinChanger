package org.lozin.af_skinchanger.Cache;

import lombok.Getter;
import lombok.Setter;
import org.lozin.af_skinchanger.AF_SkinChanger;

public class NBTCache {
    @Getter
    @Setter
    private static String PATH;
    @Getter
    @Setter
    private static String SKIN_PATH;

    public static void init() {
        PATH = AF_SkinChanger.getInstance().getConfig().getString("detectKey");
        SKIN_PATH = AF_SkinChanger.getInstance().getConfig().getString("skinKey");
    }
}
