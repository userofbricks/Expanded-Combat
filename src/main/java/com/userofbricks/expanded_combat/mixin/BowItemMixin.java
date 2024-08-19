package com.userofbricks.expanded_combat.mixin;

import com.userofbricks.expanded_combat.item.ECQuiverItem;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.ForgeEventFactory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;

@Mixin(BowItem.class)
public abstract class BowItemMixin {

    @Shadow public abstract int getUseDuration(ItemStack p_40680_);

    @Inject(method = "releaseUsing", at = @At(value = "HEAD"))
    public void updateQuiverStack(ItemStack bow, Level level, LivingEntity livingEntity, int useDuration, CallbackInfo ci) {
        if (livingEntity instanceof Player player) {
            boolean flag = player.getAbilities().instabuild || bow.getEnchantmentLevel(Enchantments.INFINITY_ARROWS) > 0;
            ItemStack itemstack = player.getProjectile(bow);
            int i = this.getUseDuration(bow) - useDuration;
            i = ForgeEventFactory.onArrowLoose(bow, level, player, i, !itemstack.isEmpty() || flag);
            if ((!itemstack.isEmpty() || flag) && !(BowItem.getPowerForTime(i) < 0.1)) {
                boolean flag1 = player.getAbilities().instabuild || itemstack.getItem() instanceof ArrowItem && ((ArrowItem)itemstack.getItem()).isInfinite(itemstack, bow, player);
                if (!flag1 && !player.getAbilities().instabuild) {
                    LazyOptional<ICuriosItemHandler> optionalCuriosInventory = CuriosApi.getCuriosInventory(player);
                    if(optionalCuriosInventory.resolve().isEmpty()) return;
                    ICuriosItemHandler playerCuriosInventory = optionalCuriosInventory.resolve().get();
                    SlotResult quiverStack = playerCuriosInventory.findFirstCurio(item -> item.getItem() instanceof ECQuiverItem).orElse(null);
                    if (quiverStack != null && !ECQuiverItem.getContents(quiverStack.stack()).toList().isEmpty()) {
                        ECQuiverItem.remove(quiverStack.stack(), itemstack.copyWithCount(1));
                    }
                }
            }
        }
    }
}
