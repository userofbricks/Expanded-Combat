package com.userofbricks.expanded_combat.item;

import com.userofbricks.expanded_combat.data.material.Material;
import com.userofbricks.expanded_combat.data.weapon_type.WeaponType;
import com.userofbricks.expanded_combat.data_components.BlockWeaponAnim;
import com.userofbricks.expanded_combat.init.DataAttachments;
import com.userofbricks.expanded_combat.init.ECEnchantments;
import com.userofbricks.expanded_combat.init.ItemDataComponents;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.Holder;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.ToolAction;
import net.neoforged.neoforge.common.ToolActions;
import net.neoforged.neoforge.registries.DeferredHolder;

import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class ArrowBlockWeaponItem extends ECWeaponItem{
    private final int baseBlockCount;
    public ArrowBlockWeaponItem(DeferredHolder<Material, Material> material, DeferredHolder<WeaponType, WeaponType> weapon, Properties properties, int baseBlockCount) {
        super(material, weapon, properties);
        this.baseBlockCount = baseBlockCount;
    }

    public static int getMaxBlocksInARow(ItemStack katanaStack) {
        if (katanaStack.getItem() instanceof ArrowBlockWeaponItem arrowBlockWeaponItem) {
            return arrowBlockWeaponItem.baseBlockCount + katanaStack.getEnchantmentLevel(ECEnchantments.BLOCKING.get());
        }
        return 0;
    }

    public static boolean blockedRecently(LivingEntity livingEntity) {
        int timeSinceBlock = livingEntity.getData(DataAttachments.TIME_SINCE_WEAPON_BLOCK);
        return timeSinceBlock < 20;
    }

    public static BlockWeaponAnim blockPosition(ItemStack itemStack) {
        BlockWeaponAnim anim = itemStack.get(ItemDataComponents.BLOCK_WEAPON_ANIM);
        return anim != null ? anim : BlockWeaponAnim.BLOCK_1;
    }

    //TODO: update block animations to use UseAnim.CUSTOM and add those custom animations
    public UseAnim getUseAnimation(ItemStack itemStack) {
        return switch (itemStack.get(ItemDataComponents.BLOCK_WEAPON_ANIM)) {
            case null -> UseAnim.BLOCK;
            case BLOCK_1 -> UseAnim.BLOCK;
            case BLOCK_2 -> UseAnim.BLOCK;
            case BLOCK_3 -> UseAnim.BLOCK;
            case BLOCK_4 -> UseAnim.BLOCK;
            case BLOCK_5 -> UseAnim.NONE;
        };
    }

    public int getUseDuration(ItemStack stack) {
        return 72000;
    }

    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        player.startUsingItem(hand);
        return InteractionResultHolder.consume(itemstack);
    }

    @Override
    public boolean canPerformAction(ItemStack stack, ToolAction toolAction) {
        return ToolActions.DEFAULT_SHIELD_ACTIONS.contains(toolAction) || ToolActions.DEFAULT_SWORD_ACTIONS.contains(toolAction);
    }
}
