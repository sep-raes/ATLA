package net.banaan.atla.item;

import net.banaan.atla.entity.ModEntities;
import net.banaan.atla.item.custom.*;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tiers;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static net.banaan.atla.Atla.MODID;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);


    //AIR BENDING
    public static final RegistryObject<Item> BISON_WHISTLE = ITEMS.register("bison_whistle",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> BANANA = ITEMS.register("banana",
            () -> new Item(new Item.Properties()
                    .food(new FoodProperties.Builder()
                            .nutrition(3)
                            .saturationMod(2)
                            .build())));

    public static final RegistryObject<Item> ROASTED_BANANA = ITEMS.register("roasted_banana",
            () -> new Item(new Item.Properties()
                    .food(new FoodProperties.Builder()
                            .nutrition(7)
                            .saturationMod(4)
                            .build())));

    public static final RegistryObject<Item> BANANA_LEAF = ITEMS.register("banana_leaf",
            () -> new FuelItem(new Item.Properties(), 400));


    public static final RegistryObject<Item> GLIDER_STAFF = ITEMS.register("glider_staff",
            () -> new StaffItem(new Item.Properties().stacksTo(1)));



    public static final RegistryObject<Item> HOOK_SWORD = ITEMS.register("hook_sword",
            () -> new SwordItem(Tiers.IRON, 3, 1, new Item.Properties()));

    public static final RegistryObject<Item> SKY_BISON_SPAWN_EGG = ITEMS.register("sky_bison_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.SKY_BISON, 0xdbd0bc, 0x7e624e, new Item.Properties()));


    public static final RegistryObject<Item> OAK_BISON_SADDLE = ITEMS.register("oak_bison_saddle",
            () -> new SaddleItem(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> SPRUCE_BISON_SADDLE = ITEMS.register("spruce_bison_saddle",
            () -> new SaddleItem(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> BIRCH_BISON_SADDLE = ITEMS.register("birch_bison_saddle",
            () -> new SaddleItem(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> JUNGLE_BISON_SADDLE = ITEMS.register("jungle_bison_saddle",
            () -> new SaddleItem(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> ACACIA_BISON_SADDLE = ITEMS.register("acacia_bison_saddle",
            () -> new SaddleItem(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> DARK_OAK_BISON_SADDLE = ITEMS.register("dark_oak_bison_saddle",
            () -> new SaddleItem(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> MANGROVE_BISON_SADDLE = ITEMS.register("mangrove_bison_saddle",
            () -> new SaddleItem(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> CHERRY_BISON_SADDLE = ITEMS.register("cherry_bison_saddle",
            () -> new SaddleItem(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> CRIMSON_BISON_SADDLE = ITEMS.register("crimson_bison_saddle",
            () -> new SaddleItem(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> WARPED_BISON_SADDLE = ITEMS.register("warped_bison_saddle",
            () -> new SaddleItem(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> BANANA_BISON_SADDLE = ITEMS.register("banana_bison_saddle",
            () -> new SaddleItem(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> BANYAN_BISON_SADDLE = ITEMS.register("banyan_bison_saddle",
            () -> new SaddleItem(new Item.Properties().stacksTo(1)));




    public static final RegistryObject<Item> BOOMERANG = ITEMS.register("boomerang",
            () -> new BoomerangItem(new Item.Properties().stacksTo(1)));

    // Dark Basic Flowers
    public static final RegistryObject<Item> ROSE_DARK_TILE = ITEMS.register("rose_dark",
            () -> new PaiShoTileItem(new Item.Properties().stacksTo(1), "rose_dark"));

    public static final RegistryObject<Item> CHRYSANTHEMUM_DARK_TILE = ITEMS.register("chrysanthemum_dark",
            () -> new PaiShoTileItem(new Item.Properties().stacksTo(1), "chrysanthemum_dark"));

    public static final RegistryObject<Item> RHODODENDRON_DARK_TILE = ITEMS.register("rhododendron_dark",
            () -> new PaiShoTileItem(new Item.Properties().stacksTo(1), "rhododendron_dark"));

    public static final RegistryObject<Item> JASMINE_DARK_TILE = ITEMS.register("jasmine_dark",
            () -> new PaiShoTileItem(new Item.Properties().stacksTo(1), "jasmine_dark"));

    public static final RegistryObject<Item> LILY_DARK_TILE = ITEMS.register("lily_dark",
            () -> new PaiShoTileItem(new Item.Properties().stacksTo(1), "lily_dark"));

    public static final RegistryObject<Item> WHITE_JADE_DARK_TILE = ITEMS.register("white_jade_dark",
            () -> new PaiShoTileItem(new Item.Properties().stacksTo(1), "white_jade_dark"));

    // White Basic Flowers
    public static final RegistryObject<Item> ROSE_WHITE_TILE = ITEMS.register("rose_white",
            () -> new PaiShoTileItem(new Item.Properties().stacksTo(1), "rose_white"));

    public static final RegistryObject<Item> CHRYSANTHEMUM_WHITE_TILE = ITEMS.register("chrysanthemum_white",
            () -> new PaiShoTileItem(new Item.Properties().stacksTo(1), "chrysanthemum_white"));

    public static final RegistryObject<Item> RHODODENDRON_WHITE_TILE = ITEMS.register("rhododendron_white",
            () -> new PaiShoTileItem(new Item.Properties().stacksTo(1), "rhododendron_white"));

    public static final RegistryObject<Item> JASMINE_WHITE_TILE = ITEMS.register("jasmine_white",
            () -> new PaiShoTileItem(new Item.Properties().stacksTo(1), "jasmine_white"));

    public static final RegistryObject<Item> LILY_WHITE_TILE = ITEMS.register("lily_white",
            () -> new PaiShoTileItem(new Item.Properties().stacksTo(1), "lily_white"));

    public static final RegistryObject<Item> WHITE_JADE_WHITE_TILE = ITEMS.register("white_jade_white",
            () -> new PaiShoTileItem(new Item.Properties().stacksTo(1), "white_jade_white"));

    // Dark Accents
    public static final RegistryObject<Item> WHEEL_DARK_TILE = ITEMS.register("wheel_dark",
            () -> new PaiShoTileItem(new Item.Properties().stacksTo(1), "wheel_dark"));

    public static final RegistryObject<Item> BOAT_DARK_TILE = ITEMS.register("boat_dark",
            () -> new PaiShoTileItem(new Item.Properties().stacksTo(1), "boat_dark"));

    public static final RegistryObject<Item> ROCK_DARK_TILE = ITEMS.register("rock_dark",
            () -> new PaiShoTileItem(new Item.Properties().stacksTo(1), "rock_dark"));

    public static final RegistryObject<Item> KNOTWEED_DARK_TILE = ITEMS.register("knotweed_dark",
            () -> new PaiShoTileItem(new Item.Properties().stacksTo(1), "knotweed_dark"));

    // White Accents
    public static final RegistryObject<Item> WHEEL_WHITE_TILE = ITEMS.register("wheel_white",
            () -> new PaiShoTileItem(new Item.Properties().stacksTo(1), "wheel_white"));

    public static final RegistryObject<Item> BOAT_WHITE_TILE = ITEMS.register("boat_white",
            () -> new PaiShoTileItem(new Item.Properties().stacksTo(1), "boat_white"));

    public static final RegistryObject<Item> ROCK_WHITE_TILE = ITEMS.register("rock_white",
            () -> new PaiShoTileItem(new Item.Properties().stacksTo(1), "rock_white"));

    public static final RegistryObject<Item> KNOTWEED_WHITE_TILE = ITEMS.register("knotweed_white",
            () -> new PaiShoTileItem(new Item.Properties().stacksTo(1), "knotweed_white"));

    // Dark Special Flowers
    public static final RegistryObject<Item> WHITE_LOTUS_DARK_TILE = ITEMS.register("white_lotus_dark",
            () -> new PaiShoTileItem(new Item.Properties().stacksTo(1), "white_lotus_dark"));

    public static final RegistryObject<Item> ORCHID_DARK_TILE = ITEMS.register("orchid_dark",
            () -> new PaiShoTileItem(new Item.Properties().stacksTo(1), "orchid_dark"));

    // White Special Flowers
    public static final RegistryObject<Item> WHITE_LOTUS_WHITE_TILE = ITEMS.register("white_lotus_white",
            () -> new PaiShoTileItem(new Item.Properties().stacksTo(1), "white_lotus_white"));

    public static final RegistryObject<Item> ORCHID_WHITE_TILE = ITEMS.register("orchid_white",
            () -> new PaiShoTileItem(new Item.Properties().stacksTo(1), "orchid_white"));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
