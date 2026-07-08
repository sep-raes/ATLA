package net.banaan.atla.entity.entities;

import net.banaan.atla.block.ModBlocks;
import net.banaan.atla.datagen.ModItemTags;
import net.banaan.atla.entity.ModEntities;
import net.banaan.atla.entity.goals.FollowOwnerStateGoal;
import net.banaan.atla.entity.goals.RandomStrollUntamedGoal;
import net.banaan.atla.entity.goals.WanderStateGoal;
import net.banaan.atla.util.enums.MobState;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class SkyBisonEntity extends TamableAnimal implements Saddleable {

    // SUPER
    public SkyBisonEntity(EntityType<? extends TamableAnimal> entityType, Level level) {
        super(entityType, level);
        this.setBoundingBox(new AABB(0,0,0, 10, 10, 10));
        this.refreshDimensions();
    }

    // ANIMATION STATES
    public final AnimationState idleAnimationState = new AnimationState();
    private int idleAnimationTimeout = 0;
    public final AnimationState walkingAnimationState = new AnimationState();
    public final AnimationState sittingAnimationState = new AnimationState();
    public final AnimationState flyingAnimationState = new AnimationState();

    // ENTITY DATA
    private static final EntityDataAccessor<Integer> DATA_MOB_STATE =
            SynchedEntityData.defineId(SkyBisonEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> DATA_SADDLED =
            SynchedEntityData.defineId(SkyBisonEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> DATA_SADDLE_TYPE =
            SynchedEntityData.defineId(SkyBisonEntity.class, EntityDataSerializers.INT);


    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_MOB_STATE, MobState.FOLLOW.ordinal());
        this.entityData.define(DATA_SADDLED, false);
        this.entityData.define(DATA_SADDLE_TYPE, 0);

    }

    // GETTERS & SETTERS
    @Override
    protected float getJumpPower() {
        return 0.0F;
    }

    private boolean isMoving() {
        return this.getDeltaMovement().horizontalDistanceSqr() > 1.0E-6D;
    }

    @Override
    public boolean isFood(ItemStack itemStack) {
        return itemStack.is(ModBlocks.FRUIT_PIE_ITEM.get());
    }

    public MobState getMobState() {
        return MobState.values()[this.entityData.get(DATA_MOB_STATE)];
    }

    public void setMobState(MobState mobState) {
        this.entityData.set(DATA_MOB_STATE, mobState.ordinal());

        if (mobState == MobState.STATIC) {
            this.setOrderedToSit(true);
            this.navigation.stop();
        } else {
            this.setOrderedToSit(false);
        }
    }

    public boolean isBisonSitting() {
        return this.getMobState() == MobState.STATIC;
    }

    public boolean isBisonFlying() {
        return this.getMobState() == MobState.FLYING;
    }

    @Override
    public boolean isSaddleable() {
        return this.isAlive() && !this.isBaby();
    }

    @Override
    public boolean isSaddled() {
        return this.entityData.get(DATA_SADDLED);
    }

    // MOBSTATES CYCLING
    public void cycleMobStates() {
        MobState[] mobStates = MobState.values();
        MobState next = mobStates[(getMobState().ordinal() + 1) % mobStates.length];
        setMobState(next);
    }



    // TICK
    @Override
    public void tick() {
        super.tick();

        if (this.level().isClientSide) {
            setupAnimationStates();

            if (this.isBisonSitting()) {
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

    // ANIMATIONS
    private void setupAnimationStates() {
        if (this.idleAnimationTimeout <= 0) {
            this.idleAnimationTimeout = this.random.nextInt(40) + 40; // Ensure it doesn't instantly snap
            this.idleAnimationState.start(this.tickCount);
        } else {
            --this.idleAnimationTimeout;
        }
    }

    // GOALS
    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));

        // Use Vanilla's goal framework, hooked directly to our updated setOrderedToSit() logic
        this.goalSelector.addGoal(1, new SitWhenOrderedToGoal(this));
        this.goalSelector.addGoal(2, new BreedGoal(this, 1.0D));

        // Custom states behaviors
        this.goalSelector.addGoal(3, new FollowOwnerStateGoal(this, 1.25D, 10F, 2F, false));
        this.goalSelector.addGoal(4, new TemptGoal(this, 1.2D, Ingredient.of(ModBlocks.FRUIT_PIE_ITEM.get()), false));
        this.goalSelector.addGoal(5, new FollowParentGoal(this, 1.1D));
        this.goalSelector.addGoal(6, new WanderStateGoal(this, 1.0D, 15));
        this.goalSelector.addGoal(7, new RandomStrollUntamedGoal(this, 1.0D, 100, true));
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(9, new RandomLookAroundGoal(this));
    }

    // ATTRIBUTES
    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 100.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.2D)
                .add(Attributes.FOLLOW_RANGE, 32.0D);
    }

    // BREEDING
    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob ageableMob) {
        return ModEntities.SKY_BISON.get().create(level);
    }

    // RIGHT CLICK INTERACTION
    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);


        if (!this.isSaddled() && itemstack.is(ModItemTags.BISON_SADDLES) && this.isSaddleable()) {
            itemstack.shrink(1);
            this.equipSaddle(this.getSoundSource());
            return InteractionResult.sidedSuccess(this.level().isClientSide);
        }
        if(this.isSaddled() && !this.isVehicle() && !player.isSecondaryUseActive()) {
            player.startRiding(this);
            return InteractionResult.sidedSuccess(this.level().isClientSide);
        }

        if (!this.isTame() && itemstack.is(ModBlocks.FRUIT_PIE_ITEM.get())) {
            if (!player.getAbilities().instabuild) {
                itemstack.shrink(1);
            }

            if (!this.level().isClientSide()) {
                if (this.random.nextInt(3) == 0) {
                    this.tame(player);
                    this.setMobState(MobState.STATIC);
                    this.level().broadcastEntityEvent(this, (byte)7);
                } else {
                    this.level().broadcastEntityEvent(this, (byte)6);
                }
            }
            return InteractionResult.sidedSuccess(this.level().isClientSide());
        }

        return super.mobInteract(player, hand);
    }

    // BLOCK MOVEMENT WHEN SITTING
    @Override
    public void travel(Vec3 travelVector) {
        if (this.isBisonSitting()) {
            this.setYRot(this.yBodyRot);
            this.yHeadRot = this.yBodyRot;
            this.yHeadRotO = this.yBodyRot;
            return;
        }
        super.travel(travelVector);
    }



    //SADLE
    @Override
    public void equipSaddle(@Nullable SoundSource source) {
        this.entityData.set(DATA_SADDLED, true);
        if (source != null) {
            this.level().playSound(null, this, SoundEvents.HORSE_SADDLE, source, 0.5f, 1.0f);
        }
    }



    //DATA
    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("MobState", this.entityData.get(DATA_MOB_STATE));
        tag.putBoolean("Saddled", this.entityData.get(DATA_SADDLED));
        tag.putInt("SaddleType", this.entityData.get(DATA_SADDLE_TYPE));
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        if (tag.contains("MobState")) {
            this.entityData.set(DATA_MOB_STATE, tag.getInt("MobState"));
        }
        if (tag.contains("Saddled")) {
            this.entityData.set(DATA_SADDLED, tag.getBoolean("Saddled"));
        }
        if (tag.contains("SaddleType")) {
            this.entityData.set(DATA_SADDLE_TYPE, tag.getInt("SaddleType"));
        }
    }
}
