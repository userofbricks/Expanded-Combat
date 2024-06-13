package com.userofbricks.expanded_combat.item;

import com.userofbricks.expanded_combat.client.renderer.QuiverRenderer;
import com.userofbricks.expanded_combat.data.material.Material;
import com.userofbricks.expanded_combat.init.ECKeyRegistry;
import com.userofbricks.expanded_combat.init.ItemDataComponents;
import com.userofbricks.expanded_combat.network.ECVariables;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.BundleItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.BundleContents;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.items.IItemHandler;
import org.apache.commons.lang3.math.Fraction;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.client.ICurioRenderer;
import top.theillusivec4.curios.api.type.capability.ICurioItem;
import top.theillusivec4.curios.api.type.inventory.IDynamicStackHandler;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

import static com.userofbricks.expanded_combat.ExpandedCombat.ARROWS_CURIOS_IDENTIFIER;
import static com.userofbricks.expanded_combat.init.DataAttachments.ARROW_SLOT;
import static com.userofbricks.expanded_combat.init.ItemDataComponents.CHARGE;
import static com.userofbricks.expanded_combat.init.ItemDataComponents.COOL_DOWN;
import static net.minecraft.core.component.DataComponents.BUNDLE_CONTENTS;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class ECQuiverItem extends BundleItem implements ICurioItem, IMaterialItem {
    public final Layer[] QUIVER_TEXTURE_LAYERS;

    public final Holder.Reference<Material> material;
    public ECQuiverItem(Properties properties, Holder.Reference<Material> material, Layer... layers) {
        super(properties.component(BUNDLE_CONTENTS, BundleContents.EMPTY).component(COOL_DOWN, 0));
        this.QUIVER_TEXTURE_LAYERS = layers;
        this.material = material;
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        ICurioItem.super.curioTick(slotContext, stack);
        LivingEntity livingEntity = slotContext.entity();

        if (livingEntity.level().isClientSide()) {
            int countdownTicks = stack.getOrDefault(COOL_DOWN, 0);
            if (countdownTicks > 0) {
                stack.set(COOL_DOWN, countdownTicks - 1);
            }
            else if (ECKeyRegistry.cycleQuiverLeft.isDown() && countdownTicks == 0) {
                sycleArrows(livingEntity, stack, false);
                stack.set(COOL_DOWN, 5);
            }
            else if (ECKeyRegistry.cycleQuiverRight.isDown() && countdownTicks == 0) {
                sycleArrows(livingEntity, stack, true);
                stack.set(COOL_DOWN, 5);
            }
        }
    }

    public void sycleArrows(LivingEntity livingEntity, ItemStack stack, boolean forward) {
        BundleContents contents = stack.getOrDefault(BUNDLE_CONTENTS, BundleContents.EMPTY);
        List<ItemStack> items = (List<ItemStack>) contents.items();
        if (!items.isEmpty()) {
            int arrowSlot = livingEntity.getData(ARROW_SLOT);
            arrowSlot += forward ? 1 : -1;
            if (arrowSlot >= items.size()) arrowSlot = 0;
            if (arrowSlot < 0) arrowSlot = items.size() - 1;
            livingEntity.setData(ARROW_SLOT, arrowSlot);
        }
    }

    public Material getMaterial() {
        return material.value();
    }
    public Supplier<ICurioRenderer> getQuiverRenderer() {
        return QuiverRenderer::new;
    }

    public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
        return true;
    }



    @Override
    public boolean overrideStackedOnOther(ItemStack pStack, Slot pSlot, ClickAction pAction, Player pPlayer) {
        if (pStack.getCount() != 1 || pAction != ClickAction.SECONDARY) {
            return false;
        } else {
            BundleContents bundlecontents = pStack.get(BUNDLE_CONTENTS);
            if (bundlecontents == null) {
                return false;
            } else {
                ItemStack itemstack = pSlot.getItem();
                MutableQuiverContents bundlecontents$mutable = new MutableQuiverContents(bundlecontents, getMaterial().offense().quiverSlots());
                if (itemstack.isEmpty()) {
                    playRemoveOneSound(pPlayer);
                    ItemStack itemstack1 = bundlecontents$mutable.removeOne();
                    if (itemstack1 != null) {
                        ItemStack itemstack2 = pSlot.safeInsert(itemstack1);
                        bundlecontents$mutable.tryInsert(itemstack2);
                    }
                } else if (itemstack.getItem().canFitInsideContainerItems() && itemstack.is(ItemTags.ARROWS)) {
                    int i = bundlecontents$mutable.tryTransfer(pSlot, pPlayer);
                    if (i > 0) {
                        playInsertSound(pPlayer);
                    }
                }

                pStack.set(BUNDLE_CONTENTS, bundlecontents$mutable.toImmutable());
                return true;
            }
        }
    }

    @Override
    public boolean overrideOtherStackedOnMe(ItemStack pStack, ItemStack pOther, Slot pSlot, ClickAction pAction, Player pPlayer, SlotAccess pAccess) {
        if (pStack.getCount() != 1) return false;
        if (pAction == ClickAction.SECONDARY && pSlot.allowModification(pPlayer)) {
            BundleContents bundlecontents = pStack.get(BUNDLE_CONTENTS);
            if (bundlecontents == null) {
                return false;
            } else {
                MutableQuiverContents bundlecontents$mutable = new MutableQuiverContents(bundlecontents, getMaterial().offense().quiverSlots());
                if (pOther.isEmpty()) {
                    ItemStack itemstack = bundlecontents$mutable.removeOne();
                    if (itemstack != null) {
                        playRemoveOneSound(pPlayer);
                        pAccess.set(itemstack);
                    }
                } else if (pOther.is(ItemTags.ARROWS)) {
                    int i = bundlecontents$mutable.tryInsert(pOther);
                    if (i > 0) {
                        playInsertSound(pPlayer);
                    }
                } else {
                    return false;
                }

                pStack.set(BUNDLE_CONTENTS, bundlecontents$mutable.toImmutable());
                return true;
            }
        } else {
            return false;
        }
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        return InteractionResultHolder.pass(pPlayer.getItemInHand(pUsedHand));
    }

    @Override
    public int getBarWidth(ItemStack pStack) {
        BundleContents bundlecontents = pStack.getOrDefault(BUNDLE_CONTENTS, BundleContents.EMPTY);
        return Math.min(1 + mulAndTruncateRelToSlots(bundlecontents.weight(), 12), 13);
    }

    public int mulAndTruncateRelToSlots(Fraction pFraction, int pFactor) {
        return ((pFraction.getNumerator() / getMaterial().offense().quiverSlots()) * pFactor) / pFraction.getDenominator();
    }

    public static final class Layer {
        private final String suffix;
        private final boolean dyeable;
        private final Function<ResourceLocation, ResourceLocation> texture;

        public Layer(String suffix, boolean pDyeable) {
            this.suffix = suffix;
            this.dyeable = pDyeable;
            this.texture = resolveTexture();
        }

        public Layer(String suffix) {
            this(suffix, false);
        }
        public Layer() {
            this("", false);
        }
        public Layer(ResourceLocation relativeTexture, boolean pDyeable){
            this.suffix = "";
            this.dyeable = pDyeable;
            this.texture = assetName -> relativeTexture.withPath(p_324187_ -> "textures/models/quiver/" + relativeTexture.getPath() + ".png");
        }

        private Function<ResourceLocation, ResourceLocation> resolveTexture() {
            return assetName -> assetName.withPath(p_324187_ -> "textures/models/quiver/" + assetName.getPath() + "_" + suffix + ".png");
        }

        public ResourceLocation texture(ResourceLocation material) {
            return texture.apply(material);
        }

        public boolean dyeable() {
            return dyeable;
        }
    }

    public static class MutableQuiverContents {
        private final int stacks;
        private final List<ItemStack> items;
        private Fraction weight;

        public MutableQuiverContents(BundleContents pContents, int stacks) {
            this.items = new ArrayList<>((List<ItemStack>)pContents.items());
            this.weight = pContents.weight();
            this.stacks = stacks;
        }

        public MutableQuiverContents clearItems() {
            items.clear();
            weight = Fraction.ZERO;
            return this;
        }

        private int findStackIndex(ItemStack pStack) {
            if (!pStack.isStackable()) {
                return -1;
            } else {
                for (int i = 0; i < items.size(); i++) {
                    if (ItemStack.isSameItemSameComponents(items.get(i), pStack)) {
                        return i;
                    }
                }

                return -1;
            }
        }

        private int getMaxAmountToAdd(ItemStack pStack) {
            Fraction fraction = Fraction.getFraction(stacks, 1).subtract(weight);
            return Math.max(fraction.divideBy(BundleContents.getWeight(pStack)).intValue(), 0);
        }

        public int tryInsert(ItemStack pStack) {
            if (!pStack.isEmpty() && pStack.getItem().canFitInsideContainerItems()) {
                int i = Math.min(pStack.getCount(), getMaxAmountToAdd(pStack));
                if (i == 0) {
                    return 0;
                } else {
                    weight = weight.add(BundleContents.getWeight(pStack).multiplyBy(Fraction.getFraction(i, 1)));
                    int j = findStackIndex(pStack);
                    if (j != -1) {
                        ItemStack itemstack = items.remove(j);
                        ItemStack itemstack1 = itemstack.copyWithCount(itemstack.getCount() + i);
                        pStack.shrink(i);
                        items.add(0, itemstack1);
                    } else {
                        items.add(0, pStack.split(i));
                    }

                    return i;
                }
            } else {
                return 0;
            }
        }

        public int tryTransfer(Slot pSlot, Player pPlayer) {
            ItemStack itemstack = pSlot.getItem();
            int i = getMaxAmountToAdd(itemstack);
            return tryInsert(pSlot.safeTake(itemstack.getCount(), i, pPlayer));
        }

        @Nullable
        public ItemStack removeOne() {
            if (items.isEmpty()) {
                return null;
            } else {
                ItemStack itemstack = items.remove(0).copy();
                weight = weight.subtract(BundleContents.getWeight(itemstack).multiplyBy(Fraction.getFraction(itemstack.getCount(), 1)));
                return itemstack;
            }
        }

        public Fraction weight() {
            return weight;
        }

        public BundleContents toImmutable() {
            return new BundleContents(List.copyOf(items));
        }
    }
}
