package com.userofbricks.expanded_combat.item;

import com.userofbricks.expanded_combat.client.ECKeyRegistry;
import com.userofbricks.expanded_combat.item.materials.QuiverMaterial;
import com.userofbricks.expanded_combat.network.ECVariables;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;
import top.theillusivec4.curios.api.type.inventory.IDynamicStackHandler;
import top.theillusivec4.curios.common.inventory.container.CuriosContainer;
import top.theillusivec4.curios.common.inventory.container.CuriosContainerProvider;

import javax.annotation.Nullable;
import java.util.Locale;

import static com.userofbricks.expanded_combat.ExpandedCombat.ARROWS_CURIOS_IDENTIFIER;
import static com.userofbricks.expanded_combat.ExpandedCombat.QUIVER_CURIOS_IDENTIFIER;

public class ECQuiverItem extends Item implements ICurioItem {
    private final ResourceLocation QUIVER_TEXTURE;
    public final int providedSlots;
    public ECQuiverItem(QuiverMaterial material, Properties properties) {
        super(properties);
        this.QUIVER_TEXTURE = new ResourceLocation("expanded_combat", "textures/entity/quiver/" + material.getName().toLowerCase(Locale.ROOT).replace(' ', '_') + ".png");
        this.providedSlots = material.getProvidedSlots();
    }

    public ResourceLocation getQUIVER_TEXTURE() {
        return this.QUIVER_TEXTURE;
    }

    public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
        return true;
    }

    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        //create a store function that stores all arrows in quiver nbt
        stack.getOrCreateTag().putInt("expanded_combat:slotIndex", 0);
    }

    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        //create a getting function that puts all stored arrows in arrow slots
        stack.getOrCreateTag().putInt("expanded_combat:slotIndex", 0);
    }


    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        ICurioItem.super.curioTick(slotContext, stack);
        LivingEntity livingEntity = slotContext.entity();

        CuriosApi.getCuriosHelper().getCuriosHandler(livingEntity).ifPresent(curios -> {
            IDynamicStackHandler stackHandler = curios.getCurios().get(ARROWS_CURIOS_IDENTIFIER).getStacks();
            if (livingEntity.level.isClientSide()) {
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
            if (arrowSlot < 0) arrowSlot = this.providedSlots;
            if (!itemHandler.getStackInSlot(arrowSlot).isEmpty())  {
                break;
            }
        }
        ECVariables.setArrowSlotTo(livingEntity, arrowSlot);
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
