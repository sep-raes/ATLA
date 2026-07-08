package net.banaan.atla.entity.goals;

import net.banaan.atla.entity.entities.SkyBisonEntity;
import net.banaan.atla.util.enums.MobState;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.goal.FollowOwnerGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.level.LevelReader;

import java.util.EnumSet;

public class FollowOwnerStateGoal extends FollowOwnerGoal {
    public static final int TELEPORT_WHEN_DISTANCE_IS = 12;
    private static final int MIN_HORIZONTAL_DISTANCE_FROM_PLAYER_WHEN_TELEPORTING = 2;
    private static final int MAX_HORIZONTAL_DISTANCE_FROM_PLAYER_WHEN_TELEPORTING = 3;
    private static final int MAX_VERTICAL_DISTANCE_FROM_PLAYER_WHEN_TELEPORTING = 1;
    private final TamableAnimal tamable;
    private LivingEntity owner;
    private final LevelReader level;
    private final double speedModifier;
    private final PathNavigation navigation;
    private int timeToRecalcPath;
    private final float stopDistance;
    private final float startDistance;
    private float oldWaterCost;
    private final boolean canFly;

    public FollowOwnerStateGoal(TamableAnimal p_25294_, double p_25295_, float p_25296_, float p_25297_, boolean p_25298_) {
        super(p_25294_, p_25295_, p_25296_, p_25297_, p_25298_);
        this.tamable = p_25294_;
        this.level = p_25294_.level();
        this.speedModifier = p_25295_;
        this.navigation = p_25294_.getNavigation();
        this.startDistance = p_25296_;
        this.stopDistance = p_25297_;
        this.canFly = p_25298_;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        if (!(p_25294_.getNavigation() instanceof GroundPathNavigation) && !(p_25294_.getNavigation() instanceof FlyingPathNavigation)) {
            throw new IllegalArgumentException("Unsupported mob type for FollowOwnerGoal");
        }
    }

    public boolean canUse() {
        LivingEntity livingentity = this.tamable.getOwner();
        if (livingentity == null) {
            return false;
        } else if (!(this.tamable instanceof SkyBisonEntity bisonEntity) || bisonEntity.getMobState() != MobState.FOLLOW) {
            return false;
        } else if (livingentity.isSpectator()) {
            return false;
        } else if (this.unableToMove()) {
            return false;
        } else if (this.tamable.distanceToSqr(livingentity) < (double) (this.startDistance * this.startDistance)) {
            return false;
        } else {
            this.owner = livingentity;
            return true;
        }
    }

    public boolean canContinueToUse() {
        if (!(this.tamable instanceof SkyBisonEntity bisonEntity) || bisonEntity.getMobState() != MobState.FOLLOW) {
            return false;
        } else if (this.navigation.isDone()) {
            return false;
        } else if (this.unableToMove()) {
            return false;
        } else {
            return !(this.tamable.distanceToSqr(this.owner) <= (double) (this.stopDistance * this.stopDistance));
        }
    }

    private boolean unableToMove() {
        return this.tamable.isOrderedToSit() || this.tamable.isPassenger() || this.tamable.isLeashed();
    }

    @Override
    public void start() {
        this.tamable.getNavigation().moveTo(this.owner, this.speedModifier);
    }

    @Override
    public void stop() {
        this.owner = null;
        this.tamable.getNavigation().stop();
    }

    @Override
    public void tick() {
        if (this.owner == null) return;

        this.tamable.getLookControl().setLookAt(this.owner, 10.0F, (float)this.tamable.getMaxHeadXRot());

        if (this.tamable.tickCount % 10 == 0) {
            this.tamable.getNavigation().moveTo(this.owner, this.speedModifier);
        }
    }
}

