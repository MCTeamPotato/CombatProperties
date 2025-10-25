package me.kall.combatproperties.registry;

import me.kall.combatproperties.CombatProperties;
import me.kall.combatproperties.attribute.block.BlockAttribute;
import me.kall.combatproperties.attribute.block.PenetrationAttribute;
import me.kall.combatproperties.attribute.crit.CritAttribute;
import me.kall.combatproperties.attribute.crit.CritResAttribute;
import me.kall.combatproperties.attribute.evasion.AccuracyAttribute;
import me.kall.combatproperties.attribute.evasion.EvasionAttribute;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraftforge.event.entity.EntityAttributeModificationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = CombatProperties.MOD_ID)
public class ModAttributes {
    public static final DeferredRegister<Attribute> REGISTER = DeferredRegister.create(ForgeRegistries.ATTRIBUTES, CombatProperties.MOD_ID);

    public static final RegistryObject<Attribute> CRIT = REGISTER.register("crit", CritAttribute::new);
    public static final RegistryObject<Attribute> CRIT_RESISTANCE = REGISTER.register("crit_resistance", CritResAttribute::new);

    public static final RegistryObject<Attribute> BLOCK = REGISTER.register("block", BlockAttribute::new);
    public static final RegistryObject<Attribute> PENETRATION = REGISTER.register("penetration", PenetrationAttribute::new);

    public static final RegistryObject<Attribute> EVASION = REGISTER.register("evasion", EvasionAttribute::new);
    public static final RegistryObject<Attribute> ACCURACY = REGISTER.register("accuracy", AccuracyAttribute::new);

    @SubscribeEvent
    public static void loadAttributes(@NotNull EntityAttributeModificationEvent event) {
        REGISTER.getEntries().stream()
                .map(RegistryObject::get)
                .forEach(attribute -> {
                    for (EntityType<? extends LivingEntity> entityType : event.getTypes()) {
                        event.add(entityType, attribute);
                    }
                });
    }
}
