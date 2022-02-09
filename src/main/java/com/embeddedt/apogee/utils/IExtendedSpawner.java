package com.embeddedt.apogee.utils;

import net.minecraft.world.level.BaseSpawner;

public interface IExtendedSpawner {
    boolean doesIgnorePlayers();
    boolean doesIgnoreConditions();
    boolean doesRequireRedstoneControl();
    boolean doesIgnoreLight();
    boolean doesHaveNoAi();
    void setIgnorePlayers(boolean flag);
    void setIgnoreConditions(boolean flag);
    void setRequireRedstoneControl(boolean flag);
    void setIgnoreLight(boolean flag);
    void setHaveNoAi(boolean flag);
    BaseSpawner getSpawner();
}
