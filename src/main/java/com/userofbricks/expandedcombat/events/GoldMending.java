package com.userofbricks.expandedcombat.events;

import com.userofbricks.expandedcombat.ExpandedCombat;
import com.userofbricks.expandedcombat.item.TagWrappers;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.item.ExperienceOrbEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerXpEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

import java.util.List;
import java.util.Map;

@Mod.EventBusSubscriber(modid = ExpandedCombat.MODID, bus = Bus.FORGE)
public class GoldMending
{
	@SubscribeEvent
	public void MendingBonus(PlayerXpEvent.PickupXp event) {
		PlayerEntity entityIn = event.getPlayer();
		ExperienceOrbEntity thisxp = event.getOrb();
		Map.Entry<EquipmentSlotType, ItemStack> entry = EnchantmentHelper.getRandomEquippedWithEnchantment(Enchantments.MENDING, entityIn, ItemStack::isDamaged);
		if (entry != null) {
			ItemStack itemstack = entry.getValue();
			if (TagWrappers.NON_EC_MENDABLE_GOLD.contains(itemstack.getItem())) {
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
		if (TagWrappers.NON_EC_MENDABLE_GOLD.contains(itemStack.getItem())) {
			List<ITextComponent> list = event.getToolTip();
			list.add(new StringTextComponent(TextFormatting.GREEN + ("Mending Bonus +" + ItemStack.DECIMALFORMAT.format(2))));
		}
	}
}
