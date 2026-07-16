package net.banaan.atla.bending.data;

import net.banaan.atla.bending.element.Element;
import net.minecraft.nbt.*;
import net.minecraft.resources.ResourceLocation;

import java.util.*;


@SuppressWarnings("removal")
public class BendingData implements IBendingData {
    public static final int SLOT_COUNT = 4;

    private final Set<Element> knownElements = EnumSet.noneOf(Element.class);
    private final Map<Element, ResourceLocation[]> slots = new EnumMap<>(Element.class);
    private final Map<ResourceLocation, Long> cooldownExpiry = new HashMap<>();

    private Element selectedElement = null;
    private boolean isAvatar = false;
    private int subBendingIndex = 1; // 1 or 2 - which sub-bending variant is currently selected

    @Override
    public boolean isAvatar() { return isAvatar; }

    @Override
    public void setAvatar(boolean isAvatar) { this.isAvatar = isAvatar; }

    @Override
    public void addElement(Element element) {
        if (!isAvatar && !knownElements.isEmpty()) {
            knownElements.clear(); // Non-avatars reset to the new element
        }
        knownElements.add(element);
        slots.putIfAbsent(element, new ResourceLocation[SLOT_COUNT]);
        if (selectedElement == null) selectedElement = element;
    }

    @Override
    public Set<Element> getKnownElements() {
        return EnumSet.copyOf(knownElements);
    }

    @Override
    public void setKnownElements(Set<Element> elements) {
        if (!isAvatar && elements.size() > 1) {
            knownElements.clear();
            knownElements.add(elements.iterator().next());
        } else {
            knownElements.clear();
            knownElements.addAll(elements);
        }

        slots.keySet().retainAll(knownElements);
        for (Element element : knownElements) {
            slots.putIfAbsent(element, new ResourceLocation[SLOT_COUNT]);
        }

        if ((selectedElement == null || !knownElements.contains(selectedElement)) && !knownElements.isEmpty()) {
            selectedElement = knownElements.iterator().next();
        }
    }

    @Override
    public boolean knowsElement(Element element) { return knownElements.contains(element); }

    @Override
    public Element getSelectedElement() { return selectedElement; }

    @Override
    public void setSelectedElement(Element element) {
        if (knowsElement(element)) {
            this.selectedElement = element;
        }
    }

    @Override
    public ResourceLocation getAbilityInSlot(Element element, int slot) {
        ResourceLocation[] elementSlots = slots.get(element);
        if (elementSlots == null || slot < 0 || slot >= SLOT_COUNT) return null;
        return elementSlots[slot];
    }

    @Override
    public void setAbilityInSlot(Element element, int slot, ResourceLocation abilityId) {
        ResourceLocation[] elementSlots = slots.computeIfAbsent(element, e -> new ResourceLocation[SLOT_COUNT]);
        if (slot >= 0 && slot < SLOT_COUNT) {
            elementSlots[slot] = abilityId;
        }
    }

    @Override
    public int getSubBendingIndex() { return subBendingIndex; }

    @Override
    public void setSubBendingIndex(int index) {
        this.subBendingIndex = (index == 2) ? 2 : 1; // clamp to valid range 1-2
    }

    @Override
    public void toggleSubBendingIndex() {
        this.subBendingIndex = (subBendingIndex == 1) ? 2 : 1;
    }

    @Override
    public void startCooldown(ResourceLocation abilityId, long currentTick, int durationTicks) {
        cooldownExpiry.put(abilityId, currentTick + durationTicks);
    }

    @Override
    public long getCooldownRemaining(ResourceLocation abilityId, long currentTick) {
        Long expiry = cooldownExpiry.get(abilityId);
        return (expiry == null) ? 0L : Math.max(expiry - currentTick, 0L);
    }

    @Override
    public void copyFrom(IBendingData other) {
        this.setAvatar(other.isAvatar());
        this.setKnownElements(other.getKnownElements());
        this.setSelectedElement(other.getSelectedElement());
        this.setSubBendingIndex(other.getSubBendingIndex());
        for (Element element : Element.values()) {
            for (int i = 0; i < SLOT_COUNT; i++) {
                ResourceLocation ability = other.getAbilityInSlot(element, i);
                if (ability != null) {
                    setAbilityInSlot(element, i, ability);
                }
            }
        }
    }

    // --- NBT Serialization ---

    public CompoundTag saveNBT() {
        CompoundTag nbt = new CompoundTag();
        nbt.putBoolean("isAvatar", isAvatar);
        nbt.putInt("subBendingIndex", subBendingIndex);

        ListTag elementList = new ListTag();
        for (Element e : knownElements) elementList.add(StringTag.valueOf(e.name()));
        nbt.put("knownElements", elementList);

        if (selectedElement != null) nbt.putString("selectedElement", selectedElement.name());

        CompoundTag slotsTag = new CompoundTag();
        for (Map.Entry<Element, ResourceLocation[]> entry : slots.entrySet()) {
            ListTag list = new ListTag();
            for (ResourceLocation rl : entry.getValue()) list.add(StringTag.valueOf(rl == null ? "" : rl.toString()));
            slotsTag.put(entry.getKey().name(), list);
        }
        nbt.put("slots", slotsTag);

        return nbt;
    }

    public void loadNBT(CompoundTag nbt) {
        this.isAvatar = nbt.getBoolean("isAvatar");
        this.subBendingIndex = nbt.contains("subBendingIndex") ? nbt.getInt("subBendingIndex") : 1;

        knownElements.clear();
        nbt.getList("knownElements", Tag.TAG_STRING).forEach(t -> knownElements.add(Element.valueOf(t.getAsString())));

        if (nbt.contains("selectedElement")) selectedElement = Element.valueOf(nbt.getString("selectedElement"));

        CompoundTag slotsTag = nbt.getCompound("slots");
        for (String key : slotsTag.getAllKeys()) {
            Element e = Element.valueOf(key);
            ListTag list = slotsTag.getList(key, Tag.TAG_STRING);
            ResourceLocation[] arr = new ResourceLocation[SLOT_COUNT];
            for (int i = 0; i < SLOT_COUNT; i++) {
                String val = list.getString(i);
                arr[i] = val.isEmpty() ? null : new ResourceLocation(val);
            }
            slots.put(e, arr);
        }
    }
}