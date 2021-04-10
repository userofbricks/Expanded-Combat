package com.userofbricks.expandedcombat.item;

import com.userofbricks.expandedcombat.ExpandedCombat;
import com.userofbricks.expandedcombat.curios.QuiverCurio;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import top.theillusivec4.curios.common.capability.CurioItemCapability;

import javax.annotation.Nullable;

public class QuiverItem extends Item {
	public QuiverItem(Properties properties) {
		super(properties);
	}

	@Nullable
	@Override
	public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundNBT nbt) {
		return CurioItemCapability.createProvider(new QuiverCurio());
	}
}
