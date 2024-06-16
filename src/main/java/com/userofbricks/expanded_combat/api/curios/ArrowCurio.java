package com.userofbricks.expanded_combat.api.curios;

import com.userofbricks.expanded_combat.item.ECQuiverItem;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.util.LazyOptional;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.SlotResult;
import top.theillusivec4.curios.api.type.capability.ICurio;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;
import java.util.Optional;
import static com.userofbricks.expanded_combat.ExpandedCombat.ARROWS_CURIOS_IDENTIFIER;


public class ArrowCurio implements ICurio {
    private final ItemStack itemStack;

    public ArrowCurio(ItemStack stack) {
        this.itemStack = stack;
    }

    @Override
    public boolean canEquip(SlotContext slotContext) {
        int slotId = slotContext.index();
        String identifier = slotContext.identifier();
        LivingEntity entity = slotContext.entity();
        LazyOptional<ICuriosItemHandler> optionalCuriosInventory = CuriosApi.getCuriosInventory(entity);
        if(optionalCuriosInventory.resolve().isEmpty()) return false;

        ICuriosItemHandler entityCuriosInventoy = optionalCuriosInventory.resolve().get();
        Optional<SlotResult> quiverSlotResult = entityCuriosInventoy.findFirstCurio(stack -> stack.getItem() instanceof  ECQuiverItem);
        if(quiverSlotResult.isEmpty()) return false;

        ECQuiverItem quiverItem = (ECQuiverItem) quiverSlotResult.get().stack().getItem();
        int providedSlots = quiverItem.providedSlots;

        return slotId < providedSlots && identifier.equals(ARROWS_CURIOS_IDENTIFIER);

    }

    @Override
    public boolean canEquipFromUse(SlotContext slotContext) {
        return true;
    }

    @Override
    public ItemStack getStack() {
        return itemStack;
    }
}
