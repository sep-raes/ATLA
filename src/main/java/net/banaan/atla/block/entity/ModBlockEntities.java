package net.banaan.atla.block.entity;

import net.banaan.atla.block.ModBlocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.banaan.atla.block.entity.custom.PaiShoTableBlockEntity;

public class ModBlockEntities {
    public static final String MOD_ID = "atla";

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, MOD_ID);

    public static final RegistryObject<BlockEntityType<PaiShoTableBlockEntity>> PAI_SHO_TABLE =
            BLOCK_ENTITIES.register("pai_sho_table_be", () ->
                    BlockEntityType.Builder.of(PaiShoTableBlockEntity::new,
                            ModBlocks.PAI_SHO_TABLE.get()).build(null));
}