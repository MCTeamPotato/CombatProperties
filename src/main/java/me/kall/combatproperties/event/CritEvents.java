package me.kall.combatproperties.event;

import me.kall.combatproperties.CombatProperties;
import me.kall.combatproperties.api.Attributes;
import me.kall.combatproperties.api.event.InteractiveEvent;
import me.kall.combatproperties.attribute.BaseAttribute;
import me.kall.combatproperties.config.CombatConfig;
import me.kall.combatproperties.network.ParticlePacket;
import me.kall.combatproperties.network.ParticlesConstant;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.player.CriticalHitEvent;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.concurrent.ThreadLocalRandom;

@EventBusSubscriber(modid = CombatProperties.MOD_ID)
public class CritEvents {
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onCrit(CriticalHitEvent event) {
        if (CombatConfig.DISABLE_CRIT_HIT.get()) event.setCriticalHit(false);

        Player player = event.getEntity();
        CompoundTag data = player.getPersistentData();

        if (CombatConfig.NOTIFICATION_CRIT.get() && !data.getBoolean("CritNotification") && player instanceof ServerPlayer) {
            player.displayClientMessage(Component.translatable("chat.combatproperties.crit"), false);
            data.putBoolean("CritNotification", true);
        }
    }

    @SubscribeEvent
    public static void onDamage(LivingDamageEvent.Pre event) {
        if (event.getSource().getEntity() instanceof LivingEntity source && source.level() instanceof ServerLevel level) {
            LivingEntity attacked = event.getEntity();

            double crit = Attributes.getCrit(source);
            double critRes = Attributes.getCritRes(attacked);
            if (crit == Attributes.NOT_PRESENT) return;

            double critChance = BaseAttribute.calChance(crit, critRes == Attributes.NOT_PRESENT ? 0.00 : critRes);
            if (ThreadLocalRandom.current().nextDouble(0.00, 1.00) > critChance) return;
            InteractiveEvent.Crit critEvent = new InteractiveEvent.Crit(source);
            boolean cancelled = NeoForge.EVENT_BUS.post(critEvent).isCanceled();

            if (cancelled) return;

            event.setNewDamage(event.getNewDamage() * critEvent.getDmgMultiply());

            if (critEvent.isSoundAllowed()) level.playSound(null, source.getX(), source.getY(), source.getZ(), SoundEvents.PLAYER_ATTACK_CRIT, source.getSoundSource(), 1.0F, 1.0F);
            if (critEvent.isParticleAllowed()) PacketDistributor.sendToPlayersTrackingEntity(attacked, new ParticlePacket(attacked.getId(), ParticlesConstant.CRIT));
        }
    }
}
