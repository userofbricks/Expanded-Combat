package com.userofbricks.expanded_combat.data_components;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.userofbricks.expanded_combat.api.material.Material;
import com.userofbricks.expanded_combat.datagen.LangStrings;
import net.minecraft.ChatFormatting;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipProvider;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.function.Consumer;

import static com.userofbricks.expanded_combat.init.ECBasePlugin.IRON;
import static com.userofbricks.expanded_combat.init.ECBasePlugin.WOOD_PLANK;

@ParametersAreNonnullByDefault
public record ShieldMaterials(Material ULMaterial, Material URMaterial, Material DLMaterial, Material DRMaterial, Material MMaterial, int lastRepairNumber) implements TooltipProvider {
    public static final Codec<ShieldMaterials> CODEC = RecordCodecBuilder.create(
            shieldMaterialsInstance -> shieldMaterialsInstance.group(
                            Material.CODEC.fieldOf("ul_material").forGetter(ShieldMaterials::getULMaterial),
                            Material.CODEC.fieldOf("ur_material").forGetter(ShieldMaterials::getURMaterial),
                            Material.CODEC.fieldOf("dl_material").forGetter(ShieldMaterials::getDLMaterial),
                            Material.CODEC.fieldOf("dr_material").forGetter(ShieldMaterials::getDRMaterial),
                            Material.CODEC.fieldOf("m_material").forGetter(ShieldMaterials::getMMaterial),
                            ExtraCodecs.NON_NEGATIVE_INT.fieldOf("last_repair_number").forGetter(ShieldMaterials::getLastRepairNumber)
                    )
                    .apply(shieldMaterialsInstance, ShieldMaterials::new)
    );
    public static final StreamCodec<RegistryFriendlyByteBuf, ShieldMaterials> STREAM_CODEC = StreamCodec.composite(
            Material.STREAM_CODEC,
            ShieldMaterials::getULMaterial,
            Material.STREAM_CODEC,
            ShieldMaterials::getURMaterial,
            Material.STREAM_CODEC,
            ShieldMaterials::getDLMaterial,
            Material.STREAM_CODEC,
            ShieldMaterials::getDRMaterial,
            Material.STREAM_CODEC,
            ShieldMaterials::getMMaterial,
            ByteBufCodecs.INT,
            ShieldMaterials::getLastRepairNumber,
            ShieldMaterials::new
    );

    public static final ShieldMaterials DEFAULT = new ShieldMaterials(WOOD_PLANK, WOOD_PLANK, WOOD_PLANK, WOOD_PLANK, IRON, 0);

    @Override
    public void addToTooltip(Item.TooltipContext pContext, Consumer<Component> pTooltipAdder, TooltipFlag pTooltipFlag) {
        String ul = ULMaterial.id().toString();
        String ur = URMaterial.id().toString();
        String dl = DLMaterial.id().toString();
        String dr = DRMaterial.id().toString();
        String m = MMaterial.id().toString();
        pTooltipAdder.accept(Component.translatable(LangStrings.UPPER_LEFT_MATERIAL).withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC).append(Component.translatable(LangStrings.SHIELD_MATERIAL_LANG_START + ul).withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC)));
        pTooltipAdder.accept(Component.translatable(LangStrings.UPPER_RIGHT_MATERIAL).withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC).append(Component.translatable(LangStrings.SHIELD_MATERIAL_LANG_START + ur).withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC)));
        pTooltipAdder.accept(Component.translatable(LangStrings.CENTER_MATERIAL).withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC).append(Component.translatable(LangStrings.SHIELD_MATERIAL_LANG_START + m).withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC)));
        pTooltipAdder.accept(Component.translatable(LangStrings.LOWER_LEFT_MATERIAL).withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC).append(Component.translatable(LangStrings.SHIELD_MATERIAL_LANG_START + dl).withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC)));
        pTooltipAdder.accept(Component.translatable(LangStrings.LOWER_RIGHT_MATERIAL).withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC).append(Component.translatable(LangStrings.SHIELD_MATERIAL_LANG_START + dr).withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC)));
    }

    public Material getULMaterial() {
        return ULMaterial;
    }

    public Material getURMaterial() {
        return URMaterial;
    }

    public Material getDLMaterial() {
        return DLMaterial;
    }

    public Material getDRMaterial() {
        return DRMaterial;
    }

    public Material getMMaterial() {
        return MMaterial;
    }

    public int getLastRepairNumber() {
        return lastRepairNumber;
    }

    public ShieldMaterials updateLastRepair(int lastRepairNumber) {
        return new ShieldMaterials(ULMaterial, URMaterial, DLMaterial, DRMaterial, MMaterial, lastRepairNumber);
    }

    public boolean canReplaceUL(Material replacement) {
        List<ResourceLocation> canReplace = replacement.crafting().onlyReplaceResource();
        if (canReplace.isEmpty())
            return true;
        return canReplace.contains(ULMaterial.id());
    }
    public boolean canReplaceUR(Material replacement) {
        List<ResourceLocation> canReplace = replacement.crafting().onlyReplaceResource();
        if (canReplace.isEmpty())
            return true;
        return canReplace.contains(URMaterial.id());
    }
    public boolean canReplaceDL(Material replacement) {
        List<ResourceLocation> canReplace = replacement.crafting().onlyReplaceResource();
        if (canReplace.isEmpty())
            return true;
        return canReplace.contains(DLMaterial.id());
    }
    public boolean canReplaceDR(Material replacement) {
        List<ResourceLocation> canReplace = replacement.crafting().onlyReplaceResource();
        if (canReplace.isEmpty())
            return true;
        return canReplace.contains(DRMaterial.id());
    }
    public boolean canReplaceM(Material replacement) {
        List<ResourceLocation> canReplace = replacement.crafting().onlyReplaceResource();
        if (canReplace.isEmpty())
            return true;
        return canReplace.contains(MMaterial.id());
    }
}
