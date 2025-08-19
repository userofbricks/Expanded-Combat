package com.userofbricks.expanded_combat.loot;

import com.google.common.base.Suppliers;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.common.loot.LootModifier;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class AddItemModifier extends LootModifier {
    public static final Supplier<MapCodec<AddItemModifier>> CODEC = Suppliers.memoize(() ->
                    RecordCodecBuilder.mapCodec(inst -> codecStart(inst)
                            .and(BuiltInRegistries.ITEM.byNameCodec().fieldOf("loot_item").forGetter(m -> m.lootItem))
                            .apply(inst, AddItemModifier::new)
                    )
            );

    protected final Item lootItem;
    public AddItemModifier(LootItemCondition[] conditionsIn, Item lootItem) {
        super(conditionsIn);
        this.lootItem = lootItem;
    }

    @Override
    protected @NotNull ObjectArrayList<ItemStack> doApply(@NotNull ObjectArrayList<ItemStack> generatedLoot, @NotNull LootContext context) {
        //for (LootItemCondition condition: conditions) {
        //    if (!condition.test(context)) return generatedLoot;
        //}

        generatedLoot.add(new ItemStack(lootItem));

        return generatedLoot;
    }

    @Override
    public @NotNull MapCodec<? extends IGlobalLootModifier> codec() {
        return CODEC.get();
    }
}
