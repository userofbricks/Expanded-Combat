package com.userofbricks.expanded_combat.events;

import com.tterrag.registrate.util.entry.RegistryEntry;
import com.userofbricks.expanded_combat.api.material.Material;
import com.userofbricks.expanded_combat.api.material.WeaponMaterial;
import com.userofbricks.expanded_combat.init.MaterialInit;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RegisterItemDecorationsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Map;

import static com.userofbricks.expanded_combat.ExpandedCombat.MODID;

@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEvents {

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void PotionWeaponPotionDurability(RegisterItemDecorationsEvent event) {
        for (Material material : MaterialInit.weaponMaterials) {
            for (Map.Entry<String, RegistryEntry<? extends Item>> weaponEntry : material.getWeapons().entrySet()) {
                WeaponMaterial weaponMaterial = MaterialInit.valueOfWeapon(weaponEntry.getKey());
                if (weaponMaterial != null && weaponMaterial.potionDippable()) {
                    Item potionWeapon = weaponEntry.getValue().get();
                    event.register(potionWeapon, (guiGraphics, font, stack, xOffset, yOffset) -> {
                        CompoundTag compoundTag = stack.getOrCreateTag();
                        if (!(compoundTag.contains("PotionUses") && compoundTag.contains("MaxPotionUses"))) return false;
                        int potionUses = compoundTag.getInt("PotionUses");
                        int maxPotionUses = compoundTag.getInt("MaxPotionUses");

                        if (potionUses != 0) {
                            guiGraphics.pose().pushPose();
                            int l = Math.round(13.0F - (float)(maxPotionUses - potionUses) * 13.0F / (float)maxPotionUses);
                            int i = Mth.hsvToRgb(0.75f, 0.2F, 0.9F);
                            int j = xOffset + 2;
                            int k = yOffset + 13;
                            if (stack.isBarVisible()) {
                                guiGraphics.fill(RenderType.guiOverlay(), j, k, j + 13, k - 1, -16777216);
                                guiGraphics.fill(RenderType.guiOverlay(), j, k, j + l, k -1, i | -16777216);
                            } else {
                                guiGraphics.fill(RenderType.guiOverlay(), j, k, j + 13, k + 2, -16777216);
                                guiGraphics.fill(RenderType.guiOverlay(), j, k, j + l, k + 1, i | -16777216);
                            }
                            guiGraphics.pose().popPose();
                            return true;
                        }

                        return false;
                    });
                }
            }
        }
    }
}
