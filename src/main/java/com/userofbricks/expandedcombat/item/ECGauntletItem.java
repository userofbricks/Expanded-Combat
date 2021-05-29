package com.userofbricks.expandedcombat.item;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.userofbricks.expandedcombat.client.renderer.model.GauntletModel;
import com.userofbricks.expandedcombat.enchentments.ECEnchantments;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.item.*;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;
import top.theillusivec4.curios.api.type.capability.ICurioItem;
import net.minecraft.util.ResourceLocation;

import java.util.List;
import java.util.UUID;

public class ECGauntletItem extends Item implements ICurioItem
{
    private final ResourceLocation GAUNTLET_TEXTURE;
    private final IGauntletMaterial material;
    private final float attackDamage;
    protected final int armorAmount;
    private Object model;
    protected static final ResourceLocation ENCHANTED_ITEM_GLINT_RES;
    private static final UUID ATTACK_UUID;
    private static final UUID ARMOR_UUID;
    private static final UUID KNOCKBACK_RESISTANCE_UUID;
    private static final UUID KNOCKBACK_UUID;
    public Boolean hasWeaponInHand = false;
    
    public ECGauntletItem(IGauntletMaterial materialIn, Item.Properties properties) {
        super(properties.defaultDurability(materialIn.getDurability()));
        this.material = materialIn;
        this.GAUNTLET_TEXTURE = new ResourceLocation("expanded_combat", "textures/entity/gauntlet/" + materialIn.getTextureName() + ".png");
        this.attackDamage = materialIn.getAttackDamage();
        this.armorAmount = materialIn.getArmorAmount();
    }
    
    public IGauntletMaterial getMaterial() {
        return this.material;
    }
    
    public int getEnchantmentValue() {
        return this.material.getEnchantability();
    }
    
    public boolean isValidRepairItem(final ItemStack toRepair, final ItemStack repair) {
        return this.material.getRepairMaterial().test(repair) || super.isValidRepairItem(toRepair, repair);
    }

    public float getXpRepairRatio( ItemStack stack) {
        return this.material == GauntletMaterials.gold ? 4.0f : 2.0f;
    }

    public void appendHoverText(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag) {
        if (this.material == GauntletMaterials.gold) {
            list.add(new TranslationTextComponent("tooltip.expanded_combat.mending_bonus").withStyle(TextFormatting.GREEN).append(new StringTextComponent(TextFormatting.GREEN + " +" + ItemStack.ATTRIBUTE_MODIFIER_FORMAT.format(2L))));
        }
    }
    
    public int getArmorAmount() {
        return this.armorAmount;
    }
    
    public float getAttackDamage() {
        return this.attackDamage;
    }
    
    public ResourceLocation getGAUNTLET_TEXTURE() {
        return this.GAUNTLET_TEXTURE;
    }

    @Override
    public boolean makesPiglinsNeutral(ItemStack stack, LivingEntity wearer) {
        return stack.getItem() instanceof ECGauntletItem && ((ECGauntletItem) stack.getItem()).getMaterial() == GauntletMaterials.gold;
    }

    @Override
    public void fillItemCategory(ItemGroup tab, NonNullList<ItemStack> list) {
        if (this.allowdedIn(tab)) {
            ItemStack istack = new ItemStack(this);
            if (this.getMaterial() == GauntletMaterials.steeleaf) {
                istack.enchant(Enchantments.ALL_DAMAGE_PROTECTION, 2);
            }
            list.add(istack);
        }
    }

    public void onEquipFromUse(SlotContext slotContext, ItemStack stack) {
        LivingEntity livingEntity = slotContext.getWearer();
        livingEntity.level.playSound(null, new BlockPos(livingEntity.position()), this.material.getSoundEvent(), SoundCategory.NEUTRAL, 1.0f, 1.0f);
    }

    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        String identifier = slotContext.getIdentifier();
        Multimap<Attribute, AttributeModifier> atts = HashMultimap.create();
        if (CuriosApi.getCuriosHelper().getCurioTags(stack.getItem()).contains(identifier) && stack.getItem() instanceof ECGauntletItem) {
            float attackDamage = ((ECGauntletItem)stack.getItem()).getAttackDamage();
            float nagaDamage = ((ECGauntletItem)stack.getItem()).getMaterial() == GauntletMaterials.naga ? (attackDamage/2.0f*3) : 0;
            float yetiDamage = ((ECGauntletItem)stack.getItem()).getMaterial() == GauntletMaterials.yeti ? (attackDamage/2.0f) : 0;
            int armorAmount = ((ECGauntletItem)stack.getItem()).getArmorAmount();
            float knockbackResistance = ((ECGauntletItem)stack.getItem()).getMaterial().getKnockback_resistance();
            float toughness = ((ECGauntletItem)stack.getItem()).getMaterial().getToughness();
            if (((ECGauntletItem) stack.getItem()).hasWeaponInHand) {
                atts.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(ECGauntletItem.ATTACK_UUID, "Attack damage bonus", (attackDamage + Math.round(attackDamage / 2.0f * EnchantmentHelper.getItemEnchantmentLevel(Enchantments.PUNCH_ARROWS, stack)) + nagaDamage + yetiDamage) / 2d, AttributeModifier.Operation.ADDITION));
            } else {
                atts.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(ECGauntletItem.ATTACK_UUID, "Attack damage bonus", (attackDamage + Math.round(attackDamage / 2.0f * EnchantmentHelper.getItemEnchantmentLevel(Enchantments.PUNCH_ARROWS, stack)) + nagaDamage + yetiDamage), AttributeModifier.Operation.ADDITION));
            }
            atts.put(Attributes.ARMOR, new AttributeModifier(ECGauntletItem.ARMOR_UUID, "Armor bonus", armorAmount, AttributeModifier.Operation.ADDITION));
            atts.put(Attributes.ARMOR_TOUGHNESS, new AttributeModifier(ECGauntletItem.ARMOR_UUID, "Armor Toughness bonus", toughness, AttributeModifier.Operation.ADDITION));
            atts.put(Attributes.KNOCKBACK_RESISTANCE, new AttributeModifier(ECGauntletItem.KNOCKBACK_RESISTANCE_UUID, "Knockback resistance bonus", (knockbackResistance + EnchantmentHelper.getItemEnchantmentLevel(ECEnchantments.KNOCKBACK_RESISTANCE.get(), stack) / 5.0f), AttributeModifier.Operation.ADDITION));
            atts.put(Attributes.ATTACK_KNOCKBACK, new AttributeModifier(ECGauntletItem.KNOCKBACK_UUID, "Knockback bonus", EnchantmentHelper.getItemEnchantmentLevel(Enchantments.KNOCKBACK, stack), AttributeModifier.Operation.ADDITION));
        }
        return atts;
    }

    public boolean canRender( String identifier,  int index,  LivingEntity livingEntity, ItemStack stack) {
        return true;
    }

    public void render(String identifier, int index, MatrixStack matrixStack, IRenderTypeBuffer renderTypeBuffer, int light, LivingEntity livingEntity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, ItemStack stack) {
        if (!(this.model instanceof GauntletModel)) {
            this.model = new GauntletModel();
        }
        GauntletModel gauntlet = (GauntletModel)this.model;
        gauntlet.prepareMobModel(livingEntity, limbSwing, limbSwingAmount, partialTicks);
        gauntlet.setupAnim(livingEntity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        ICurio.RenderHelper.followBodyRotations(livingEntity, gauntlet);
        IVertexBuilder vertexBuilder = ItemRenderer.getFoilBuffer(renderTypeBuffer, gauntlet.renderType(this.GAUNTLET_TEXTURE), false, stack.hasFoil());
        gauntlet.renderToBuffer(matrixStack, vertexBuilder, light, OverlayTexture.NO_OVERLAY, 1.0f, 1.0f, 1.0f, 1.0f);
    }

    /*
    public void renderRightHand(MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int light, AbstractClientPlayerEntity abstractClientPlayerEntity, ResourceLocation gauntlet_texture) {
        if (!(this.model instanceof GauntletModel)) {
            this.model = new GauntletModel();
        }
        GauntletModel gauntlet = (GauntletModel)this.model;
        renderHand(matrixStack, iRenderTypeBuffer, light, abstractClientPlayerEntity, gauntlet.rightArm, gauntlet_texture, gauntlet);
    }

    public void renderLeftHand(MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int light, AbstractClientPlayerEntity abstractClientPlayerEntity, ResourceLocation gauntlet_texture) {
        if (!(this.model instanceof GauntletModel)) {
            this.model = new GauntletModel();
        }
        GauntletModel gauntlet = (GauntletModel)this.model;
        renderHand(matrixStack, iRenderTypeBuffer, light, abstractClientPlayerEntity, gauntlet.leftArm, gauntlet_texture, gauntlet);
    }

    private void renderHand(MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int p_229145_3_, AbstractClientPlayerEntity abstractClientPlayerEntity, ModelRenderer modelRenderer, ResourceLocation gauntlet_texture, GauntletModel gauntletModel) {
        gauntletModel.attackTime = 0.0F;
        gauntletModel.crouching = false;
        gauntletModel.swimAmount = 0.0F;
        gauntletModel.setupAnim(abstractClientPlayerEntity, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        modelRenderer.xRot = 0.0F;
        modelRenderer.render(matrixStack, iRenderTypeBuffer.getBuffer(RenderType.entitySolid(gauntlet_texture)), p_229145_3_, OverlayTexture.NO_OVERLAY);
    }
     */

    public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
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
