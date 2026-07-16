package net.banaan.atla.commands;

import com.mojang.brigadier.CommandDispatcher;
import net.banaan.atla.bending.ability.air.normal.AirBlast;
import net.banaan.atla.bending.data.BendingData;
import net.banaan.atla.bending.data.ModCapabilities;
import net.banaan.atla.bending.element.Element;
import net.banaan.atla.network.BendingSyncPacket;
import net.banaan.atla.network.ModMessages;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
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

        dispatcher.register(Commands.literal("addair").executes(ctx -> {
            if (ctx.getSource().getEntity() instanceof ServerPlayer player) {
                player.getCapability(ModCapabilities.BENDING_DATA).ifPresent(data -> {
                    data.addElement(Element.AIR);
                    player.sendSystemMessage(Component.literal("You have learned Airbending!"));
                    ModMessages.sendToPlayer(new BendingSyncPacket((BendingData) data), player);
                });
            }
            return 1;
        }));
        dispatcher.register(Commands.literal("element").executes(ctx -> {
            if (ctx.getSource().getEntity() instanceof ServerPlayer player) {
                player.getCapability(ModCapabilities.BENDING_DATA).ifPresent(data -> {
                    Element element = data.getSelectedElement();
                    System.out.println("The player has: " + element);
                });
            }
            return 1;
        }));
        dispatcher.register(Commands.literal("remove_bending").executes(ctx -> {
            if (ctx.getSource().getEntity() instanceof ServerPlayer player) {
                player.getCapability(ModCapabilities.BENDING_DATA).ifPresent(data -> {
                    Element element = data.getSelectedElement();
                    System.out.println("The player first has: " + (element != null ? element.name() : "None"));
                    data.clearElements();
                    Element element_after = data.getSelectedElement();
                    System.out.println("The player now has: " + (element_after != null ? element_after.name() : "None"));

                    ModMessages.sendToPlayer(new BendingSyncPacket((BendingData) data), player);

                    player.sendSystemMessage(Component.literal("Your bending has been removed."));
                });
            }
            return 1;
        }));
    }
}
