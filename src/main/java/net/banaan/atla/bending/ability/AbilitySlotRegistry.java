package net.banaan.atla.bending.ability;

import net.banaan.atla.bending.element.Element;
import net.banaan.atla.bending.registry.AbilityRegistry;

import java.util.EnumMap;
import java.util.Map;

public final class AbilitySlotRegistry {
    private static final int SLOT_COUNT = 4;
    private static final int STATE_COUNT = AbilityType.ActivationState.values().length;

    // Element -> [slot][activationState.ordinal()] -> AbilityType
    private static final Map<Element, AbilityType[][]> SLOTS = new EnumMap<>(Element.class);
    private static boolean initialized = false;

    private AbilitySlotRegistry() {}

    public static void init() {
        if (initialized) {
            return;
        }
        for (AbilityType type : AbilityRegistry.all()) {
            int slot = type.hudInteger() - 1; // convert 1-4 to 0-3
            if (slot < 0 || slot >= SLOT_COUNT) {
                throw new IllegalStateException(
                        "hudInteger for " + type.id() + " must be between 1 and " + SLOT_COUNT);
            }

            int state = type.activationState().ordinal();

            AbilityType[][] elementSlots =
                    SLOTS.computeIfAbsent(type.element(), e -> new AbilityType[SLOT_COUNT][STATE_COUNT]);

            AbilityType existing = elementSlots[slot][state];
            if (existing != null) {

                throw new IllegalStateException(
                        "Slot " + type.hudInteger() + " for " + type.element() + " already has "
                                + existing.id() + " bound to " + type.activationState()
                                + ", cannot also assign " + type.id());
            }

            elementSlots[slot][state] = type;
        }
        initialized = true;
    }

    public static AbilityType get(Element element, int zeroBasedSlot, AbilityType.ActivationState state) {
        AbilityType[][] elementSlots = SLOTS.get(element);
        if (elementSlots == null) {
            return null;
        }
        return elementSlots[zeroBasedSlot][state.ordinal()];
    }
}