package io.github.ultimateboomer.mvtweaks.mixin;

import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(LivingEntity.class)
public interface LivingEntityAccessor {
    @Accessor int getJumpingCooldown();
    @Accessor void setJumpingCooldown(int n);
}
