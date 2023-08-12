package com.userofbricks.expanded_combat.item.materials;

import com.userofbricks.expanded_combat.api.events.registering.MaterialRegistryEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class MaterialInit {
    public static final IForgeRegistry<Material> MATERIALS

    public static List<WeaponMaterial> weaponMaterialConfigs = new ArrayList<>();

    public static List<ShieldToMaterials> shieldToMaterials = new ArrayList<>();

    public static List<Material> materials = new ArrayList<>();
    public static List<Material> gauntletMaterials = new ArrayList<>();
    public static List<Material> shieldMaterials = new ArrayList<>();
    public static List<Material> bowMaterials = new ArrayList<>();
    public static List<Material> crossbowMaterials = new ArrayList<>();
    public static List<Material> arrowMaterials = new ArrayList<>();
    public static List<Material> quiverMaterials = new ArrayList<>();
    public static List<Material> weaponMaterials = new ArrayList<>();

    public static void loadClass() {
        MinecraftForge.EVENT_BUS.post(new MaterialRegistryEvent());
    }
}
