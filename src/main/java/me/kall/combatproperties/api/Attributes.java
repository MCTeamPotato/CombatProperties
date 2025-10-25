package me.kall.combatproperties.api;

import me.kall.combatproperties.registry.ModAttributes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public final class Attributes {
    public static @NotNull Optional<AttributeInstance> get(@NotNull LivingEntity entity, Attribute attribute) {
        return Optional.ofNullable(entity.getAttribute(attribute));
    }

    public static @NotNull Optional<AttributeInstance> getBlock(LivingEntity entity) {
        return get(entity, ModAttributes.BLOCK.get());
    }

    public static @NotNull Optional<AttributeInstance> getPenetration(LivingEntity entity) {
        return get(entity, ModAttributes.PENETRATION.get());
    }

    public static @NotNull Optional<AttributeInstance> getCrit(LivingEntity entity) {
        return get(entity, ModAttributes.CRIT.get());
    }

    public static @NotNull Optional<AttributeInstance> getCritRes(LivingEntity entity) {
        return get(entity, ModAttributes.CRIT_RESISTANCE.get());
    }

    public static @NotNull Optional<AttributeInstance> getAccuracy(LivingEntity entity) {
        return get(entity, ModAttributes.ACCURACY.get());
    }

    public static @NotNull Optional<AttributeInstance> getEvasion(LivingEntity entity) {
        return get(entity, ModAttributes.EVASION.get());
    }
}
