package org.lozin.af_skinchanger.Enums;

import lombok.Getter;

@Getter
public enum LISTENERS {
    GUI("gui");

    private String name;
    LISTENERS(String name) {
        this.name = name;
    }
    public static LISTENERS getListener(String name) {
        for (LISTENERS listener : LISTENERS.values()) {
            if (listener.getName().equals(name)) {
                return listener;
            }
        }
        return null;
    }
}
