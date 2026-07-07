package net.banaan.atla.block;

import net.banaan.atla.block.custom.FruitPieBlock;
import net.banaan.atla.item.ModItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DoubleHighBlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

import static net.banaan.atla.Atla.MODID;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);



    public static final RegistryObject<Block> FRUIT_PIE = BLOCKS.register("fruit_pie",
            () -> new FruitPieBlock(BlockBehaviour
                    .Properties.copy(Blocks.CAKE)));

    public static final RegistryObject<Item> FRUIT_PIE_ITEM = ITEMS.register("fruit_pie",
            () -> new BlockItem(FRUIT_PIE.get(), new Item.Properties()));


    public static final RegistryObject<Block> BANANA_PLANT = registerBlock("banana_plant",
            () -> new Block(BlockBehaviour
                    .Properties.of()
                    .noCollission()
                    .instabreak()
                    .sound(SoundType.GRASS)));

    public static final RegistryObject<Block> BANANA_LOG = registerBlock("banana_log",
            () -> new RotatedPillarBlock(BlockBehaviour
                    .Properties.copy(Blocks.SPRUCE_LOG)));

    public static final RegistryObject<Block> STRIPPED_BANANA_LOG = registerBlock("stripped_banana_log",
            () -> new RotatedPillarBlock(BlockBehaviour
                    .Properties.copy(Blocks.STRIPPED_SPRUCE_LOG)));

    public static final RegistryObject<Block> BANANA_WOOD = registerBlock("banana_wood",
            () -> new RotatedPillarBlock(BlockBehaviour
                    .Properties.copy(Blocks.SPRUCE_WOOD)));

    public static final RegistryObject<Block> STRIPPED_BANANA_WOOD = registerBlock("stripped_banana_wood",
            () -> new RotatedPillarBlock(BlockBehaviour
                    .Properties.copy(Blocks.STRIPPED_SPRUCE_WOOD)));

    public static final RegistryObject<Block> BANANA_PLANKS = registerBlock("banana_planks",
            () -> new Block(BlockBehaviour
                    .Properties.copy(Blocks.SPRUCE_PLANKS)));

    public static final RegistryObject<Block> BANANA_STAIRS = registerBlock("banana_stairs",
            () -> new StairBlock(
                    BANANA_PLANKS.get().defaultBlockState(),
                    BlockBehaviour.Properties.copy(Blocks.SPRUCE_STAIRS)));

    public static final RegistryObject<Block> BANANA_SLAB = registerBlock("banana_slab",
            () -> new SlabBlock(BlockBehaviour
                    .Properties.copy(Blocks.SPRUCE_SLAB)));

    public static final RegistryObject<Block> BANANA_TRAPDOOR = registerBlock("banana_trapdoor",
            () -> new TrapDoorBlock(BlockBehaviour
                    .Properties.copy(Blocks.SPRUCE_TRAPDOOR),
                    BlockSetType.SPRUCE));

    public static final RegistryObject<Block> BANANA_DOOR = BLOCKS.register("banana_door",
            () -> new DoorBlock(BlockBehaviour
                    .Properties.copy(Blocks.SPRUCE_DOOR),
                    BlockSetType.SPRUCE));

    public static final RegistryObject<Item> BANANA_DOOR_ITEM = ITEMS.register("banana_door",
            () -> new DoubleHighBlockItem(BANANA_DOOR.get(), new Item.Properties()));

    public static final RegistryObject<Block> BANANA_FENCE = registerBlock("banana_fence",
            () -> new FenceBlock(BlockBehaviour
                    .Properties.copy(Blocks.SPRUCE_FENCE)));

    public static final RegistryObject<Block> BANANA_FENCE_GATE = registerBlock("banana_fence_gate",
            () -> new FenceGateBlock(BlockBehaviour
                    .Properties.copy(Blocks.SPRUCE_FENCE_GATE),
                    WoodType.SPRUCE));

    public static final RegistryObject<Block> BANANA_PRESSURE_PLATE = registerBlock("banana_pressure_plate",
            () -> new PressurePlateBlock(
                    PressurePlateBlock.Sensitivity.EVERYTHING,
                    BlockBehaviour.Properties.copy(Blocks.SPRUCE_PRESSURE_PLATE),
                    BlockSetType.SPRUCE));

    public static final RegistryObject<Block> BANANA_BUTTON = registerBlock("banana_button",
            () -> new ButtonBlock(BlockBehaviour
                    .Properties.copy(Blocks.SPRUCE_PRESSURE_PLATE),
                    BlockSetType.SPRUCE, 10,true));

    public static final RegistryObject<Block> BANANA_LEAVES = registerBlock("banana_leaves",
            () -> new LeavesBlock(BlockBehaviour
                    .Properties.copy(Blocks.SPRUCE_PRESSURE_PLATE)));

    public static final RegistryObject<Block> BANYAN_PLANKS = registerBlock("banyan_planks",
            () -> new Block(BlockBehaviour
                    .Properties.copy(Blocks.SPRUCE_PLANKS)));

    public static final RegistryObject<Block> BANYAN_LOG = registerBlock("banyan_log",
            () -> new RotatedPillarBlock(BlockBehaviour
                    .Properties.copy(Blocks.SPRUCE_LOG)));

    public static final RegistryObject<Block> STRIPPED_BANYAN_LOG = registerBlock("stripped_banyan_log",
            () -> new RotatedPillarBlock(BlockBehaviour
                    .Properties.copy(Blocks.STRIPPED_SPRUCE_LOG)));

    public static final RegistryObject<Block> BANYAN_STAIRS = registerBlock("banyan_stairs",
            () -> new StairBlock(
                    BANYAN_PLANKS.get().defaultBlockState(),
                    BlockBehaviour.Properties.copy(Blocks.SPRUCE_STAIRS)));

    public static final RegistryObject<Block> BANYAN_WOOD = registerBlock("banyan_wood",
            () -> new RotatedPillarBlock(BlockBehaviour
                    .Properties.copy(Blocks.SPRUCE_WOOD)));

    public static final RegistryObject<Block> STRIPPED_BANYAN_WOOD = registerBlock("stripped_banyan_wood",
            () -> new RotatedPillarBlock(BlockBehaviour
                    .Properties.copy(Blocks.STRIPPED_SPRUCE_WOOD)));

    public static final RegistryObject<Block> BANYAN_SLAB = registerBlock("banyan_slab",
            () -> new SlabBlock(BlockBehaviour
                    .Properties.copy(Blocks.SPRUCE_SLAB)));

    public static final RegistryObject<Block> BANYAN_FENCE = registerBlock("banyan_fence",
            () -> new FenceBlock(BlockBehaviour
                    .Properties.copy(Blocks.SPRUCE_FENCE)));

    public static final RegistryObject<Block> BANYAN_FENCE_GATE = registerBlock("banyan_fence_gate",
            () -> new FenceGateBlock(BlockBehaviour
                    .Properties.copy(Blocks.SPRUCE_FENCE_GATE),
                    WoodType.SPRUCE));

    public static final RegistryObject<Block> BANYAN_PRESSURE_PLATE = registerBlock("banyan_pressure_plate",
            () -> new PressurePlateBlock(
                    PressurePlateBlock.Sensitivity.EVERYTHING,
                    BlockBehaviour.Properties.copy(Blocks.SPRUCE_PRESSURE_PLATE),
                    BlockSetType.SPRUCE));

    public static final RegistryObject<Block> BANYAN_BUTTON = registerBlock("banyan_button",
            () -> new ButtonBlock(BlockBehaviour
                    .Properties.copy(Blocks.SPRUCE_PRESSURE_PLATE),
                    BlockSetType.SPRUCE, 10,true));

    public static final RegistryObject<Block> BANYAN_LEAVES = registerBlock("banyan_leaves",
            () -> new LeavesBlock(BlockBehaviour
                    .Properties.copy(Blocks.SPRUCE_LEAVES)));

    public static final RegistryObject<Block> BANYAN_TRAPDOOR = registerBlock("banyan_trapdoor",
            () -> new TrapDoorBlock(BlockBehaviour
                    .Properties.copy(Blocks.SPRUCE_TRAPDOOR),
                    BlockSetType.SPRUCE));

    public static final RegistryObject<Block> BANYAN_DOOR = BLOCKS.register("banyan_door",
            () -> new DoorBlock(BlockBehaviour
                    .Properties.copy(Blocks.SPRUCE_DOOR),
                    BlockSetType.SPRUCE));

    public static final RegistryObject<Item> BANYAN_DOOR_ITEM = ITEMS.register("banyan_door",
            () -> new DoubleHighBlockItem(BANYAN_DOOR.get(), new Item.Properties()));
    /*
    public static final RegistryObject<SignBlock> BANANA_SIGN = BLOCKS.register("banana_sign",
            () -> new SignBlock(BlockBehaviour.Properties.copy(Blocks.SPRUCE_SIGN), WoodType.SPRUCE) {
                @Override
                protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
                    super.createBlockStateDefinition(builder);
                    builder.add(WATERLOGGED);
                }

                @Override
                public FluidState getFluidState(BlockState state) {
                    return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
                }

                @Override
                public boolean placeLiquid(LevelAccessor world, BlockPos pos, BlockState state, FluidState fluidState) {
                    if (!state.getValue(WATERLOGGED) && fluidState.getType() == Fluids.WATER) {
                        world.setBlock(pos, state.setValue(WATERLOGGED, true), 3);
                        world.scheduleTick(pos, fluidState.getType(), fluidState.getType().getTickDelay(world));
                        return true;
                    }
                    return false;
                }
            });

    public static final RegistryObject<WallSignBlock> WALL_BANANA_SIGN = BLOCKS.register("wall_banana_sign",
            () -> new WallSignBlock(BlockBehaviour.Properties.copy(Blocks.SPRUCE_WALL_SIGN).lootFrom(BANANA_SIGN), WoodType.SPRUCE));


    */
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
