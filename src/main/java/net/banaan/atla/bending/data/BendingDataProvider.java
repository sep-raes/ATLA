package net.banaan.atla.bending.data;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BendingDataProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    private final BendingData data = new BendingData();
    private final LazyOptional<IBendingData> optional = LazyOptional.of(() -> data);

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == ModCapabilities.BENDING_DATA) {
            return optional.cast();
        }
        return LazyOptional.empty();
    }
    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = data.saveNBT();
        System.out.println("DEBUG: Serializing BendingData: " + nbt.toString());
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        System.out.println("DEBUG: Deserializing BendingData");
        data.loadNBT(nbt);
    }
}