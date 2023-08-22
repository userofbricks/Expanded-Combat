package com.userofbricks.expanded_combat.item;

import com.userofbricks.expanded_combat.item.materials.Material;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static com.userofbricks.expanded_combat.util.LangStrings.TIPPED_ARROW_POTION_ENDING;

public class ECTippedArrowItem extends ECArrowItem{
    public ECTippedArrowItem(Material material, Properties properties) {
        super(material, properties);
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
        return PotionUtils.getPotion(stack).getName(TIPPED_ARROW_POTION_ENDING);
    }
}
