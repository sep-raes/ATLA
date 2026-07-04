package net.banaan.atla.block;

import net.banaan.atla.block.custom.FruitPieBlock;
import net.banaan.atla.item.ModItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

import static net.banaan.atla.Atla.MODID;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);



    public static final RegistryObject<Block> FRUIT_PIE = registerBlock("fruit_pie",
            () -> new FruitPieBlock(BlockBehaviour
                    .Properties.copy(Blocks.CAKE)));


    public static final RegistryObject<Block> BANANA_PLANT = registerBlock("banana_plant",
            () -> new Block(BlockBehaviour
                    .Properties.of()
                    .noCollission()
                    .instabreak()
                    .sound(SoundType.GRASS)));

    public static final RegistryObject<Block> BANANA_LOG = registerBlock("banana_log",
            () -> new RotatedPillarBlock(BlockBehaviour
                    .Properties.copy(Blocks.SPRUCE_LOG)));

    public static final RegistryObject<Block> BANANA_PLANKS = registerBlock("banana_planks",
            () -> new Block(BlockBehaviour
                    .Properties.copy(Blocks.SPRUCE_PLANKS)));

    public static final RegistryObject<Block> BANANA_STAIRS = registerBlock("banana_stairs",
            () -> new StairBlock(
                    BANANA_PLANKS.get().defaultBlockState(),
                    BlockBehaviour.Properties.copy(Blocks.SPRUCE_STAIRS)));




    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block) {
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
        ITEMS.register(eventBus);
    }





}
