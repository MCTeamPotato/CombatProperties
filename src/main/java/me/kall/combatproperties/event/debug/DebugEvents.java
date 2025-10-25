package me.kall.combatproperties.event.debug;

import me.kall.combatproperties.CombatProperties;
import me.kall.combatproperties.registry.ModAttributes;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegistryObject;

import java.util.Objects;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, modid = CombatProperties.MOD_ID)
public class DebugEvents {
    private static final boolean enabled = false;

    @SubscribeEvent
    public static void onLogin(PlayerEvent.PlayerLoggedInEvent event) {
        if (!enabled) return;
        ModAttributes.REGISTER.getEntries()
                .stream()
                .map(RegistryObject::get)
                .map(attribute -> event.getEntity().getAttribute(attribute))
                .filter(Objects::nonNull)
                .forEach(attributeInstance -> attributeInstance.setBaseValue(1.00));
    }
}
