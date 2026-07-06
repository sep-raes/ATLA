package net.banaan.atla.entity.entities;

import net.banaan.atla.block.ModBlocks;
import net.banaan.atla.entity.ModEntities;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
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
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class SkyBisonEntity extends Animal {
    private static final EntityDataAccessor<Boolean> SITTING =
            SynchedEntityData.defineId(SkyBisonEntity.class, EntityDataSerializers.BOOLEAN);

    public SkyBisonEntity(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
    }

    public final AnimationState idleAnimationState = new AnimationState();
    private int idleAnimationTimeout = 0;
    public final AnimationState walkingAnimationState = new AnimationState();
    public final AnimationState sittingAnimationState = new AnimationState();
    public final AnimationState flyingAnimationState = new AnimationState();

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(SITTING, false);
    }

    public boolean isSitting() {
        return this.entityData.get(SITTING);
    }

    public void setSitting(boolean sitting) {
        this.entityData.set(SITTING, sitting);
    }

    @Override
    protected float getJumpPower() {
        return 0.0F;
    }

    @Override
    public void tick() {
        super.tick();

        if (this.isSitting()) {

            this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.0D);
        } else {
            this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.4D);
        }

        if (this.level().isClientSide) {
            setupAnimationStates();

            if (this.isSitting()) {
                this.walkingAnimationState.stop();
                this.idleAnimationState.stop();
                this.sittingAnimationState.startIfStopped(this.tickCount);
            } else if (this.isMoving()) {
                this.idleAnimationState.stop();
                this.sittingAnimationState.stop();
                this.walkingAnimationState.startIfStopped(this.tickCount);
            } else {
                this.walkingAnimationState.stop();
                this.sittingAnimationState.stop();
            }
        }
    }

    private boolean isMoving() {
        return this.getDeltaMovement().horizontalDistanceSqr() > 1.0E-6D;
    }


    private void setupAnimationStates() {
        if(this.idleAnimationTimeout <= 0) {
            this.idleAnimationTimeout = this.random.nextInt(40);
            this.idleAnimationState.start(this.tickCount);
        } else {
            --this.idleAnimationTimeout;
        }
    }

    

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new BreedGoal(this, 0.5D));
        this.goalSelector.addGoal(2, new TemptGoal(this, 1.2D, Ingredient.of(ModBlocks.FRUIT_PIE_ITEM.get()), false));
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

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        if (this.isInLove()) {
            return InteractionResult.PASS;
        }

        if (!this.level().isClientSide && player.getMainHandItem().getItem() != ModBlocks.FRUIT_PIE_ITEM.get() && player.getOffhandItem().getItem() != ModBlocks.FRUIT_PIE_ITEM.get()) {
            this.setSitting(!this.isSitting());
            return InteractionResult.CONSUME;
        } else if (player.getMainHandItem().getItem() == ModBlocks.FRUIT_PIE_ITEM.get() && player.getOffhandItem().getItem() == ModBlocks.FRUIT_PIE_ITEM.get()) {
            return InteractionResult.FAIL;
        }
        return InteractionResult.SUCCESS;
    }


    @Override
    public void travel(Vec3 travelVector) {
        if (this.isSitting()) {
            this.setYRot(this.yBodyRot);
            this.yHeadRot = this.yBodyRot;
            this.yHeadRotO = this.yBodyRot;
        }
        super.travel(travelVector);
    }
}
