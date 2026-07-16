package net.banaan.atla.mixin;

import net.banaan.atla.events.GliderStaffHandler;
import net.banaan.atla.util.glider.GlidePhysics;
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

import java.util.HashMap;
import java.util.UUID;

@Mixin(HumanoidModel.class)
public abstract class HumanoidModelMixin<T extends LivingEntity> {

    @Shadow public ModelPart rightArm;
    @Shadow public ModelPart leftArm;

    @Shadow @Final public ModelPart rightLeg;

    @Shadow @Final public ModelPart body;

    @Shadow @Final public ModelPart head;

    @Shadow @Final public ModelPart leftLeg;

    private static final HashMap<UUID, Float> lastYaw = new HashMap<>();
    private static final HashMap<UUID, Float> currentRoll = new HashMap<>();

    @Inject(method = "setupAnim", at = @At("TAIL"))
    private void atla$gliderArmPose(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, CallbackInfo ci) {
        if (entity instanceof Player player) {

            if (!GlidePhysics.isValidGlidePose(player)) {
                rightArm.yScale = 1f;
                leftArm.yScale = 1f;
                return;
            };

            boolean state = GliderStaffHandler.getToggle(player.getUUID());
            if (!state) return;

            rightArm.xRot = (float) Math.toRadians(-165);
            rightArm.zRot = (float) Math.toRadians(20);
            rightArm.yRot = 0;
            rightArm.yScale = 1.2f;

            leftArm.xRot = (float) Math.toRadians(-165);
            leftArm.zRot = (float) Math.toRadians(-20);
            leftArm.yRot = 0;
            leftArm.yScale = 1.2f;

            if (player.onGround()) {
                lastYaw.remove(player.getUUID());
                currentRoll.remove(player.getUUID());
                return;
            }

            float vecXZ = GlidePhysics.getVecXZ(player);
            if (vecXZ < 0.01f) return;

            float swingLevel = 2;
            float back = 30;

            body.xRot = (float) Math.toRadians(15);
            head.xRot = (float) Math.toRadians(-10);

            rightLeg.xRot = (float) Math.sin(limbSwing / swingLevel) * 0.3f + (float) Math.toRadians(back);
            leftLeg.xRot = (float) Math.sin((limbSwing / swingLevel) + Math.PI) * 0.3f + (float) Math.toRadians(back);

            rightLeg.setPos(rightLeg.x, rightLeg.y - 0.8F, rightLeg.z + 3F);
            leftLeg.setPos(leftLeg.x, leftLeg.y - 0.8F, leftLeg.z + 3F);

            UUID uuid = player.getUUID();
            float yaw = player.getYRot();
            float prevYaw = lastYaw.getOrDefault(uuid, yaw);

            float yawDelta = Mth_wrapDegrees(yaw - prevYaw);
            lastYaw.put(uuid, yaw);

            float maxRoll = 35f;
            float rollSensitivity = 4f;
            float targetRoll = Math.max(-maxRoll, Math.min(maxRoll, -yawDelta * rollSensitivity));

            float smoothing = 0.2f;
            float prevRoll = currentRoll.getOrDefault(uuid, 0f);
            float roll = prevRoll + (targetRoll - prevRoll) * smoothing;
            currentRoll.put(uuid, roll);

            float rollRad = (float) Math.toRadians(roll);

            body.zRot = rollRad;
            head.zRot = rollRad * 0.5f;

            // Arms - add roll on top of existing zRot pose
            rightArm.zRot += rollRad;
            leftArm.zRot += rollRad;

            // Legs - roll applied on top of existing xRot pose
            rightLeg.zRot = rollRad * 0.6f;
            leftLeg.zRot = rollRad * 0.6f;
        }
    }

    private static float Mth_wrapDegrees(float degrees) {
        float f = degrees % 360.0F;
        if (f >= 180.0F) f -= 360.0F;
        if (f < -180.0F) f += 360.0F;
        return f;
    }
}