package net.banaan.atla.bending.ability;

import net.minecraft.server.level.ServerPlayer;

public abstract class Ability {
    protected final ServerPlayer caster;
    protected final AbilityType type;

    protected Ability(ServerPlayer caster, AbilityType type) {
        this.caster = caster;
        this.type = type;
    }

    public abstract boolean activate();

    public UpdateResult update() {
        return UpdateResult.REMOVE;
    }

    public void onDestroy() {}

    public AbilityType type() {
        return type;
    }

    public ServerPlayer caster() {
        return caster;
    }

    public enum UpdateResult {
        CONTINUE,
        REMOVE
    }
}