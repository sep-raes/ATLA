package net.banaan.atla.mixin;

import net.banaan.atla.Atla;
import net.banaan.atla.events.RightClickHandler;
import net.banaan.atla.item.ModItems;
import net.banaan.atla.item.data.ModItemData;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HumanoidModel.class)
public abstract class HumanoidModelMixin<T extends LivingEntity> {

    @Shadow public ModelPart rightArm;
    @Shadow public ModelPart leftArm;

    @Inject(method = "setupAnim", at = @At("TAIL"))
    private void atla$gliderArmPose(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, CallbackInfo ci) {
        boolean state = RightClickHandler.getState();
        if (!state) return;

        rightArm.xRot = (float) Math.toRadians(-165);
        rightArm.zRot = (float) Math.toRadians(20);
        rightArm.yRot = 0;
        rightArm.yScale = 1.2f; // stretches the arm lengthwise

        leftArm.xRot = (float) Math.toRadians(-165);
        leftArm.zRot = (float) Math.toRadians(-20);
        leftArm.yRot = 0;
        leftArm.yScale = 1.2f;
    }
}