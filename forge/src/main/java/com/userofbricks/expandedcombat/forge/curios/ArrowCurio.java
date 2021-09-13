package com.userofbricks.expandedcombat.forge.curios;

import com.userofbricks.expandedcombat.forge.ExpandedCombatForge;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;

public class ArrowCurio implements ICurio
{
    private Object model;
    
    public boolean canEquip(SlotContext slotContext) {
        LivingEntity livingEntity = slotContext.entity();
        return CuriosApi.getCuriosHelper().findEquippedCurio(ExpandedCombatForge.quiver_predicate, livingEntity).map(stringIntegerItemStackImmutableTriple -> stringIntegerItemStackImmutableTriple.right).map(ItemStack::getItem).map(ExpandedCombatForge.quiver_curios::contains).orElse(false);
    }

    @Override public boolean canEquipFromUse(SlotContext slotContext) {return true;}

    @Override public ItemStack getStack() {return null;}
}
