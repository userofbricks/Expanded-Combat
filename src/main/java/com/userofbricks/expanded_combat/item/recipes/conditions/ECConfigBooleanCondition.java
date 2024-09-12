package com.userofbricks.expanded_combat.item.recipes.conditions;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.neoforged.neoforge.common.conditions.ICondition;
import org.jetbrains.annotations.NotNull;

import static com.userofbricks.expanded_combat.ExpandedCombat.CONFIG;

public class ECConfigBooleanCondition implements ICondition {
    public static MapCodec<ECConfigBooleanCondition> CODEC = RecordCodecBuilder.mapCodec(
            builder -> builder
                    .group(
                            Codec.STRING.fieldOf("config_entry").forGetter(ECConfigBooleanCondition::getConfigEntry))
                    .apply(builder, ECConfigBooleanCondition::new));
    private final String configBooleanName;

    public ECConfigBooleanCondition(String configBooleanName)
    {
        this.configBooleanName = configBooleanName;
    }

    @Override
    public boolean test(ICondition.@NotNull IContext context)
    {
        return switch (configBooleanName) {
            default -> false;
            case "gauntlet" -> CONFIG.enableGauntlets;
            case "shield" -> CONFIG.enableShields;
            case "bow" -> CONFIG.enableBows;
            case "crossbow" -> CONFIG.enableCrossbows;
            case "quiver" -> CONFIG.enableQuivers;
            case "arrow" -> CONFIG.enableArrows;
            case "weapon" -> CONFIG.enableWeapons;
            case "soul" -> CONFIG.enableSouls;
        };
    }

    @Override
    public @NotNull MapCodec<? extends ICondition> codec() {
        return CODEC;
    }
    public String getConfigEntry() {
        return configBooleanName;
    }

    @Override
    public String toString()
    {
        return "ec:config_boolean(\"" + this.configBooleanName + "\")";
    }
}
