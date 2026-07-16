package net.banaan.atla.block.entity.custom;

import net.banaan.atla.block.custom.PaiShoTableBlock;
import net.banaan.atla.block.entity.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class PaiShoTableBlockEntity extends BlockEntity implements MenuProvider {

    private final ItemStackHandler itemHandler = new ItemStackHandler(324) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }
    };

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

    public PaiShoTableBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.PAI_SHO_TABLE.get(), pos, state);
    }

    public Direction getTableFacing() {
        BlockState state = this.getBlockState();
        if (state.hasProperty(PaiShoTableBlock.FACING)) {
            return state.getValue(PaiShoTableBlock.FACING);
        }
        return Direction.NORTH;
    }

    public void setTableFacing(Direction direction) {
        if (this.level != null && !this.level.isClientSide()) {
            BlockState state = this.getBlockState();
            if (state.hasProperty(PaiShoTableBlock.FACING)) {
                this.level.setBlock(this.worldPosition, state.setValue(PaiShoTableBlock.FACING, direction), 3);
                setChanged();
            }
        }
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            return lazyItemHandler.cast();
        }
        return super.getCapability(cap, side);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        pTag.put("inventory", itemHandler.serializeNBT());
        super.saveAdditional(pTag);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        itemHandler.deserializeNBT(pTag.getCompound("inventory"));
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Pai Sho Table");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int containerId, Inventory playerInventory, Player player) {
        return new net.banaan.atla.block.menu.PaiShoTableMenu(containerId, playerInventory, this);
    }

    //GAME LOGIC
    private UUID player1;
    private UUID player2;

    public enum JoinResult { JOINED_WAITING, JOINED_READY, ALREADY_JOINED, TABLE_FULL }

    public JoinResult tryJoin(Player player) {
        UUID id = player.getUUID();
        if (id.equals(player1) || id.equals(player2)) return JoinResult.ALREADY_JOINED;
        if (player1 == null) {
            player1 = id;
            setChanged();
            return JoinResult.JOINED_WAITING;
        }
        if (player2 == null) {
            player2 = id;
            setChanged();
            return JoinResult.JOINED_READY;
        }
        return JoinResult.TABLE_FULL;
    }

    public void leave(Player player) {
        UUID id = player.getUUID();
        if (id.equals(player1)) player1 = null;
        if (id.equals(player2)) player2 = null;
        setChanged();
    }

    public void resetPlayers() {
        player1 = null;
        player2 = null;
        setChanged();
    }

    @Nullable public UUID getPlayer1() { return player1; }
    @Nullable public UUID getPlayer2() { return player2; }

    @Nullable
    public UUID getOpponent(UUID leavingPlayer) {
        if (leavingPlayer.equals(player1)) return player2;
        if (leavingPlayer.equals(player2)) return player1;
        return null;
    }

    public void endGame() {
        player1 = null;
        player2 = null;
        setChanged();
    }
}