package com.userofbricks.expanded_combat.item;

import com.userofbricks.expanded_combat.enchentments.ECEnchantments;
import com.userofbricks.expanded_combat.item.materials.Material;
import com.userofbricks.expanded_combat.item.materials.WeaponMaterial;
import com.userofbricks.expanded_combat.item.materials.plugins.VanillaECPlugin;
import com.userofbricks.expanded_combat.network.ECVariables;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.common.ToolActions;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.function.Consumer;

import static com.userofbricks.expanded_combat.ExpandedCombat.CONFIG;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class ECKatanaItem extends ECWeaponItem{
    public ECKatanaItem(Material material, Properties properties) {
        super(material, VanillaECPlugin.KATANA, properties);
    }

    public static int getMaxBlocksInARow(ItemStack katanaStack) {
        if (katanaStack.getItem() instanceof ECKatanaItem) {
            return CONFIG.enchantmentLevels.baseKatanaArrowBlocks + katanaStack.getEnchantmentLevel(ECEnchantments.BLOCKING.get());
        }
        return 0;
    }

    public static boolean blockedRecently(LivingEntity livingEntity) {
        int timeSinceBlock = ECVariables.getKatanaTimeSinceBlock(livingEntity);
        return timeSinceBlock < 20;
    }

    public static float blockPosition(ItemStack itemStack) {
        if (itemStack.getOrCreateTag().contains("BlockingPos")) {
            return itemStack.getOrCreateTag().getFloat("BlockingPos");
        }
        return 0;
    }

    public UseAnim getUseAnimation(ItemStack itemStack) {
        CompoundTag compoundtag = itemStack.getOrCreateTag();
        if (compoundtag.contains("BlockingPos")) {
            float pos = compoundtag.getFloat("BlockingPos");
            if (pos == 0) return UseAnim.BLOCK;
            if (pos == 0.1) return UseAnim.BLOCK;
            if (pos == 0.2) return UseAnim.BLOCK;
            if (pos == 0.3) return UseAnim.BLOCK;
            if (pos == 0.4) return UseAnim.NONE;
        }

        return UseAnim.BLOCK;
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
    public boolean canPerformAction(ItemStack stack, net.minecraftforge.common.ToolAction toolAction) {
        return ToolActions.DEFAULT_SHIELD_ACTIONS.contains(toolAction) || ToolActions.DEFAULT_SWORD_ACTIONS.contains(toolAction);
    }

    @Override
    public boolean hurtEnemy(ItemStack p_43278_, LivingEntity p_43279_, LivingEntity p_43280_) {
        return super.hurtEnemy(p_43278_, p_43279_, p_43280_);
    }
}
