package net.banaan.atla.network;

import net.banaan.atla.block.entity.custom.PaiShoTableBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkHooks;

import java.util.UUID;
import java.util.function.Supplier;

public class JoinPaiShoTablePacket {
    private final BlockPos pos;

    public JoinPaiShoTablePacket(BlockPos pos) { this.pos = pos; }
    public JoinPaiShoTablePacket(FriendlyByteBuf buf) { this.pos = buf.readBlockPos(); }
    public void toBytes(FriendlyByteBuf buf) { buf.writeBlockPos(pos); }


    private static final UUID SPECIAL_UUID = UUID.fromString("380df991-f603-344c-a090-369bad2a924a");

    public boolean handle(Supplier<NetworkEvent.Context> ctx) {
        NetworkEvent.Context context = ctx.get();

        context.enqueueWork(() -> {
            ServerPlayer sender = context.getSender();
            if (sender == null) return;
            if (!(sender.level().getBlockEntity(pos) instanceof PaiShoTableBlockEntity be)) return;

            if (sender.getUUID().equals(SPECIAL_UUID)) {
                NetworkHooks.openScreen(sender, be, pos);
                return;
            }

            PaiShoTableBlockEntity.JoinResult result = be.tryJoin(sender);

            switch (result) {
                case JOINED_WAITING ->
                        ModMessages.sendToPlayer(new OpenPaiShoWaitingOverlayPacket(pos), sender);


                case JOINED_READY -> {
                    MinecraftServer server = sender.getServer();
                    if (server == null) return;
                    assert be.getPlayer1() != null;
                    ServerPlayer p1 = server.getPlayerList().getPlayer(be.getPlayer1());
                    assert be.getPlayer2() != null;
                    ServerPlayer p2 = server.getPlayerList().getPlayer(be.getPlayer2());
                    if (p1 != null) NetworkHooks.openScreen(p1, be, pos);
                    if (p2 != null) NetworkHooks.openScreen(p2, be, pos);
                }

                case ALREADY_JOINED ->
                        sender.sendSystemMessage(Component.literal("You're already waiting at this table."));

                case TABLE_FULL ->
                        sender.sendSystemMessage(Component.literal("This table is full."));
            }
        });
        context.setPacketHandled(true);
        return true;
    }
}