package net.banaan.atla.mixin;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashMap;
import java.util.UUID;

@Mixin(HumanoidModel.class)
public abstract class SkyWhistleMixin<T extends LivingEntity> {

    @Shadow public ModelPart rightArm;
    @Shadow public ModelPart leftArm;

    private static final HashMap<UUID, Float> lastYaw = new HashMap<>();
    private static final HashMap<UUID, Float> currentRoll = new HashMap<>();

    @Inject(method = "setupAnim", at = @At("TAIL"))
    private void atla$SkyWhistle(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, CallbackInfo ci) {
        if (entity instanceof Player player) {

        }
    }

}