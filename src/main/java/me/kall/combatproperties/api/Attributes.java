package me.kall.combatproperties.api;

import me.kall.combatproperties.registry.ModAttributes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public final class Attributes {
    public static final double NOT_PRESENT = -1.00;

    public static double get(@NotNull LivingEntity entity, Attribute attribute) {
        return Optional.ofNullable(entity.getAttribute(attribute)).map(AttributeInstance::getValue).orElse(NOT_PRESENT);
    }

    public static double getBlock(LivingEntity entity) {
        return get(entity, ModAttributes.BLOCK.get());
    }

    public static double getPenetration(LivingEntity entity) {
        return get(entity, ModAttributes.PENETRATION.get());
    }

    public static double getCrit(LivingEntity entity) {
        return get(entity, ModAttributes.CRIT.get());
    }

    public static double getCritRes(LivingEntity entity) {
        return get(entity, ModAttributes.CRIT_RESISTANCE.get());
    }

    public static double getAccuracy(LivingEntity entity) {
        return get(entity, ModAttributes.ACCURACY.get());
    }

    public static double getEvasion(LivingEntity entity) {
        return get(entity, ModAttributes.EVASION.get());
    }
}
