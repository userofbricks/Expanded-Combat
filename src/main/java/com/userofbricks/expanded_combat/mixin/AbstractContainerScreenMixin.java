package com.userofbricks.expanded_combat.mixin;

import com.userofbricks.expanded_combat.inventory.container.ArrowSlot;
import com.userofbricks.expanded_combat.item.ECQuiverItem;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.world.inventory.Slot;
import org.spongepowered.asm.mixin.Mixin;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;
import top.theillusivec4.curios.client.gui.CuriosScreen;

import java.util.Objects;

import static com.userofbricks.expanded_combat.ExpandedCombat.QUIVER_CURIOS_IDENTIFIER;

@Mixin(CuriosScreen.class)
public class AbstractContainerScreenMixin {

    public int getSlotColor(int slotId) {
        SlotResult slotResult = CuriosApi.getCuriosHelper().findCurio(Objects.requireNonNull(((AbstractContainerScreen<?>) (Object) this).getMinecraft().player), QUIVER_CURIOS_IDENTIFIER, 0).orElse(null);
        int curiosSlots = 0;
        if (slotResult != null && slotResult.stack().getItem() instanceof ECQuiverItem ecQuiverItem) curiosSlots = ecQuiverItem.providedSlots;
        Slot slot = ((AbstractContainerScreen<?>)(Object)this).getMenu().getSlot(slotId);
        if (slot instanceof ArrowSlot arrowSlot) {
            int id = arrowSlot.getSlotContext().index();
            if (curiosSlots <= id) return 0;
        }
        return -2130706433;
    }
}
