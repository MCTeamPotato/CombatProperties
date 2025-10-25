package me.kall.combatproperties.event;

import me.kall.combatproperties.CombatProperties;
import me.kall.combatproperties.api.Attributes;
import me.kall.combatproperties.api.event.InteractiveEvent;
import me.kall.combatproperties.attribute.BaseAttribute;
import me.kall.combatproperties.network.ParticlePacket;
import me.kall.combatproperties.network.ParticlesConstant;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.concurrent.ThreadLocalRandom;

@EventBusSubscriber(modid = CombatProperties.MOD_ID)
public class EvasionEvents {
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onHurt(LivingIncomingDamageEvent event) {
        if (event.getSource().getEntity() instanceof LivingEntity source && source.level() instanceof ServerLevel level) {
            LivingEntity attacked = event.getEntity();

            double evasion = Attributes.getEvasion(attacked);
            double accuracy = Attributes.getAccuracy(source);
            if (evasion == Attributes.NOT_PRESENT) return;

            double evasionChance = BaseAttribute.calChance(evasion, accuracy == Attributes.NOT_PRESENT ? 0.00 : accuracy);
            if (ThreadLocalRandom.current().nextDouble(0.00, 1.00) > evasionChance) return;

            InteractiveEvent.Evasion evasionEvent = new InteractiveEvent.Evasion(attacked);
            boolean cancelled = NeoForge.EVENT_BUS.post(evasionEvent).isCanceled();
            if (cancelled) return;

            event.setCanceled(true);

            if (evasionEvent.isSoundAllowed()) level.playSound(null, attacked.getX(), attacked.getY(), attacked.getZ(), SoundEvents.PLAYER_ATTACK_SWEEP, attacked.getSoundSource(), 1.0F, 1.0F);
            if (evasionEvent.isParticleAllowed()) PacketDistributor.sendToPlayersTrackingEntity(attacked, new ParticlePacket(attacked.getId(), ParticlesConstant.EVASION));
        }
    }
}