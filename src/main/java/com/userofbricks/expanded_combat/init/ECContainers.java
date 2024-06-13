package com.userofbricks.expanded_combat.init;

import com.userofbricks.expanded_combat.ExpandedCombat;
import com.userofbricks.expanded_combat.client.renderer.gui.screen.inventory.FletchingTableScreen;
import com.userofbricks.expanded_combat.client.renderer.gui.screen.inventory.ShieldSmithingTableScreen;
import com.userofbricks.expanded_combat.inventory.container.FletchingTableMenu;
import com.userofbricks.expanded_combat.inventory.container.ShieldSmithingMenu;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import static com.userofbricks.expanded_combat.ExpandedCombat.MODID;

@EventBusSubscriber(modid = MODID, value = Dist.CLIENT)
public class ECContainers {
    public static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(BuiltInRegistries.MENU, MODID);

    public static final DeferredHolder<MenuType<?>, MenuType<FletchingTableMenu>> FLETCHING = MENU_TYPES.register("ec_fletching", () -> IMenuTypeExtension.create(FletchingTableMenu::new));
    public static final DeferredHolder<MenuType<?>, MenuType<ShieldSmithingMenu>> SHIELD_SMITHING = MENU_TYPES.register("shield_smithing", () -> new MenuType<>(ShieldSmithingMenu::new, FeatureFlags.DEFAULT_FLAGS));

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public static void registerScreensToMenus(RegisterMenuScreensEvent event) {
        event.register(ECContainers.SHIELD_SMITHING.get(), ShieldSmithingTableScreen::new);
        event.register(ECContainers.FLETCHING.get(), FletchingTableScreen::new);
    }
}
