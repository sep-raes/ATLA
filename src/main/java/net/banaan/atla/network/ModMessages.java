package net.banaan.atla.network;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;


@SuppressWarnings("removal")
public class ModMessages {
    private static SimpleChannel channel;
    private static int id = 0;

    public static void register() {
        channel = NetworkRegistry.newSimpleChannel(
                new ResourceLocation("atla", "messages"), () -> "1.0", s -> true, s -> true);

        channel.messageBuilder(OpenPaiShoWaitingOverlayPacket.class, id++)
                .encoder(OpenPaiShoWaitingOverlayPacket::toBytes)
                .decoder(OpenPaiShoWaitingOverlayPacket::new)
                .consumerMainThread(OpenPaiShoWaitingOverlayPacket::handle)
                .add();

        channel.messageBuilder(JoinPaiShoTablePacket.class, id++)
                .encoder(JoinPaiShoTablePacket::toBytes)
                .decoder(JoinPaiShoTablePacket::new)
                .consumerMainThread(JoinPaiShoTablePacket::handle)
                .add();


        channel.messageBuilder(LeavePaiShoTablePacket.class, id++)
                .encoder(LeavePaiShoTablePacket::toBytes)
                .decoder(LeavePaiShoTablePacket::new)
                .consumerMainThread(LeavePaiShoTablePacket::handle)
                .add();

        channel.messageBuilder(BendingSyncPacket.class, id++)
                .encoder(BendingSyncPacket::toBytes)
                .decoder(BendingSyncPacket::new)
                .consumerMainThread(BendingSyncPacket::handle)
                .add();

        channel.messageBuilder(AbilityOne.class, id++)
                .encoder(AbilityOne::toBytes)
                .decoder(AbilityOne::new)
                .consumerMainThread(AbilityOne::handle)
                .add();

        channel.messageBuilder(AbilityTwo.class, id++)
                .encoder(AbilityTwo::toBytes)
                .decoder(AbilityTwo::new)
                .consumerMainThread(AbilityTwo::handle)
                .add();

    channel.messageBuilder(AbilityThree.class, id++)
                .encoder(AbilityThree::toBytes)
                .decoder(AbilityThree::new)
                .consumerMainThread(AbilityThree::handle)
                .add();

        channel.messageBuilder(AbilityFour.class, id++)
                .encoder(AbilityFour::toBytes)
                .decoder(AbilityFour::new)
                .consumerMainThread(AbilityFour::handle)
                .add();

        channel.messageBuilder(AbilityMenu.class, id++)
                .encoder(AbilityMenu::toBytes)
                .decoder(AbilityMenu::new)
                .consumerMainThread(AbilityMenu::handle)
                .add();

        channel.messageBuilder(CycleElements.class, id++)
                .encoder(CycleElements::toBytes)
                .decoder(CycleElements::new)
                .consumerMainThread(CycleElements::handle)
                .add();


    }

    public static void sendToServer(Object msg) { channel.sendToServer(msg); }

    public static void sendToPlayer(Object msg, ServerPlayer player) {
        channel.send(PacketDistributor.PLAYER.with(() -> player), msg);
    }
}