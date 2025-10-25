package me.kall.combatproperties.api.event;

import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.ICancellableEvent;
import net.neoforged.neoforge.event.entity.living.LivingEvent;

public abstract class InteractiveEvent extends LivingEvent {
    private boolean particle = true;
    private boolean sound = true;

    public InteractiveEvent(LivingEntity entity) {
        super(entity);
    }

    public boolean isParticleAllowed() {
        return this.particle;
    }

    public boolean isSoundAllowed() {
        return this.sound;
    }

    public void setParticleAllowed(boolean particleAllowed) {
        this.particle = particleAllowed;
    }

    public void setSoundAllowed(boolean soundAllowed) {
        this.sound = soundAllowed;
    }

    public static class Block extends InteractiveEvent implements ICancellableEvent {
        private float dmgMultiply = 0.5F;

        public Block(LivingEntity entity) {
            super(entity);
        }

        public float getDmgMultiply() {
            return this.dmgMultiply;
        }

        public void setDmgMultiply(float dmgMultiply) {
            this.dmgMultiply = dmgMultiply;
        }
    }

    public static class Crit extends InteractiveEvent implements ICancellableEvent {
        private float dmgMultiply = 2.0F;

        public Crit(LivingEntity entity) {
            super(entity);
        }

        public float getDmgMultiply() {
            return this.dmgMultiply;
        }

        public void setDmgMultiply(float dmgMultiply) {
            this.dmgMultiply = dmgMultiply;
        }
    }

    public static class Evasion extends InteractiveEvent implements ICancellableEvent {
        public Evasion(LivingEntity entity) {
            super(entity);
        }
    }
}
