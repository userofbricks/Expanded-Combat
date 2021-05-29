package com.userofbricks.expandedcombat.mixin;

import com.userofbricks.expandedcombat.ExpandedCombat;
import com.userofbricks.expandedcombat.item.ECQuiverItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;

@Mixin(AbstractArrowEntity.class)
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
	public void playerTouch(PlayerEntity p_70100_1_) {
		if (!((AbstractArrowEntity)(Object)this).level.isClientSide && (this.inGround || ((AbstractArrowEntity)(Object)this).isNoPhysics()) && ((AbstractArrowEntity)(Object)this).shakeTime <= 0) {
			boolean flag;
			ItemStack arrowStack = CuriosApi.getCuriosHelper().findEquippedCurio(ExpandedCombat.arrow_predicate,p_70100_1_)
					.map(stringIntegerItemStackImmutableTriple -> stringIntegerItemStackImmutableTriple.right).orElse(ItemStack.EMPTY);
			ItemStack quiverStack = CuriosApi.getCuriosHelper().findEquippedCurio(ExpandedCombat.quiver_predicate,p_70100_1_)
					.map(stringIntegerItemStackImmutableTriple -> stringIntegerItemStackImmutableTriple.right).orElse(ItemStack.EMPTY);
			if(arrowStack.getItem() == this.getPickupItem().getItem() && arrowStack.getCount() < 64 && !quiverStack.isEmpty()) {
				arrowStack.setCount(arrowStack.getCount()+ 1);
				((AbstractArrowEntity)(Object)this).remove();
				return;
			}
			if(arrowStack.isEmpty() && !quiverStack.isEmpty()) {
				CuriosApi.getCuriosHelper().getCuriosHandler(p_70100_1_).map(ICuriosItemHandler::getCurios)
						.map(stringICurioStacksHandlerMap -> stringICurioStacksHandlerMap.get("arrows"))
						.map(ICurioStacksHandler::getStacks)
						.ifPresent(curioStackHandler -> curioStackHandler.setStackInSlot(0,this.getPickupItem()));
				((AbstractArrowEntity)(Object)this).remove();
				return;
			}
			flag = ((AbstractArrowEntity)(Object)this).pickup == AbstractArrowEntity.PickupStatus.ALLOWED ||
					((AbstractArrowEntity)(Object)this).pickup == AbstractArrowEntity.PickupStatus.CREATIVE_ONLY
							&& p_70100_1_.abilities.instabuild || ((AbstractArrowEntity)(Object)this).isNoPhysics() &&
					((AbstractArrowEntity)(Object)this).getOwner().getUUID() == p_70100_1_.getUUID();
			if (((AbstractArrowEntity)(Object)this).pickup == AbstractArrowEntity.PickupStatus.ALLOWED
					&& !p_70100_1_.inventory.add(this.getPickupItem())) {
				flag = false;
			}

			if (flag) {
				p_70100_1_.take(((AbstractArrowEntity)(Object)this), 1);
				((AbstractArrowEntity)(Object)this).remove();
			}
		}
	}
}
