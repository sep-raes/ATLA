package net.banaan.atla.network;

import net.banaan.atla.bending.ability.Ability;
import net.banaan.atla.bending.ability.AbilityManager;
import net.banaan.atla.bending.ability.AbilitySlotRegistry;
import net.banaan.atla.bending.ability.AbilityType;
import net.banaan.atla.bending.data.BendingData;
import net.banaan.atla.bending.data.IBendingData;
import net.banaan.atla.bending.data.ModCapabilities;
import net.banaan.atla.bending.element.Element;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class AbilityOne {

    private final boolean isRightClicking;

    public AbilityOne(boolean isRightClicking) {
        this.isRightClicking = isRightClicking;
    }

    public AbilityOne(FriendlyByteBuf buf) {
        this.isRightClicking = buf.readBoolean();
    }

    public static void toBytes(AbilityOne msg, FriendlyByteBuf buf) {
        buf.writeBoolean(msg.isRightClicking);
    }

    public static void handle(AbilityOne msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayer player = ctx.get().getSender();
            if (player == null) return;

            player.getCapability(ModCapabilities.BENDING_DATA).ifPresent(cap -> {
                Element element = cap.getSelectedElement();
                if (element == null) return;

                AbilityType.ActivationState state = determineState(player, cap, msg.isRightClicking);

                AbilityType type = AbilitySlotRegistry.get(element, 0, state);
                System.out.println("DEBUG: Looking up Ability. Element: " + element +
                        " | Slot: 0 | State: " + state);




                if (type != null) {
                    long currentTick = player.level().getGameTime();
                    long remaining = cap.getCooldownRemaining(type.id(), currentTick);
                    int duration = type.cooldownTicks();

                    System.out.println("DEBUG: Ability: " + type.id());
                    System.out.println("DEBUG: Current Tick: " + currentTick);
                    System.out.println("DEBUG: Cooldown Remaining: " + remaining);
                    System.out.println("DEBUG: Duration: " + duration);

                    if (!cap.isOnCooldown(type.id(), currentTick)) {
                        System.out.println("2");
                        Ability abilityInstance = type.create(player);
                        if (abilityInstance.activate()) {
                            System.out.println("Cooldown was empty, setting to " + duration);
                            cap.startCooldown(type.id(), currentTick, type.cooldownTicks());
                            AbilityManager.addAbility(abilityInstance);
                            ModMessages.sendToPlayer(new BendingSyncPacket((BendingData) cap), player);
                        }
                    }
                }
            });
        });
        ctx.get().setPacketHandled(true);
    }

    private static AbilityType.ActivationState determineState(ServerPlayer player, IBendingData data, boolean rightClickDown) {

        if (rightClickDown) {
            return AbilityType.ActivationState.CLICK;
        }

        if (player.isCrouching()) {
            return AbilityType.ActivationState.SNEAK;
        }

        if (data.getSubBendingIndex() == 2) {
            return AbilityType.ActivationState.SUB_BENDING_2;
        }

        if (data.getSubBendingIndex() == 1) {
            return AbilityType.ActivationState.SUB_BENDING_1;
        }



        return AbilityType.ActivationState.NORMAL;
    }
}