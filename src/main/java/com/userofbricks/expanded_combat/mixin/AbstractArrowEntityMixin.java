package com.userofbricks.expanded_combat.mixin;

import com.userofbricks.expanded_combat.item.ECQuiverItem;
import net.minecraft.stats.Stats;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.util.LazyOptional;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;
import top.theillusivec4.curios.api.type.inventory.IDynamicStackHandler;

import java.util.concurrent.atomic.AtomicBoolean;

import static com.userofbricks.expanded_combat.ExpandedCombat.ARROWS_CURIOS_IDENTIFIER;

@Mixin(AbstractArrow.class)
public abstract class AbstractArrowEntityMixin {

    @Shadow
    protected boolean inGround;
    @Shadow
    protected abstract ItemStack getPickupItem();

    @Shadow public AbstractArrow.Pickup pickup;

    /**
     * Changed Version
     * author: Skijearz;
     * reason: changed the mixin from a overwrite to inject in order to keep compatibility with other mods and mixins. needs to be cancellable, if a arrow is pickedup into the quiver it cancels the vanilla behaviour so the arrow isnt duped. Otherwise if the quiver is not equipped just dont cancel the vanilla behavior so it will be picked up into the inventory.
     *
     *
     * @author Userofbricks and theNyfaria for the original overwrite of this method
     * (this is now so heavily modified that I don't know if any of theNyfaria's work even exists in it anymore)
     * @reason need this to check the arrow slots if a quiver exists.
     */
    @Inject(method = "playerTouch",at = @At("HEAD"),cancellable = true)
    public void playerTouch(Player player,CallbackInfo callback) {
        if (!((AbstractArrow)(Object)this).level().isClientSide && (this.inGround || ((AbstractArrow)(Object)this).isNoPhysics()) && ((AbstractArrow)(Object)this).shakeTime <= 0) {
            ItemStack pickupItem = this.getPickupItem();
            AtomicBoolean added = new AtomicBoolean(false);
            if (this.pickup == AbstractArrow.Pickup.ALLOWED && this.getPickupItem().is(ItemTags.ARROWS)){
                LazyOptional<ICuriosItemHandler> optionalCuriosInventory = CuriosApi.getCuriosInventory(player);
                if(optionalCuriosInventory.resolve().isEmpty()) return;
                ICuriosItemHandler playerCuriosInventory = optionalCuriosInventory.resolve().get();
                SlotResult quiverSlot =playerCuriosInventory.findFirstCurio(item -> item.getItem() instanceof ECQuiverItem).orElse(null);
                if (quiverSlot == null) return;
                ItemStack quiverStack = quiverSlot.stack();
                IDynamicStackHandler arrowStackHandler = playerCuriosInventory.getCurios().get(ARROWS_CURIOS_IDENTIFIER).getStacks();
                int slots = arrowStackHandler.getSlots();
                for (int s = 0; s < slots; s++) {
                    ItemStack currentStack = arrowStackHandler.getStackInSlot(s);
                    if (((currentStack.getItem() == this.getPickupItem().getItem() && currentStack.getCount() < currentStack.getMaxStackSize()) || currentStack.isEmpty()) && ((ECQuiverItem) quiverStack.getItem()).providedSlots > s) {
                        arrowStackHandler.insertItem(s, this.getPickupItem().copy(), false);
                        player.awardStat(Stats.ITEM_PICKED_UP.get(this.getPickupItem().getItem()), 1);
                        ((AbstractArrow) (Object) this).discard();
                        added.set(true);
                        break;
                    }
                }
                if (added.get()){
                    callback.cancel();
                }
            }
        }
    }
}
