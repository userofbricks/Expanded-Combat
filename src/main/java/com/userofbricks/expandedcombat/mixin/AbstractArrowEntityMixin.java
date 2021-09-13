package com.userofbricks.expandedcombat.mixin;

import com.userofbricks.expandedcombat.ExpandedCombatOld;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;

@Mixin(AbstractArrow.class)
public abstract class AbstractArrowEntityMixin {

    @Shadow
    protected boolean inGround;
    @Shadow
    protected abstract ItemStack getPickupItem();


    /**
     * @author Userofbricks and theNyfaria
     * @reason need this to chack the arrow slots if a quiver exists
     */
    @Overwrite
    public void playerTouch(Player p_70100_1_) {
        if (!((AbstractArrow)(Object)this).level.isClientSide && (this.inGround || ((AbstractArrow)(Object)this).isNoPhysics()) && ((AbstractArrow)(Object)this).shakeTime <= 0) {
            boolean flag;
            ItemStack arrowStack = CuriosApi.getCuriosHelper().findEquippedCurio(ExpandedCombatOld.arrow_predicate, p_70100_1_)
                    .map(stringIntegerItemStackImmutableTriple -> stringIntegerItemStackImmutableTriple.right).orElse(ItemStack.EMPTY);
            ItemStack quiverStack = CuriosApi.getCuriosHelper().findEquippedCurio(ExpandedCombatOld.quiver_predicate, p_70100_1_)
                    .map(stringIntegerItemStackImmutableTriple -> stringIntegerItemStackImmutableTriple.right).orElse(ItemStack.EMPTY);
            if(arrowStack.getItem() == this.getPickupItem().getItem() && arrowStack.getCount() < 64 && !quiverStack.isEmpty()) {
                arrowStack.setCount(arrowStack.getCount()+ 1);
                ((AbstractArrow)(Object)this).discard();
                return;
            }
            if(arrowStack.isEmpty() && !quiverStack.isEmpty()) {
                CuriosApi.getCuriosHelper().getCuriosHandler(p_70100_1_).map(ICuriosItemHandler::getCurios)
                        .map(stringICurioStacksHandlerMap -> stringICurioStacksHandlerMap.get("arrows"))
                        .map(ICurioStacksHandler::getStacks)
                        .ifPresent(curioStackHandler -> curioStackHandler.setStackInSlot(0,this.getPickupItem()));
                ((AbstractArrow)(Object)this).discard();
                return;
            }
            flag = ((AbstractArrow)(Object)this).pickup == AbstractArrow.Pickup.ALLOWED ||
                    ((AbstractArrow)(Object)this).pickup == AbstractArrow.Pickup.CREATIVE_ONLY
                            && p_70100_1_.getAbilities().instabuild || ((AbstractArrow)(Object)this).isNoPhysics() &&
                    ((AbstractArrow)(Object)this).getOwner().getUUID() == p_70100_1_.getUUID();
            if (((AbstractArrow)(Object)this).pickup == AbstractArrow.Pickup.ALLOWED
                    && !p_70100_1_.getInventory().add(this.getPickupItem())) {
                flag = false;
            }

            if (flag) {
                p_70100_1_.take(((AbstractArrow)(Object)this), 1);
                ((AbstractArrow)(Object)this).discard();
            }
        }
    }
}