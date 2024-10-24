package com.userofbricks.expanded_combat.datagen;

import com.userofbricks.expanded_combat.init.ECItems;
import com.userofbricks.expanded_combat.init.ECTags;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.advancements.critereon.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.neoforged.neoforge.common.data.AdvancementProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

import static com.userofbricks.expanded_combat.ExpandedCombat.MODID;

public class ECAdvancementProvider extends AdvancementProvider {
    public ECAdvancementProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> provider, ExistingFileHelper existingFileHelper) {
        super(output, provider, existingFileHelper, List.of(new ECAdvancementGenerator()));
    }

    @ParametersAreNonnullByDefault
    private static class ECAdvancementGenerator implements AdvancementProvider.AdvancementGenerator{
        @Override
        public void generate(HolderLookup.Provider registries, Consumer<AdvancementHolder> saver, ExistingFileHelper existingFileHelper) {
            AdvancementHolder root = Advancement.Builder.advancement()
                    .display(
                            //display item
                            ECItems.LEATHER_QUIVER,
                            Component.translatable(LangStrings.advancementRootTitle),
                            Component.translatable(LangStrings.advancementRootDesc),
                            //background
                            ResourceLocation.parse("minecraft:textures/gui/advancements/backgrounds/stone.png"),
                            AdvancementType.TASK,
                            //1:showToast, 2:announceChat, 3:hidden
                            false, false, false
                    )
                    .addCriterion("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(Items.CRAFTING_TABLE))
                    .save(saver, MODID + ":root");

            AdvancementHolder punchGauntlet = Advancement.Builder.advancement().parent(root)
                    .display(
                            ECItems.NETHERITE_GAUNTLET,
                            Component.translatable(LangStrings.advancementPunchGauntletTitle),
                            Component.translatable(LangStrings.advancementPunchGauntletDesc),
                            null, AdvancementType.TASK, true, true, false
                    )
                    .addCriterion("gauntlet_with_punch_2", InventoryChangeTrigger.TriggerInstance.hasItems(
                            ItemPredicate.Builder.item().of(ECTags.GAUNTLETS)
                                    .withSubPredicate(ItemSubPredicates.ENCHANTMENTS, ItemEnchantmentsPredicate.enchantments(
                                            List.of(new EnchantmentPredicate(registries.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.PUNCH), MinMaxBounds.Ints.atLeast(2)))
                                    ))
                    ))
                    .save(saver, MODID + ":punch_gauntlet");

            AdvancementHolder powerGlove = Advancement.Builder.advancement().parent(punchGauntlet)
                    .display(
                            ECItems.GOLD_GAUNTLET,
                            Component.translatable(LangStrings.advancementPowerGloveTitle),
                            Component.translatable(LangStrings.advancementPowerGloveDesc),
                            null, AdvancementType.TASK, true, true, true
                    )
                    .addCriterion("gold_gauntlet", InventoryChangeTrigger.TriggerInstance.hasItems(ECItems.GOLD_GAUNTLET))
                    .save(saver, MODID + ":gold_gauntlet");
        }
    }
}
