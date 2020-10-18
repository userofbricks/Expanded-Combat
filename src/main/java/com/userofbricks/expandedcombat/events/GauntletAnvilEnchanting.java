package com.userofbricks.expandedcombat.events;

import com.userofbricks.expandedcombat.ExpandedCombat;
import com.userofbricks.expandedcombat.enchentments.ECEnchantments;
import com.userofbricks.expandedcombat.item.GauntletItem;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

@Mod.EventBusSubscriber(modid = ExpandedCombat.MODID, bus = Bus.FORGE)
public class GauntletAnvilEnchanting
{
	@SubscribeEvent
	public void GauntletAnvilEnchanting(AnvilUpdateEvent event)
	{
		ItemStack left = event.getLeft();
		ItemStack right = event.getRight();
		ItemStack output = left;
		
		if (left.getItem() instanceof GauntletItem && (right.getItem() instanceof EnchantedBookItem || left.getItem() == right.getItem())) {
			int xpCost = 0;
			int nameCost = 0;
			int maximumCost = event.getCost();
			ItemStack stack1 = left.copy();
			ItemStack stack2 = right.copy();
			Map<Enchantment, Integer> map = EnchantmentHelper.getEnchantments(stack1);
			int j = left.getRepairCost() + (stack2.isEmpty() ? 0 : stack2.getRepairCost());
			boolean flag = stack2.getItem() == Items.ENCHANTED_BOOK && !EnchantedBookItem.getEnchantments(stack2).isEmpty();

			if (stack1.isDamageable() && !flag) {
				int l = left.getMaxDamage() - left.getDamage();
				int i1 = stack2.getMaxDamage() - stack2.getDamage();
				int j1 = i1 + stack1.getMaxDamage() * 12 / 100;
				int k1 = l + j1;
				int l1 = stack1.getMaxDamage() - k1;
				if (l1 < 0) {
					l1 = 0;
				}

				if (l1 < stack1.getDamage()) {
					stack1.setDamage(l1);
					xpCost += 2;
				}
			}

			Map<Enchantment, Integer> map1 = EnchantmentHelper.getEnchantments(stack2);
			boolean flag2 = false;
			boolean flag3 = false;

			for(Enchantment enchantment1 : map1.keySet()) {
				if (enchantment1 != null) {
					int i2 = map.getOrDefault(enchantment1, 0);
					int j2 = map1.get(enchantment1);
					j2 = i2 == j2 ? j2 + 1 : Math.max(j2, i2);
					boolean flag1 = enchantment1.canApply(left);
					if (left.getItem() == Items.ENCHANTED_BOOK) {
						flag1 = true;
					} else if (enchantment1 == Enchantments.PUNCH || enchantment1 == Enchantments.KNOCKBACK) {
						flag1 = true;
					}

					for(Enchantment enchantment : map.keySet()) {
						if (enchantment != enchantment1 && !enchantment1.isCompatibleWith(enchantment)) {
							flag1 = false;
							++xpCost;
						}
					}

					if (!flag1) { flag3 = true; }
					else {
						flag2 = true;
						if (j2 > enchantment1.getMaxLevel()) { j2 = enchantment1.getMaxLevel(); }

						map.put(enchantment1, j2);
						int k3 = 0;
						switch(enchantment1.getRarity()) {
							case COMMON: k3 = 1; break;
							case UNCOMMON: k3 = 2; break;
							case RARE: k3 = 4; break;
							case VERY_RARE: k3 = 8;
						}

						if (flag) { k3 = Math.max(1, k3 / 2); }

						xpCost += k3 * j2;
						if (left.getCount() > 1) { xpCost = 40; }
					}
				}
			}

			ItemStack vanillaOutput;

			if (flag3 && !flag2) {
				event.setOutput(ItemStack.EMPTY);
				event.setCost(0);
				return;
			}

			if (StringUtils.isBlank(event.getName())) {
				if (left.hasDisplayName()) {
					nameCost = 1;
					xpCost += nameCost;
					stack1.clearCustomName();
				}
			} else if (!event.getName().equals(left.getDisplayName().getString())) {
				nameCost = 1;
				xpCost += nameCost;
				stack1.setDisplayName(new StringTextComponent(event.getName()));
			}
			if (flag && !stack1.isBookEnchantable(stack2)) stack1 = ItemStack.EMPTY;

			maximumCost = (j + xpCost);
			if (xpCost <= 0) { stack1 = ItemStack.EMPTY; }

			if (nameCost == xpCost && nameCost > 0 && maximumCost >= 40) { maximumCost = 39; }

			if (maximumCost >= 40) { stack1 = ItemStack.EMPTY; }

			if (!stack1.isEmpty()) {
				int k2 = stack1.getRepairCost();
				if (!stack2.isEmpty() && k2 < stack2.getRepairCost()) {
					k2 = stack2.getRepairCost();
				}

				if (nameCost != xpCost || nameCost == 0) {
					k2 = getNewRepairCost(k2);
				}

				stack1.setRepairCost(k2);
				EnchantmentHelper.setEnchantments(map, stack1);
			}

			event.setOutput(stack1);
			event.setCost(stack1.getRepairCost());
		}
	}

	public static int getNewRepairCost(int oldRepairCost) {
		return oldRepairCost * 2 + 1;
	}
}
