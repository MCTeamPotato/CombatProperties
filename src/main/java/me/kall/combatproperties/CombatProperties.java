package me.kall.combatproperties;

import me.kall.combatproperties.registry.ModAttributes;
import me.kall.combatproperties.registry.ModPackets;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

@Mod(CombatProperties.MOD_ID)
public final class CombatProperties {
    public static final String MOD_ID = "combatproperties";
    public static final String MOD_NAME = "CombatProperties";
    public static final Logger LOGGER = LogManager.getLogger(MOD_NAME);

    public CombatProperties(@NotNull FMLJavaModLoadingContext context) {
        ModAttributes.REGISTER.register(context.getModEventBus());
        ModPackets.register();
    }
}
