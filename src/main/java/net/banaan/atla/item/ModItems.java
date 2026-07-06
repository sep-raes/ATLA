package net.banaan.atla.item;

import net.banaan.atla.entity.ModEntities;
import net.banaan.atla.item.custom.FuelItem;
import net.banaan.atla.item.custom.SaddleItem;
import net.banaan.atla.item.custom.StaffItem;
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
            () -> new StaffItem(new Item.Properties()));

    public static final RegistryObject<Item> BISON_SADDLE = ITEMS.register("bison_saddle",
            () -> new SaddleItem(new Item.Properties()));

    public static final RegistryObject<Item> HOOK_SWORD = ITEMS.register("hook_sword",
            () -> new SwordItem(Tiers.IRON, 3, 1, new Item.Properties()));

    public static final RegistryObject<Item> SKY_BISON_SPAWN_EGG = ITEMS.register("sky_bison_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.SKY_BISON, 0x7e9680, 0xc5d1c5, new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
