package org.lozin.af_skinchanger.GUI;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.lozin.af_skinchanger.Cache.PlayerCache;

public class GUIManager implements GUIService, Listener {
    public static GUIManager instance;
    public static GUIManager getInstance() {return instance == null ? instance = new GUIManager() : instance;}

    public void renderGUI(Player player) {
        PlayerCache pc = PlayerCache.getInstance(player);
        switch (pc.getGuiType()){
            case LIB:
                break;
            case SKIN:
                SkinGUI.getInstance().render(pc);
                break;
        }
    }
}