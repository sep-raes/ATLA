package net.banaan.atla.commands;

import com.mojang.brigadier.CommandDispatcher;
import net.banaan.atla.bending.ability.air.AirBlast;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "atla", bus = Mod.EventBusSubscriber.Bus.FORGE)
public final class ModCommands {
    @SubscribeEvent
    public static void onRegisterCommands(RegisterCommandsEvent event) {
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();
        dispatcher.register(Commands.literal("testairblast").executes(ctx -> {
            if (ctx.getSource().getEntity() instanceof ServerPlayer player) {
                new AirBlast(player).activate();
            }
            return 1;
        }));
    }
}
