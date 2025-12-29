package com.userofbricks.expanded_combat.mixin;

import com.userofbricks.expanded_combat.ExpandedCombat;
import com.userofbricks.expanded_combat.item.QuiverItem;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.item.component.BundleContents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;

import java.util.List;

import static net.minecraft.core.component.DataComponents.BUNDLE_CONTENTS;

@Mixin(ProjectileWeaponItem.class)
public class ProjectileWeaponItemMixin {
    @Inject(method = "draw", at = @At("RETURN"), cancellable = true)
    private static void noEmptyStacksInQuivers(ItemStack p_331565_, ItemStack p_330406_, LivingEntity p_330823_, CallbackInfoReturnable<List<ItemStack>> cir) {
        SlotResult slotResult = CuriosApi.getCuriosInventory(p_330823_).flatMap(curiosInventory -> curiosInventory.findCurio(ExpandedCombat.QUIVER_CURIOS_IDENTIFIER, 0)).orElse(null);
        if (slotResult != null && slotResult.stack().getItem() instanceof QuiverItem quiverItem) {
            BundleContents bundlecontents = slotResult.stack().getOrDefault(BUNDLE_CONTENTS, BundleContents.EMPTY);

            QuiverItem.MutableQuiverContents bundlecontents$mutable = new QuiverItem.MutableQuiverContents(bundlecontents, quiverItem.getMaterial().offense().quiverStacks());
            bundlecontents$mutable.assureNoEmpties();
            slotResult.stack().set(BUNDLE_CONTENTS, bundlecontents$mutable.toImmutable());
        }
    }
}
