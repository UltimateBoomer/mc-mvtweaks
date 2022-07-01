package io.github.ultimateboomer.mvtweaks.mixin;

import io.github.ultimateboomer.mvtweaks.MovementTweaks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.input.Input;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientPlayerEntity.class)
public abstract class ClientPlayerEntityMixin extends LivingEntity {
    protected ClientPlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }
    @Shadow public Input input;

    @Shadow public abstract boolean isSneaking();

    @Shadow @Final protected MinecraftClient client;

    @Shadow public abstract void setSprinting(boolean sprinting);

    @Shadow protected int ticksLeftToDoubleTapSprint;

    @Inject(method = "tickMovement", at = @At("HEAD"))
    private void onTickMovement(CallbackInfo ci) {
        if (!MovementTweaks.getInstance().getConfig().enableFastJump) return;

        var e = ((LivingEntityAccessor) this);
        if (e.getJumpingCooldown() == 10) {
            e.setJumpingCooldown(0);
        }
    }

    @Inject(method = "tickMovement", at = @At("HEAD"))
    private void onTickMovement2(CallbackInfo ci) {
        if (this.input != null && this.input.sneaking) {
            MovementTweaks.getInstance().sneakEnabled = false;
        }
    }

    @Inject(method = "tickMovement", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/AbstractClientPlayerEntity;tickMovement()V"))
    private void onTickMovement3(CallbackInfo ci) {
        if (!MovementTweaks.getInstance().getConfig().enableEasySprint) return;

        if (!this.isTouchingWater() && this.client.options.sprintKey.isPressed()) {
            this.setSprinting(true);
        }
    }

    @Inject(method = "isSneaking", at = @At("HEAD"), cancellable = true)
    private void onIsSneaking(CallbackInfoReturnable<Boolean> cir) {
        if (!MovementTweaks.getInstance().getConfig().enableToggleSneak) return;

        cir.setReturnValue(this.input != null && (this.input.sneaking || MovementTweaks.getInstance().sneakEnabled));
        cir.cancel();
    }
}
