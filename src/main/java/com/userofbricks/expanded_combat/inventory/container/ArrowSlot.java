package com.userofbricks.expanded_combat.inventory.container;

import com.mojang.datafixers.util.Pair;
import com.userofbricks.expanded_combat.item.ECQuiverItem;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.items.SlotItemHandler;
import top.theillusivec4.curios.Curios;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.SlotResult;
import top.theillusivec4.curios.api.event.CurioEquipEvent;
import top.theillusivec4.curios.api.event.CurioUnequipEvent;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;
import top.theillusivec4.curios.api.type.inventory.IDynamicStackHandler;
import top.theillusivec4.curios.mixin.core.AccessorEntity;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;

import static com.userofbricks.expanded_combat.ExpandedCombat.QUIVER_CURIOS_IDENTIFIER;

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
                        CuriosApi.getSlot(identifier,player.getCommandSenderWorld()).get().getIcon()
                        : new ResourceLocation(Curios.MODID, "slot/empty_curio_slot"));
    }

    public SlotContext getSlotContext() {
        return slotContext;
    }


    @Nullable
    public Pair<ResourceLocation, ResourceLocation> getNoItemIcon() {
        LazyOptional<ICuriosItemHandler> optionalCuriosIventory = CuriosApi.getCuriosInventory(this.player);
        if(optionalCuriosIventory.resolve().isEmpty()) return null;
        ICuriosItemHandler playerCuriosInventory = optionalCuriosIventory.resolve().get();
        SlotResult slotResult = playerCuriosInventory.findFirstCurio(item -> item.getItem() instanceof ECQuiverItem).orElse(null);
        if (slotResult != null && slotResult.stack().getItem() instanceof ECQuiverItem ecQuiverItem && ecQuiverItem.providedSlots > slotContext.index()) return super.getNoItemIcon();
        else return null;
    }

    @Override
    public void set(@Nonnull ItemStack stack) {
        ItemStack current = this.getItem();
        boolean flag = current.isEmpty() && stack.isEmpty();
        super.set(stack);

        if (!flag && !ItemStack.matches(current, stack) &&
                !((AccessorEntity) this.player).getFirstTick()) {
            CuriosApi.getCurio(stack)
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
                (CuriosApi.isStackValid(slotContext, stack) &&
                        CuriosApi.getCurio(stack).map(curio -> curio.canEquip(slotContext))
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
                        CuriosApi.getCurio(stack).map(curio -> curio.canUnequip(slotContext))
                                .orElse(true) && super.mayPickup(playerIn));
    }

    @Override
    public boolean isHighlightable() {
        LazyOptional<ICuriosItemHandler> optionalCuriosInventory = CuriosApi.getCuriosInventory(Objects.requireNonNull(Minecraft.getInstance().player));
        if(optionalCuriosInventory.resolve().isEmpty()) return false;
        ICuriosItemHandler playerCuriosInventory = optionalCuriosInventory.resolve().get();
        SlotResult slotResult = playerCuriosInventory.findCurio(QUIVER_CURIOS_IDENTIFIER, 0).orElse(null);
        int curiosSlots = 0;
        if (slotResult != null && slotResult.stack().getItem() instanceof ECQuiverItem ecQuiverItem) curiosSlots = ecQuiverItem.providedSlots;
        int id = slotContext.index();
        return curiosSlots > id;
    }
}
