package com.userofbricks.expandedcombat.mixin;

import org.apache.commons.lang3.tuple.ImmutableTriple;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import com.userofbricks.expandedcombat.entity.AttributeRegistry;
import java.util.Objects;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import java.util.function.Predicate;
import com.userofbricks.expandedcombat.ExpandedCombat;
import top.theillusivec4.curios.api.CuriosApi;
import net.minecraft.item.ShootableItem;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import net.minecraft.entity.LivingEntity;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;

@Mixin(value = { PlayerEntity.class }, priority = 1100)
public abstract class PlayerEntityMixin extends LivingEntity
{
    protected PlayerEntityMixin( EntityType<? extends LivingEntity> entityType_1,  World world_1) {
        super(entityType_1, world_1);
    }
    
    @Inject(method = { "getProjectile" }, at = { @At("HEAD") }, cancellable = true)
    private void checkQuiver( ItemStack shootable,  CallbackInfoReturnable<ItemStack> cir) {
        if (!(shootable.getItem() instanceof ShootableItem)) {
            return;
        }
        ItemStack quiverStack = CuriosApi.getCuriosHelper().findEquippedCurio(ExpandedCombat.quiver_predicate, this).map(stringIntegerItemStackImmutableTriple -> stringIntegerItemStackImmutableTriple.right).orElse(ItemStack.EMPTY);
        if (!quiverStack.isEmpty()) {
            CuriosApi.getCuriosHelper().getCuriosHandler(this).map(ICuriosItemHandler::getCurios).map(stringICurioStacksHandlerMap -> stringICurioStacksHandlerMap.get("arrows")).map(ICurioStacksHandler::getStacks).ifPresent(arrowHandler -> {
                ItemStack stack = CuriosApi.getCuriosHelper().findEquippedCurio(ExpandedCombat.arrow_predicate, this).map(stringIntegerItemStackImmutableTriple -> stringIntegerItemStackImmutableTriple.right).orElse(ItemStack.EMPTY);
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
            attackReachValue = this.getAttributeValue(AttributeRegistry.ATTACK_REACH.get());
        }
        return attackReachValue * attackReachValue;
    }
    
    @Inject(method = { "createAttributes" }, at = { @At("RETURN") })
    private static void initAttributes( CallbackInfoReturnable<AttributeModifierMap.MutableAttribute> ci) {
        if (ForgeRegistries.ATTRIBUTES.containsKey(new ResourceLocation("dungeons_gear:attack_reach"))) {
            ci.getReturnValue().add(Objects.requireNonNull(ForgeRegistries.ATTRIBUTES.getValue(new ResourceLocation("dungeons_gear:attack_reach"))));
        }
        else {
            ci.getReturnValue().add(AttributeRegistry.ATTACK_REACH.get());
        }
    }
}
