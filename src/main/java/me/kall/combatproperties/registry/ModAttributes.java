package me.kall.combatproperties.registry;

import me.kall.combatproperties.CombatProperties;
import me.kall.combatproperties.attribute.block.BlockAttribute;
import me.kall.combatproperties.attribute.block.PenetrationAttribute;
import me.kall.combatproperties.attribute.crit.CritAttribute;
import me.kall.combatproperties.attribute.crit.CritResAttribute;
import me.kall.combatproperties.attribute.evasion.AccuracyAttribute;
import me.kall.combatproperties.attribute.evasion.EvasionAttribute;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityAttributeModificationEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jetbrains.annotations.NotNull;

@EventBusSubscriber(modid = CombatProperties.MOD_ID)
public class ModAttributes {
    public static final DeferredRegister<Attribute> REGISTER = DeferredRegister.create(Registries.ATTRIBUTE, CombatProperties.MOD_ID);

    public static final DeferredHolder<Attribute, Attribute> CRIT = REGISTER.register("crit", CritAttribute::new);
    public static final DeferredHolder<Attribute, Attribute> CRIT_RESISTANCE = REGISTER.register("crit_resistance", CritResAttribute::new);

    public static final DeferredHolder<Attribute, Attribute> BLOCK = REGISTER.register("block", BlockAttribute::new);
    public static final DeferredHolder<Attribute, Attribute> PENETRATION = REGISTER.register("penetration", PenetrationAttribute::new);

    public static final DeferredHolder<Attribute, Attribute> EVASION = REGISTER.register("evasion", EvasionAttribute::new);
    public static final DeferredHolder<Attribute, Attribute> ACCURACY = REGISTER.register("accuracy", AccuracyAttribute::new);

    @SubscribeEvent
    public static void loadAttributes(@NotNull EntityAttributeModificationEvent event) {
        REGISTER.getEntries().stream()
                .map(DeferredHolder::getDelegate)
                .forEach(attribute -> {
                    for (EntityType<? extends LivingEntity> entityType : event.getTypes()) {
                        event.add(entityType, attribute);
                    }
                });
    }
}
