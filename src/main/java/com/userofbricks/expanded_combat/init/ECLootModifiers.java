package com.userofbricks.expanded_combat.init;

import com.mojang.serialization.MapCodec;
import com.userofbricks.expanded_combat.loot.AddItemFromGauntletModifier;
import com.userofbricks.expanded_combat.loot.AddItemModifier;
import com.userofbricks.expanded_combat.loot.AddItemWithoutGauntletModifier;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import static com.userofbricks.expanded_combat.ExpandedCombat.MODID;

public class ECLootModifiers {
    public static final DeferredRegister<MapCodec<? extends IGlobalLootModifier>> GLOBAL_LOOT_MODIFIER_SERIALIZERS = DeferredRegister.create(NeoForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, MODID);

    public static final DeferredHolder<MapCodec<? extends IGlobalLootModifier>, MapCodec<? extends IGlobalLootModifier>> ADD_ITEM_FROM_GAUNTLET = GLOBAL_LOOT_MODIFIER_SERIALIZERS.register("add_item_from_gauntlet", AddItemFromGauntletModifier.CODEC);
    public static final DeferredHolder<MapCodec<? extends IGlobalLootModifier>, MapCodec<? extends IGlobalLootModifier>> ADD_ITEM_WITHOUT_GAUNTLET = GLOBAL_LOOT_MODIFIER_SERIALIZERS.register("add_item_without_gauntlet", AddItemWithoutGauntletModifier.CODEC);
    public static final DeferredHolder<MapCodec<? extends IGlobalLootModifier>, MapCodec<? extends IGlobalLootModifier>> ADD_ITEM = GLOBAL_LOOT_MODIFIER_SERIALIZERS.register("add_item", AddItemModifier.CODEC);
}
