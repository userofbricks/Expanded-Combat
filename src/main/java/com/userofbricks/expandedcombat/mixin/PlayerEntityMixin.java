package com.userofbricks.expandedcombat.mixin;

import com.userofbricks.expandedcombat.entity.AttributeRegistry;
import com.userofbricks.expandedcombat.ExpandedCombat;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShootableItem;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.Objects;


@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {
	protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType_1, World world_1) {
		super(entityType_1, world_1);
	}

	@Inject(method = "findAmmo",at = @At("HEAD"),cancellable = true)
	private void checkQuiver(ItemStack shootable, CallbackInfoReturnable<ItemStack> cir) {
		if (!(shootable.getItem() instanceof ShootableItem)) { return; }
		ItemStack stack = CuriosApi.getCuriosHelper().findEquippedCurio(ExpandedCombat.arrow_predicate,(PlayerEntity)(Object)this)
				.map(stringIntegerItemStackImmutableTriple -> stringIntegerItemStackImmutableTriple.right)
				.orElse(ItemStack.EMPTY);
		if (!stack.isEmpty())cir.setReturnValue(stack);
	}


	@ModifyConstant(
			method = "attackTargetEntityWithCurrentItem",
			constant = @Constant(doubleValue = 9.0D)
	)
	private double getAttackReachSquared(double value) {
		double attackReachValue = 3.0d;
		if (ForgeRegistries.ATTRIBUTES.containsKey(new ResourceLocation("dungeons_gear:attack_reach"))) {
			attackReachValue = this.getAttributeValue(Objects.requireNonNull(ForgeRegistries.ATTRIBUTES.getValue(new ResourceLocation("dungeons_gear:attack_reach"))));
		} else {
			attackReachValue = this.getAttributeValue(AttributeRegistry.ATTACK_REACH.get());
		}
		return attackReachValue * attackReachValue;
	}

	@Inject(
			method = "func_234570_el_",
			at = @At("RETURN")
	)
	private static void initAttributes(CallbackInfoReturnable<AttributeModifierMap.MutableAttribute> ci) {
		if (ForgeRegistries.ATTRIBUTES.containsKey(new ResourceLocation("dungeons_gear:attack_reach"))) {
			ci.getReturnValue().createMutableAttribute(Objects.requireNonNull(ForgeRegistries.ATTRIBUTES.getValue(new ResourceLocation("dungeons_gear:attack_reach"))));
		} else {
			ci.getReturnValue().createMutableAttribute(AttributeRegistry.ATTACK_REACH.get());
		}
	}
}
