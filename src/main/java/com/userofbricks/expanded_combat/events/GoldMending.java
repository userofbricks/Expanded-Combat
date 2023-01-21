package com.userofbricks.expanded_combat.events;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerXpEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.tags.ITagManager;

import java.util.List;
import java.util.Map;

@Mod.EventBusSubscriber(modid = "expanded_combat", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class GoldMending
{
    @SubscribeEvent
    public void MendingBonus( PlayerXpEvent.PickupXp event) {
         Player entityIn = event.getEntity();
        ExperienceOrb thisxp = event.getOrb();
         Map.Entry<EquipmentSlot, ItemStack> entry = EnchantmentHelper.getRandomItemWith(Enchantments.MENDING, entityIn, ItemStack::isDamaged);
        if (entry != null) {
             ItemStack itemstack = entry.getValue();
            if (doesGoldMendingContainItem(itemstack)) {
                entityIn.takeXpDelay = 2;
                entityIn.take((Entity)thisxp, 1);
                if (!itemstack.isEmpty() && itemstack.isDamaged()) {
                     int repairedDamage = Math.min(thisxp.value * 4, itemstack.getDamageValue());
                     ExperienceOrb experienceOrbEntity = thisxp;
                    experienceOrbEntity.value -= durabilityToXp(repairedDamage);
                    itemstack.setDamageValue(itemstack.getDamageValue() - repairedDamage);
                }
                if (thisxp.value > 0) {
                    entityIn.giveExperiencePoints(thisxp.value);
                }
                thisxp.kill();
            }
        }
    }

    private int durabilityToXp(int p_20794_) {
        return p_20794_ / 2;
    }
    
    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public static void handleToolTip( ItemTooltipEvent event) {
         ItemStack itemStack = event.getItemStack();
        if (doesGoldMendingContainItem(itemStack)) {
             List<Component> list = event.getToolTip();
            list.add(Component.translatable("tooltip.expanded_combat.mending_bonus").withStyle(ChatFormatting.GREEN)
                    .append(Component.literal(ChatFormatting.GREEN + " +" + ItemStack.ATTRIBUTE_MODIFIER_FORMAT.format(2L))));
        }
    }
    
    public static boolean doesGoldMendingContainItem( ItemStack itemStack) {
        return doesGoldMendingContainItem(itemStack.getItem());
    }
    
    public static boolean doesGoldMendingContainItem( Item item) {
        ITagManager<Item> tagManager = ForgeRegistries.ITEMS.tags();
        assert tagManager != null;
        return tagManager.getTag(tagManager.createTagKey(new ResourceLocation("expanded_combat", "non_ec_mendable_gold"))).contains(item);
    }
}
