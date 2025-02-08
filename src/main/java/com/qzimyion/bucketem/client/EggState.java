package com.qzimyion.bucketem.client;

import java.util.Arrays;
import java.util.Comparator;

public enum EggState {
    DEFAULT(0),
    ALT(1),
    TWO_YOLKS(2),
    THREE_YOLKS(3)
    ;

    private static final EggState[] BY_ID = Arrays.stream(values()).sorted(Comparator.comparingInt(EggState::getId)).toArray(EggState[]::new);
    private final int id;

    EggState(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public static EggState byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}
