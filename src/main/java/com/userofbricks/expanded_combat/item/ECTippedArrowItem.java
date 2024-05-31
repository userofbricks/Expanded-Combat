package com.userofbricks.expanded_combat.item;

import com.userofbricks.expanded_combat.data.material.Material;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.ItemLike;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

import static com.userofbricks.expanded_combat.datagen.LangStrings.TIPPED_ARROW_POTION_ENDING;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ECTippedArrowItem extends ECArrowItem {
    private final ItemLike notTipped;
    public ECTippedArrowItem(Properties properties, Holder.Reference<Material> material, ItemLike notTipped) {
        super(properties, material);
        this.notTipped = notTipped;
    }

    @Override
    public ItemStack getDefaultInstance() {
        ItemStack itemstack = super.getDefaultInstance();
        itemstack.set(DataComponents.POTION_CONTENTS, new PotionContents(Potions.POISON));
        return itemstack;
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag flagIn) {
        PotionContents potioncontents = stack.get(DataComponents.POTION_CONTENTS);
        if (potioncontents != null) {
            potioncontents.addPotionTooltip(tooltip::add, 0.125F, context.tickRate());
        }
    }

    @Override
    public Component getName(ItemStack stack) {
        return Component.translatable(this.getDescriptionId(stack)).append(" ").append(Component.translatable(this.getPotionId(stack)));
    }

    public String getPotionId(ItemStack stack) {
        return Potion.getName(stack.getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY).potion(), TIPPED_ARROW_POTION_ENDING);
    }

    public Item getNotTipped() {
        return notTipped.asItem();
    }
}
