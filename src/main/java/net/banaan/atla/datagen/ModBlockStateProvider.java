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
        simpleBlock(ModBlocks.FRUIT_PIE.get());
        blockWithItem(ModBlocks.BANANA_PLANKS);
        simpleBlock(ModBlocks.BANANA_LEAVES.get());
        simpleBlock(ModBlocks.BANYAN_LEAVES.get());
        blockWithItem(ModBlocks.BANYAN_PLANKS);


        logBlock(((RotatedPillarBlock) ModBlocks.BANANA_LOG.get()));
        logBlock(((RotatedPillarBlock) ModBlocks.STRIPPED_BANANA_LOG.get()));
        logBlock(((RotatedPillarBlock) ModBlocks.BANANA_WOOD.get()));
        logBlock(((RotatedPillarBlock) ModBlocks.STRIPPED_BANANA_WOOD.get()));
        logBlock(((RotatedPillarBlock) ModBlocks.BANYAN_LOG.get()));
        logBlock(((RotatedPillarBlock) ModBlocks.BANYAN_WOOD.get()));
        logBlock(((RotatedPillarBlock) ModBlocks.STRIPPED_BANYAN_LOG.get()));
        logBlock(((RotatedPillarBlock) ModBlocks.STRIPPED_BANYAN_WOOD.get()));

        stairsBlock(((StairBlock) ModBlocks.BANANA_STAIRS.get()), blockTexture(ModBlocks.BANANA_PLANKS.get()));
        stairsBlock(((StairBlock) ModBlocks.BANYAN_STAIRS.get()), blockTexture(ModBlocks.BANYAN_PLANKS.get()));

        slabBlock(((SlabBlock) ModBlocks.BANANA_SLAB.get()), blockTexture(ModBlocks.BANANA_PLANKS.get()), blockTexture(ModBlocks.BANANA_PLANKS.get()));
        slabBlock(((SlabBlock) ModBlocks.BANYAN_SLAB.get()), blockTexture(ModBlocks.BANYAN_PLANKS.get()), blockTexture(ModBlocks.BANYAN_PLANKS.get()));

        buttonBlock(((ButtonBlock) ModBlocks.BANANA_BUTTON.get()), blockTexture(ModBlocks.BANANA_PLANKS.get()));
        buttonBlock(((ButtonBlock) ModBlocks.BANYAN_BUTTON.get()), blockTexture(ModBlocks.BANYAN_PLANKS.get()));
        pressurePlateBlock(((PressurePlateBlock) ModBlocks.BANANA_PRESSURE_PLATE.get()), blockTexture(ModBlocks.BANANA_PLANKS.get()));
        pressurePlateBlock(((PressurePlateBlock) ModBlocks.BANYAN_PRESSURE_PLATE.get()), blockTexture(ModBlocks.BANYAN_PLANKS.get()));

        fenceBlock(((FenceBlock) ModBlocks.BANANA_FENCE.get()), blockTexture(ModBlocks.BANANA_PLANKS.get()));
        fenceGateBlock(((FenceGateBlock) ModBlocks.BANANA_FENCE_GATE.get()), blockTexture(ModBlocks.BANANA_PLANKS.get()));
        //wallBlock(((WallBlock) ModBlocks.BANANA_WALL.get()), blockTexture(ModBlocks.BANANA_PLANKS.get()));

        fenceBlock(((FenceBlock) ModBlocks.BANYAN_FENCE.get()), blockTexture(ModBlocks.BANYAN_PLANKS.get()));
        fenceGateBlock(((FenceGateBlock) ModBlocks.BANYAN_FENCE_GATE.get()), blockTexture(ModBlocks.BANYAN_PLANKS.get()));
        //wallBlock(((WallBlock) ModBlocks.BANYAN_WALL.get()), blockTexture(ModBlocks.BANYAN_PLANKS.get()));

        doorBlockWithRenderType(((DoorBlock) ModBlocks.BANANA_DOOR.get()), modLoc("block/banana_door_bottom"), modLoc("block/banana_door_top"), "cutout");
        trapdoorBlockWithRenderType(((TrapDoorBlock) ModBlocks.BANANA_TRAPDOOR.get()), modLoc("block/banana_trapdoor"), true, "cutout");

        doorBlockWithRenderType(((DoorBlock) ModBlocks.BANYAN_DOOR.get()), modLoc("block/banyan_door_bottom"), modLoc("block/banyan_door_top"), "cutout");
        trapdoorBlockWithRenderType(((TrapDoorBlock) ModBlocks.BANYAN_TRAPDOOR.get()), modLoc("block/banyan_trapdoor"), true, "cutout");


    }

    private void blockWithItem(RegistryObject<Block> blockRegistryObject) {
        simpleBlockWithItem(blockRegistryObject.get(), cubeAll(blockRegistryObject.get()));
    }
}