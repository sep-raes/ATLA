package net.banaan.atla.entity.goals;

import net.banaan.atla.entity.entities.SkyBisonEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

public class WanderFlyGoal extends Goal {
    protected final SkyBisonEntity bison;

    protected enum FlightPhase { ASCEND, ROAM, GLIDE }
    protected FlightPhase currentPhase;

    protected int flightTimer;
    protected Vec3 targetPos;
    protected double startX, startY, startZ;

    private static final double BOUNDARY_HALF_SIZE = 30;
    private static final double BOUNDARY_SOFT_MARGIN = 12.0;



    public WanderFlyGoal(SkyBisonEntity bison) {
        this.bison = bison;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        if (this.bison.groundCooldown > 0) return false;
        if (this.bison.isTame() || this.bison.isFlying() || this.bison.isVehicle()) return false;
        if (this.bison.getRandom().nextInt(200) != 0) return false;

        BlockPos above = this.bison.blockPosition().above(2);
        return this.bison.level().canSeeSky(above) || this.bison.level().isEmptyBlock(above);
    }

    @Override
    public void start() {



        this.bison.setFlying(true);
        this.bison.hasEngaged = true;
        this.bison.forceLand = false;
        this.bison.isBraking = false;

        this.startX = this.bison.getX();
        this.startY = this.bison.getY();
        this.startZ = this.bison.getZ();

        this.flightTimer = this.bison.getRandom().nextInt(600) + 300;

        this.bison.setDeltaMovement(this.bison.getDeltaMovement().add(0, 0.6, 0));

        startPhase(FlightPhase.ASCEND);
    }

    @Override
    public boolean canContinueToUse() {
        return this.bison.isFlying();
    }

    @Override
    public void tick() {

        if (this.bison.getMobState() != net.banaan.atla.util.enums.MobState.FLYING) {
            System.out.println("[BisonBrain][WARN] MobState was reset out from under WanderFlyGoal! "
                    + "Found=" + this.bison.getMobState() + " - forcing back to FLYING. "
                    + "(If you keep seeing this, another goal is preempting flight.)");
            this.bison.setFlying(true);
        }


        enforceBoundary();
        applySoftBoundaryBias();


        if (this.bison.forceLand && this.currentPhase != FlightPhase.GLIDE) {
            System.out.println("[BisonBrain] Player interrupted! Forcing landing.");
            startPhase(FlightPhase.GLIDE);
        }

        switch (this.currentPhase) {
            case ASCEND -> {
                if (this.bison.tickCount % 10 == 0) {
                    System.out.println("[BisonBrain][ASCEND] CurrentY=" + String.format("%.2f", this.bison.getY())
                            + " TargetY=" + String.format("%.2f", this.targetPos.y));
                }
                if (this.bison.getY() >= this.targetPos.y - 2.0) {
                    System.out.println("[BisonBrain] Reached altitude. Switching to ROAM.");
                    startPhase(FlightPhase.ROAM);
                }
            }
            case ROAM -> {
                this.flightTimer--;

                if (this.flightTimer <= 0) {
                    System.out.println("[BisonBrain] Timer out. Switching to GLIDE.");
                    startPhase(FlightPhase.GLIDE);
                } else if (this.targetPos == null || this.bison.distanceToSqr(this.targetPos) < 15.0) {
                    System.out.println("[BisonBrain] Reached roam waypoint. Picking new one.");
                    pickRoamTarget();
                }
            }
            case GLIDE -> {
                if (this.targetPos == null || this.bison.distanceToSqr(this.targetPos) < 15.0) {
                    pickLandingTarget();
                }
            }
        }

        if (this.targetPos != null) {
            double dX = this.targetPos.x - this.bison.getX();
            double dY = this.targetPos.y - this.bison.getY();
            double dZ = this.targetPos.z - this.bison.getZ();
            double horizDist = Math.sqrt(dX * dX + dZ * dZ);

            float targetYaw = (float) (Math.atan2(dZ, dX) * (180D / Math.PI)) - 90.0F;
            float targetPitch = (float) (-(Math.atan2(dY, horizDist) * (180D / Math.PI)));

            this.bison.setXRot(Mth.approachDegrees(this.bison.getXRot(), targetPitch, 4.0F));
            this.bison.setYRot(Mth.approachDegrees(this.bison.getYRot(), targetYaw, 4.0F));

            this.bison.yBodyRot = this.bison.getYRot();
            this.bison.yHeadRot = this.bison.getYRot();

            if (this.bison.tickCount % 10 == 0) {
                System.out.println("[BisonBrain] Phase: " + this.currentPhase +
                        " | Pitch: " + this.bison.getXRot() +
                        " | Yaw: " + this.bison.getYRot());
            }
        }
    }

    // Hard wall: the bison physically cannot leave the 60x60 box around its spawn point.
    // Clamps position back onto the edge and kills the velocity component pushing outward.
    private void enforceBoundary() {
        double spawnX = this.bison.getSpawnPointX();
        double spawnZ = this.bison.getSpawnPointZ();

        double clampedX = Mth.clamp(this.bison.getX(), spawnX - BOUNDARY_HALF_SIZE, spawnX + BOUNDARY_HALF_SIZE);
        double clampedZ = Mth.clamp(this.bison.getZ(), spawnZ - BOUNDARY_HALF_SIZE, spawnZ + BOUNDARY_HALF_SIZE);

        boolean hitWallX = clampedX != this.bison.getX();
        boolean hitWallZ = clampedZ != this.bison.getZ();

        if (hitWallX || hitWallZ) {
            this.bison.setPos(clampedX, this.bison.getY(), clampedZ);

            Vec3 vel = this.bison.getDeltaMovement();
            this.bison.setDeltaMovement(hitWallX ? 0.0 : vel.x, vel.y, hitWallZ ? 0.0 : vel.z);

            System.out.println("[BisonBrain] Hit boundary wall - clamped position, killed outward velocity.");

            this.targetPos = new Vec3(spawnX, this.bison.getY(), spawnZ);
        }
    }

    private void applySoftBoundaryBias() {
        if (this.currentPhase != FlightPhase.ROAM) return;

        double spawnX = this.bison.getSpawnPointX();
        double spawnZ = this.bison.getSpawnPointZ();
        double distFromCenter = Math.sqrt(
                Math.pow(this.bison.getX() - spawnX, 2) + Math.pow(this.bison.getZ() - spawnZ, 2));

        if (distFromCenter > (BOUNDARY_HALF_SIZE - BOUNDARY_SOFT_MARGIN)) {
            double keepY = this.targetPos != null ? this.targetPos.y : this.bison.getY();
            this.targetPos = new Vec3(spawnX, keepY, spawnZ);
        }
    }

    private void startPhase(FlightPhase newPhase) {
        this.currentPhase = newPhase;

        switch (newPhase) {
            case ASCEND -> {
                double targetAlt = this.startY + 20.0 + this.bison.getRandom().nextDouble() * 20.0;
                this.targetPos = new Vec3(this.startX, targetAlt, this.startZ);
            }
            case ROAM -> pickRoamTarget();
            case GLIDE -> {
                this.bison.isBraking = true;
                pickLandingTarget();
            }
        }
    }

    // Picks anywhere inside the 60x60 box around the spawn point - not a step from
    // the current position, so it can't accumulate drift outside the area over time.
    private void pickRoamTarget() {
        var random = this.bison.getRandom();

        double spawnX = this.bison.getSpawnPointX();
        double spawnZ = this.bison.getSpawnPointZ();

        double targetX = spawnX + (random.nextDouble() * 2.0 - 1.0) * BOUNDARY_HALF_SIZE;
        double targetZ = spawnZ + (random.nextDouble() * 2.0 - 1.0) * BOUNDARY_HALF_SIZE;
        double targetY = Mth.clamp(this.bison.getY() + (random.nextDouble() * 20.0 - 10.0), this.startY - 10, 300);

        this.targetPos = new Vec3(targetX, targetY, targetZ);
    }

    private void pickLandingTarget() {
        double spawnX = this.bison.getSpawnPointX();
        double spawnZ = this.bison.getSpawnPointZ();

        double targetX = Mth.clamp(this.bison.getX() + (this.bison.getRandom().nextDouble() * 30.0 - 15.0),
                spawnX - BOUNDARY_HALF_SIZE, spawnX + BOUNDARY_HALF_SIZE);
        double targetZ = Mth.clamp(this.bison.getZ() + (this.bison.getRandom().nextDouble() * 30.0 - 15.0),
                spawnZ - BOUNDARY_HALF_SIZE, spawnZ + BOUNDARY_HALF_SIZE);
        int terrainY = this.bison.level().getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (int) targetX, (int) targetZ);

        this.targetPos = new Vec3(targetX, terrainY, targetZ);
    }

    @Override
    public void stop() {
        this.bison.hasEngaged = false;
        this.bison.isBraking = false;
        this.bison.forceLand = false;
    }

    @Override
    public boolean isInterruptable() {
        return false;
    }
}