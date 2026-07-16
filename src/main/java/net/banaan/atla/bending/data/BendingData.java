package net.banaan.atla.bending.data;

import net.banaan.atla.bending.element.Element;
import net.minecraft.resources.ResourceLocation;

import java.util.*;

public class BendingData implements IBendingData {
    public static final int SLOT_COUNT = 4;

    private final Set<Element> knownElements = EnumSet.noneOf(Element.class);
    private final Map<Element, ResourceLocation[]> slots = new EnumMap<>(Element.class);
    private final Map<ResourceLocation, Long> cooldownExpiry = new HashMap<>();
    private Element selectedElement = null;

    @Override
    public Set<Element> getKnownElements() {
        return EnumSet.copyOf(knownElements);
    }

    @Override
    public void setKnownElements(Set<Element> elements) {
        knownElements.clear();
        knownElements.addAll(elements);
        slots.keySet().retainAll(knownElements);
        for (Element element : elements) {
            slots.putIfAbsent(element, new ResourceLocation[SLOT_COUNT]);
        }
        if ((selectedElement == null || !knownElements.contains(selectedElement)) && !knownElements.isEmpty()) {
            selectedElement = knownElements.iterator().next();
        }
    }

    @Override
    public boolean knowsElement(Element element) {
        return knownElements.contains(element);
    }

    @Override
    public Element getSelectedElement() {
        return selectedElement;
    }

    @Override
    public void setSelectedElement(Element element) {
        if (knowsElement(element)) {
            this.selectedElement = element;
        }
    }

    @Override
    public ResourceLocation getAbilityInSlot(Element element, int slot) {
        ResourceLocation[] elementSlots = slots.get(element);
        if (elementSlots == null || slot < 0 || slot >= SLOT_COUNT) {
            return null;
        }
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
    public void startCooldown(ResourceLocation abilityId, long currentTick, int durationTicks) {
        cooldownExpiry.put(abilityId, currentTick + durationTicks);
    }

    @Override
    public long getCooldownRemaining(ResourceLocation abilityId, long currentTick) {
        Long expiry = cooldownExpiry.get(abilityId);
        if (expiry == null) {
            return 0L;
        }
        return Math.max(expiry - currentTick, 0L);
    }

    @Override
    public void copyFrom(IBendingData other) {
        setKnownElements(other.getKnownElements());
        setSelectedElement(other.getSelectedElement());
        for (Element element : Element.values()) {
            for (int i = 0; i < SLOT_COUNT; i++) {
                ResourceLocation ability = other.getAbilityInSlot(element, i);
                if (ability != null) {
                    setAbilityInSlot(element, i, ability);
                }
            }
        }
    }
}