package net.banaan.atla.util;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class GlidePhysics {
    private static final float MAX_SPEED = 2;

    public static void applyGlideMotion(Player player, int airTicks) {

        if (!isValidGlidePose(player)) return;

        Vec3 deltaMovement = player.getDeltaMovement();
        float yaw = player.getYRot();
        double radians = Math.toRadians(yaw);
        float dirX = (float) (-Math.sin(radians));
        float dirZ = (float) (Math.cos(radians));

        float speedProgress = (float) Math.min(Math.sqrt(airTicks / 50f), 1f); // 0 -> 1 over time
        float targetSpeed = MAX_SPEED * speedProgress;

        float descentProgress = 1;

        float inputZ = player.zza;
        if (inputZ < 0) {
            float brakeFactor = 0.4f;
            targetSpeed *= brakeFactor;
            descentProgress = 1.9f;
        }

        float targetVelX = dirX * targetSpeed;
        float targetVelZ = dirZ * targetSpeed;

        float accelSpeed = 0.08f;
        float brakeSpeed = 0.04f;
        float blendRate = (inputZ < 0) ? brakeSpeed : accelSpeed;

        float velX = (float) (deltaMovement.x + (targetVelX - deltaMovement.x) * blendRate);
        float velZ = (float) (deltaMovement.z + (targetVelZ - deltaMovement.z) * blendRate);

        float speed = (float) Math.sqrt(velX * velX + velZ * velZ);
        if (speed > MAX_SPEED) {
            float scale = MAX_SPEED / speed;
            velX *= scale;
            velZ *= scale;
        }

        descentProgress *= (float) Math.min(airTicks / 30f, 1f);
        float velY = -0.2f * descentProgress;

        if (!player.onGround()) {
            Vec3 vec = new Vec3(velX, velY, velZ);
            player.setDeltaMovement(vec);
        }
    }

    public static float getVecXZ(Player player) {
        float x = (float) Math.abs(player.getDeltaMovement().x);
        float z = (float) Math.abs(player.getDeltaMovement().z);
        if (x < 0.1 && z < 0.1) { return 0.001f; }

        float vecXZ = (float) Math.sqrt(Math.abs(x * x + z * z));
        if (vecXZ == 0) { vecXZ = 0.001f; }
        return vecXZ;
    }


    public static boolean isValidGlidePose(Player player) {

        if (player.getAbilities().flying) return false;
        Level level = player.level();
        Vec3 start = player.position();
        Vec3 end = start.add(0, -319, 0);

        ClipContext context = new ClipContext(
                start,
                end,
                ClipContext.Block.COLLIDER,
                ClipContext.Fluid.NONE,
                player
        );

        HitResult result = level.clip(context);


        return start.y - result.getLocation().y > 2;
    }
}