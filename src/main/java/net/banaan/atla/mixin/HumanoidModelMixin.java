package net.banaan.atla.mixin;

import net.banaan.atla.events.GliderStaffHandler;
import net.banaan.atla.util.GlidePhysics;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
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

    @Shadow @Final public ModelPart rightLeg;

    @Shadow @Final public ModelPart body;

    @Shadow @Final public ModelPart head;

    @Shadow @Final public ModelPart leftLeg;

    @Shadow protected abstract void poseLeftArm(T p_102879_);

    @Inject(method = "setupAnim", at = @At("TAIL"))
    private void atla$gliderArmPose(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, CallbackInfo ci) {
        if (entity instanceof Player player) {

            boolean state = GliderStaffHandler.getToggle(player.getUUID());
            if (!state) return;

            rightArm.xRot = (float) Math.toRadians(-165);
            rightArm.zRot = (float) Math.toRadians(20);
            rightArm.yRot = 0;
            rightArm.yScale = 1.2f; // stretches the arm lengthwise

            leftArm.xRot = (float) Math.toRadians(-165);
            leftArm.zRot = (float) Math.toRadians(-20);
            leftArm.yRot = 0;
            leftArm.yScale = 1.2f;

            if (player.onGround()) return;

            float vecXZ = GlidePhysics.getVecXZ(player);

            if (vecXZ < 0.01f) return;

            float swingLevel = vecXZ * 8;
            float back = vecXZ * 200;

            body.xRot = (float) Math.toRadians(15);
            head.xRot = (float) Math.toRadians(-10);

            rightLeg.xRot = (float) Math.sin(limbSwing / swingLevel) * 0.3f + (float) Math.toRadians(back);
            leftLeg.xRot = (float) Math.sin((limbSwing / swingLevel) + Math.PI) * 0.3f + (float) Math.toRadians(back);

            rightLeg.setPos(rightLeg.x, rightLeg.y - 0.8F, rightLeg.z + 3F);
            leftLeg.setPos(leftLeg.x, leftLeg.y - 0.8F, leftLeg.z + 3F);
        }


    }
}