package net.banaan.atla.network;

import net.banaan.atla.bending.data.BendingData;
import net.banaan.atla.bending.data.ModCapabilities;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class AbilityFour {
    private final CompoundTag data;

    public AbilityFour(BendingData data) {
        this.data = data.saveNBT();
    }
    public AbilityFour(FriendlyByteBuf byteBuf) {
        this.data = byteBuf.readNbt();
    }

    public static void toBytes(AbilityFour msg, FriendlyByteBuf buf) {
        buf.writeNbt(msg.data);
    }
    public static void handle(AbilityFour msg, Supplier<NetworkEvent.Context> ctx) {

        ctx.get().enqueueWork(() -> {
            Minecraft.getInstance().player.getCapability(ModCapabilities.BENDING_DATA).ifPresent(cap -> {
                ((BendingData) cap).loadNBT(msg.data);
            });
        });
        ctx.get().setPacketHandled(true);
    }
}



