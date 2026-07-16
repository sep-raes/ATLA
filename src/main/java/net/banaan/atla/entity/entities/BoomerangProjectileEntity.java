package net.banaan.atla.entity.entities;

import net.banaan.atla.entity.ModEntities;
import net.banaan.atla.item.ModItems;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;

public class BoomerangProjectileEntity extends ThrowableItemProjectile {
    public BoomerangProjectileEntity(EntityType<? extends ThrowableItemProjectile> p_37442_, Level p_37443_) {
        super(p_37442_, p_37443_);
    }

    public BoomerangProjectileEntity(Level p_37443_) {
        super(ModEntities.BOOMERANG_PROJECTILE.get(), p_37443_);
    }

    public BoomerangProjectileEntity(Level p_37443_, LivingEntity livingEntity) {
        super(ModEntities.BOOMERANG_PROJECTILE.get(),livingEntity, p_37443_);
    }

    @Override
    protected Item getDefaultItem() {
        return ModItems.BOOMERANG.get();
    }

    @Override
    protected void onHitEntity(EntityHitResult p_37259_) {
       if(!this.level().isClientSide()) {
       this.level().broadcastEntityEvent(this,((byte) 3));
       }
        super.onHitEntity(p_37259_);
    }


}

