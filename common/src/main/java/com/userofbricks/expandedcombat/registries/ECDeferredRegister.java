package com.userofbricks.expandedcombat.registries;

import com.google.common.base.Suppliers;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.Registrar;
import dev.architectury.registry.registries.Registries;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

//mostly copied from Architectury. added a getEntries() method.
public class ECDeferredRegister<T> {
    private final Supplier<Registries> registriesSupplier;
    private final ResourceKey<Registry<T>> key;
    private final List<ECDeferredRegister<T>.Entry<T>> entries = new ArrayList();
    private boolean registered = false;
    @Nullable
    private final String modId;

    private ECDeferredRegister(Supplier<Registries> registriesSupplier, ResourceKey<Registry<T>> key, @Nullable String modId) {
        this.registriesSupplier = Objects.requireNonNull(registriesSupplier);
        this.key = Objects.requireNonNull(key);
        this.modId = modId;
    }

    public static <T> ECDeferredRegister<T> create(String modId, ResourceKey<Registry<T>> key) {
        Supplier<Registries> value = Suppliers.memoize(() -> {
            return Registries.get(modId);
        });
        return new ECDeferredRegister<>(value, key, Objects.requireNonNull(modId));
    }

    public <R extends T> RegistrySupplier<R> register(String id, Supplier<? extends R> supplier) {
        if (this.modId == null) {
            throw new NullPointerException("You must create the deferred register with a mod id to register entries without the namespace!");
        } else {
            return this.register(new ResourceLocation(this.modId, id), supplier);
        }
    }

    public <R extends T> RegistrySupplier<R> register(ResourceLocation id, Supplier<? extends R> supplier) {
        ECDeferredRegister<T>.Entry<T> entry = new ECDeferredRegister.Entry(id, supplier);
        this.entries.add(entry);
        if (this.registered) {
            Registrar<T> registrar = this.registriesSupplier.get().get(this.key);
            entry.value = registrar.register(entry.id, entry.supplier);
        }

        return (RegistrySupplier<R>) entry;
    }

    public void register() {
        if (this.registered) {
            throw new IllegalStateException("Cannot register a deferred register twice!");
        } else {
            this.registered = true;
            Registrar<T> registrar = ((Registries)this.registriesSupplier.get()).get(this.key);

            ECDeferredRegister.Entry entry;
            for(Iterator var2 = this.entries.iterator(); var2.hasNext(); entry.value = registrar.register(entry.id, entry.supplier)) {
                entry = (ECDeferredRegister.Entry)var2.next();
            }

        }
    }
    /**
     * simmilar to the forge DiferredRegister getEntries method
     * @return The unmodifiable view of registered entries. Useful for bulk operations on all values.
     */
    public Collection<RegistrySupplier<T>> getEntries()
    {
        return entries.stream().collect(Collectors.toUnmodifiableSet());
    }

    private class Entry<R> implements RegistrySupplier<R> {
        private final ResourceLocation id;
        private final Supplier<R> supplier;
        private RegistrySupplier<R> value;

        public Entry(ResourceLocation id, Supplier<R> supplier) {
            this.id = id;
            this.supplier = supplier;
        }

        public ResourceLocation getRegistryId() {
            return ECDeferredRegister.this.key.location();
        }

        public ResourceLocation getId() {
            return this.id;
        }

        public boolean isPresent() {
            return this.value != null && this.value.isPresent();
        }

        public R get() {
            if (this.isPresent()) {
                return this.value.get();
            } else {
                throw new NullPointerException("Registry Object not present: " + this.id);
            }
        }

        public int hashCode() {
            return com.google.common.base.Objects.hashCode(new Object[]{this.getRegistryId(), this.getId()});
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            } else if (!(obj instanceof RegistrySupplier)) {
                return false;
            } else {
                RegistrySupplier<?> other = (RegistrySupplier)obj;
                return other.getRegistryId().equals(this.getRegistryId()) && other.getId().equals(this.getId());
            }
        }

        public String toString() {
            String var10000 = this.getRegistryId().toString();
            return var10000 + "@" + this.id.toString();
        }
    }
}
