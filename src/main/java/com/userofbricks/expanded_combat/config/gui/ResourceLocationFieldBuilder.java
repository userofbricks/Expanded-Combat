package com.userofbricks.expanded_combat.config.gui;

import me.shedaniel.clothconfig2.gui.entries.StringListEntry;
import me.shedaniel.clothconfig2.impl.builders.AbstractFieldBuilder;
import me.shedaniel.clothconfig2.impl.builders.StringFieldBuilder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class ResourceLocationFieldBuilder extends AbstractFieldBuilder<ResourceLocation, ResourceLocationListEntry, ResourceLocationFieldBuilder> {
    private Supplier<List<ResourceLocation>> availableValues = null;

    public ResourceLocationFieldBuilder(Component resetButtonKey, Component fieldNameKey, ResourceLocation value) {
        super(resetButtonKey, fieldNameKey);
        Objects.requireNonNull(value);
        this.value = value;
    }

    public ResourceLocationFieldBuilder setErrorSupplier(Function<ResourceLocation, Optional<Component>> errorSupplier) {
        return super.setErrorSupplier(errorSupplier);
    }

    public ResourceLocationFieldBuilder requireRestart() {
        return super.requireRestart();
    }

    public ResourceLocationFieldBuilder setSaveConsumer(Consumer<ResourceLocation> saveConsumer) {
        return super.setSaveConsumer(saveConsumer);
    }

    public ResourceLocationFieldBuilder setDefaultValue(Supplier<ResourceLocation> defaultValue) {
        return super.setDefaultValue(defaultValue);
    }

    public ResourceLocationFieldBuilder setDefaultValue(ResourceLocation defaultValue) {
        return super.setDefaultValue(defaultValue);
    }

    public ResourceLocationFieldBuilder setTooltipSupplier(Function<ResourceLocation, Optional<Component[]>> tooltipSupplier) {
        return super.setTooltipSupplier(tooltipSupplier);
    }

    public ResourceLocationFieldBuilder setTooltipSupplier(Supplier<Optional<Component[]>> tooltipSupplier) {
        return super.setTooltipSupplier(tooltipSupplier);
    }

    public ResourceLocationFieldBuilder setTooltip(Optional<Component[]> tooltip) {
        return super.setTooltip(tooltip);
    }

    public ResourceLocationFieldBuilder setTooltip(Component... tooltip) {
        return super.setTooltip(tooltip);
    }

    public ResourceLocationFieldBuilder setAvailableValues(Supplier<List<ResourceLocation>> availableValues) {
        this.availableValues = availableValues;
        return this;
    }

    public ResourceLocationFieldBuilder removeAvailableValues() {
        this.availableValues = null;
        return this;
    }

    @Override
    public @NotNull ResourceLocationListEntry build() {
        ResourceLocationListEntry entry = new ResourceLocationListEntry(this.getFieldNameKey(), this.value, this.getResetButtonKey(), this.defaultValue, this.getSaveConsumer(), null, this.isRequireRestart());
        if (this.availableValues != null) {
            entry.setAvailableValues(this.availableValues);
        }

        entry.setTooltipSupplier(() -> this.getTooltipSupplier().apply(entry.getValue()));

        if (this.errorSupplier != null) {
            entry.setErrorSupplier(() -> this.errorSupplier.apply(entry.getValue()));
        }

        return this.finishBuilding(entry);
    }
}
