package com.userofbricks.expanded_combat.item;

import com.userofbricks.expanded_combat.api.material.Material;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static com.userofbricks.expanded_combat.datagen.LangStrings.TIPPED_ARROW_POTION_ENDING;

public class ECTippedArrowItem extends ECArrowItem{
    public ECTippedArrowItem(Properties properties, Material material) {
        super(properties, material);
    }

    @Override
    public @NotNull ItemStack getDefaultInstance() {
        return PotionUtils.setPotion(super.getDefaultInstance(), Potions.POISON);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, Level worldIn, @NotNull List<Component> tooltip, @NotNull TooltipFlag flagIn) {
        PotionUtils.addPotionTooltip(stack, tooltip, 0.125f);
    }

    @Override
    public @NotNull Component getName(@NotNull ItemStack stack) {
        return Component.translatable(this.getDescriptionId(stack)).append(" ").append(Component.translatable(this.getPotionId(stack)));
    }

    public String getPotionId(ItemStack stack) {
        return Potion.getName(stack.getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY).potion(), TIPPED_ARROW_POTION_ENDING);
    }

    public Item getNotTipped() {
        return null;
    }
}
