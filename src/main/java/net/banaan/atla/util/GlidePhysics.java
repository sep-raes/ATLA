package net.banaan.atla.util;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

public class GlidePhysics {
    private static final float MAX_SPEED = 1;
    private static final float MULTIPLIER = 0.03f;
    public static void applyGlideMotion(Player player, int airTicks) {


        Vec3 deltaMovement = player.getDeltaMovement();
        float vecXZ = getVecXZ(player);
        float velY;
        if (vecXZ < 0.05) {
            velY = (float) airTicks / 20 * 0.006f;
        } else {
            velY = (float) airTicks / 20 * 0.003f;
        }
        if (velY > MAX_SPEED) velY = MAX_SPEED;

        float velX = (float) player.getDeltaMovement().x; //(float) (airTicks * deltaMovement.x * MULTIPLIER);  //CRASH
        float velZ = (float) player.getDeltaMovement().z; //(float) (airTicks * deltaMovement.x * MULTIPLIER); //CRASH



        if (deltaMovement.y < 0) {
            Vec3 vec = new Vec3(velX, -velY, velZ);
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
}
