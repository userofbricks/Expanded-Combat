package com.userofbricks.expandedcombat.events;

import net.minecraft.util.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import java.util.List;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraft.inventory.EquipmentSlotType;
import java.util.Map;
import net.minecraft.entity.item.ExperienceOrbEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.enchantment.Enchantments;
import net.minecraftforge.event.entity.player.PlayerXpEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "expanded_combat", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class GoldMending
{
    @SubscribeEvent
    public void MendingBonus( PlayerXpEvent.PickupXp event) {
         PlayerEntity entityIn = event.getPlayer();
         ExperienceOrbEntity thisxp = event.getOrb();
         Map.Entry<EquipmentSlotType, ItemStack> entry = EnchantmentHelper.getRandomItemWith(Enchantments.MENDING, entityIn, ItemStack::isDamaged);
        if (entry != null) {
             ItemStack itemstack = entry.getValue();
            if (doesGoldMendingContainItem(itemstack)) {
                entityIn.takeXpDelay = 2;
                entityIn.take((Entity)thisxp, 1);
                if (!itemstack.isEmpty() && itemstack.isDamaged()) {
                     int repairedDamage = Math.min(thisxp.value * 4, itemstack.getDamageValue());
                     ExperienceOrbEntity experienceOrbEntity = thisxp;
                    experienceOrbEntity.value -= thisxp.durabilityToXp(repairedDamage);
                    itemstack.setDamageValue(itemstack.getDamageValue() - repairedDamage);
                }
                if (thisxp.value > 0) {
                    entityIn.giveExperiencePoints(thisxp.value);
                }
                thisxp.remove();
            }
        }
    }
    
    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public static void handleToolTip( ItemTooltipEvent event) {
         ItemStack itemStack = event.getItemStack();
        if (doesGoldMendingContainItem(itemStack)) {
             List<ITextComponent> list = event.getToolTip();
            list.add(new StringTextComponent(TextFormatting.GREEN + "Mending Bonus +" + ItemStack.ATTRIBUTE_MODIFIER_FORMAT.format(2L)));
        }
    }
    
    public static boolean doesGoldMendingContainItem( ItemStack itemStack) {
        return doesGoldMendingContainItem(itemStack.getItem());
    }
    
    public static boolean doesGoldMendingContainItem( Item item) {
        return ItemTags.getAllTags().getTagOrEmpty(new ResourceLocation("expanded_combat", "non_ec_mendable_gold")).contains(item);
    }
}
