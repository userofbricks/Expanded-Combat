package com.userofbricks.expanded_combat.item;

import com.userofbricks.expanded_combat.data.material.Material;
import com.userofbricks.expanded_combat.data.weapon_type.WeaponType;
import com.userofbricks.expanded_combat.init.ItemDataComponents;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponents;
import net.minecraft.util.Unit;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.jetbrains.annotations.NotNull;

public class PotionWeaponItem extends ECWeaponItem {
    public PotionWeaponItem(DeferredHolder<Material, Material> material, DeferredHolder<WeaponType, WeaponType> weapon, Properties builderIn) {
        super(material, weapon, builderIn.component(DataComponents.POTION_CONTENTS, new PotionContents(Potions.WATER)));
    }

    @Override
    public boolean hurtEnemy(@NotNull ItemStack weapon, @NotNull LivingEntity target, @NotNull LivingEntity attacker) {
        super.hurtEnemy(weapon, target, attacker);
        PotionContents potioncontents = weapon.get(DataComponents.POTION_CONTENTS);
        int potionUses = weapon.get(ItemDataComponents.POTION_USES);

        if (potioncontents != null && potioncontents.potion().isPresent() && potioncontents.potion().get() != Potions.WATER && potionUses >= 1) {

            if (potionUses == 1) {
                weapon.set(DataComponents.POTION_CONTENTS, new PotionContents(Potions.WATER));
                weapon.set(ItemDataComponents.MAX_POTION_USES, 0);
            }
            weapon.set(ItemDataComponents.POTION_USES, potionUses - 1);

            for (MobEffectInstance effectInstance : potioncontents.getAllEffects()) {
                target.addEffect(effectInstance);
            }
        } else {
            weapon.set(DataComponents.POTION_CONTENTS, new PotionContents(Potions.WATER));
            weapon.set(ItemDataComponents.MAX_POTION_USES, 0);
            weapon.set(ItemDataComponents.POTION_USES, 0);
        }
        return true;
    }

    public @NotNull ItemStack getDefaultInstance() {
        ItemStack itemstack = super.getDefaultInstance();
        itemstack.set(DataComponents.POTION_CONTENTS, new PotionContents(Potions.WATER));
        itemstack.set(ItemDataComponents.MAX_POTION_USES, 0);
        itemstack.set(ItemDataComponents.POTION_USES, 0);
        return itemstack;
    }
}
