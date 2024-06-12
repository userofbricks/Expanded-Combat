package com.userofbricks.expanded_combat.data_components;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.userofbricks.expanded_combat.data.material.Material;
import com.userofbricks.expanded_combat.datagen.LangStrings;
import com.userofbricks.expanded_combat.init.Materials;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
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
import java.util.Optional;
import java.util.function.Consumer;

@ParametersAreNonnullByDefault
public class ShieldMaterials implements TooltipProvider {
    public static final Codec<ShieldMaterials> CODEC = RecordCodecBuilder.create(
            shieldMaterialsInstance -> shieldMaterialsInstance.group(
                            Material.HOLDER_CODEC.fieldOf("ul_material").forGetter(ShieldMaterials::getULMaterial),
                            Material.HOLDER_CODEC.fieldOf("ur_material").forGetter(ShieldMaterials::getURMaterial),
                            Material.HOLDER_CODEC.fieldOf("dl_material").forGetter(ShieldMaterials::getDLMaterial),
                            Material.HOLDER_CODEC.fieldOf("dr_material").forGetter(ShieldMaterials::getDRMaterial),
                            Material.HOLDER_CODEC.fieldOf("m_material").forGetter(ShieldMaterials::getMMaterial),
                            ExtraCodecs.NON_NEGATIVE_INT.fieldOf("last_repair_number").forGetter(ShieldMaterials::getLastRepairNumber)
                    )
                    .apply(shieldMaterialsInstance, ShieldMaterials::new)
    );
    public static final StreamCodec<RegistryFriendlyByteBuf, ShieldMaterials> STREAM_CODEC = StreamCodec.composite(
            Material.HOLDER_STREAM_CODEC,
            ShieldMaterials::getULMaterial,
            Material.HOLDER_STREAM_CODEC,
            ShieldMaterials::getURMaterial,
            Material.HOLDER_STREAM_CODEC,
            ShieldMaterials::getDLMaterial,
            Material.HOLDER_STREAM_CODEC,
            ShieldMaterials::getDRMaterial,
            Material.HOLDER_STREAM_CODEC,
            ShieldMaterials::getMMaterial,
            ByteBufCodecs.INT,
            ShieldMaterials::getLastRepairNumber,
            ShieldMaterials::new
    );

    public static final ShieldMaterials DEFAULT = new ShieldMaterials(Materials.WOOD_PLANK, Materials.WOOD_PLANK, Materials.WOOD_PLANK, Materials.WOOD_PLANK, Materials.IRON, 0);

    public final Holder<Material>  ULMaterial;
    public final Holder<Material>  URMaterial;
    public final Holder<Material>  DLMaterial;
    public final Holder<Material>  DRMaterial;
    public final Holder<Material>  MMaterial;
    public final int LastRepairNumber;

    public ShieldMaterials(Holder<Material> ULMaterial, Holder<Material> URMaterial, Holder<Material> DLMaterial, Holder<Material> DRMaterial, Holder<Material> MMaterial, int lastRepairNumber) {
        this.ULMaterial = ULMaterial;
        this.URMaterial = URMaterial;
        this.DLMaterial = DLMaterial;
        this.DRMaterial = DRMaterial;
        this.MMaterial = MMaterial;
        LastRepairNumber = lastRepairNumber;
    }


    @Override
    public void addToTooltip(Item.TooltipContext pContext, Consumer<Component> pTooltipAdder, TooltipFlag pTooltipFlag) {
        String ul = ULMaterial.getRegisteredName();
        String ur = URMaterial.getRegisteredName();
        String dl = DLMaterial.getRegisteredName();
        String dr = DRMaterial.getRegisteredName();
        String m = MMaterial.getRegisteredName();
        pTooltipAdder.accept(Component.translatable(LangStrings.UPPER_LEFT_MATERIAL).withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC).append(Component.translatable(LangStrings.SHIELD_MATERIAL_LANG_START + ul).withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC)));
        pTooltipAdder.accept(Component.translatable(LangStrings.UPPER_RIGHT_MATERIAL).withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC).append(Component.translatable(LangStrings.SHIELD_MATERIAL_LANG_START + ur).withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC)));
        pTooltipAdder.accept(Component.translatable(LangStrings.CENTER_MATERIAL).withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC).append(Component.translatable(LangStrings.SHIELD_MATERIAL_LANG_START + m).withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC)));
        pTooltipAdder.accept(Component.translatable(LangStrings.LOWER_LEFT_MATERIAL).withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC).append(Component.translatable(LangStrings.SHIELD_MATERIAL_LANG_START + dl).withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC)));
        pTooltipAdder.accept(Component.translatable(LangStrings.LOWER_RIGHT_MATERIAL).withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC).append(Component.translatable(LangStrings.SHIELD_MATERIAL_LANG_START + dr).withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC)));
    }

    public Holder<Material> getULMaterial() {
        return ULMaterial;
    }

    public Holder<Material> getURMaterial() {
        return URMaterial;
    }

    public Holder<Material> getDLMaterial() {
        return DLMaterial;
    }

    public Holder<Material> getDRMaterial() {
        return DRMaterial;
    }

    public Holder<Material> getMMaterial() {
        return MMaterial;
    }

    public int getLastRepairNumber() {
        return LastRepairNumber;
    }

    public ShieldMaterials updateLastRepair(int lastRepairNumber) {
        return new ShieldMaterials(ULMaterial, URMaterial, DLMaterial, DRMaterial, MMaterial, lastRepairNumber);
    }

    public boolean canReplaceUL(Holder<Material> replacement) {
        Optional<List<ResourceLocation>> canReplace = replacement.value().onlyReplaceResource();
        return canReplace.map(resourceLocations -> resourceLocations.contains(new ResourceLocation(ULMaterial.getRegisteredName()))).orElse(true);
    }
    public boolean canReplaceUR(Holder<Material> replacement) {
        Optional<List<ResourceLocation>> canReplace = replacement.value().onlyReplaceResource();
        return canReplace.map(resourceLocations -> resourceLocations.contains(new ResourceLocation(URMaterial.getRegisteredName()))).orElse(true);
    }
    public boolean canReplaceDL(Holder<Material> replacement) {
        Optional<List<ResourceLocation>> canReplace = replacement.value().onlyReplaceResource();
        return canReplace.map(resourceLocations -> resourceLocations.contains(new ResourceLocation(DLMaterial.getRegisteredName()))).orElse(true);
    }
    public boolean canReplaceDR(Holder<Material> replacement) {
        Optional<List<ResourceLocation>> canReplace = replacement.value().onlyReplaceResource();
        return canReplace.map(resourceLocations -> resourceLocations.contains(new ResourceLocation(DRMaterial.getRegisteredName()))).orElse(true);
    }
    public boolean canReplaceM(Holder<Material> replacement) {
        Optional<List<ResourceLocation>> canReplace = replacement.value().onlyReplaceResource();
        return canReplace.map(resourceLocations -> resourceLocations.contains(new ResourceLocation(MMaterial.getRegisteredName()))).orElse(true);
    }
}
