package net.banaan.atla.entity.entities;

import net.banaan.atla.block.ModBlocks;
import net.banaan.atla.entity.ModEntities;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class SkyBisonEntity extends Animal {

    public SkyBisonEntity(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
    }

    public final AnimationState idleAnimationState = new AnimationState();
    private int idleAnimationTimeout = 0;
    public final AnimationState walkingAnimationState = new AnimationState();
    public final AnimationState sittingAnimationState = new AnimationState();
    public final AnimationState flyingAnimationState = new AnimationState();


    @Override
    public void tick() {
        super.tick();

        if(this.level().isClientSide) {
            setupAnimationStates();

            if (this.isMoving()) {
                this.idleAnimationState.stop();
                this.walkingAnimationState.startIfStopped(this.tickCount);
            } else {
                this.walkingAnimationState.stop();
            }
        }
    }

    private boolean isMoving() {
        return this.getDeltaMovement().horizontalDistanceSqr() > 1.0E-6D;
    }


    private void setupAnimationStates() {
        if(this.idleAnimationTimeout <= 0) {
            this.idleAnimationTimeout = this.random.nextInt(40) + 80;
            this.idleAnimationState.start(this.tickCount);
        } else {
            --this.idleAnimationTimeout;
        }
    }

    

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new BreedGoal(this, 0.5D));
        this.goalSelector.addGoal(2, new TemptGoal(this, 1.2D, Ingredient.of(ModBlocks.FRUIT_PIE.get()), false));
        this.goalSelector.addGoal(3, new FollowParentGoal(this, 1.1D));
        this.goalSelector.addGoal(4, new WaterAvoidingRandomStrollGoal(this, 1));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1));

        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 4f));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));

    }

    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 20D)
                .add(Attributes.MOVEMENT_SPEED, 0.4D)
                .add(Attributes.FOLLOW_RANGE, 24D);

    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob ageableMob) {
        return ModEntities.SKY_BISON.get().create(level);
    }

    @Override
    public boolean isFood(ItemStack itemStack) {
        return itemStack.is(ModBlocks.FRUIT_PIE_ITEM.get());
    }
}
