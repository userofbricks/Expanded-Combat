package com.userofbricks.expanded_combat.datagen;

import com.userofbricks.expanded_combat.item.ECItemTags;
import com.userofbricks.expanded_combat.item.materials.plugins.VanillaECPlugin;
import com.userofbricks.expanded_combat.util.LangStrings;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.critereon.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.ForgeAdvancementProvider;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

import static com.userofbricks.expanded_combat.ExpandedCombat.MODID;

public class ECAdvancementProvider extends ForgeAdvancementProvider {
    public ECAdvancementProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> provider, ExistingFileHelper existingFileHelper) {
        super(output, provider, existingFileHelper, List.of(new ECAdvancementGenerator()));
    }

    @ParametersAreNonnullByDefault
    private static class ECAdvancementGenerator implements ForgeAdvancementProvider.AdvancementGenerator{
        @Override
        public void generate(HolderLookup.Provider registries, Consumer<Advancement> saver, ExistingFileHelper existingFileHelper) {
            Advancement root = Advancement.Builder.advancement().display(
                    //display item
                    VanillaECPlugin.LEATHER.getQuiverEntry().get(),
                    Component.translatable(LangStrings.createAdvancementLang("root", "Expanded Combat", true)),
                    Component.translatable(LangStrings.createAdvancementLang("root", "Expanded Combat", false)),
                    //background
                    new ResourceLocation("minecraft:textures/gui/advancements/backgrounds/stone.png"),
                    FrameType.TASK,
                    //1:showToast, 2:announceChat, 3:hidden
                    false, false, false)
                    .addCriterion("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(Items.CRAFTING_TABLE))
                    .save(saver, MODID + ":root");

            Advancement powerGlove = Advancement.Builder.advancement().parent(root).display(
                            VanillaECPlugin.GOLD.getGauntletEntry().get(),
                            Component.translatable(LangStrings.createAdvancementLang("gold_gauntlet", "The Power Glove", true)),
                            Component.translatable(LangStrings.createAdvancementLang("gold_gauntlet", "Snap", false)),
                            null, FrameType.TASK, true, true, true)
                    .addCriterion("gold_gauntlet", InventoryChangeTrigger.TriggerInstance.hasItems(VanillaECPlugin.GOLD.getGauntletEntry().get()))
                    .save(saver, MODID + ":gold_gauntlet");

            ItemStack punch2NetheriteGauntlet = new ItemStack(VanillaECPlugin.NETHERITE.getGauntletEntry().get());
            punch2NetheriteGauntlet.enchant(Enchantments.PUNCH_ARROWS, 2);
            Advancement PunchGauntlet = Advancement.Builder.advancement().parent(root).display(
                            punch2NetheriteGauntlet,
                            Component.translatable(LangStrings.createAdvancementLang("punch_gauntlet", "Punch it!", true)),
                            Component.translatable(LangStrings.createAdvancementLang("punch_gauntlet", "Punch 2 Gauntlet", false)),
                            null, FrameType.TASK, true, true, false)
                    .addCriterion("gauntlet_with_punch_2", InventoryChangeTrigger.TriggerInstance.hasItems(
                            new ItemPredicate(ECItemTags.GAUNTLETS, null,
                                    MinMaxBounds.Ints.ANY, MinMaxBounds.Ints.ANY,
                                    new EnchantmentPredicate[]{new EnchantmentPredicate(Enchantments.PUNCH_ARROWS, MinMaxBounds.Ints.atLeast(2))},
                                    new EnchantmentPredicate[]{EnchantmentPredicate.ANY}, null, NbtPredicate.ANY)
                    ))
                    .save(saver, MODID + ":punch_gauntlet");
        }
    }
}
