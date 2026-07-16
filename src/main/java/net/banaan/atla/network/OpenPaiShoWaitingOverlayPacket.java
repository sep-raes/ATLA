package net.banaan.atla.network;

import net.banaan.atla.GUI.PaiSho.PaiShoWaitingOverlayScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class OpenPaiShoWaitingOverlayPacket {
    private final BlockPos pos;

    public OpenPaiShoWaitingOverlayPacket(BlockPos pos) { this.pos = pos; }
    public OpenPaiShoWaitingOverlayPacket(FriendlyByteBuf buf) { this.pos = buf.readBlockPos(); }
    public void toBytes(FriendlyByteBuf buf) { buf.writeBlockPos(pos); }

    public boolean handle(Supplier<NetworkEvent.Context> ctx) {
        NetworkEvent.Context context = ctx.get();
        context.enqueueWork(() -> DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () ->
                Minecraft.getInstance().setScreen(new PaiShoWaitingOverlayScreen(pos))));
        context.setPacketHandled(true);
        return true;
    }
}