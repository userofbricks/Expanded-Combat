package com.userofbricks.expanded_combat.data_components;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.userofbricks.expanded_combat.data.material.Material;
import net.minecraft.core.Holder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipProvider;

import javax.annotation.ParametersAreNonnullByDefault;
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
}
