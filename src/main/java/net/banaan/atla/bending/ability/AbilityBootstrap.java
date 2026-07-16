package net.banaan.atla.bending.ability;

import net.banaan.atla.bending.ability.air.block.AirShield;
import net.banaan.atla.bending.ability.air.block.AirWall;
import net.banaan.atla.bending.ability.air.block.Cyclone;
import net.banaan.atla.bending.ability.air.block.FeatherLight;
import net.banaan.atla.bending.ability.air.crouch.AirScooter;
import net.banaan.atla.bending.ability.air.crouch.Extinguish;
import net.banaan.atla.bending.ability.air.crouch.Flight;
import net.banaan.atla.bending.ability.air.crouch.Vacuum;
import net.banaan.atla.bending.ability.air.flight.AirJump;
import net.banaan.atla.bending.ability.air.flight.Ascension;
import net.banaan.atla.bending.ability.air.flight.Hover;
import net.banaan.atla.bending.ability.air.flight.Main;
import net.banaan.atla.bending.ability.air.normal.AirBlast;
import net.banaan.atla.bending.ability.air.normal.AirSlice;
import net.banaan.atla.bending.ability.air.normal.AirSwipe;
import net.banaan.atla.bending.ability.air.normal.Dash;
import net.banaan.atla.bending.ability.air.vacuum.AirSiphon;
import net.banaan.atla.bending.ability.air.vacuum.BlackHole;
import net.banaan.atla.bending.ability.air.vacuum.SuffocationField;

// TODO: import every other ability class as you write them:
// import net.banaan.atla.bending.ability.water.normal.WaterWhip;
// import net.banaan.atla.bending.ability.fire.normal.FireBlast;
// import net.banaan.atla.bending.ability.earth.normal.EarthBlast;

public final class AbilityBootstrap {

    private AbilityBootstrap() {}

    public static void init() {
        Object[] forceLoad = new Object[] {
                AirShield.TYPE,
                AirWall.TYPE,
                Cyclone.TYPE,
                FeatherLight.TYPE,
                AirScooter.TYPE,
                Extinguish.TYPE,
                Flight.TYPE,
                Vacuum.TYPE,
                AirJump.TYPE,
                Ascension.TYPE,
                Hover.TYPE,
                Main.TYPE,
                net.banaan.atla.bending.ability.air.vacuum.Main.TYPE,
                AirSiphon.TYPE,
                BlackHole.TYPE,
                SuffocationField.TYPE,
                AirBlast.TYPE,
                AirSlice.TYPE,
                AirSwipe.TYPE,
                Dash.TYPE

        };
    }
}