package com.userofbricks.expandedcombat.item;

import com.userofbricks.expandedcombat.ExpandedCombat;
import com.userofbricks.expandedcombat.curios.GauntletCurio;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

public class GauntletItem extends Item
{
	
	private final ResourceLocation GAUNTLET_TEXTURE;
	private final IGauntletMaterial material;
	private final float attackDamage;
	protected final int armorAmount;
	
	public GauntletItem(IGauntletMaterial materialIn, Properties properties) {
		super(properties.defaultMaxDamage(materialIn.getDurability()));
		this.material = materialIn;
		this.GAUNTLET_TEXTURE = new ResourceLocation(ExpandedCombat.MODID, "textures/entity/gauntlet/" + materialIn.getTextureName() + ".png");
		this.attackDamage = materialIn.getAttackDamage();
		this.armorAmount = materialIn.getArmorAmount();
	}
	
	@Override
	  public ICapabilityProvider initCapabilities(ItemStack stack, CompoundNBT unused) {
		
		return ExpandedCombat.createProvider(new GauntletCurio(stack, ((GauntletItem)stack.getItem()).getGAUNTLET_TEXTURE()));
	}
	
	public IGauntletMaterial getMaterial() {
		return this.material;
	}
	
	public int getItemEnchantability() {
		return this.material.getEnchantability();
	}
	
	public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
		return this.material.getRepairMaterial().test(repair) || super.getIsRepairable(toRepair, repair);
	}
	
	public int getArmorAmount() {
		return this.armorAmount;
	}
	
	public float getAttackDamage() {
		return this.attackDamage;
	}

	public ResourceLocation getGAUNTLET_TEXTURE() {
		return GAUNTLET_TEXTURE;
	}
}

