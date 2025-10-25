package me.kall.combatproperties;

import me.kall.combatproperties.config.CombatConfig;
import me.kall.combatproperties.registry.ModAttributes;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(CombatProperties.MOD_ID)
public final class CombatProperties {
    public static final String MOD_ID = "combatproperties";
    public static final String MOD_NAME = "CombatProperties";
    public static final Logger LOGGER = LogManager.getLogger(MOD_NAME);

    public CombatProperties(IEventBus modBus, Dist dist, ModContainer container) {
        ModAttributes.REGISTER.register(modBus);
        container.registerConfig(ModConfig.Type.COMMON, CombatConfig.INSTANCE);
    }
}
