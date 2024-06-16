package com.userofbricks.expanded_combat.item;

import com.userofbricks.expanded_combat.ExpandedCombat;
import com.userofbricks.expanded_combat.api.material.Material;
import com.userofbricks.expanded_combat.client.renderer.QuiverRenderer;
import com.userofbricks.expanded_combat.init.ECKeyRegistry;
import com.userofbricks.expanded_combat.network.ECVariables;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.client.ICurioRenderer;
import top.theillusivec4.curios.api.type.capability.ICurioItem;
import top.theillusivec4.curios.api.type.inventory.IDynamicStackHandler;

import java.util.function.Supplier;

import static com.userofbricks.expanded_combat.ExpandedCombat.ARROWS_CURIOS_IDENTIFIER;

public class ECQuiverItem extends Item implements ICurioItem {
    private final ResourceLocation QUIVER_TEXTURE;
    public final int providedSlots;
    public final Material material;
    public ECQuiverItem(Properties properties, Material material) {
        super(properties);
        this.QUIVER_TEXTURE = new ResourceLocation(ExpandedCombat.MODID, "textures/entity/quiver/" + material.getLocationName().getPath() + ".png");
        this.providedSlots = material.getConfig().quiverSlots;
        this.material = material;
    }

    public ResourceLocation getQUIVER_TEXTURE() {
        return this.QUIVER_TEXTURE;
    }
    public Supplier<ICurioRenderer> getQuiverRenderer() {
        return QuiverRenderer::new;
    }

    public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
        return true;
    }

    /*
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        if (!(newStack.getItem() instanceof ECQuiverItem)) {
            serializeArrowsNBT(stack, slotContext.entity());
        }
    }

    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        if (prevStack.getItem() instanceof ECQuiverItem) {
            serializeArrowsNBT(prevStack, slotContext.entity());
        }
        CuriosApi.getCuriosHelper().getCuriosHandler(slotContext.entity()).ifPresent(curios -> {
                IDynamicStackHandler arrowStackHandler = curios.getCurios().get(ARROWS_CURIOS_IDENTIFIER).getStacks();
                arrowStackHandler.deserializeNBT(stack.getOrCreateTag().getCompound("Arrows"));
        });
    }

    private static void serializeArrowsNBT(ItemStack stack, LivingEntity entity) {
        CuriosApi.getCuriosHelper().getCuriosHandler(entity).ifPresent(curios -> {
            IDynamicStackHandler arrowStackHandler = curios.getCurios().get(ARROWS_CURIOS_IDENTIFIER).getStacks();
            stack.getOrCreateTag().put("Arrows", arrowStackHandler.serializeNBT());
            for (int s = 0; s < arrowStackHandler.getSlots(); s++) {arrowStackHandler.setStackInSlot(s, ItemStack.EMPTY);}
        });
    }
*/

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        ICurioItem.super.curioTick(slotContext, stack);
        LivingEntity livingEntity = slotContext.entity();

        CuriosApi.getCuriosInventory(livingEntity).ifPresent(curios -> {
            IDynamicStackHandler stackHandler = curios.getCurios().get(ARROWS_CURIOS_IDENTIFIER).getStacks();
            if (livingEntity.level().isClientSide()) {
                int countdownTicks = stack.getOrCreateTag().getInt("countdown_ticks");
                if (countdownTicks > 0) {
                    stack.getOrCreateTag().putInt("countdown_ticks", countdownTicks - 1);
                }
                else if (ECKeyRegistry.cycleQuiverLeft.isDown() && countdownTicks == 0) {
                    sycleArrows(livingEntity, stackHandler, false);
                    stack.getOrCreateTag().putInt("countdown_ticks", 5);
                }
                else if (ECKeyRegistry.cycleQuiverRight.isDown() && countdownTicks == 0) {
                    sycleArrows(livingEntity, stackHandler, true);
                    stack.getOrCreateTag().putInt("countdown_ticks", 5);
                }
            }
        });
    }

    public void sycleArrows(LivingEntity livingEntity, IItemHandler itemHandler, boolean forward) {
        int arrowSlot = ECVariables.getArrowSlot(livingEntity);
        for (int check = 0; check < this.providedSlots; check++) {
            arrowSlot += forward ? 1 : -1;
            if (arrowSlot >= this.providedSlots) arrowSlot = 0;
            if (arrowSlot < 0) arrowSlot = this.providedSlots - 1;
            if (!itemHandler.getStackInSlot(arrowSlot).isEmpty())  {
                break;
            }
        }
        ECVariables.setArrowSlotTo(livingEntity, arrowSlot);
    }

    @Override
    public boolean makesPiglinsNeutral(ItemStack stack, LivingEntity wearer) {
        return this.material.getName().equals("Gold");
    }

    /*
    @Override
    public CompoundTag getShareTag(ItemStack stack) {
        CompoundTag nbt = super.getShareTag(stack);
        if (nbt != null)
            stack.getCapability(ForgeCapabilities.ITEM_HANDLER, null).ifPresent(capability -> nbt.put("Inventory", ((ItemStackHandler) capability).serializeNBT()));
        return nbt;
    }

    @Override
    public void readShareTag(ItemStack stack, @Nullable CompoundTag nbt) {
        super.readShareTag(stack, nbt);
        if (nbt != null)
            stack.getCapability(ForgeCapabilities.ITEM_HANDLER, null).ifPresent(capability -> ((ItemStackHandler) capability).deserializeNBT((CompoundTag) nbt.get("Inventory")));
    }*/
}
