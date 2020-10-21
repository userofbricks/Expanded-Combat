package com.userofbricks.expandedcombat.curios;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.userofbricks.expandedcombat.client.renderer.GauntletModel;
import com.userofbricks.expandedcombat.enchentments.ECEnchantments;
import com.userofbricks.expandedcombat.item.GauntletItem;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.capability.ICurio;

import java.util.UUID;

public class GauntletCurio implements ICurio {

    private Object model;
    private final ItemStack stack;
    private final ResourceLocation GAUNTLET_TEXTURE;
    protected static final ResourceLocation ENCHANTED_ITEM_GLINT_RES = new ResourceLocation("textures/misc/enchanted_item_glint.png");
    private static final UUID ATTACK_UUID = UUID.fromString("7ce10414-adcc-4bf2-8804-f5dbd39fadaf");
    private static final UUID ARMOR_UUID = UUID.fromString("38faf191-bf78-4654-b349-cc1f4f1143bf");
    private static final UUID KNOCKBACK_RESISTANCE_UUID = UUID.fromString("b64fd3d6-a9fe-46a1-a972-90e4b0849678");
    private static final UUID KNOCKBACK_UUID = UUID.fromString("a3617883-03fa-4538-a821-7c0a506e8c56");

    public GauntletCurio(ItemStack stack, ResourceLocation gauntlet_texture) {
        this.stack = stack;
        GAUNTLET_TEXTURE = gauntlet_texture;
    }

    @Override
    public void playRightClickEquipSound(LivingEntity livingEntity) {
        livingEntity.world.playSound(null, new BlockPos(livingEntity.getPositionVec()), ((GauntletItem)stack.getItem()).getMaterial().getSoundEvent(), SoundCategory.NEUTRAL, 1.0f, 1.0f);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(String identifier) {

        Multimap<Attribute, AttributeModifier> atts = HashMultimap.create();

        if (CuriosApi.getCuriosHelper().getCurioTags(stack.getItem()).contains(identifier) && stack.getItem() instanceof GauntletItem) {
            float attackDamage = ((GauntletItem)stack.getItem()).getAttackDamage();
            int armorAmount = ((GauntletItem)stack.getItem()).getArmorAmount();
            float knockbackResistance = ((GauntletItem)stack.getItem()).getMaterial().getKnockback_resistance();
            float toughness = ((GauntletItem)stack.getItem()).getMaterial().getToughness();
            atts.put(Attributes.field_233823_f_, new AttributeModifier(ATTACK_UUID, "Attack damage bonus", attackDamage + Math.round((attackDamage / 2) * EnchantmentHelper.getEnchantmentLevel(Enchantments.PUNCH, stack)), AttributeModifier.Operation.ADDITION));
            atts.put(Attributes.field_233826_i_, new AttributeModifier(ARMOR_UUID, "Armor bonus", armorAmount, AttributeModifier.Operation.ADDITION));
            atts.put(Attributes.field_233827_j_, new AttributeModifier(ARMOR_UUID, "Armor Toughness bonus", toughness, AttributeModifier.Operation.ADDITION));
            atts.put(Attributes.field_233820_c_, new AttributeModifier(KNOCKBACK_RESISTANCE_UUID, "Knockback resistance bonus", knockbackResistance + ((float)EnchantmentHelper.getEnchantmentLevel(ECEnchantments.KNOCKBACK_RESISTANCE.get(), stack))/5.0f, AttributeModifier.Operation.ADDITION));
            atts.put(Attributes.field_233824_g_, new AttributeModifier(KNOCKBACK_UUID, "Knockback bonus", EnchantmentHelper.getEnchantmentLevel(Enchantments.KNOCKBACK, stack), AttributeModifier.Operation.ADDITION));
        }
        return atts;
    }

    @Override
    public boolean canRender(String identifier, int index, LivingEntity livingEntity) {
        return true;
    }

    @Override
    public void render(String identifier, int index, MatrixStack matrixStack, IRenderTypeBuffer renderTypeBuffer, int light, LivingEntity livingEntity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {

        if (!(this.model instanceof GauntletModel)) {
            model = new GauntletModel();
        }

        GauntletModel gauntlet = (GauntletModel) this.model;
        ICurio.RenderHelper.followBodyRotations(livingEntity, new BipedModel[]{gauntlet});
        gauntlet.setLivingAnimations(livingEntity, limbSwing, limbSwingAmount, partialTicks);
        gauntlet.setRotationAngles(livingEntity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        IVertexBuilder vertexBuilder = ItemRenderer.getBuffer(renderTypeBuffer, gauntlet.getRenderType(GAUNTLET_TEXTURE), false, stack.hasEffect());
        gauntlet.render(matrixStack, vertexBuilder, light, OverlayTexture.NO_OVERLAY, 1.0f, 1.0f, 1.0f, 1.0f);
    }

    @Override
    public boolean canRightClickEquip() {
        return true;
    }
}
