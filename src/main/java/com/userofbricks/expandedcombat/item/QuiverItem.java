package com.userofbricks.expandedcombat.item;

import top.theillusivec4.curios.api.type.capability.ICurio;
import top.theillusivec4.curios.common.capability.CurioItemCapability;
import com.userofbricks.expandedcombat.curios.QuiverCurio;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item;

public class QuiverItem extends Item
{
    public QuiverItem(final Item.Properties properties) {
        super(properties);
    }
    
    @Nullable
    public ICapabilityProvider initCapabilities(final ItemStack stack, @Nullable final CompoundNBT nbt) {
        return CurioItemCapability.createProvider(new QuiverCurio());
    }
}
