package net.banaan.atla.bending.data;

import net.banaan.atla.bending.element.Element;
import net.minecraft.resources.ResourceLocation;
import java.util.Set;

public interface IBendingData {
    Set<Element> getKnownElements();
    void setKnownElements(Set<Element> elements);
    boolean knowsElement(Element element);

    // New methods for Avatar logic
    boolean isAvatar();
    void setAvatar(boolean isAvatar);
    void addElement(Element element);

    Element getSelectedElement();
    void setSelectedElement(Element element);
    ResourceLocation getAbilityInSlot(Element element, int slot);
    void setAbilityInSlot(Element element, int slot, ResourceLocation abilityId);
    void startCooldown(ResourceLocation abilityId, long currentTick, int durationTicks);
    long getCooldownRemaining(ResourceLocation abilityId, long currentTick);
    int getSubBendingIndex();
    void setSubBendingIndex(int index);
    void toggleSubBendingIndex();

    default boolean isOnCooldown(ResourceLocation abilityId, long currentTick) {
        return getCooldownRemaining(abilityId, currentTick) > 0;
    }
    void copyFrom(IBendingData other);
}