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
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import top.theillusivec4.curios.api.CuriosAPI;
import top.theillusivec4.curios.api.capability.ICurio;

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
    public void playEquipSound(LivingEntity livingEntity) {
        livingEntity.world
                .playSound(null, livingEntity.getPosition(), ((GauntletItem)stack.getItem()).getMaterial().getSoundEvent(),
                        SoundCategory.NEUTRAL, 1.0f, 1.0f);
    }

    @Override
    public Multimap<String, AttributeModifier> getAttributeModifiers(String identifier) {

        Multimap<String, AttributeModifier> atts = HashMultimap.create();

        if (CuriosAPI.getCurioTags(stack.getItem()).contains(identifier) && stack.getItem() instanceof GauntletItem) {
            float attackDamage = ((GauntletItem)stack.getItem()).getAttackDamage();
            int armorAmount = ((GauntletItem)stack.getItem()).getArmorAmount();
            atts.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_UUID, "Attack damage bonus", attackDamage + Math.round((attackDamage / 2) * EnchantmentHelper.getEnchantmentLevel(Enchantments.PUNCH, stack)), AttributeModifier.Operation.ADDITION));
            atts.put(SharedMonsterAttributes.ARMOR.getName(), new AttributeModifier(ARMOR_UUID, "Armor bonus", armorAmount, AttributeModifier.Operation.ADDITION));
            atts.put(SharedMonsterAttributes.KNOCKBACK_RESISTANCE.getName(), new AttributeModifier(KNOCKBACK_RESISTANCE_UUID, "Knockback resistance bonus", EnchantmentHelper.getEnchantmentLevel(ECEnchantments.KNOCKBACK_RESISTANCE.get(), stack), AttributeModifier.Operation.ADDITION));
            atts.put(SharedMonsterAttributes.ATTACK_KNOCKBACK.getName(), new AttributeModifier(KNOCKBACK_UUID, "Knockback bonus", EnchantmentHelper.getEnchantmentLevel(Enchantments.KNOCKBACK, stack), AttributeModifier.Operation.ADDITION));
        }
        return atts;
    }

    @Override
    public boolean hasRender(String identifier, LivingEntity livingEntity) {
        return true;
    }

    @Override
    public void render(String identifier, MatrixStack matrixStack, IRenderTypeBuffer renderTypeBuffer, int light,
                       LivingEntity livingEntity, float limbSwing, float limbSwingAmount, float partialTicks,
                       float ageInTicks, float netHeadYaw, float headPitch) {

        if (!(this.model instanceof GauntletModel)) {
            model = new GauntletModel();
        }

        GauntletModel gauntlet = (GauntletModel) this.model;
        ICurio.RenderHelper.followBodyRotations(livingEntity, new BipedModel[]{gauntlet});
        gauntlet.setLivingAnimations(livingEntity, limbSwing, limbSwingAmount, partialTicks);
        gauntlet.setRotationAngles(livingEntity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        IVertexBuilder vertexBuilder = ItemRenderer.getBuffer(renderTypeBuffer, gauntlet.getRenderType(GAUNTLET_TEXTURE), false, stack.hasEffect());
        gauntlet.render(matrixStack, vertexBuilder, light, OverlayTexture.NO_OVERLAY, 1.0f, 1.0f, 1.0f, 1.0f);
/*
        if (stack.hasEffect()) {
            func_215338_a(Minecraft.getInstance().getTextureManager()::bindTexture, livingEntity, gauntlet, limbSwing,
                    limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch);
        } */
    }
/*
    public <T extends Entity> void func_215338_a(Consumer<ResourceLocation> p_215338_0_, T p_215338_1_,
                                                 EntityModel<T> p_215338_2_, float p_215338_3_, float p_215338_4_,
                                                 float p_215338_5_, float p_215338_6_, float p_215338_7_,
                                                 float p_215338_8_) {
        float f = (float)p_215338_1_.ticksExisted + p_215338_5_;
        p_215338_0_.accept(ENCHANTED_ITEM_GLINT_RES);
        GameRenderer gamerenderer = Minecraft.getInstance().gameRenderer;
        gamerenderer.setupFogColor(true);
        GlStateManager.enableBlend();
        GlStateManager.depthFunc(514);
        GlStateManager.depthMask(false);
        GlStateManager.color4f(0.5F, 0.5F, 0.5F, 1.0F);

        for(int i = 0; i < 2; ++i) {
            GlStateManager.disableLighting();
            GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_COLOR, GlStateManager.DestFactor.ONE);
            GlStateManager.color4f(0.38F, 0.19F, 0.608F, 1.0F);
            GlStateManager.matrixMode(5890);
            GlStateManager.loadIdentity();
            float f3 = 0.33333334F;
            GlStateManager.scalef(f3, f3, f3);
            GlStateManager.rotatef(30.0F - (float)i * 60.0F, 0.0F, 0.0F, 1.0F);
            GlStateManager.translatef(0.0F, f * (0.001F + (float)i * 0.003F) * 20.0F, 0.0F);
            GlStateManager.matrixMode(5888);
            p_215338_2_.render(p_215338_1_, p_215338_3_, p_215338_4_, p_215338_6_, p_215338_7_, p_215338_8_);
            GlStateManager.blendFunc(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        }

        GlStateManager.matrixMode(5890);
        GlStateManager.loadIdentity();
        GlStateManager.matrixMode(5888);
        GlStateManager.enableLighting();
        GlStateManager.depthMask(true);
        GlStateManager.depthFunc(515);
        GlStateManager.disableBlend();
        gamerenderer.setupFogColor(false);
    } */

    @Override
    public boolean canRightClickEquip() {
        return true;
    }
}
