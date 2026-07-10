package net.banaan.atla.entity.goals;

import net.banaan.atla.entity.entities.SkyBisonEntity;
import net.banaan.atla.util.enums.MobState;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;

public class WanderStateGoal extends RandomStrollGoal {
    private final SkyBisonEntity entity;
    private final int radius;
    private BlockPos blockPos;

    public WanderStateGoal(SkyBisonEntity entity, double speedModifier, int radius) {
        super(entity, speedModifier);
        this.entity = entity;
        this.radius = radius;
    }

    @Override
    public boolean canUse() {
        return entity.getMobState() == MobState.WANDER && entity.isTame() && super.canUse();
    }

    @Override
    public boolean canContinueToUse() {
        return entity.getMobState() == MobState.WANDER && entity.isTame() && super.canContinueToUse();
    }
    @Override
    public void start() {
        if (this.blockPos == null) {
            blockPos = entity.blockPosition();
        }
        entity.restrictTo(blockPos, radius);
        super.start();
    }

    @Override
    public void stop() {
        super.stop();
        if (entity.getMobState() != MobState.WANDER) {
            entity.clearRestriction();
            this.blockPos = null;
        }
    }
}
