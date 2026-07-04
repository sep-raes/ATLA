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
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.registries.RegistryObject;

import java.util.Set;

public class ModBlockLootTables extends BlockLootSubProvider {
    public ModBlockLootTables() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {
        this.dropSelf(ModBlocks.BANANA_PLANT.get());


        this.add(ModBlocks.BANANA_PLANT.get(),
                block -> createFortuneDrop(ModBlocks.BANANA_PLANT.get(), ModItems.BANANA.get()));
        this.add(ModBlocks.BANANA_PLANT.get(),
                block -> createFortuneDrop(ModBlocks.BANANA_PLANT.get(), ModItems.BANANA_LEAF.get()));
        this.add(ModBlocks.FRUIT_PIE.get(),
                block -> createClearDrop());
        this.add(ModBlocks.BANANA_LOG.get(),
                block -> createSingleItemTable(ModBlocks.BANANA_LOG.get()));
        this.add(ModBlocks.BANANA_PLANKS.get(),
                block -> createSingleItemTable(ModBlocks.BANANA_PLANKS.get()));
        this.add(ModBlocks.BANANA_STAIRS.get(),
                block -> createSingleItemTable(ModBlocks.BANANA_STAIRS.get()));


    }

    protected LootTable.Builder createFortuneDrop(Block block, Item item) {
        return createSilkTouchDispatchTable(block,
                this.applyExplosionDecay(block,
                        LootItem.lootTableItem(item)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 5.0F)))
                                .apply(ApplyBonusCount.addUniformBonusCount(Enchantments.BLOCK_FORTUNE))));
    }

    protected LootTable.Builder createClearDrop() { return LootTable.lootTable(); }

    protected LootTable.Builder createSingleDrop(Item item) { return createSingleItemTable(item); }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }
}