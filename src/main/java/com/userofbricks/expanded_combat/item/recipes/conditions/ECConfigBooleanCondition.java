package com.userofbricks.expanded_combat.item.recipes.conditions;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.neoforged.neoforge.common.conditions.ICondition;
import org.jetbrains.annotations.NotNull;

import static com.userofbricks.expanded_combat.config.CommonECConfig.pair;

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
            case "gauntlet" -> pair.getLeft().enableGauntlets.get();
            case "shield" -> pair.getLeft().enableShields.get();
            case "bow" -> pair.getLeft().enableBows.get();
            case "crossbow" -> pair.getLeft().enableCrossbows.get();
            case "quiver" -> pair.getLeft().enableQuivers.get();
            case "arrow" -> pair.getLeft().enableArrows.get();
            case "weapon" -> pair.getLeft().enableWeapons.get();
            case "soul" -> pair.getLeft().enableSouls.get();
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
