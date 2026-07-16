package net.banaan.atla.bending.ability;

import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.*;

public final class AbilityManager {
    private static final Map<UUID, List<Ability>> ACTIVE = new HashMap<>();

    private AbilityManager() {}

    public static void addAbility(Ability ability) {
        ACTIVE.computeIfAbsent(ability.caster().getUUID(), id -> new ArrayList<>()).add(ability);
    }

    public static void clearInstances(UUID playerId) {
        List<Ability> removed = ACTIVE.remove(playerId);
        if (removed != null) {
            removed.forEach(Ability::onDestroy);
        }
    }

    @SubscribeEvent
    public static void onServerTick(TickEvent.ServerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) {
            return;
        }
        for (Iterator<Map.Entry<UUID, List<Ability>>> it = ACTIVE.entrySet().iterator(); it.hasNext(); ) {
            List<Ability> abilities = it.next().getValue();
            abilities.removeIf(ability -> {
                boolean remove = ability.update() == Ability.UpdateResult.REMOVE;
                if (remove) {
                    ability.onDestroy();
                }
                return remove;
            });
            if (abilities.isEmpty()) {
                it.remove();
            }
        }
    }
}