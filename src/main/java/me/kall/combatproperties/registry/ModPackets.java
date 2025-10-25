package me.kall.combatproperties.registry;

import com.google.common.base.Predicates;
import me.kall.combatproperties.CombatProperties;
import me.kall.combatproperties.network.ParticlePacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class ModPackets {
    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(ResourceLocation.fromNamespaceAndPath(CombatProperties.MOD_ID, "main"), () -> "1", Predicates.alwaysTrue(), Predicates.alwaysTrue());

    private static int id = 0;

    public static void register() {
        INSTANCE.registerMessage(id++, ParticlePacket.class, ParticlePacket::toBytes, ParticlePacket::new, ParticlePacket::handle);
    }
}
