package io.github.ultimateboomer.mvtweaks.config;

import io.github.ultimateboomer.mvtweaks.MovementTweaks;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;

@Config(name = MovementTweaks.MODID)
public class MovementTweaksConfig implements ConfigData {
    public boolean enableFastJump = false;
    public boolean enableToggleSneak = true;
    public boolean enableEasySprint = false;
}
