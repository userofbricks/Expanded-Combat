package com.userofbricks.expandedcombat.util;

import com.userofbricks.expandedcombat.forge.ExpandedCombatForge;
import com.userofbricks.expandedcombat.item.ECQuiverItem;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;
import top.theillusivec4.curios.api.CuriosApi;

public class QuiverUtil {
    public static void updateArrowSlotCount(ItemStack quiver, LivingEntity livingEntity) {
        int providedSlots = getQuiverProvidedSlots(quiver.copy());
        CuriosApi.getSlotHelper().setSlotsForType("arrows", livingEntity, providedSlots);
    }

    public static boolean isSpartanQuiver(ItemStack stack) {
        return isSpartanQuiver(stack.getItem());
    }

    public static boolean isSpartanQuiver(Item item) {
        for (SpartanWeponryQuiver quiverType : SpartanWeponryQuiver.values()) {
            if (quiverType.getQuiver() == item) return true;
        }
        return false;
    }

    public static int getQuiverProvidedSlots(ItemStack stack) {
        return getQuiverProvidedSlots(stack.getItem());
    }

    public static int getQuiverProvidedSlots(Item item) {
        if (ExpandedCombatForge.isSpartanWeponryLoaded && isSpartanQuiver(item)) {
            for (SpartanWeponryQuiver quiverType : SpartanWeponryQuiver.values()) {
                if (quiverType.getQuiver() == item) return quiverType.getProvidedSlots();
            }
        } else if (item instanceof ECQuiverItem) {
            return ((ECQuiverItem)item).providedSlots;
        }
        return 0;
    }

    public enum SpartanWeponryQuiver {
        QUIVER_ARROW_SMALL("quiver_arrow_small", 4),
        QUIVER_ARROW_MEDIUM("quiver_arrow_medium", 6),
        QUIVER_ARROW_LARGE("quiver_arrow_large", 9),
        QUIVER_ARROW_HUGE("quiver_arrow_huge", 12),
        QUIVER_BOLT_SMALL("quiver_bolt_small", 4),
        QUIVER_BOLT_MEDIUM("quiver_bolt_medium", 6),
        QUIVER_BOLT_LARGE("quiver_bolt_large", 9),
        QUIVER_BOLT_HUGE("quiver_bolt_huge", 12);
        private final Item quiver;
        private final int providedSlots;

        SpartanWeponryQuiver(String quiver, int providedSlots) {
            this.quiver = ExpandedCombatForge.isSpartanWeponryLoaded ? ForgeRegistries.ITEMS.getValue(new ResourceLocation("spartanweaponry", quiver)) : null;
            this.providedSlots = providedSlots;
        }

        public Item getQuiver() {
            return quiver;
        }

        public int getProvidedSlots() {
            return providedSlots;
        }
    }
}
