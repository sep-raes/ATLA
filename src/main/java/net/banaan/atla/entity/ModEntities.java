package net.banaan.atla.entity;

import net.banaan.atla.Atla;
import net.banaan.atla.entity.entities.BoomerangProjectileEntity;
import net.banaan.atla.entity.entities.SkyBisonEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Atla.MODID);

    public static final RegistryObject<EntityType<SkyBisonEntity>> SKY_BISON = ENTITY_TYPES.register("sky_bison",
            () -> EntityType.Builder
                    .of(SkyBisonEntity::new, MobCategory.CREATURE)
                    .sized(3f, 3f)
                    .build("sky_bison"));

    public static final RegistryObject<EntityType<BoomerangProjectileEntity>> BOOMERANG_PROJECTILE = ENTITY_TYPES.register("boomerang_projectile",
            () -> EntityType.Builder
                    .<BoomerangProjectileEntity>of(BoomerangProjectileEntity::new, MobCategory.MISC)
                    .sized(0.5f, 0.5f)
                    .build("boomerang_projectile"));

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
