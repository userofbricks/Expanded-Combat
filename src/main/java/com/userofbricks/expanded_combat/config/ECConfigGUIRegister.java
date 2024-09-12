package com.userofbricks.expanded_combat.config;

import com.userofbricks.expanded_combat.init.PluginInit;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.gui.registry.GuiRegistry;
import me.shedaniel.autoconfig.util.Utils;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import me.shedaniel.clothconfig2.gui.entries.SelectionListEntry;
import me.shedaniel.clothconfig2.impl.builders.DropdownMenuBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Stream;

@OnlyIn(Dist.CLIENT)
public class ECConfigGUIRegister {
    private static final GuiRegistry registry = AutoConfig.getGuiRegistry(ECConfig.class);
    private static final ConfigEntryBuilder ENTRY_BUILDER = ConfigEntryBuilder.create();
    private static final Function<ResourceLocation, Component> DEFAULT_NAME_PROVIDER = (t) ->
            Component.translatable(t instanceof SelectionListEntry.Translatable ? ((SelectionListEntry.Translatable)t).getKey() : t.toString());
    public ECConfigGUIRegister(){}

    public static void registerModsPage() {
        registry.registerPredicateProvider((i18n, field, config, defaults, guiProvider) ->
                {
                    List<ResourceLocation> sounds = BuiltInRegistries.SOUND_EVENT.keySet().stream().toList();
                    return Collections.singletonList(ENTRY_BUILDER
                            .startDropdownMenu(Component.translatable(i18n), DropdownMenuBuilder.TopCellElementBuilder.of(
                                    Utils.getUnsafely(field, config, Utils.getUnsafely(field, defaults)),
                                    str -> {
                                        String s = Component.literal(str).getString();
                                        Iterator<ResourceLocation> soundIterator = sounds.iterator();

                                        ResourceLocation constant;
                                        do {
                                            if (!soundIterator.hasNext()) return null;

                                            constant = soundIterator.next();
                                        } while (!DEFAULT_NAME_PROVIDER.apply(constant).toString().equals(s));

                                        return constant;
                                    },
                                    DEFAULT_NAME_PROVIDER
                            ), DropdownMenuBuilder.CellCreatorBuilder.of(DEFAULT_NAME_PROVIDER)).setSelections(sounds).setDefaultValue(() -> Utils.getUnsafely(field, defaults))
                            .setSelections(sounds)
                            .setDefaultValue(() -> Utils.getUnsafely(field, defaults))
                            .setSaveConsumer((newValue) -> Utils.setUnsafely(field, config, newValue))
                            .build());
                },
                (field) -> field.getType() == ResourceLocation.class && field.isAnnotationPresent(ConfigEntryGui.SoundEvent.class));
        registry.registerPredicateProvider((i18n, field, config, defaults, giuProvider) -> {
                    List<ResourceLocation> materials = PluginInit.materials.keySet().stream().toList();
                    return Collections.singletonList(ENTRY_BUILDER

                            .startIntList(Component.translatable(i18n), (List)Utils.getUnsafely(field, config)).setDefaultValue(() -> {
                        return (List)Utils.getUnsafely(field, defaults);
                    }).setSaveConsumer((newValue) -> {
                        Utils.setUnsafely(field, config, newValue);
                    }).build());
            },
            (field) -> {
                if (List.class.isAssignableFrom(field.getType()) && field.getGenericType() instanceof ParameterizedType) {
                    Type[] args = ((ParameterizedType)field.getGenericType()).getActualTypeArguments();
                    return args.length == 1 && Stream.of(ResourceLocation.class).anyMatch((type) -> Objects.equals(args[0], type)) && field.isAnnotationPresent(ConfigEntryGui.Material.class);
                } else {
                    return false;
                }
            }
        );
        registry.registerTypeProvider((i18n, field, config, defaults, guiProvider) ->
                        Collections.singletonList(ENTRY_BUILDER
                                .startStrField(Component.translatable(i18n), Utils.getUnsafely(field, config, new ResourceLocation("")).toString())
                                .setDefaultValue(() -> Utils.getUnsafely(field, defaults.toString()))
                                .setSaveConsumer((newValue) -> Utils.setUnsafely(field, config, new ResourceLocation(newValue)))
                                .build()),
                ResourceLocation.class);

        ModLoadingContext.get().registerExtensionPoint(
                IConfigScreenFactory.class,
                () -> (client, parent) -> AutoConfig.getConfigScreen(ECConfig.class, parent).get()
        );
    }
}
