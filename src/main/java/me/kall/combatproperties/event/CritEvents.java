package me.kall.combatproperties.event;

import me.kall.combatproperties.CombatProperties;
import me.kall.combatproperties.attribute.BaseAttribute;
import me.kall.combatproperties.api.event.InteractiveEvent;
import me.kall.combatproperties.config.CombatConfig;
import me.kall.combatproperties.network.ParticlePacket;
import me.kall.combatproperties.network.ParticlesConstant;
import me.kall.combatproperties.registry.ModAttributes;
import me.kall.combatproperties.registry.ModPackets;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.player.CriticalHitEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.PacketDistributor;

import java.util.concurrent.ThreadLocalRandom;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, modid = CombatProperties.MOD_ID)
public class CritEvents {
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onCrit(CriticalHitEvent event) {
        if (CombatConfig.DISABLE_CRIT_HIT.get()) event.setResult(Event.Result.DENY);

        Player player = event.getEntity();
        CompoundTag data = player.getPersistentData();

        if (CombatConfig.NOTIFICATION_CRIT.get() && !data.getBoolean("CritNotification") && player instanceof ServerPlayer) {
            player.displayClientMessage(Component.translatable("chat.combatproperties.crit"), false);
            data.putBoolean("CritNotification", true);
        }
    }

    @SubscribeEvent
    public static void onDamage(LivingDamageEvent event) {
        if (event.getSource().getEntity() instanceof LivingEntity source && source.level() instanceof ServerLevel level) {
            LivingEntity attacked = event.getEntity();

            AttributeInstance crit = source.getAttribute(ModAttributes.CRIT.get());
            AttributeInstance critRes = attacked.getAttribute(ModAttributes.CRIT_RESISTANCE.get());
            if (crit == null || critRes == null) return;

            double critChance = BaseAttribute.calChance(crit.getValue(), critRes.getValue());
            if (ThreadLocalRandom.current().nextDouble(0.00, 1.00) > critChance) return;
            InteractiveEvent.Crit critEvent = new InteractiveEvent.Crit(source);
            boolean cancelled = MinecraftForge.EVENT_BUS.post(critEvent);

            if (cancelled) return;

            event.setAmount(event.getAmount() * critEvent.getDmgMultiply());

            if (critEvent.isSoundAllowed()) level.playSound(null, source.getX(), source.getY(), source.getZ(), SoundEvents.PLAYER_ATTACK_CRIT, source.getSoundSource(), 1.0F, 1.0F);
            if (critEvent.isParticleAllowed()) ModPackets.INSTANCE.send(PacketDistributor.TRACKING_ENTITY.with(() -> attacked), new ParticlePacket(attacked.getId(), ParticlesConstant.CRIT));
        }
    }
}
