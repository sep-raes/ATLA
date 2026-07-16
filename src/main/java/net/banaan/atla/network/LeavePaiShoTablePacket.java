package net.banaan.atla.network;

import net.banaan.atla.block.entity.custom.PaiShoTableBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class LeavePaiShoTablePacket {
    private final BlockPos pos;

    public LeavePaiShoTablePacket(BlockPos pos) { this.pos = pos; }
    public LeavePaiShoTablePacket(FriendlyByteBuf buf) { this.pos = buf.readBlockPos(); }
    public void toBytes(FriendlyByteBuf buf) { buf.writeBlockPos(pos); }

    public boolean handle(Supplier<NetworkEvent.Context> ctx) {
        NetworkEvent.Context context = ctx.get();
        context.enqueueWork(() -> {
            ServerPlayer sender = context.getSender();
            if (sender != null && sender.level().getBlockEntity(pos) instanceof PaiShoTableBlockEntity be) {
                be.leave(sender);
            }
        });
        context.setPacketHandled(true);
        return true;
    }
}