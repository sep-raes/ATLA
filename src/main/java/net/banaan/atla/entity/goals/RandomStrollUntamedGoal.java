package net.banaan.atla.entity.goals;

import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.EnumSet;



public class RandomStrollUntamedGoal extends Goal {
    public static final int DEFAULT_INTERVAL = 120;
    protected PathfinderMob mob;
    protected double wantedX;
    protected double wantedY;
    protected double wantedZ;
    protected double speedModifier;
    protected int interval;
    protected boolean forceTrigger;
    private boolean checkNoActionTime;



    public RandomStrollUntamedGoal(PathfinderMob p_25741_, double p_25742_, int p_25743_, boolean p_25744_) {
        this.mob = p_25741_;
        this.speedModifier = p_25742_;
        this.interval = p_25743_;
        this.checkNoActionTime = p_25744_;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE));
    }



    public boolean canUse() {
        if (this.mob.isVehicle()) {
            return false;
        } else {
            if (!this.forceTrigger) {
                if (this.checkNoActionTime && this.mob.getNoActionTime() >= 100) {
                    return false;
                }

                if (this.mob.getRandom().nextInt(reducedTickDelay(this.interval)) != 0) {
                    return false;
                }
            }

            Vec3 vec3 = this.getPosition();
            if (vec3 == null) {
                return false;
            } else {
                this.wantedX = vec3.x;
                this.wantedY = vec3.y;
                this.wantedZ = vec3.z;
                this.forceTrigger = false;
                return true;
            }
        }
    }

    @Nullable
    protected Vec3 getPosition() {
        return DefaultRandomPos.getPos(this.mob, 10, 7);
    }

    public boolean canContinueToUse() {
        return !this.mob.getNavigation().isDone() && !this.mob.isVehicle();
    }

    public void start() {
        if (mob instanceof TamableAnimal animal && animal.isTame()) {
            return;
        }
        this.mob.getNavigation().moveTo(this.wantedX, this.wantedY, this.wantedZ, this.speedModifier);
    }

    public void stop() {
        this.mob.getNavigation().stop();
        super.stop();
    }

    public void trigger() {
        this.forceTrigger = true;
    }

    public void setInterval(int p_25747_) {
        this.interval = p_25747_;
    }
}