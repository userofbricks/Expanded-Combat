package com.userofbricks.expanded_combat.mixin;

import com.userofbricks.expanded_combat.item.ECQuiverItem;
import net.minecraft.stats.Stats;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
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
public abstract class AbstractArrowEntityMixin extends Projectile {

    @Shadow
    protected boolean inGround;

    protected AbstractArrowEntityMixin(EntityType<? extends Projectile> p_37248_, Level p_37249_) {
        super(p_37248_, p_37249_);
    }

    @Shadow
    protected abstract ItemStack getPickupItem();

    @Shadow public AbstractArrow.Pickup pickup;

    /**
     * Changed Version
     * author: Skijearz;
     * reason: changed the mixin from an overwrite to inject in order to keep compatibility with other mods and mixins. needs to be cancellable, if an arrow is picked up into the quiver it cancels the vanilla behaviour so the arrow isn't duped. Otherwise, if the quiver is not equipped just don't cancel the vanilla behavior, so it will be picked up into the inventory.
     *
     *
     * @author Userofbricks
     * @reason need this to check the quiver if one exists.
     */
    @Inject(method = "playerTouch",at = @At("HEAD"),cancellable = true)
    public void playerTouch(Player player,CallbackInfo callback) {
        if (!this.level().isClientSide && (this.inGround || ((AbstractArrow)(Object)this).isNoPhysics()) && ((AbstractArrow)(Object)this).shakeTime <= 0) {
            AtomicBoolean added = new AtomicBoolean(false);
            if (this.pickup == AbstractArrow.Pickup.ALLOWED && this.getPickupItem().is(ItemTags.ARROWS)){
                LazyOptional<ICuriosItemHandler> optionalCuriosInventory = CuriosApi.getCuriosInventory(player);
                if(optionalCuriosInventory.resolve().isEmpty()) return;
                ICuriosItemHandler playerCuriosInventory = optionalCuriosInventory.resolve().get();
                SlotResult quiverSlot = playerCuriosInventory.findFirstCurio(item -> item.getItem() instanceof ECQuiverItem).orElse(null);
                if (quiverSlot == null) return;
                ItemStack quiverStack = quiverSlot.stack();



                IDynamicStackHandler arrowStackHandler = playerCuriosInventory.getCurios().get(ARROWS_CURIOS_IDENTIFIER).getStacks();
                int slots = arrowStackHandler.getSlots();
                for (int s = 0; s < slots; s++) {
                    ItemStack currentStack = arrowStackHandler.getStackInSlot(s);
                    if (((currentStack.getItem() == this.getPickupItem().getItem() && currentStack.getCount() < currentStack.getMaxStackSize()) || currentStack.isEmpty()) && ((ECQuiverItem) quiverStack.getItem()).providedSlots > s) {
                        arrowStackHandler.insertItem(s, this.getPickupItem().copy(), false);
                        player.awardStat(Stats.ITEM_PICKED_UP.get(this.getPickupItem().getItem()), 1);
                        this.discard();
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
