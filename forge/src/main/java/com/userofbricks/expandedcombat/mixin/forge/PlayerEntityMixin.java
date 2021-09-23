package com.userofbricks.expandedcombat.mixin.forge;

import com.userofbricks.expandedcombat.forge.ExpandedCombatForge;
import com.userofbricks.expandedcombat.registries.ECAttributes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;

import java.util.Objects;

@Mixin(value = { Player.class }, priority = 1100)
public abstract class PlayerEntityMixin extends LivingEntity
{
    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType_1, Level world_1) {
        super(entityType_1, world_1);
    }

    @Inject(method = { "getProjectile" }, at = { @At("HEAD") }, cancellable = true)
    private void checkQuiver(ItemStack shootable, CallbackInfoReturnable<ItemStack> cir) {
        if (!(shootable.getItem() instanceof ProjectileWeaponItem)) {
            return;
        }
        ItemStack quiverStack = CuriosApi.getCuriosHelper().findEquippedCurio(ExpandedCombatForge.quiver_predicate, this).map(stringIntegerItemStackImmutableTriple -> stringIntegerItemStackImmutableTriple.right).orElse(ItemStack.EMPTY);
        if (!quiverStack.isEmpty()) {
            CuriosApi.getCuriosHelper().getCuriosHandler(this).map(ICuriosItemHandler::getCurios).map(stringICurioStacksHandlerMap -> stringICurioStacksHandlerMap.get("arrows")).map(ICurioStacksHandler::getStacks).ifPresent(arrowHandler -> {
                ItemStack stack = CuriosApi.getCuriosHelper().findEquippedCurio(ExpandedCombatForge.arrow_predicate, this).map(stringIntegerItemStackImmutableTriple -> stringIntegerItemStackImmutableTriple.right).orElse(ItemStack.EMPTY);
                if (!stack.isEmpty()) {
                    cir.setReturnValue(stack);
                }
            });
        }
    }

    @ModifyConstant(method = { "attack" }, constant = { @Constant(doubleValue = 9.0) })
    private double getAttackReachSquared( double value) {
        double attackReachValue = 3.0;
        if (ForgeRegistries.ATTRIBUTES.containsKey(new ResourceLocation("dungeons_gear:attack_reach"))) {
            attackReachValue = this.getAttributeValue(Objects.requireNonNull(ForgeRegistries.ATTRIBUTES.getValue(new ResourceLocation("dungeons_gear:attack_reach"))));
        }
        else {
            attackReachValue = this.getAttributeValue(ECAttributes.ATTACK_REACH.get());
        }
        return attackReachValue * attackReachValue;
    }

    @Inject(method = { "createAttributes" }, at = { @At("RETURN") })
    private static void initAttributes( CallbackInfoReturnable<AttributeSupplier.Builder> ci) {
        if (ForgeRegistries.ATTRIBUTES.containsKey(new ResourceLocation("dungeons_gear:attack_reach"))) {
            ci.getReturnValue().add(Objects.requireNonNull(ForgeRegistries.ATTRIBUTES.getValue(new ResourceLocation("dungeons_gear:attack_reach"))));
        }
        else {
            ci.getReturnValue().add(ECAttributes.ATTACK_REACH.get());
        }
    }
}