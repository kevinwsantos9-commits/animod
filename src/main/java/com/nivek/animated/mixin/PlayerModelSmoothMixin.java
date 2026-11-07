package com.nivek.animated.mixin;

import com.nivek.animated.client.PoseSmoother;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntityModel.class)
public abstract class PlayerModelSmoothMixin {
    @Inject(method = "setAngles(Lnet/minecraft/entity/LivingEntity;FFFFF)V", at = @At("TAIL"))
    private void nivek$smooth(LivingEntity entity, float limbAngle, float limbDistance, float animationProgress,
                              float headYaw, float headPitch, CallbackInfo ci) {
        PoseSmoother.apply(entity, (PlayerEntityModel<?>) (Object) this);
    }
}
