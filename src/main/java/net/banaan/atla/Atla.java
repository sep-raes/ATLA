package net.banaan.atla;

import com.mojang.logging.LogUtils;
import net.banaan.atla.GUI.ModMenuTypes;
import net.banaan.atla.bending.ability.AbilityBootstrap;
import net.banaan.atla.bending.ability.AbilitySlotRegistry;
import net.banaan.atla.block.ModBlocks;
import net.banaan.atla.block.entity.ModBlockEntities;
import net.banaan.atla.category.ModCategory;
import net.banaan.atla.entity.ModEntities;
import net.banaan.atla.entity.client.SkyBisonRenderer;
import net.banaan.atla.entity.entities.SkyBisonEntity;
import net.banaan.atla.item.ModItems;
import net.banaan.atla.network.ModMessages;
import net.banaan.atla.util.glider.GlideData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import org.slf4j.Logger;

import java.util.UUID;


@Mod(Atla.MODID)
@SuppressWarnings("removal")
public class Atla {

    public static final String MODID = "atla";
    public static final Logger LOGGER = LogUtils.getLogger();



    public Atla() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        System.out.println("doodoogaysex");
        modEventBus.addListener(this::commonSetup);

        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModCategory.register(modEventBus);
        ModEntities.register(modEventBus);

        AbilityBootstrap.init();
        AbilitySlotRegistry.init();


        ModMenuTypes.MENU_TYPES.register(modEventBus);
        ModBlockEntities.BLOCK_ENTITIES.register(modEventBus);
        ModMessages.register();



        MinecraftForge.EVENT_BUS.register(this);

        modEventBus.addListener(this::addCreative);
        GlideData.addUUID(UUID.randomUUID());


        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        LOGGER.info("HELLO FROM COMMON SETUP");
        LOGGER.info("DIRT BLOCK >> {}", ForgeRegistries.BLOCKS.getKey(Blocks.DIRT));

        if (Config.logDirtBlock) LOGGER.info("DIRT BLOCK >> {}", ForgeRegistries.BLOCKS.getKey(Blocks.DIRT));

        LOGGER.info(Config.magicNumberIntroduction + Config.magicNumber);

        Config.items.forEach((item) -> LOGGER.info("ITEM >> {}", item.toString()));

        event.enqueueWork(() -> {
            SpawnPlacements.register(
                    ModEntities.SKY_BISON.get(),
                    SpawnPlacements.Type.ON_GROUND,
                    Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                    SkyBisonEntity::checkSkyBisonSpawnRules
            );
        });


    }


    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS) event.accept(ModBlocks.FRUIT_PIE);
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        // Do something when the server starts
        LOGGER.info("HELLO from server starting");
    }


    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {

        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            // Some client setup code
            LOGGER.info("HELLO FROM CLIENT SETUP");
            LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
            EntityRenderers.register(ModEntities.SKY_BISON.get(), SkyBisonRenderer::new);
            EntityRenderers.register(ModEntities.BOOMERANG_PROJECTILE.get(), ThrownItemRenderer::new);
        }


    }
}
