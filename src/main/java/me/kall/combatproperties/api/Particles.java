package me.kall.combatproperties.api;

import me.kall.combatproperties.network.ParticlesConstant;
import net.minecraft.core.particles.SimpleParticleType;

public final class Particles {
    public static SimpleParticleType setParticle(byte type, SimpleParticleType particle) {
        synchronized (ParticlesConstant.PARTICLES) {
            return ParticlesConstant.PARTICLES.put(type, particle);
        }
    }

    public static SimpleParticleType setCritParticle(SimpleParticleType particle) {
        return setParticle(ParticlesConstant.CRIT, particle);
    }

    public static SimpleParticleType setBlockParticle(SimpleParticleType particle) {
        return setParticle(ParticlesConstant.BLOCK, particle);
    }

    public static SimpleParticleType setEvasionParticle(SimpleParticleType particle) {
        return setParticle(ParticlesConstant.EVASION, particle);
    }
}
