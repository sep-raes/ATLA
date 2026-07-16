package net.banaan.atla.block.menu;

import net.banaan.atla.GUI.ModMenuTypes;
import net.banaan.atla.block.entity.custom.PaiShoTableBlockEntity;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;

import java.util.UUID;

@SuppressWarnings("removal")
public class PaiShoTableMenu extends AbstractContainerMenu {

    private final ItemStackHandler itemHandler;
    private final PaiShoTableBlockEntity blockEntity;
    private final Level level;

    private static final int GRID_SIZE   = 17;
    private static final int CELL_SIZE   = 26;
    private static final int ORIGIN_X    = 28;
    private static final int ORIGIN_Y    = 23;

    public PaiShoTableMenu(int containerId, Inventory playerInv, FriendlyByteBuf extraData) {
        this(containerId, playerInv, (PaiShoTableBlockEntity) playerInv.player.level().getBlockEntity(extraData.readBlockPos()));
    }

    public PaiShoTableMenu(int containerId, Inventory playerInv, PaiShoTableBlockEntity be) {
        super(ModMenuTypes.PAI_SHO_TABLE_MENU.get(), containerId);
        this.blockEntity = be;
        this.level = playerInv.player.level();

        this.itemHandler = (ItemStackHandler) be.getCapability(ForgeCapabilities.ITEM_HANDLER)
                .orElseThrow(() -> new IllegalStateException("No item handler on table"));



        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                int slotIndex = (row * GRID_SIZE) + col;

                if (slotIndex < this.itemHandler.getSlots()) {
                    int x = ORIGIN_X + col * CELL_SIZE - 9;
                    int y = ORIGIN_Y + row * CELL_SIZE - 9;
                    addSlot(new SlotItemHandler(itemHandler, slotIndex, x, y));
                }
            }
        }

    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean stillValid(Player player) {
        return stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()),
                player, blockEntity.getBlockState().getBlock());
    }

    @Override
    public void removed(Player player) {
        super.removed(player);
        if (player.level().isClientSide()) return;
        if (!(player instanceof ServerPlayer serverPlayer)) return;

        UUID leavingId = player.getUUID();
        UUID opponentId = blockEntity.getOpponent(leavingId);

        blockEntity.endGame();

        if (opponentId != null) {
            MinecraftServer server = serverPlayer.getServer();
            if (server != null) {
                ServerPlayer opponent = server.getPlayerList().getPlayer(opponentId);
                if (opponent != null && opponent.containerMenu instanceof PaiShoTableMenu) {
                    opponent.sendSystemMessage(Component.literal("Your opponent left the game."));
                    opponent.closeContainer();
                }
            }
        }
    }
}