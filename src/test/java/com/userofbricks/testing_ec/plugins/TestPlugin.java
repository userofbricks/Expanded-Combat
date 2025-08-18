package com.userofbricks.testing_ec.plugins;

import com.userofbricks.expanded_combat.api.material.Material;
import com.userofbricks.expanded_combat.api.material.MaterialBuilder;
import com.userofbricks.expanded_combat.api.registry.ECPlugin;
import com.userofbricks.expanded_combat.api.registry.IExpandedCombatPlugin;
import com.userofbricks.expanded_combat.api.registry.RegistrationHandler;
import com.userofbricks.testing_ec.config.PluginConfig;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;
import net.minecraft.resources.ResourceLocation;

import static com.userofbricks.testing_ec.TestingPlugin.*;


@ECPlugin
public class TestPlugin implements IExpandedCombatPlugin {
    public static Material TEST_MATERIAL;

    @Override
    public ResourceLocation getPluginUid() {
        return ResourceLocation.fromNamespaceAndPath(MODID, "create");
    }

    @Override
    public void registerMaterials(RegistrationHandler registrationHandler) {
        AutoConfig.register(PluginConfig.class, Toml4jConfigSerializer::new);
        CONFIG = AutoConfig.getConfigHolder(PluginConfig.class).getConfig();

        TEST_MATERIAL = registrationHandler.registerMaterial(new MaterialBuilder(REGISTRATE, "Test Material", CONFIG.testMaterial)
                .bow(null, false)
                .crossBow()
                .gauntlet()
                .quiver()
                .shield()
        );
    }
}