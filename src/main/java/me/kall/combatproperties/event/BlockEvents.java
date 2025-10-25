package me.kall.combatproperties.event;

import me.kall.combatproperties.CombatProperties;
import me.kall.combatproperties.api.Attributes;
import me.kall.combatproperties.api.event.InteractiveEvent;
import me.kall.combatproperties.attribute.BaseAttribute;
import me.kall.combatproperties.config.CombatConfig;
import me.kall.combatproperties.network.ParticlePacket;
import me.kall.combatproperties.network.ParticlesConstant;
import me.kall.combatproperties.registry.ModPackets;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.ShieldBlockEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ThreadLocalRandom;

@Mod.EventBusSubscriber(modid = CombatProperties.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class BlockEvents {
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onShieldBlock(ShieldBlockEvent event) {
        if (CombatConfig.DISABLE_SHIELD_BLOCK.get()) event.setCanceled(true);

        LivingEntity entity = event.getEntity();
        CompoundTag data = entity.getPersistentData();

        if (CombatConfig.NOTIFICATION_BLOCK.get() && !data.getBoolean("BlockNotification") && entity instanceof ServerPlayer player) {
            player.displayClientMessage(Component.translatable("chat.combatproperties.block"), false);
            data.putBoolean("BlockNotification", true);
        }
    }

    @SubscribeEvent
    public static void onDamage(@NotNull LivingDamageEvent event) {
        if (event.getSource().getEntity() instanceof LivingEntity source && source.level() instanceof ServerLevel level) {
            LivingEntity attacked = event.getEntity();

            double block = Attributes.getBlock(attacked);
            double penetration = Attributes.getPenetration(source);

            if (block == Attributes.NOT_PRESENT) return;

            double blockChance = BaseAttribute.calChance(block, penetration == Attributes.NOT_PRESENT ? 0.00 : penetration);
            if (ThreadLocalRandom.current().nextDouble(0.00, 1.00) > blockChance) return;

            InteractiveEvent.Block blockEvent = new InteractiveEvent.Block(attacked);
            boolean cancelled = MinecraftForge.EVENT_BUS.post(blockEvent);
            if (cancelled) return;

            event.setAmount(event.getAmount() * blockEvent.getDmgMultiply());

            if (blockEvent.isSoundAllowed()) level.playSound(null, attacked.getX(), attacked.getY(), attacked.getZ(), SoundEvents.ANVIL_LAND, attacked.getSoundSource(), 1.0F, 1.0F);
            if (blockEvent.isParticleAllowed()) ModPackets.INSTANCE.send(PacketDistributor.TRACKING_ENTITY.with(() -> attacked), new ParticlePacket(attacked.getId(), ParticlesConstant.BLOCK));
        }
    }
}
