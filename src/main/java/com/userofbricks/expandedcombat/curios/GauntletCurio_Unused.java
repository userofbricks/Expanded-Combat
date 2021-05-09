package com.userofbricks.expandedcombat.curios;

import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.ItemRenderer;
import com.userofbricks.expandedcombat.client.renderer.model.GauntletModel;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.userofbricks.expandedcombat.enchentments.ECEnchantments;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.ai.attributes.Attributes;
import top.theillusivec4.curios.api.CuriosApi;
import com.google.common.collect.HashMultimap;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attribute;
import com.google.common.collect.Multimap;
import net.minecraft.util.SoundCategory;
import com.userofbricks.expandedcombat.item.GauntletItem;
import net.minecraft.util.math.BlockPos;
import net.minecraft.entity.LivingEntity;
import java.util.UUID;
import net.minecraft.util.ResourceLocation;
import net.minecraft.item.ItemStack;
import top.theillusivec4.curios.api.type.capability.ICurio;

public class GauntletCurio_Unused implements ICurio
{
    private Object model;
    private final ItemStack stack;
    private final ResourceLocation GAUNTLET_TEXTURE;
    protected static final ResourceLocation ENCHANTED_ITEM_GLINT_RES;
    private static final UUID ATTACK_UUID;
    private static final UUID ARMOR_UUID;
    private static final UUID KNOCKBACK_RESISTANCE_UUID;
    private static final UUID KNOCKBACK_UUID;
    
    public GauntletCurio_Unused(ItemStack stack, ResourceLocation gauntlet_texture) {
        this.stack = stack;
        this.GAUNTLET_TEXTURE = gauntlet_texture;
    }

    public void playRightClickEquipSound( LivingEntity livingEntity) {
        livingEntity.level.playSound(null, new BlockPos(livingEntity.position()), ((GauntletItem)this.stack.getItem()).getMaterial().getSoundEvent(), SoundCategory.NEUTRAL, 1.0f, 1.0f);
    }
    
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers( String identifier) {
        Multimap<Attribute, AttributeModifier> atts = HashMultimap.create();
        if (CuriosApi.getCuriosHelper().getCurioTags(this.stack.getItem()).contains(identifier) && this.stack.getItem() instanceof GauntletItem) {
            float attackDamage = ((GauntletItem)this.stack.getItem()).getAttackDamage();
            int armorAmount = ((GauntletItem)this.stack.getItem()).getArmorAmount();
            float knockbackResistance = ((GauntletItem)this.stack.getItem()).getMaterial().getKnockback_resistance();
            float toughness = ((GauntletItem)this.stack.getItem()).getMaterial().getToughness();
            atts.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(GauntletCurio_Unused.ATTACK_UUID, "Attack damage bonus", (double)(attackDamage + Math.round(attackDamage / 2.0f * EnchantmentHelper.getItemEnchantmentLevel(Enchantments.PUNCH_ARROWS, this.stack))), AttributeModifier.Operation.ADDITION));
            atts.put(Attributes.ARMOR, new AttributeModifier(GauntletCurio_Unused.ARMOR_UUID, "Armor bonus", (double)armorAmount, AttributeModifier.Operation.ADDITION));
            atts.put(Attributes.ARMOR_TOUGHNESS, new AttributeModifier(GauntletCurio_Unused.ARMOR_UUID, "Armor Toughness bonus", (double)toughness, AttributeModifier.Operation.ADDITION));
            atts.put(Attributes.KNOCKBACK_RESISTANCE, new AttributeModifier(GauntletCurio_Unused.KNOCKBACK_RESISTANCE_UUID, "Knockback resistance bonus", (double)(knockbackResistance + EnchantmentHelper.getItemEnchantmentLevel((Enchantment)ECEnchantments.KNOCKBACK_RESISTANCE.get(), this.stack) / 5.0f), AttributeModifier.Operation.ADDITION));
            atts.put(Attributes.ATTACK_KNOCKBACK, new AttributeModifier(GauntletCurio_Unused.KNOCKBACK_UUID, "Knockback bonus", (double)EnchantmentHelper.getItemEnchantmentLevel(Enchantments.KNOCKBACK, this.stack), AttributeModifier.Operation.ADDITION));
        }
        return atts;
    }
    
    public boolean canRender( String identifier,  int index,  LivingEntity livingEntity) {
        return true;
    }
    
    public void render( String identifier,  int index,  MatrixStack matrixStack,  IRenderTypeBuffer renderTypeBuffer,  int light,  LivingEntity livingEntity,  float limbSwing,  float limbSwingAmount,  float partialTicks,  float ageInTicks,  float netHeadYaw,  float headPitch) {
        if (!(this.model instanceof GauntletModel)) {
            this.model = new GauntletModel();
        }
         GauntletModel gauntlet = (GauntletModel)this.model;
        gauntlet.prepareMobModel(livingEntity, limbSwing, limbSwingAmount, partialTicks);
        gauntlet.setupAnim(livingEntity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        ICurio.RenderHelper.followBodyRotations(livingEntity, gauntlet);
        IVertexBuilder vertexBuilder = ItemRenderer.getFoilBuffer(renderTypeBuffer, gauntlet.renderType(this.GAUNTLET_TEXTURE), false, this.stack.hasFoil());
        gauntlet.renderToBuffer(matrixStack, vertexBuilder, light, OverlayTexture.NO_OVERLAY, 1.0f, 1.0f, 1.0f, 1.0f);
    }

    public static void renderRightHand(MatrixStack p_229144_1_, IRenderTypeBuffer p_229144_2_, int p_229144_3_, AbstractClientPlayerEntity p_229144_4_, ResourceLocation gauntlet_texture) {
        GauntletModel gauntlet = new GauntletModel();
        renderHand(p_229144_1_, p_229144_2_, p_229144_3_, p_229144_4_, gauntlet.rightArm, gauntlet_texture, gauntlet);
    }

    public static void renderLeftHand(MatrixStack p_229146_1_, IRenderTypeBuffer p_229146_2_, int p_229146_3_, AbstractClientPlayerEntity p_229146_4_, ResourceLocation gauntlet_texture) {
        GauntletModel gauntlet = new GauntletModel();
        renderHand(p_229146_1_, p_229146_2_, p_229146_3_, p_229146_4_, gauntlet.leftArm, gauntlet_texture, gauntlet);
    }

    private static void renderHand(MatrixStack p_229145_1_, IRenderTypeBuffer p_229145_2_, int p_229145_3_, AbstractClientPlayerEntity p_229145_4_, ModelRenderer p_229145_5_, ResourceLocation gauntlet_texture, GauntletModel gauntletModel) {
        gauntletModel.attackTime = 0.0F;
        gauntletModel.crouching = false;
        gauntletModel.swimAmount = 0.0F;
        gauntletModel.setupAnim(p_229145_4_, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        p_229145_5_.xRot = 0.0F;
        p_229145_5_.render(p_229145_1_, p_229145_2_.getBuffer(RenderType.entitySolid(gauntlet_texture)), p_229145_3_, OverlayTexture.NO_OVERLAY);
    }
    
    public boolean canRightClickEquip() {
        return true;
    }
    
    static {
        ENCHANTED_ITEM_GLINT_RES = new ResourceLocation("textures/misc/enchanted_item_glint.png");
        ATTACK_UUID = UUID.fromString("7ce10414-adcc-4bf2-8804-f5dbd39fadaf");
        ARMOR_UUID = UUID.fromString("38faf191-bf78-4654-b349-cc1f4f1143bf");
        KNOCKBACK_RESISTANCE_UUID = UUID.fromString("b64fd3d6-a9fe-46a1-a972-90e4b0849678");
        KNOCKBACK_UUID = UUID.fromString("a3617883-03fa-4538-a821-7c0a506e8c56");
    }
}
