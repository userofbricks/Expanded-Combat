package com.userofbricks.expanded_combat.mixin;

import com.userofbricks.expanded_combat.config.CommonECConfig;
import com.userofbricks.expanded_combat.item.QuiverItem;
import net.minecraft.stats.Stats;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.BundleContents;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;

import static net.minecraft.core.component.DataComponents.BUNDLE_CONTENTS;

@Mixin(AbstractArrow.class)
public abstract class AbstractArrowEntityMixin extends Projectile {

    @Shadow
    protected boolean inGround;
    @Shadow
    public int shakeTime;


    protected AbstractArrowEntityMixin(EntityType<? extends Projectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Shadow
    protected abstract ItemStack getPickupItem();

    @Shadow
    public abstract boolean isNoPhysics();

    @Shadow public AbstractArrow.Pickup pickup;

    /**
     * Changed Version
     * @author Skijearz;
     * @reason changed the mixin from an overwrite to inject in order to keep compatibility with other mods and mixins.
     * Needs to be cancellable, if an arrow is picked up into the quiver it cancels the vanilla behaviour so the arrow isn't duped.
     * Otherwise, if the quiver is not equipped just don't cancel the vanilla behavior, so it will be picked up into the inventory.
     *
     *
     * @author Userofbricks and theNyfaria for the original overwrite of this method
     * (this is now so heavily modified that I don't know if any of theNyfaria's work even exists in it anymore)
     * @reason need this to check the arrow slots if a quiver exists.
     */
    @Inject(method = "playerTouch",at = @At("HEAD"),cancellable = true)
    public void playerTouch(Player player, CallbackInfo callback) {
        if (!level().isClientSide && (this.inGround || isNoPhysics()) && shakeTime <= 0 && CommonECConfig.pair.getLeft().enableQuivers.get()) {
            ItemStack pickupItem = this.getPickupItem();
            if (this.pickup == AbstractArrow.Pickup.ALLOWED && this.getPickupItem().is(ItemTags.ARROWS)){
                SlotResult quiverSlot = CuriosApi.getCuriosInventory(player).flatMap(curiosInventory -> curiosInventory.findFirstCurio(item -> item.getItem() instanceof QuiverItem)).orElse(null);
                if (quiverSlot == null) return;
                ItemStack quiverStack = quiverSlot.stack();
                QuiverItem quiverItem = ((QuiverItem)quiverStack.getItem());

                BundleContents bundlecontents = quiverStack.getOrDefault(BUNDLE_CONTENTS, BundleContents.EMPTY);

                QuiverItem.MutableQuiverContents bundlecontents$mutable = new QuiverItem.MutableQuiverContents(bundlecontents, quiverItem.getMaterial().offense().quiverSlots());

                int i = bundlecontents$mutable.tryInsert(pickupItem);
                if (i > 0) {
                    quiverStack.set(BUNDLE_CONTENTS, bundlecontents$mutable.toImmutable());
                    quiverItem.playInsert(player);
                    player.awardStat(Stats.ITEM_PICKED_UP.get(pickupItem.getItem()), 1);
                    discard();
                    callback.cancel();
                }
            }
        }
    }
}
