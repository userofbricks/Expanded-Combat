package com.userofbricks.expanded_combat.data_components;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.userofbricks.expanded_combat.data.material.Material;
import com.userofbricks.expanded_combat.datagen.LangStrings;
import com.userofbricks.expanded_combat.init.Materials;
import com.userofbricks.expanded_combat.init.Registries;
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
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;

@ParametersAreNonnullByDefault
public record ShieldMaterials(Material ULMaterial, Material URMaterial, Material DLMaterial, Material DRMaterial, Material MMaterial, int lastRepairNumber) implements TooltipProvider {
    public static final Codec<ShieldMaterials> CODEC = RecordCodecBuilder.create(
            shieldMaterialsInstance -> shieldMaterialsInstance.group(
                            Material.HOLDER_CODEC2.fieldOf("ul_material").forGetter(ShieldMaterials::getULMaterial),
                            Material.HOLDER_CODEC2.fieldOf("ur_material").forGetter(ShieldMaterials::getURMaterial),
                            Material.HOLDER_CODEC2.fieldOf("dl_material").forGetter(ShieldMaterials::getDLMaterial),
                            Material.HOLDER_CODEC2.fieldOf("dr_material").forGetter(ShieldMaterials::getDRMaterial),
                            Material.HOLDER_CODEC2.fieldOf("m_material").forGetter(ShieldMaterials::getMMaterial),
                            ExtraCodecs.NON_NEGATIVE_INT.fieldOf("last_repair_number").forGetter(ShieldMaterials::getLastRepairNumber)
                    )
                    .apply(shieldMaterialsInstance, ShieldMaterials::new)
    );
    public static final StreamCodec<RegistryFriendlyByteBuf, ShieldMaterials> STREAM_CODEC = StreamCodec.composite(
            Material.HOLDER_STREAM_CODEC2,
            ShieldMaterials::getULMaterial,
            Material.HOLDER_STREAM_CODEC2,
            ShieldMaterials::getURMaterial,
            Material.HOLDER_STREAM_CODEC2,
            ShieldMaterials::getDLMaterial,
            Material.HOLDER_STREAM_CODEC2,
            ShieldMaterials::getDRMaterial,
            Material.HOLDER_STREAM_CODEC2,
            ShieldMaterials::getMMaterial,
            ByteBufCodecs.INT,
            ShieldMaterials::getLastRepairNumber,
            ShieldMaterials::new
    );

    public static final ShieldMaterials DEFAULT = new ShieldMaterials(Materials.WOOD_PLANK.get(), Materials.WOOD_PLANK.get(), Materials.WOOD_PLANK.get(), Materials.WOOD_PLANK.get(), Materials.IRON.get(), 0);

    @Override
    public void addToTooltip(Item.TooltipContext pContext, Consumer<Component> pTooltipAdder, TooltipFlag pTooltipFlag) {
        String ul = Objects.requireNonNull(Registries.MATERIAL_REGISTRY.getKey(ULMaterial)).toString();
        String ur = Objects.requireNonNull(Registries.MATERIAL_REGISTRY.getKey(URMaterial)).toString();
        String dl = Objects.requireNonNull(Registries.MATERIAL_REGISTRY.getKey(DLMaterial)).toString();
        String dr = Objects.requireNonNull(Registries.MATERIAL_REGISTRY.getKey(DRMaterial)).toString();
        String m = Objects.requireNonNull(Registries.MATERIAL_REGISTRY.getKey(MMaterial)).toString();
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
        Optional<List<ResourceLocation>> canReplace = replacement.onlyReplaceResource();
        return canReplace.map(resourceLocations -> resourceLocations.contains(Registries.MATERIAL_REGISTRY.getKey(ULMaterial))).orElse(true);
    }
    public boolean canReplaceUR(Material replacement) {
        Optional<List<ResourceLocation>> canReplace = replacement.onlyReplaceResource();
        return canReplace.map(resourceLocations -> resourceLocations.contains(Registries.MATERIAL_REGISTRY.getKey(URMaterial))).orElse(true);
    }
    public boolean canReplaceDL(Material replacement) {
        Optional<List<ResourceLocation>> canReplace = replacement.onlyReplaceResource();
        return canReplace.map(resourceLocations -> resourceLocations.contains(Registries.MATERIAL_REGISTRY.getKey(DLMaterial))).orElse(true);
    }
    public boolean canReplaceDR(Material replacement) {
        Optional<List<ResourceLocation>> canReplace = replacement.onlyReplaceResource();
        return canReplace.map(resourceLocations -> resourceLocations.contains(Registries.MATERIAL_REGISTRY.getKey(DRMaterial))).orElse(true);
    }
    public boolean canReplaceM(Material replacement) {
        Optional<List<ResourceLocation>> canReplace = replacement.onlyReplaceResource();
        return canReplace.map(resourceLocations -> resourceLocations.contains(Registries.MATERIAL_REGISTRY.getKey(MMaterial))).orElse(true);
    }
}
