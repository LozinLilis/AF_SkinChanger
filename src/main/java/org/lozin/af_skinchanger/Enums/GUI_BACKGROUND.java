package org.lozin.af_skinchanger.Enums;

import lombok.Getter;

@Getter
public enum GUI_BACKGROUND {
    FRAME(0,1,2,3,4,5,6,7,8,45,46,47,48,49,50,51,52,53), PRE(47), NEXT(51);

    private int[] slots;

    GUI_BACKGROUND(int ... slots) {
        this.slots = slots;
    }

    public int getSlot(){
        return this.slots[0];
    }
}
