package me.kall.combatproperties.event.debug;

import me.kall.combatproperties.CombatProperties;
import me.kall.combatproperties.registry.ModAttributes;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.Objects;

@EventBusSubscriber(modid = CombatProperties.MOD_ID)
public class DebugEvents {
    private static final boolean enabled = false;

    @SubscribeEvent
    public static void onLogin(PlayerEvent.PlayerLoggedInEvent event) {
        if (!enabled) return;
        ModAttributes.REGISTER.getEntries()
                .stream()
                .map(DeferredHolder::getDelegate)
                .map(attribute -> event.getEntity().getAttribute(attribute))
                .filter(Objects::nonNull)
                .forEach(attributeInstance -> attributeInstance.setBaseValue(1.00));
    }
}
