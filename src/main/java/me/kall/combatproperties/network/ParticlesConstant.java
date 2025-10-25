package me.kall.combatproperties.network;

import it.unimi.dsi.fastutil.bytes.Byte2ObjectMap;
import it.unimi.dsi.fastutil.bytes.Byte2ObjectOpenHashMap;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;

public class ParticlesConstant {
    public static final Byte2ObjectMap<SimpleParticleType> PARTICLES = new Byte2ObjectOpenHashMap<>();

    public static final byte CRIT = 0;
    public static final byte BLOCK = 1;
    public static final byte EVASION = 2;

    static {
        PARTICLES.put(CRIT, ParticleTypes.CRIT);
        PARTICLES.put(BLOCK, ParticleTypes.SONIC_BOOM);
        PARTICLES.put(EVASION, ParticleTypes.CLOUD);
    }
}
