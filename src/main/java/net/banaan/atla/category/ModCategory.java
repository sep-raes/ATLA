
package net.banaan.atla.category;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import static net.banaan.atla.Atla.MODID;
import static net.banaan.atla.block.ModBlocks.*;
import static net.banaan.atla.item.ModItems.*;

public class ModCategory {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_TAB = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);

    public static final RegistryObject<CreativeModeTab> AIR_BENDING = CREATIVE_TAB.register("air_bending",
            () -> CreativeModeTab.builder()
                    .withTabsBefore(CreativeModeTabs.COMBAT)
                    .icon(() -> BISON_WHISTLE.get().getDefaultInstance())
                    .title(Component.translatable("itemGroup.atla.air_bending"))
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(BISON_WHISTLE.get());
                        output.accept(FRUIT_PIE.get());
                        output.accept(BANANA.get());
                        output.accept(ROASTED_BANANA.get());
                        output.accept(BANANA_LEAF.get());
                        output.accept(BANANA_PLANT.get());
                        output.accept(GLIDER_STAFF.get());
                        output.accept(BISON_SADDLE.get());

                        output.accept(BANANA_LOG.get());
                        output.accept(BANANA_PLANKS.get());
                        output.accept(BANANA_STAIRS.get());
                    })
                    .build());

    public static void register(IEventBus eventBus) {
        CREATIVE_TAB.register(eventBus);

    }


}
