package me.kall.combatproperties.api.event;

import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.Cancelable;

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

    @Cancelable
    public static class Block extends InteractiveEvent {
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

    @Cancelable
    public static class Crit extends InteractiveEvent {
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

    @Cancelable
    public static class Evasion extends InteractiveEvent {
        public Evasion(LivingEntity entity) {
            super(entity);
        }
    }
}
