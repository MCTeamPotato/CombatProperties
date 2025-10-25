package me.kall.combatproperties.registry;

import me.kall.combatproperties.CombatProperties;
import me.kall.combatproperties.network.ParticlePacket;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;

@EventBusSubscriber(modid = CombatProperties.MOD_ID)
public class ModPackets {
    @SubscribeEvent
    public static void register(RegisterPayloadHandlersEvent event) {
        event.registrar("1").playToClient(ParticlePacket.TYPE, ParticlePacket.CODEC, ParticlePacket::handle);
    }
}
