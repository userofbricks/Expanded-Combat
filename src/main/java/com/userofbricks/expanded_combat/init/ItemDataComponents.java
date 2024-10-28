package com.userofbricks.expanded_combat.init;

import com.userofbricks.expanded_combat.data_components.BlockWeaponAnim;
import com.userofbricks.expanded_combat.data_components.ShieldMaterials;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.util.ExtraCodecs;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import static com.userofbricks.expanded_combat.ExpandedCombat.MODID;

public class ItemDataComponents {
    public static final DeferredRegister.DataComponents DATA_COMPONENTS = DeferredRegister.createDataComponents(MODID);
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> CHARGE = DATA_COMPONENTS.registerComponentType(
            "charge", p_331382_ -> p_331382_.persistent(ExtraCodecs.NON_NEGATIVE_INT).networkSynchronized(ByteBufCodecs.VAR_INT)
    );
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> MAX_POTION_USES = DATA_COMPONENTS.registerComponentType(
            "max_potion_uses", p_335177_ -> p_335177_.persistent(ExtraCodecs.NON_NEGATIVE_INT).networkSynchronized(ByteBufCodecs.VAR_INT)
    );
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> POTION_USES = DATA_COMPONENTS.registerComponentType(
            "potion_uses", p_331382_ -> p_331382_.persistent(ExtraCodecs.NON_NEGATIVE_INT).networkSynchronized(ByteBufCodecs.VAR_INT)
    );
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> HITS_TILL_SLAM = DATA_COMPONENTS.registerComponentType(
            "hits_till_slam", p_331382_ -> p_331382_.persistent(ExtraCodecs.NON_NEGATIVE_INT).networkSynchronized(ByteBufCodecs.VAR_INT)
    );
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<BlockWeaponAnim>> BLOCK_WEAPON_ANIM = DATA_COMPONENTS.registerComponentType(
            "blocking_weapon_anim", p_331382_ -> p_331382_.persistent(BlockWeaponAnim.CODEC).networkSynchronized(BlockWeaponAnim.STREAM_CODEC)
    );
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> COOL_DOWN = DATA_COMPONENTS.registerComponentType(
            "cool_down", p_331382_ -> p_331382_.persistent(ExtraCodecs.NON_NEGATIVE_INT).networkSynchronized(ByteBufCodecs.VAR_INT)
    );
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<ShieldMaterials>> SHIELD_MATERIALS = DATA_COMPONENTS.registerComponentType(
            "shield_materials", p_331382_ -> p_331382_.persistent(ShieldMaterials.CODEC).networkSynchronized(ShieldMaterials.STREAM_CODEC)
    );
}
