package com.userofbricks.expanded_combat.item;

import com.userofbricks.expanded_combat.client.renderer.QuiverRenderer;
import com.userofbricks.expanded_combat.data.material.Material;
import com.userofbricks.expanded_combat.init.ECKeyRegistry;
import com.userofbricks.expanded_combat.network.ECVariables;
import net.minecraft.core.Holder;
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

import java.util.function.Function;
import java.util.function.Supplier;

import static com.userofbricks.expanded_combat.ExpandedCombat.ARROWS_CURIOS_IDENTIFIER;

public class ECQuiverItem extends Item implements ICurioItem {
    public final Layer[] QUIVER_TEXTURE_LAYERS;
    public final int providedSlots;

    public final Holder.Reference<Material> material;
    public ECQuiverItem(Properties properties, Holder.Reference<Material> material, Layer... layers) {
        super(properties);
        this.QUIVER_TEXTURE_LAYERS = layers;
        this.providedSlots = material.getConfig().quiverSlots;
        this.material = material;
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

        CuriosApi.getCuriosHelper().getCuriosHandler(livingEntity).ifPresent(curios -> {
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

    public Material getMaterial() {
        return material.value();
    }

    @Override
    public boolean makesPiglinsNeutral(ItemStack stack, LivingEntity wearer) {
        return this.material.getName().equals("Gold");
    }

    public static final class Layer {
        private final String suffix;
        private final boolean dyeable;
        private final Function<ResourceLocation, ResourceLocation> texture;

        public Layer(String suffix, boolean pDyeable) {
            this.suffix = suffix;
            this.dyeable = pDyeable;
            this.texture = this.resolveTexture();
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
            return this.dyeable;
        }
    }
}
