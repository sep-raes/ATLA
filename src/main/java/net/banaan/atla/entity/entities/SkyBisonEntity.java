package net.banaan.atla.entity.entities;

import net.banaan.atla.block.ModBlocks;
import net.banaan.atla.datagen.tag.ModItemTagGenerator;
import net.banaan.atla.entity.ModEntities;
import net.banaan.atla.entity.goals.FollowOwnerStateGoal;
import net.banaan.atla.entity.goals.RandomStrollUntamedGoal;
import net.banaan.atla.entity.goals.WanderFlyGoal;
import net.banaan.atla.entity.goals.WanderStateGoal;
import net.banaan.atla.item.ModItems;
import net.banaan.atla.util.ModTags;
import net.banaan.atla.util.enums.MobSaddle;
import net.banaan.atla.util.enums.MobState;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SkyBisonEntity extends TamableAnimal implements Saddleable, PlayerRideableJumping {

    // SUPER
    public SkyBisonEntity(EntityType<? extends TamableAnimal> entityType, Level level) {
        super(entityType, level);
    }

    // ANIMATION STATES
    public final AnimationState idleAnimationState = new AnimationState();
    private int idleAnimationTimeout = 0;
    public final AnimationState walkingAnimationState = new AnimationState();
    public final AnimationState sittingAnimationState = new AnimationState();
    public final AnimationState flyingAnimationState = new AnimationState();
    public final AnimationState flyingIdleAnimationState = new AnimationState();

    // ENTITY DATA
    private static final EntityDataAccessor<Integer> DATA_MOB_STATE =
            SynchedEntityData.defineId(SkyBisonEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> DATA_SADDLED =
            SynchedEntityData.defineId(SkyBisonEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> DATA_SADDLE_TYPE =
            SynchedEntityData.defineId(SkyBisonEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> DATA_FLYING =
            SynchedEntityData.defineId(SkyBisonEntity.class, EntityDataSerializers.BOOLEAN);

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_MOB_STATE, MobState.WANDER.ordinal());
        this.entityData.define(DATA_SADDLED, false);
        this.entityData.define(DATA_SADDLE_TYPE, 0);
        this.entityData.define(DATA_FLYING, false);
    }

    // GETTERS & SETTERS
    @Override
    protected float getJumpPower() {
        return 0.0F;
    }

    private static final double FLIGHT_ACTIVE_THRESHOLD_SQR = 0.0009D; // ~0.03 blocks/tick

    private boolean isActivelyFlyingForward() {
        boolean riddenFreeFlight = this.isVehicle() && this.getControllingPassenger() instanceof Player;
        boolean tameSelfFlight = this.isTame() && this.isFlying() && !this.isVehicle();

        if (riddenFreeFlight || tameSelfFlight) {

            return this.hasEngagedForward;
        }

        return this.getDeltaMovement().lengthSqr() > FLIGHT_ACTIVE_THRESHOLD_SQR;
    }

    private boolean isMoving() {
        if (this.isVehicle() && this.getControllingPassenger() instanceof Player player) {
            return Math.abs(player.zza) > 0.0f || Math.abs(player.xxa) > 0.0f;
        }
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
    }

    public boolean isBisonSitting() {
        return this.getMobState() == MobState.STATIC;
    }

    public boolean isFlying() {
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

    public void setSaddleType(MobSaddle saddleType) {
        this.entityData.set(DATA_SADDLE_TYPE, saddleType.ordinal());
    }

    public MobSaddle getSaddleType() {
        return MobSaddle.values()[this.entityData.get(DATA_SADDLE_TYPE)];
    }

    public void setFlying(boolean flying) {
        this.entityData.set(DATA_FLYING, flying);
        this.setMobState(flying ? MobState.FLYING : MobState.WANDER);
        this.setNoGravity(flying);
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

        if (!this.hasSpawnPoint && !this.level().isClientSide()) {
            this.spawnPointX = this.getX();
            this.spawnPointZ = this.getZ();
            this.hasSpawnPoint = true;
        }

        if (this.groundCooldown > 0) {
            this.groundCooldown--;
        }

        if (this.level().isClientSide) {
            setupAnimationStates();

            if (this.isBisonSitting()) {
                this.walkingAnimationState.stop();
                this.idleAnimationState.stop();
                this.flyingAnimationState.stop();
                this.flyingIdleAnimationState.stop();
                this.sittingAnimationState.startIfStopped(this.tickCount);
            } else if (this.isFlying() && !this.isActivelyFlyingForward()) {
                this.walkingAnimationState.stop();
                this.idleAnimationState.stop();
                this.sittingAnimationState.stop();
                this.flyingAnimationState.stop();
                this.flyingIdleAnimationState.startIfStopped(this.tickCount);
            } else if (this.isFlying() && this.isActivelyFlyingForward()) {
                this.walkingAnimationState.stop();
                this.idleAnimationState.stop();
                this.sittingAnimationState.stop();
                this.flyingIdleAnimationState.stop();
                this.flyingAnimationState.startIfStopped(this.tickCount);
            } else if (this.isMoving() && this.onGround()) {
                this.idleAnimationState.stop();
                this.sittingAnimationState.stop();
                this.flyingAnimationState.stop();
                this.flyingIdleAnimationState.stop();
                this.walkingAnimationState.startIfStopped(this.tickCount);
            } else {
                this.walkingAnimationState.stop();
                this.sittingAnimationState.stop();
                this.flyingAnimationState.stop();
                this.flyingIdleAnimationState.stop();
            }
        }
    }

    // ANIMATIONS
    private void setupAnimationStates() {
        if (this.idleAnimationTimeout <= 0) {
            this.idleAnimationTimeout = this.random.nextInt(40) + 40;
            this.idleAnimationState.start(this.tickCount);
        } else {
            --this.idleAnimationTimeout;
        }
    }

    // GOALS
    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 1.5D));
        this.goalSelector.addGoal(2, new SitWhenOrderedToGoal(this));
        this.goalSelector.addGoal(3, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(4, new FollowOwnerStateGoal(this, 1.25D, 10F, 2F, false));
        this.goalSelector.addGoal(5, new TemptGoal(this, 1.2D, Ingredient.of(ModBlocks.FRUIT_PIE_ITEM.get()), false));
        this.goalSelector.addGoal(6, new FollowParentGoal(this, 1.1D));
        this.goalSelector.addGoal(7, new WanderStateGoal(this, 1.0D, 15));
        this.goalSelector.addGoal(8, new RandomStrollUntamedGoal(this, 1.0D, 100, true));
        this.goalSelector.addGoal(9, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(10, new WanderFlyGoal(this));
        this.goalSelector.addGoal(11, new RandomLookAroundGoal(this));
    }

    // ATTRIBUTES
    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 100.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.2D)
                .add(Attributes.FOLLOW_RANGE, 32.0D)
                .add(Attributes.JUMP_STRENGTH, 0.7D);
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

        if (this.isTame() && player.isSecondaryUseActive() && hand == InteractionHand.MAIN_HAND) {
            if (!this.level().isClientSide) {
                this.cycleMobStates();
                player.displayClientMessage(Component.literal("Bison Behavior: " + this.getMobState().name()), true);
            }
            return InteractionResult.sidedSuccess(this.level().isClientSide);
        }

        if (!this.isTame() && this.isFlying()) {
            if (!this.level().isClientSide) {
                this.forceLand = true;
                this.groundCooldown = 1000; // 50 seconds * 20 ticks = 1000 ticks
                // Optional: add a frustrated/startled bison sound here

            }
            return InteractionResult.sidedSuccess(this.level().isClientSide);
        }

        if (!this.isSaddled() && itemstack.is(ModItemTagGenerator.BISON_SADDLES) && this.isSaddleable() && this.isTame()) {
            setSaddle(itemstack);
            itemstack.shrink(1);
            this.equipSaddle(this.getSoundSource());
            return InteractionResult.sidedSuccess(this.level().isClientSide);
        }

        if (this.isSaddled() && this.getPassengers().size() < 3 && !player.isSecondaryUseActive()) {
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
                    this.level().broadcastEntityEvent(this, (byte) 7);
                } else {
                    this.level().broadcastEntityEvent(this, (byte) 6);
                }
            }
            return InteractionResult.sidedSuccess(this.level().isClientSide());
        }

        return super.mobInteract(player, hand);
    }

    private void setSaddle(ItemStack itemStack) {
        if (itemStack.is(ModItems.OAK_BISON_SADDLE.get())) {
            setSaddleType(MobSaddle.OAK);
        } else if (itemStack.is(ModItems.SPRUCE_BISON_SADDLE.get())) {
            setSaddleType(MobSaddle.SPRUCE);
        } else if (itemStack.is(ModItems.BIRCH_BISON_SADDLE.get())) {
            setSaddleType(MobSaddle.BIRCH);
        } else if (itemStack.is(ModItems.JUNGLE_BISON_SADDLE.get())) {
            setSaddleType(MobSaddle.JUNGLE);
        } else if (itemStack.is(ModItems.ACACIA_BISON_SADDLE.get())) {
            setSaddleType(MobSaddle.ACACIA);
        } else if (itemStack.is(ModItems.DARK_OAK_BISON_SADDLE.get())) {
            setSaddleType(MobSaddle.DARK_OAK);
        } else if (itemStack.is(ModItems.MANGROVE_BISON_SADDLE.get())) {
            setSaddleType(MobSaddle.MANGROVE);
        } else if (itemStack.is(ModItems.CHERRY_BISON_SADDLE.get())) {
            setSaddleType(MobSaddle.CHERRY);
        } else if (itemStack.is(ModItems.CRIMSON_BISON_SADDLE.get())) {
            setSaddleType(MobSaddle.CRIMSON);
        } else if (itemStack.is(ModItems.WARPED_BISON_SADDLE.get())) {
            setSaddleType(MobSaddle.WARPED);
        } else if (itemStack.is(ModItems.BANANA_BISON_SADDLE.get())) {
            setSaddleType(MobSaddle.BANANA);
        } else if (itemStack.is(ModItems.BANYAN_BISON_SADDLE.get())) {
            setSaddleType(MobSaddle.BANYAN);
        } else {
            setSaddleType(MobSaddle.UNSADDLED);
        }
    }

    //SADDLE
    @Override
    public void equipSaddle(@Nullable SoundSource source) {
        this.entityData.set(DATA_SADDLED, true);
        if (source != null) {
            this.level().playSound(null, this, SoundEvents.HORSE_SADDLE, source, 0.5f, 1.0f);
        }
    }

    @Override
    protected boolean canAddPassenger(Entity entity) {
        return this.getPassengers().size() < 3 && entity instanceof Player;
    }

    @Override
    protected void positionRider(Entity passenger, MoveFunction p_19958_) {
        if (this.hasPassenger(passenger)) {
            int index = this.getPassengers().indexOf(passenger);
            double[][] offsets = {
                    {0.0, 3.2, 2},
                    {0.0, 2.8, 0.5},
                    {0.0, 2.8, -0.5}
            };

            if (index < offsets.length) {
                double offsetX = offsets[index][0];
                double offsetY = offsets[index][1];
                double offsetZ = offsets[index][2];

                double rotatedX = offsetX * Math.cos(Math.toRadians(this.getYRot())) - offsetZ * Math.sin(Math.toRadians(this.getYRot()));
                double rotatedZ = offsetX * Math.sin(Math.toRadians(this.getYRot())) + offsetZ * Math.cos(Math.toRadians(this.getYRot()));

                passenger.setPos(this.getX() + rotatedX, this.getY() + offsetY, this.getZ() + rotatedZ);
            }
        }
    }

    @NotNull
    @Override
    public Vec3 getDismountLocationForPassenger(LivingEntity passenger) {
        float baseValue = 2.0f + random.nextFloat();

        if (random.nextBoolean()) {
            baseValue = -baseValue;
        }

        double offsetX = baseValue;
        double offsetY = 0;
        double offsetZ = -baseValue;

        return new Vec3(this.getX() + offsetX, this.getY() + offsetY, this.getZ() + offsetZ);
    }

    @Override
    public boolean causeFallDamage(float p_147187_, float p_147188_, DamageSource p_147189_) {
        return false;
    }

    @Override
    protected void removePassenger(Entity passenger) {
        super.removePassenger(passenger);
    }

    //DATA
    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("MobState", this.entityData.get(DATA_MOB_STATE));
        tag.putBoolean("Saddled", this.entityData.get(DATA_SADDLED));
        tag.putInt("SaddleType", this.entityData.get(DATA_SADDLE_TYPE));
        tag.putBoolean("HasSpawnPoint", this.hasSpawnPoint);
        tag.putDouble("SpawnPointX", this.spawnPointX);
        tag.putDouble("SpawnPointZ", this.spawnPointZ);
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
        if (tag.contains("HasSpawnPoint")) {
            this.hasSpawnPoint = tag.getBoolean("HasSpawnPoint");
        }
        if (tag.contains("SpawnPointX")) {
            this.spawnPointX = tag.getDouble("SpawnPointX");
        }
        if (tag.contains("SpawnPointZ")) {
            this.spawnPointZ = tag.getDouble("SpawnPointZ");
        }

        this.setNoGravity(this.isFlying());
    }

    //MOVE
    @Nullable
    @Override
    public LivingEntity getControllingPassenger() {
        if (this.getPassengers().isEmpty() || !(this.getPassengers().get(0) instanceof Player player)) {
            return null;
        }
        return player;
    }

    @Override
    public boolean isControlledByLocalInstance() {
        Entity controller = this.getControllingPassenger();
        if (controller != null) {
            return controller.isControlledByLocalInstance();
        }
        return super.isControlledByLocalInstance();
    }

    @Override
    protected void tickRidden(Player player, Vec3 travelVector) {
        super.tickRidden(player, travelVector);

        this.setYRot(player.getYRot());
        this.yRotO = this.getYRot();
        this.setXRot(player.getXRot() * 0.5f);
        this.setRot(this.getYRot(), this.getXRot());
        this.yBodyRot = this.getYRot();
        this.yHeadRot = this.yBodyRot;
    }

    @Override
    protected Vec3 getRiddenInput(Player player, Vec3 travelVector) {
        float forward = player.zza;

        if (forward < 0.0f) {
            forward *= 0.25f;
        }

        return new Vec3(0.0, 0.0, forward);
    }

    @Override
    protected float getRiddenSpeed(Player player) {
        return (float) this.getAttributeValue(Attributes.MOVEMENT_SPEED) * 1.5f;
    }

    private int flightToggleCooldown = 0;

    @Override
    public void travel(Vec3 travelVector) {
        if (this.isTame() && this.isBisonSitting()) {
            this.setYRot(this.yBodyRot);
            this.yHeadRot = this.yBodyRot;
            this.yHeadRotO = this.yBodyRot;

            Vec3 currentMovement = this.getDeltaMovement();
            this.setDeltaMovement(0, currentMovement.y, 0);
            super.travel(Vec3.ZERO);
            return;
        }

        if (this.isVehicle() && this.getControllingPassenger() instanceof Player controller) {
            this.setYRot(controller.getYRot());
            this.yRotO = this.getYRot();
            this.setXRot(controller.getXRot() * 0.5f);
            this.setRot(this.getYRot(), this.getXRot());
            this.yBodyRot = this.getYRot();
            this.yHeadRot = this.yBodyRot;


            System.out.println(flightToggleCooldown);

            if (flightToggleCooldown != 0) {
                flightToggleCooldown--;
            }

            if (this.isFlying()) {
                controller.displayClientMessage(Component.translatable("Press: " + Minecraft.getInstance().options.keyLeft.getKey().getDisplayName().getString() + " when low to the ground go into walk state!"), true);
                tickFreeFlight(controller);
                return;
            } else if (isAKeyDown(controller) && flightToggleCooldown == 0) {
                flightToggleCooldown = 20;
                this.setFlying(true);
                return;
            }




            super.travel(travelVector);

            controller.displayClientMessage(Component.translatable("Press: " + Minecraft.getInstance().options.keyLeft.getKey().getDisplayName().getString() + " to go into fly state!" ), true);
            float speed = this.getRiddenSpeed(controller);
            this.setSpeed(speed);
            Vec3 input = this.getRiddenInput(controller, travelVector);

            super.travel(new Vec3(0.0D, travelVector.y, input.z));
            return;
        }

        if (this.isFlying()) {
            if (this.isTame()) {
                tickUnriddenFlight();
            } else {
                tickWildFlight();
            }
            return;
        }

        super.travel(travelVector);
    }




    private boolean hasEngagedForward = false;

    private void tickFreeFlight(Player controller) {

        boolean isParked = !this.level().noCollision(this.getBoundingBox().move(0, -2.0, 0));
        if (isParked) {
            this.hasEngagedForward = false;
        }

        if (isAKeyDown(controller) && isParked && flightToggleCooldown == 0) {
            flightToggleCooldown = 20;
            this.setFlying(false);
            this.setMobState(MobState.WANDER);
            return;
        }

        float inputZ = controller.zza;
        boolean spacePressed = isJumpKeyDown(controller);

        float yawRad = this.getYRot() * ((float) Math.PI / 180F);
        float pitchRad = this.getXRot() * ((float) Math.PI / 180F);
        float dirX = (float) (-Math.sin(yawRad) * Math.cos(pitchRad));
        float dirZ = (float) (Math.cos(yawRad) * Math.cos(pitchRad));

        Vec3 delta = this.getDeltaMovement();
        double velX;
        double velZ;
        double velY;

        if (inputZ > 0) {
            this.hasEngagedForward = true;
        }

        if (this.hasEngagedForward) {
            if (spacePressed) {
                double currentForwardSpeed = delta.x * dirX + delta.z * dirZ;

                double targetForwardSpeed = currentForwardSpeed * 0.85D;

                if (targetForwardSpeed < 0.08D) {
                    targetForwardSpeed = 0.0D;
                    this.hasEngagedForward = false;
                }

                velX = dirX * targetForwardSpeed;
                velZ = dirZ * targetForwardSpeed;
                velY = 0.4D;
            } else {
                float targetSpeed = 1.3f;
                float blendRate = 0.08f;
                float gravity = 1.9f;

                double glideSinkTarget = -0.05D;
                double glideBlend = 0.15D;

                velY = delta.y + (glideSinkTarget - delta.y) * glideBlend;
                velY = Math.max(velY, -0.15D);

                if (inputZ < 0) {
                    targetSpeed *= 0.15f;
                    blendRate = 0.4f;
                    velY = -0.2f * gravity;
                }

                velX = delta.x + ((dirX * targetSpeed) - delta.x) * blendRate;
                velZ = delta.z + ((dirZ * targetSpeed) - delta.z) * blendRate;
            }
        } else {
            if (spacePressed) {
                velY = 0.4D;
                velX = 0.0D;
                velZ = 0.0D;
            } else {
                velY = delta.y * 0.8D;
                velX = delta.x * 0.9D;
                velZ = delta.z * 0.9D;
            }
        }

        this.setDeltaMovement(velX, velY, velZ);
        this.move(MoverType.SELF, this.getDeltaMovement());
    }


    private void tickUnriddenFlight() {

        boolean isParked = !this.level().noCollision(this.getBoundingBox().move(0, -2.0, 0));
        if (isParked) {
            this.hasEngagedForward = false;
            this.setFlying(false);
            this.setMobState(MobState.WANDER);
            return;
        }

        if (this.hasEngagedForward) {
            float yawRad = this.getYRot() * ((float) Math.PI / 180F);
            float pitchRad = this.getXRot() * ((float) Math.PI / 180F);

            float dirX = (float) (-Math.sin(yawRad) * Math.cos(pitchRad));
            float dirZ = (float) (Math.cos(yawRad) * Math.cos(pitchRad));

            float targetSpeed = 1.3f;
            float blendRate = 0.08f;

            Vec3 delta = this.getDeltaMovement();
            double velX = delta.x + ((dirX * targetSpeed) - delta.x) * blendRate;
            double velZ = delta.z + ((dirZ * targetSpeed) - delta.z) * blendRate;

            double velY = delta.y - 0.003D;
            velY = Math.max(velY, -0.15D);

            this.setDeltaMovement(velX, velY, velZ);
        } else {
            Vec3 delta = this.getDeltaMovement();
            double velY = delta.y * 0.8D;

            this.setDeltaMovement(delta.x * 0.9D, velY, delta.z * 0.9D);
        }

        this.move(MoverType.SELF, this.getDeltaMovement());
    }



    @Override
    public boolean canJump() { return false; }

    @Override
    public void onPlayerJump(int jumpPower) { }

    @Override
    public void handleStartJump(int jumpPower) { }

    @Override
    public void handleStopJump() { }

    private boolean isJumpKeyDown(Player controller) {
        try {
            Boolean jumping = ObfuscationReflectionHelper.getPrivateValue(LivingEntity.class, controller, "f_20899_");
            return jumping != null && jumping;
        } catch (Exception e) {
            return false;
        }
    }

    private boolean isAKeyDown(Player controller) {
        return controller.xxa > 0.0F;
    }





    //SPAWN RULES
    public static boolean checkSkyBisonSpawnRules(EntityType<SkyBisonEntity> entityType,
                                                  ServerLevelAccessor level,
                                                  MobSpawnType spawnType,
                                                  BlockPos pos,
                                                  RandomSource random) {
        BlockPos blockBelow = pos.below();

        boolean onValidBlock = level.getBlockState(blockBelow).is(ModTags.Blocks.SKY_BISON_SPAWN_ON);

        boolean isBrightEnough = isBrightEnoughToSpawn(level, pos);

        boolean isHighEnough = pos.getY() >= 130;

        return onValidBlock && isBrightEnough && isHighEnough;
    }

    private static boolean isBrightEnoughToSpawn(LevelAccessor level, BlockPos pos) {
        return level.getRawBrightness(pos, 0) > 8;
    }


    //FREE WANDER
    public boolean hasEngaged = false;
    public boolean forceLand = false;
    public boolean isBraking = false;
    public int groundCooldown = 0;
    public int flightTicks = 0;


    private void completeLanding(String reason, int cooldownTicks) {
        //System.out.println("[BisonMuscle] " + reason);
        this.flightTicks = 0;
        this.hasEngagedForward = false;
        this.isBraking = false;
        this.forceLand = false;
        this.groundCooldown = Math.max(this.groundCooldown, cooldownTicks);
        this.setFlying(false);
        this.setMobState(MobState.WANDER);
    }

    private void tickWildFlight() {
        this.flightTicks++;

        boolean isParked = !this.level().noCollision(this.getBoundingBox().move(0, -0.5, 0));

        // Close to the ground = land. No phase check, no braking check, no decision -
        // this is the one thing that was causing the stuck loop, so it's now unconditional.
        // flightTicks > 40 is just takeoff immunity so it doesn't instantly re-land on launch.
        if (this.flightTicks > 40 && isParked) {
            completeLanding("Close to ground - landing now (parked=true).", 100);
            return;
        }

        if (this.flightTicks < 20) {
            this.setDeltaMovement(this.getDeltaMovement().add(0, 0.1, 0));
        }

        double gravity = 0.004D;
        double thrustFactor = 0.08D;

        float yawRad = this.getYRot() * ((float) Math.PI / 180F);
        Vec3 delta = this.getDeltaMovement();

        double velX = (delta.x * 0.98) + (-Math.sin(yawRad) * thrustFactor);
        double velZ = (delta.z * 0.98) + (Math.cos(yawRad) * thrustFactor);
        double velY = (delta.y * 0.98) - gravity;

        if (this.isBraking) {
            velX *= 0.8;
            velZ *= 0.8;
            velY -= 0.02;
        }

        this.setDeltaMovement(
                Mth.clamp(velX, -0.5, 0.5),
                Mth.clamp(velY, -0.4, 0.4),
                Mth.clamp(velZ, -0.5, 0.5)
        );

        if (this.tickCount % 20 == 0 && !this.level().isClientSide) {
            //System.out.println(String.format("[BisonMuscle] VelY: %.4f | PosY: %.2f | Parked: %b | FlightTicks: %d",
                    //this.getDeltaMovement().y, this.getY(), isParked, this.flightTicks));
        }

        this.move(MoverType.SELF, this.getDeltaMovement());
    }




    //SPAWN
    private double spawnPointX;
    private double spawnPointZ;
    private boolean hasSpawnPoint = false;

    public double getSpawnPointX() {
        return this.hasSpawnPoint ? this.spawnPointX : this.getX();
    }

    public double getSpawnPointZ() {
        return this.hasSpawnPoint ? this.spawnPointZ : this.getZ();
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty,
                                        MobSpawnType spawnType, @Nullable SpawnGroupData spawnGroupData, @Nullable CompoundTag compoundTag) {
        this.spawnPointX = this.getX();
        this.spawnPointZ = this.getZ();
        this.hasSpawnPoint = true;
        //System.out.println("[BisonBrain] Spawn point locked at X=" + this.spawnPointX + " Z=" + this.spawnPointZ);
        return super.finalizeSpawn(level, difficulty, spawnType, spawnGroupData, compoundTag);
    }
}