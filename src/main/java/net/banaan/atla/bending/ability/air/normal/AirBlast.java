package net.banaan.atla.bending.ability.air.normal;

import net.banaan.atla.bending.ability.Ability;
import net.banaan.atla.bending.ability.AbilityType;
import net.banaan.atla.bending.element.Element;
import net.banaan.atla.bending.registry.AbilityRegistry;
import net.banaan.atla.util.glider.GlideData;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.List;

@SuppressWarnings("removal")
public class AirBlast extends Ability {
    public static final AbilityType TYPE = AbilityRegistry.register(new AbilityType(
            new ResourceLocation("atla", "air_blast"), Element.AIR, 80, new ResourceLocation("atla", "textures/gui/bending/hud/air/air_blast.png"), 1, AbilityType.ActivationState.NORMAL, AirBlast::new));

    private static final double RADIUS = 4.0;
    private static final double HORIZONTAL_KNOCKBACK = 1.1;
    private static final double VERTICAL_LIFT = 0.35;
    private static final float DAMAGE = 2.0f;

    private static final double SELF_LAUNCH_VELOCITY = 1.4;
    private static final int BOOST_SUPPRESS_TICKS = 80;



    public AirBlast(ServerPlayer caster) {
        super(caster, TYPE);
    }

    @Override
    public boolean activate() {
        System.out.println("DEBUG: Entering activate() for " + type.id());
        if (!(caster.level() instanceof ServerLevel level)) {
            return false;
        }

        if (GlideData.isGliding(caster.getUUID()) && !caster.onGround()) {
            selfLaunch(level);
        } else {
            radialBlast(level);
        }

        return true;
    }


    private void selfLaunch(ServerLevel level) {
        Vec3 current = caster.getDeltaMovement();
        caster.setDeltaMovement(current.x, SELF_LAUNCH_VELOCITY, current.z);
        caster.hurtMarked = true;
        caster.connection.send(new ClientboundSetEntityMotionPacket(caster));
        GlideData.startBoost(caster.getUUID(), BOOST_SUPPRESS_TICKS);

        level.sendParticles(ParticleTypes.CLOUD,
                caster.getX(), caster.getY(), caster.getZ(),
                30, 0.4, 0.1, 0.4, 0.05);
        level.playSound(null, caster.blockPosition(),
                SoundEvents.PHANTOM_FLAP, SoundSource.PLAYERS, 1.0f, 0.7f);
    }

    private void radialBlast(ServerLevel level) {
        Vec3 center = caster.position().add(0, caster.getBbHeight() * 0.5, 0);
        AABB area = new AABB(center, center).inflate(RADIUS);

        List<Entity> nearby = level.getEntities(caster, area, e -> !e.isSpectator());
        for (Entity entity : nearby) {
            if (!(entity instanceof LivingEntity target)) {
                continue;
            }

            Vec3 offset = target.position().subtract(center);
            double distance = offset.length();
            if (distance < 1.0e-4 || distance > RADIUS) {
                continue;
            }

            double falloff = 1.0 - (distance / RADIUS);
            Vec3 direction = offset.normalize();

            target.hurt(caster.damageSources().playerAttack(caster), DAMAGE);

            Vec3 push = new Vec3(
                    direction.x * HORIZONTAL_KNOCKBACK * falloff,
                    VERTICAL_LIFT * falloff,
                    direction.z * HORIZONTAL_KNOCKBACK * falloff
            );
            target.setDeltaMovement(target.getDeltaMovement().add(push));
            target.hurtMarked = true;

            if (target instanceof ServerPlayer targetPlayer) {
                targetPlayer.connection.send(new ClientboundSetEntityMotionPacket(targetPlayer));
            }
        }

        level.sendParticles(ParticleTypes.CLOUD,
                center.x, center.y, center.z,
                40, RADIUS * 0.5, 0.3, RADIUS * 0.5, 0.02);
        level.playSound(null, caster.blockPosition(),
                SoundEvents.PHANTOM_FLAP, SoundSource.PLAYERS, 1.0f, 1.0f);
    }
}