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
        Optional<SlotResult> quiverStack = playerCuriosInventory.findCurio( ExpandedCombat.QUIVER_CURIOS_IDENTIFIER, 0);
        if (quiverStack.isPresent()) {
            int providedSlots = ((ECQuiverItem)quiverStack.get().stack().getItem()).providedSlots;
            int selectedSlot = Math.max(Math.min(ECVariables.getArrowSlot(this), providedSlots - 1), 0);
            //ECVariables.setArrowSlotTo(this, selectedSlot);

            Optional<SlotResult> currentSelectedSlot =playerCuriosInventory.findCurio( ExpandedCombat.ARROWS_CURIOS_IDENTIFIER, selectedSlot);
            if (currentSelectedSlot.isPresent() && currentSelectedSlot.get().slotContext().index() == selectedSlot) cir.setReturnValue(currentSelectedSlot.get().stack());
            else {
                playerCuriosInventory.findFirstCurio(stack -> Objects.requireNonNull(ForgeRegistries.ITEMS.tags()).getTag(ItemTags.ARROWS).contains(stack.getItem()))
                        .ifPresent(slotResult -> cir.setReturnValue(slotResult.stack()));
            }
        }
    }
}
