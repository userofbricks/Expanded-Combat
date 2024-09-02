package com.userofbricks.expanded_combat.mixin;

import com.userofbricks.expanded_combat.ExpandedCombat;
import com.userofbricks.expanded_combat.item.ECQuiverItem;
import com.userofbricks.expanded_combat.network.ECVariables;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.registries.ForgeRegistries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Mixin(Player.class)
public abstract class PlayerEntityMixin extends LivingEntity {

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> p_20966_, Level p_20967_) {
        super(p_20966_, p_20967_);
    }

    @Inject(method = "getProjectile", at = @At("HEAD"), cancellable = true)
    private void checkQuiver(ItemStack shootable, CallbackInfoReturnable<ItemStack> cir) {
        if (!(shootable.getItem() instanceof ProjectileWeaponItem)) {
            return;
        }
        LazyOptional<ICuriosItemHandler> optionalCuriosInventory = CuriosApi.getCuriosInventory(this);
        if(optionalCuriosInventory.resolve().isEmpty()) return;
        ICuriosItemHandler playerCuriosInventory = optionalCuriosInventory.resolve().get();
        SlotResult quiverStack = playerCuriosInventory.findFirstCurio(item -> item.getItem() instanceof ECQuiverItem).orElse(null);
        if (quiverStack != null) {
            int providedSlots = ECQuiverItem.numberOfArrowStacks(quiverStack.stack());
            int selectedSlot = Math.max(Math.min(ECVariables.getArrowSlot(this), providedSlots <= 0 ? 0 : providedSlots - 1), 0);

            List<ItemStack> arrows = ECQuiverItem.getContents(quiverStack.stack()).toList();
            if (!arrows.isEmpty()) cir.setReturnValue(arrows.get(selectedSlot));
            else playerCuriosInventory.findFirstCurio(stack -> stack.is(ItemTags.ARROWS)).ifPresent(slotResult -> cir.setReturnValue(slotResult.stack()));
        }
    }
}
