package com.userofbricks.expanded_combat.config.gui;

import com.userofbricks.expanded_combat.datagen.LangStrings;
import me.shedaniel.clothconfig2.gui.entries.AbstractTextFieldListListEntry;
import me.shedaniel.clothconfig2.gui.entries.TextFieldListEntry;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

@OnlyIn(Dist.CLIENT)
public class ResourceLocationListEntry extends TextFieldListEntry<ResourceLocation> {
    private Supplier<List<ResourceLocation>> availableValues = null;

    protected ResourceLocationListEntry(Component fieldName, ResourceLocation original, Component resetButtonKey, Supplier<ResourceLocation> defaultValue, Consumer<ResourceLocation> saveConsumer, Supplier<Optional<Component[]>> tooltipSupplier, boolean requiresRestart) {
        super(fieldName, original, resetButtonKey, defaultValue, tooltipSupplier, requiresRestart);
        this.saveCallback = saveConsumer;
    }

    public ResourceLocationListEntry setAvailableValues(Supplier<List<ResourceLocation>> availableValues) {
        this.availableValues = availableValues;
        return this;
    }

    @Override
    public ResourceLocation getValue() {
        return new ResourceLocation(this.textFieldWidget.getValue());
    }

    @Override
    public Optional<Component> getError() {
        if (!ResourceLocation.isValidResourceLocation(this.textFieldWidget.getValue()))
            return Optional.of(Component.translatable(LangStrings.NOT_VALID_RESOURCE_LOCATION_CONFIG_ERROR));
        if (this.availableValues != null && !availableValues.get().contains(new ResourceLocation(textFieldWidget.getValue())))
            return Optional.of(Component.translatable(LangStrings.RESOURCE_LOCATION_NOT_WITHIN_AVAILABLE_VALUES));
        return super.getError();
    }
}