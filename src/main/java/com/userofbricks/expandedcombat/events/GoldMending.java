package com.userofbricks.expandedcombat.events;

import com.userofbricks.expandedcombat.ExpandedCombat;
import com.userofbricks.expandedcombat.item.TagWrappers;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.item.ExperienceOrbEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.data.ForgeItemTagsProvider;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerXpEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

import java.util.*;

@Mod.EventBusSubscriber(modid = ExpandedCombat.MODID, bus = Bus.FORGE)
public class GoldMending {
	@SubscribeEvent
	public void MendingBonus(PlayerXpEvent.PickupXp event) {
		PlayerEntity entityIn = event.getPlayer();
		ExperienceOrbEntity thisxp = event.getOrb();
		Map.Entry<EquipmentSlotType, ItemStack> entry = EnchantmentHelper.getRandomEquippedWithEnchantment(Enchantments.MENDING, entityIn, ItemStack::isDamaged);
		if (entry != null) {
			ItemStack itemstack = entry.getValue();
			if (doesGoldMendingContainItem(itemstack)) {
				entityIn.xpCooldown = 2;
				entityIn.onItemPickup(thisxp, 1);
				if (!itemstack.isEmpty() && itemstack.isDamaged()) {
					int repairedDamage = Math.min(thisxp.xpValue * 4, itemstack.getDamage());
					thisxp.xpValue -= thisxp.durabilityToXp(repairedDamage);
					itemstack.setDamage(itemstack.getDamage() - repairedDamage);
				}

				if (thisxp.xpValue > 0) {
					entityIn.giveExperiencePoints(thisxp.xpValue);
				}

				thisxp.remove();
			}
		}
	}

	@SubscribeEvent
	@OnlyIn(Dist.CLIENT)
	public static void handleToolTip(ItemTooltipEvent event) {
		ItemStack itemStack = event.getItemStack();
		if (doesGoldMendingContainItem(itemStack)) {
			List<ITextComponent> list = event.getToolTip();
			list.add(new StringTextComponent(TextFormatting.GREEN + ("Mending Bonus +" + ItemStack.DECIMALFORMAT.format(2))));
		}
	}


	public static boolean doesGoldMendingContainItem(ItemStack itemStack) {
		return doesGoldMendingContainItem(itemStack.getItem());
	}

	public static boolean doesGoldMendingContainItem(Item item) {
		return ItemTags.getCollection().getTagByID(new ResourceLocation(ExpandedCombat.MODID, "non_ec_mendable_gold"))
				.contains(item);
	}
}
