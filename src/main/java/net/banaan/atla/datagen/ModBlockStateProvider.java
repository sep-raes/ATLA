package net.banaan.atla.datagen;

import net.banaan.atla.Atla;
import net.banaan.atla.block.ModBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.*;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, Atla.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        blockWithItem(ModBlocks.BANANA_PLANT);
        blockWithItem(ModBlocks.FRUIT_PIE);

        stairsBlock(((StairBlock) ModBlocks.BANANA_STAIRS.get()), blockTexture(ModBlocks.BANANA_PLANKS.get()));

        //slabBlock(((SlabBlock) ModBlocks.BANANA_SLAB.get()), blockTexture(ModBlocks.BANANA_PLANKS.get()), blockTexture(ModBlocks.BANANA_PLANKS.get()));

        //buttonBlock(((ButtonBlock) ModBlocks.BANANA_BUTTON.get()), blockTexture(ModBlocks.BANANA_PLANKS.get()));
        //pressurePlateBlock(((PressurePlateBlock) ModBlocks.BANANA_STAIRS.get()), blockTexture(ModBlocks.BANANA_PLANKS.get()));

        // fenceBlock(((FenceBlock) ModBlocks.BANANA_STAIRS.get()), blockTexture(ModBlocks.BANANA_PLANKS.get()));
        //fenceGateBlock(((FenceGateBlock) ModBlocks.BANANA_FENCE.get()), blockTexture(ModBlocks.BANANA_PLANKS.get()));
        //wallBlock(((WallBlock) ModBlocks.BANANA_WALL.get()), blockTexture(ModBlocks.BANANA_PLANKS.get()));

        //doorBlockWithRenderType(((DoorBlock) ModBlocks.SAPPHIRE_DOOR.get()), modLoc("block/sapphire_door_bottom"), modLoc("block/sapphire_door_top"), "cutout");
        //trapdoorBlockWithRenderType(((TrapDoorBlock) ModBlocks.SAPPHIRE_TRAPDOOR.get()), modLoc("block/sapphire_trapdoor"), true, "cutout");
    }

    private void blockWithItem(RegistryObject<Block> blockRegistryObject) {
        simpleBlockWithItem(blockRegistryObject.get(), cubeAll(blockRegistryObject.get()));
    }
}