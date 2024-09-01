package com.userofbricks.expanded_combat.item;

import com.userofbricks.expanded_combat.ExpandedCombat;
import com.userofbricks.expanded_combat.api.material.Material;
import com.userofbricks.expanded_combat.client.renderer.QuiverRenderer;
import com.userofbricks.expanded_combat.init.ECKeyRegistry;
import com.userofbricks.expanded_combat.network.ECVariables;
import net.minecraft.ChatFormatting;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.inventory.tooltip.BundleTooltip;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.IItemHandler;
import org.jetbrains.annotations.NotNull;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.client.ICurioRenderer;
import top.theillusivec4.curios.api.type.capability.ICurioItem;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;
import top.theillusivec4.curios.api.type.inventory.IDynamicStackHandler;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static com.userofbricks.expanded_combat.ExpandedCombat.ARROWS_CURIOS_IDENTIFIER;
@ParametersAreNonnullByDefault
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

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        ICurioItem.super.curioTick(slotContext, stack);
        LivingEntity livingEntity = slotContext.entity();

        CuriosApi.getCuriosInventory(livingEntity).ifPresent(curios -> {
            if (livingEntity.level().isClientSide()) {
                int countdownTicks = stack.getOrCreateTag().getInt("countdown_ticks");
                if (countdownTicks > 0) {
                    stack.getOrCreateTag().putInt("countdown_ticks", countdownTicks - 1);
                }
                else if (ECKeyRegistry.cycleQuiverLeft.isDown() && countdownTicks == 0) {
                    sycleArrows(livingEntity, false, numberOfArrowStacks(stack));
                    stack.getOrCreateTag().putInt("countdown_ticks", 5);
                }
                else if (ECKeyRegistry.cycleQuiverRight.isDown() && countdownTicks == 0) {
                    sycleArrows(livingEntity, true, numberOfArrowStacks(stack));
                    stack.getOrCreateTag().putInt("countdown_ticks", 5);
                }
            }
        });
    }

    @Override
    public  void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        Optional<ICuriosItemHandler> curiosItemHandlerOptional = CuriosApi.getCuriosInventory(slotContext.entity()).resolve();
        if (curiosItemHandlerOptional.isEmpty())  return;

        IDynamicStackHandler arrowStackHandler = curiosItemHandlerOptional.get().getCurios().get(ARROWS_CURIOS_IDENTIFIER).getStacks();
        for (int i = 0; i < arrowStackHandler.getSlots(); i++) {
            ItemStack arrow = arrowStackHandler.getStackInSlot(i);
            if (arrow.isEmpty()) continue;
            int added = add(stack, arrow.copy());
            arrow.shrink(added);
            if (added < arrow.getCount()) break;
        }
    }

    public void sycleArrows(LivingEntity livingEntity, boolean forward, int providedSlots) {
        int arrowSlot = ECVariables.getArrowSlot(livingEntity);
        arrowSlot += forward ? 1 : -1;
        if (arrowSlot >= providedSlots) arrowSlot = 0;
        if (arrowSlot < 0) arrowSlot = providedSlots - 1;

        ECVariables.setArrowSlotTo(livingEntity, arrowSlot);
    }

    @Override
    public boolean makesPiglinsNeutral(ItemStack stack, LivingEntity wearer) {
        return this.material.getName().equals("Gold");
    }


    ////////START BUNDLE FUNCTIONALITY////////

    public float getFullnessDisplay(ItemStack p_150767_) {
        return (float)getContentWeight(p_150767_) / maxFullness();
    }

    private int maxFullness() {
        return 64 * providedSlots;
    }

    public boolean overrideStackedOnOther(ItemStack quiver, Slot slot, ClickAction clickAction, Player player) {
        if (quiver.getCount() == 1 && clickAction == ClickAction.SECONDARY) {
            ItemStack itemstack = slot.getItem();
            if (itemstack.isEmpty()) {
                playRemoveOneSound(player);
                removeOne(quiver).ifPresent((p_150740_) -> {
                    add(quiver, slot.safeInsert(p_150740_));
                });
            } else if (itemstack.getItem().canFitInsideContainerItems() && itemstack.is(ItemTags.ARROWS)) {
                int i = (maxFullness() - getContentWeight(quiver)) / getWeight(itemstack);
                int j = add(quiver, slot.safeTake(itemstack.getCount(), i, player));
                if (j > 0) {
                    playInsertSound(player);
                }
            }

            return true;
        } else {
            return false;
        }
    }

    public boolean overrideOtherStackedOnMe(ItemStack quiver, ItemStack stackInHand, Slot slot, ClickAction clickAction, Player player, SlotAccess slotAccess) {
        if (quiver.getCount() != 1) {
            return false;
        } else if (clickAction == ClickAction.SECONDARY && slot.allowModification(player)) {
            if (stackInHand.isEmpty()) {
                removeOne(quiver).ifPresent((p_186347_) -> {
                    playRemoveOneSound(player);
                    slotAccess.set(p_186347_);
                });
            } else {
                int i = add(quiver, stackInHand);
                if (i > 0) {
                    playInsertSound(player);
                    stackInHand.shrink(i);
                }
            }

            return true;
        } else {
            return false;
        }
    }

    public boolean isBarVisible(ItemStack quiver) {
        return getContentWeight(quiver) > 0;
    }

    public int getBarWidth(ItemStack quiver) {
        return Math.min(1 + 12 * getContentWeight(quiver) / maxFullness(), 13);
    }

    public int getBarColor(ItemStack quiver) {
        return Mth.color(0.4F, 0.4F, 1.0F);
    }

    public static int add(ItemStack quiver, ItemStack stackToAdd) {
        if (!stackToAdd.isEmpty() && stackToAdd.getItem().canFitInsideContainerItems() && stackToAdd.is(ItemTags.ARROWS)) {
            CompoundTag compoundtag = quiver.getOrCreateTag();
            if (!compoundtag.contains("Items")) {
                compoundtag.put("Items", new ListTag());
            }

            int i = getContentWeight(quiver);
            int j = getWeight(stackToAdd);
            int k = Math.min(stackToAdd.getCount(), (((ECQuiverItem) quiver.getItem()).maxFullness() - i) / j);
            if (k == 0) {
                return 0;
            } else {
                ListTag listtag = compoundtag.getList("Items", 10);
                Optional<CompoundTag> optional = getMatchingItem(stackToAdd, listtag);
                if (optional.isPresent()) {
                    CompoundTag compoundtag1 = optional.get();
                    ItemStack itemstack = ItemStack.of(compoundtag1);

                    int l = Math.min(itemstack.getMaxStackSize() - itemstack.getCount(), k);
                    itemstack.grow(l);
                    itemstack.save(compoundtag1);
                    listtag.remove(compoundtag1);
                    listtag.add(0, compoundtag1);
                    int m = k - l;
                    if (m > 0) {
                        ItemStack itemstack1 = stackToAdd.copyWithCount(m);
                        CompoundTag compoundtag2 = new CompoundTag();
                        itemstack1.save(compoundtag2);
                        listtag.add(0, compoundtag2);
                    }
                } else {
                    ItemStack itemstack1 = stackToAdd.copyWithCount(k);
                    CompoundTag compoundtag2 = new CompoundTag();
                    itemstack1.save(compoundtag2);
                    listtag.add(0, compoundtag2);
                }

                return k;
            }
        } else {
            return 0;
        }
    }

    public static void remove(ItemStack quiver, ItemStack stackToRemove) {
        if (!stackToRemove.isEmpty() && stackToRemove.getItem().canFitInsideContainerItems() && stackToRemove.is(ItemTags.ARROWS)) {
            CompoundTag compoundtag = quiver.getOrCreateTag();
            if (!compoundtag.contains("Items")) {
                return;
            }

            ListTag listtag = compoundtag.getList("Items", 10);
            for (int slot = 0; slot < listtag.size(); slot++) {
                CompoundTag arrowTag = listtag.getCompound(slot);
                if (ItemStack.isSameItemSameTags(ItemStack.of(arrowTag), stackToRemove)) {
                    ItemStack itemstack = ItemStack.of(arrowTag);
                    itemstack.shrink(stackToRemove.getCount());
                    itemstack.save(arrowTag);
                }
            }
        }
    }

    private static Optional<CompoundTag> getMatchingItem(ItemStack stack, ListTag p_150758_) {
        Optional<CompoundTag> var10000;
        if (stack.is(Items.BUNDLE)) {
            var10000 = Optional.empty();
        } else {
            Stream<Tag> var2 = p_150758_.stream();
            Objects.requireNonNull(CompoundTag.class);
            var2 = var2.filter(CompoundTag.class::isInstance);
            Objects.requireNonNull(CompoundTag.class);
            var10000 = var2.map(CompoundTag.class::cast).filter((p_186350_) -> ItemStack.isSameItemSameTags(ItemStack.of(p_186350_), stack)).findFirst();
        }

        return var10000;
    }

    private static int getWeight(ItemStack stack) {
        if (stack.is(ItemTags.ARROWS)) {
            return 64 / stack.getMaxStackSize();
        } else {
            return 1000000;
        }
    }

    private static int getContentWeight(ItemStack quiver) {
        return getContents(quiver).mapToInt((stack) -> getWeight(stack) * stack.getCount()).sum();
    }

    public static Optional<ItemStack> removeOne(ItemStack quiver) {
        CompoundTag compoundtag = quiver.getOrCreateTag();
        if (!compoundtag.contains("Items")) {
            return Optional.empty();
        } else {
            ListTag listtag = compoundtag.getList("Items", 10);
            if (listtag.isEmpty()) {
                return Optional.empty();
            } else {
                CompoundTag firstInList = listtag.getCompound(0);
                ItemStack itemstack = ItemStack.of(firstInList);
                listtag.remove(0);
                if (listtag.isEmpty()) {
                    quiver.removeTagKey("Items");
                }

                return Optional.of(itemstack);
            }
        }
    }

    public static Stream<ItemStack> getContents(ItemStack quiver) {
        CompoundTag compoundtag = quiver.getTag();
        if (compoundtag == null) {
            return Stream.empty();
        } else {
            ListTag listtag = compoundtag.getList("Items", 10);
            Stream<Tag> tagStream = listtag.stream();
            Objects.requireNonNull(CompoundTag.class);
            return tagStream.map(CompoundTag.class::cast).map(ItemStack::of);
        }
    }

    public static int numberOfArrowStacks(ItemStack quiver) {
        return getContents(quiver).toList().size();
    }

    public @NotNull Optional<TooltipComponent> getTooltipImage(ItemStack p_150775_) {
        NonNullList<ItemStack> nonnulllist = NonNullList.create();
        Stream<ItemStack> var10000 = getContents(p_150775_);
        Objects.requireNonNull(nonnulllist);
        var10000.forEach(nonnulllist::add);
        return Optional.of(new BundleTooltip(nonnulllist, getContentWeight(p_150775_)));
    }

    public void appendHoverText(ItemStack p_150749_, @Nullable Level p_150750_, List<Component> p_150751_, TooltipFlag p_150752_) {
        p_150751_.add(Component.translatable("item.minecraft.bundle.fullness", getContentWeight(p_150749_), maxFullness()).withStyle(ChatFormatting.GRAY));
    }

    public void onDestroyed(ItemEntity p_150728_) {
        ItemUtils.onContainerDestroyed(p_150728_, getContents(p_150728_.getItem()));
    }

    private void playRemoveOneSound(Entity p_186343_) {
        p_186343_.playSound(SoundEvents.BUNDLE_REMOVE_ONE, 0.8F, 0.8F + p_186343_.level().getRandom().nextFloat() * 0.4F);
    }

    public void playInsertSound(Entity p_186352_) {
        p_186352_.playSound(SoundEvents.BUNDLE_INSERT, 0.8F, 0.8F + p_186352_.level().getRandom().nextFloat() * 0.4F);
    }
}
