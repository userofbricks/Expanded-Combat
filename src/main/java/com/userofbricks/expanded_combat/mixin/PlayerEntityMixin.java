package com.userofbricks.expanded_combat.mixin;

import com.userofbricks.expanded_combat.ExpandedCombat;
import com.userofbricks.expanded_combat.config.CommonECConfig;
import com.userofbricks.expanded_combat.init.DataAttachments;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.item.component.BundleContents;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;

import java.util.Optional;

import static net.minecraft.core.component.DataComponents.BUNDLE_CONTENTS;

@Mixin(Player.class)
public abstract class PlayerEntityMixin extends LivingEntity {

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> p_20966_, Level p_20967_) {
        super(p_20966_, p_20967_);
    }

    @Inject(method = "getProjectile", at = @At("HEAD"), cancellable = true)
    private void checkQuiver(ItemStack shootable, CallbackInfoReturnable<ItemStack> cir) {
        if (!(shootable.getItem() instanceof ProjectileWeaponItem) || !CommonECConfig.pair.getLeft().enableQuivers.get()) {
            return;
        }
        Optional<SlotResult> quiverStack = CuriosApi.getCuriosInventory(this).flatMap(curiosInventory -> curiosInventory.findCurio(ExpandedCombat.QUIVER_CURIOS_IDENTIFIER, 0));
        if (quiverStack.isPresent()) {
            BundleContents bundlecontents = quiverStack.get().stack().getOrDefault(BUNDLE_CONTENTS, BundleContents.EMPTY);
            int providedSlots = bundlecontents.size();
            int selectedSlot = Math.max(Math.min(this.getData(DataAttachments.ARROW_SLOT), providedSlots - 1), 0);

            //TODO may need to create methods of removal after shot or mimic Spartan weaponry and put the arrow stack in offhand instead of using a mixin
            cir.setReturnValue(bundlecontents.getItemUnsafe(selectedSlot));
        }
    }
}
