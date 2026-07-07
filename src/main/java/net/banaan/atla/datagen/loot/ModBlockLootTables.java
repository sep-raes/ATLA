package net.banaan.atla.datagen.loot;

import net.banaan.atla.block.ModBlocks;
import net.banaan.atla.item.ModItems;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegistryObject;

import java.util.Set;

public class ModBlockLootTables extends BlockLootSubProvider {
    public ModBlockLootTables() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {
        this.dropSelf(ModBlocks.BANANA_LOG.get());
        this.dropSelf(ModBlocks.STRIPPED_BANANA_LOG.get());
        this.dropSelf(ModBlocks.BANANA_WOOD.get());
        this.dropSelf(ModBlocks.STRIPPED_BANANA_WOOD.get());

        this.dropSelf(ModBlocks.BANANA_PLANKS.get());
        this.dropSelf(ModBlocks.BANANA_STAIRS.get());

        this.add(ModBlocks.BANANA_SLAB.get(), block -> createSlabDrop(ModBlocks.BANANA_SLAB.get()));

        this.dropSelf(ModBlocks.BANANA_TRAPDOOR.get());
        this.dropSelf(ModBlocks.BANANA_DOOR.get());
        this.dropSelf(ModBlocks.BANANA_FENCE.get());
        this.dropSelf(ModBlocks.BANANA_FENCE_GATE.get());
        this.dropSelf(ModBlocks.BANANA_PRESSURE_PLATE.get());
        this.dropSelf(ModBlocks.BANANA_BUTTON.get());
        this.dropSelf(ModBlocks.BANANA_LEAVES.get());
        //this.dropSelf(ModBlocks.BANANA_SIGN.get());
        //this.dropSelf(ModBlocks.WALL_BANANA_SIGN.get());


        this.dropSelf(ModBlocks.BANYAN_PLANKS.get());
        this.dropSelf(ModBlocks.BANYAN_LOG.get());
        this.dropSelf(ModBlocks.BANYAN_WOOD.get());
        this.dropSelf(ModBlocks.STRIPPED_BANYAN_LOG.get());
        this.dropSelf(ModBlocks.STRIPPED_BANYAN_WOOD.get());
        this.dropSelf(ModBlocks.BANYAN_STAIRS.get());

        this.add(ModBlocks.BANYAN_SLAB.get(), block -> createSlabDrop(ModBlocks.BANYAN_SLAB.get()));

        this.dropSelf(ModBlocks.BANYAN_FENCE.get());
        this.dropSelf(ModBlocks.BANYAN_FENCE_GATE.get());
        this.dropSelf(ModBlocks.BANYAN_PRESSURE_PLATE.get());
        this.dropSelf(ModBlocks.BANYAN_BUTTON.get());
        this.dropSelf(ModBlocks.BANYAN_LEAVES.get());

        this.add(ModBlocks.BANANA_PLANT.get(),
                block -> createFortuneDrop(ModBlocks.BANANA_PLANT.get(), ModItems.BANANA.get()));
        this.add(ModBlocks.BANANA_PLANT.get(),
                block -> createFortuneDrop(ModBlocks.BANANA_PLANT.get(), ModItems.BANANA_LEAF.get()));
        this.add(ModBlocks.FRUIT_PIE.get(),
                block -> createClearDrop());


    }

    protected LootTable.Builder createFortuneDrop(Block block, Item item) {
        return createSilkTouchDispatchTable(block,
                this.applyExplosionDecay(block,
                        LootItem.lootTableItem(item)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 5.0F)))
                                .apply(ApplyBonusCount.addUniformBonusCount(Enchantments.BLOCK_FORTUNE))));
    }

    protected LootTable.Builder createClearDrop() { return LootTable.lootTable(); }

    protected LootTable.Builder createSlabDrop(Block block) {  return createSlabItemTable(block); }


    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }
}