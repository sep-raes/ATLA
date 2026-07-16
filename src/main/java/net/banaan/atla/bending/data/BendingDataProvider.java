package net.banaan.atla.bending.data;

import net.banaan.atla.bending.element.Element;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.Optional;
import java.util.Set;


@SuppressWarnings("removal")
public class BendingDataProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    private final BendingData data = new BendingData();
    private final LazyOptional<IBendingData> optional = LazyOptional.of(() -> data);

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        return cap == ModCapabilities.BENDING_DATA ? optional.cast() : LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();

        ListTag knownTag = new ListTag();
        for (Element element : data.getKnownElements()) {
            knownTag.add(StringTag.valueOf(element.id()));
        }
        tag.put("KnownElements", knownTag);

        if (data.getSelectedElement() != null) {
            tag.putString("SelectedElement", data.getSelectedElement().id());
        }

        for (Element element : Element.values()) {
            ListTag slotTag = new ListTag();
            for (int i = 0; i < BendingData.SLOT_COUNT; i++) {
                ResourceLocation ability = data.getAbilityInSlot(element, i);
                slotTag.add(StringTag.valueOf(ability == null ? "" : ability.toString()));
            }
            tag.put("Slots_" + element.id(), slotTag);
        }

        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        Set<Element> known = EnumSet.noneOf(Element.class);
        ListTag knownTag = tag.getList("KnownElements", 8); // 8 = StringTag type id
        for (int i = 0; i < knownTag.size(); i++) {
            findElement(knownTag.getString(i)).ifPresent(known::add);
        }
        data.setKnownElements(known);

        if (tag.contains("SelectedElement")) {
            findElement(tag.getString("SelectedElement")).ifPresent(data::setSelectedElement);
        }

        for (Element element : Element.values()) {
            String key = "Slots_" + element.id();
            if (!tag.contains(key)) {
                continue;
            }
            ListTag slotTag = tag.getList(key, 8);
            for (int i = 0; i < slotTag.size() && i < BendingData.SLOT_COUNT; i++) {
                String raw = slotTag.getString(i);
                if (!raw.isEmpty()) {
                    data.setAbilityInSlot(element, i, new ResourceLocation(raw));
                }
            }
        }
    }

    private static Optional<Element> findElement(String id) {
        for (Element element : Element.values()) {
            if (element.id().equals(id)) {
                return Optional.of(element);
            }
        }
        return Optional.empty();
    }
}