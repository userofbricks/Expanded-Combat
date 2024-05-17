package com.userofbricks.expanded_combat.data.material;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.userofbricks.expanded_combat.item.generators.*;
import net.minecraft.core.Holder;

import java.util.Optional;

public record ItemGeneration(
        Optional<Holder<GauntletType>> gauntletType,
        Optional<Holder<BowType>> bowType,
        Optional<Holder<CrossBowType>> crossBowType,
        Optional<Holder<ArrowType>> arrowType,
        Optional<Holder<QuiverType>> quiverType
) {
    public static final Codec<ItemGeneration> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    GauntletType.HOLDER_CODEC.optionalFieldOf("gauntlet_type").forGetter(ItemGeneration::gauntletType),
                    BowType.HOLDER_CODEC.optionalFieldOf("bow_type").forGetter(ItemGeneration::bowType),
                    CrossBowType.HOLDER_CODEC.optionalFieldOf("crossbow_type").forGetter(ItemGeneration::crossBowType),
                    ArrowType.HOLDER_CODEC.optionalFieldOf("arrow_type").forGetter(ItemGeneration::arrowType),
                    QuiverType.HOLDER_CODEC.optionalFieldOf("quiver_type").forGetter(ItemGeneration::quiverType)
            ).apply(instance, ItemGeneration::new)
    );
}
