package me.kall.combatproperties.event;

import me.kall.combatproperties.CombatProperties;
import me.kall.combatproperties.api.Attributes;
import me.kall.combatproperties.api.event.InteractiveEvent;
import me.kall.combatproperties.attribute.BaseAttribute;
import me.kall.combatproperties.network.ParticlePacket;
import me.kall.combatproperties.network.ParticlesConstant;
import me.kall.combatproperties.registry.ModPackets;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.PacketDistributor;

import java.util.concurrent.ThreadLocalRandom;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, modid = CombatProperties.MOD_ID)
public class EvasionEvents {
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onHurt(LivingAttackEvent event) {
        if (event.getSource().getEntity() instanceof LivingEntity source && source.level() instanceof ServerLevel level) {
            LivingEntity attacked = event.getEntity();

            double evasion = Attributes.getEvasion(attacked);
            double accuracy = Attributes.getAccuracy(source);
            if (evasion == Attributes.NOT_PRESENT) return;

            double evasionChance = BaseAttribute.calChance(evasion, accuracy == Attributes.NOT_PRESENT ? 0.00 : accuracy);
            if (ThreadLocalRandom.current().nextDouble(0.00, 1.00) > evasionChance) return;

            InteractiveEvent.Evasion evasionEvent = new InteractiveEvent.Evasion(attacked);
            boolean cancelled = MinecraftForge.EVENT_BUS.post(evasionEvent);
            if (cancelled) return;

            event.setCanceled(true);

            if (evasionEvent.isSoundAllowed()) level.playSound(null, attacked.getX(), attacked.getY(), attacked.getZ(), SoundEvents.PLAYER_ATTACK_SWEEP, attacked.getSoundSource(), 0.6F, 0.6F);
            if (evasionEvent.isParticleAllowed()) ModPackets.INSTANCE.send(PacketDistributor.TRACKING_ENTITY.with(() -> attacked), new ParticlePacket(attacked.getId(), ParticlesConstant.EVASION));
        }
    }
}