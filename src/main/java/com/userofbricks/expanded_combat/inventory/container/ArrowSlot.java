package com.userofbricks.expanded_combat.inventory.container;

import com.mojang.datafixers.util.Pair;
import com.userofbricks.expanded_combat.item.ECQuiverItem;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.items.SlotItemHandler;
import top.theillusivec4.curios.Curios;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.SlotResult;
import top.theillusivec4.curios.api.event.CurioEquipEvent;
import top.theillusivec4.curios.api.event.CurioUnequipEvent;
import top.theillusivec4.curios.api.type.inventory.IDynamicStackHandler;
import top.theillusivec4.curios.mixin.core.AccessorEntity;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ArrowSlot extends SlotItemHandler {

    private final Player player;
    private final SlotContext slotContext;

    public ArrowSlot(Player player, IDynamicStackHandler handler, int index, String identifier,
                     int xPosition, int yPosition) {
        super(handler, index, xPosition, yPosition);
        this.player = player;
        this.slotContext = new SlotContext(identifier, player, index, false,
                false);
        this.setBackground(InventoryMenu.BLOCK_ATLAS,
                player.getCommandSenderWorld().isClientSide() ?
                        CuriosApi.getIconHelper().getIcon(identifier)
                        : new ResourceLocation(Curios.MODID, "slot/empty_curio_slot"));
    }

    public SlotContext getSlotContext() {
        return slotContext;
    }


    @Nullable
    public Pair<ResourceLocation, ResourceLocation> getNoItemIcon() {
        SlotResult slotResult = CuriosApi.getCuriosHelper().findFirstCurio(this.player, item -> item.getItem() instanceof ECQuiverItem).orElse(null);
        if (slotResult != null && slotResult.stack().getItem() instanceof ECQuiverItem ecQuiverItem && ecQuiverItem.providedSlots > slotContext.index()) return super.getNoItemIcon();
        else return null;
    }

    @Override
    public void set(@Nonnull ItemStack stack) {
        ItemStack current = this.getItem();
        boolean flag = current.isEmpty() && stack.isEmpty();
        super.set(stack);

        if (!flag && !ItemStack.isSame(current, stack) &&
                !((AccessorEntity) this.player).getFirstTick()) {
            CuriosApi.getCuriosHelper().getCurio(stack)
                    .ifPresent(curio -> curio.onEquipFromUse(this.slotContext));
        }
    }

    @Override
    public boolean mayPlace(@Nonnull ItemStack stack) {
        CurioEquipEvent equipEvent = new CurioEquipEvent(stack, slotContext);
        MinecraftForge.EVENT_BUS.post(equipEvent);
        Event.Result result = equipEvent.getResult();

        if (result == Event.Result.DENY) {
            return false;
        }
        return result == Event.Result.ALLOW ||
                (CuriosApi.getCuriosHelper().isStackValid(slotContext, stack) &&
                        CuriosApi.getCuriosHelper().getCurio(stack).map(curio -> curio.canEquip(slotContext))
                                .orElse(true) && super.mayPlace(stack));
    }

    @Override
    public boolean mayPickup(Player playerIn) {
        ItemStack stack = this.getItem();
        CurioUnequipEvent unequipEvent = new CurioUnequipEvent(stack, slotContext);
        MinecraftForge.EVENT_BUS.post(unequipEvent);
        Event.Result result = unequipEvent.getResult();

        if (result == Event.Result.DENY) {
            return false;
        }
        return result == Event.Result.ALLOW ||
                ((stack.isEmpty() || playerIn.isCreative() || !EnchantmentHelper.hasBindingCurse(stack)) &&
                        CuriosApi.getCuriosHelper().getCurio(stack).map(curio -> curio.canUnequip(slotContext))
                                .orElse(true) && super.mayPickup(playerIn));
    }
}
