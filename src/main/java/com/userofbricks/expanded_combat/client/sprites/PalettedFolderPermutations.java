package com.userofbricks.expanded_combat.client.sprites;

import com.google.common.base.Suppliers;
import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.userofbricks.expanded_combat.init.SpriteSourceTypes;
import it.unimi.dsi.fastutil.ints.Int2IntMap;
import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.renderer.texture.SpriteContents;
import net.minecraft.client.renderer.texture.atlas.SpriteResourceLoader;
import net.minecraft.client.renderer.texture.atlas.SpriteSource;
import net.minecraft.client.renderer.texture.atlas.SpriteSourceType;
import net.minecraft.client.renderer.texture.atlas.sources.LazyLoadedImage;
import net.minecraft.client.resources.metadata.animation.FrameSize;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceMetadata;
import net.minecraft.util.ARGB;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.IntUnaryOperator;
import java.util.function.Supplier;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class PalettedFolderPermutations implements SpriteSource {
    static final Logger LOGGER = LogUtils.getLogger();
    public static final MapCodec<PalettedFolderPermutations> CODEC = RecordCodecBuilder.mapCodec((p_266838_) ->
            p_266838_.group(Codec.list(ResourceLocation.CODEC).fieldOf("textures").forGetter((p_267300_) -> p_267300_.textures),
                            ResourceLocation.CODEC.fieldOf("palette_key").forGetter((p_266732_) -> p_266732_.paletteKey),
                            Codec.BOOL.fieldOf("texture_name_as_folder").forGetter(palette -> palette.textureNameAsFolder),
                            Codec.unboundedMap(Codec.STRING, ResourceLocation.CODEC).fieldOf("permutations").forGetter((p_267234_) -> p_267234_.permutations))
                    .apply(p_266838_, PalettedFolderPermutations::new));
    private final List<ResourceLocation> textures;
    private final Map<String, ResourceLocation> permutations;
    private final ResourceLocation paletteKey;
    private final boolean textureNameAsFolder;

    public PalettedFolderPermutations(List<ResourceLocation> textures, ResourceLocation paletteKey, boolean textureNameAsFolder, Map<String, ResourceLocation> permutations) {
        this.textures = textures;
        this.textureNameAsFolder = textureNameAsFolder;
        this.permutations = permutations;
        this.paletteKey = paletteKey;
    }

    @Override
    public void run(ResourceManager resourceManager, Output output) {
        Supplier<int[]> supplier = Suppliers.memoize(() -> loadPaletteEntryFromImage(resourceManager, this.paletteKey));

        Map<String, Supplier<IntUnaryOperator>> map = new HashMap<>();
        this.permutations.forEach((string, resourceLocation) ->
                map.put(string, Suppliers.memoize(() ->
                        createPaletteMapping(supplier.get(), loadPaletteEntryFromImage(resourceManager, resourceLocation)))));

        for(ResourceLocation resourcelocation : this.textures) {
            ResourceLocation resourcelocation1 = TEXTURE_ID_CONVERTER.idToFile(resourcelocation);
            Optional<Resource> optional = resourceManager.getResource(resourcelocation1);
            if (optional.isEmpty()) {
                LOGGER.warn("Unable to find texture {}", resourcelocation1);
            } else {
                LazyLoadedImage lazyloadedimage = new LazyLoadedImage(resourcelocation1, optional.get(), map.size());

                for(Map.Entry<String, Supplier<IntUnaryOperator>> entry : map.entrySet()) {
                    ResourceLocation resourcelocation2 = resourcelocation.withSuffix((textureNameAsFolder ? "/" : "_") + entry.getKey());
                    output.add(resourcelocation2, new PalettedSpriteSupplier(lazyloadedimage, entry.getValue(), resourcelocation2));
                }
            }
        }
    }

    private static IntUnaryOperator createPaletteMapping(int[] key, int[] permutation) {
        if (permutation.length != key.length) {
            LOGGER.warn("Palette mapping has different sizes: {} and {}", key.length, permutation.length);
            throw new IllegalArgumentException();
        } else {
            Int2IntMap int2intmap = new Int2IntOpenHashMap(permutation.length);

            for(int i = 0; i < key.length; ++i) {
                int j = key[i];
                if (ARGB.alpha(j) != 0) {
                    int2intmap.put(ARGB.transparent(j), permutation[i]);
                }
            }

            return (colorInt) -> {
                int k = ARGB.alpha(colorInt);
                if (k == 0) {
                    return colorInt;
                } else {
                    int l = ARGB.transparent(colorInt);
                    int i1 = int2intmap.getOrDefault(l, ARGB.opaque(l));
                    int j1 = ARGB.alpha(i1);
                    return ARGB.color(k * j1 / 255, i1);
                }
            };
        }
    }

    private static int[] loadPaletteEntryFromImage(ResourceManager pResourceMananger, ResourceLocation pPalette) {
        Optional<Resource> optional = pResourceMananger.getResource(TEXTURE_ID_CONVERTER.idToFile(pPalette));
        if (optional.isEmpty()) {
            LOGGER.error("Failed to load palette image {}", pPalette);
            throw new IllegalArgumentException();
        } else {
            try {
                int[] aint;
                try (
                        InputStream inputstream = optional.get().open();
                        NativeImage nativeimage = NativeImage.read(inputstream);
                ) {
                    aint = nativeimage.getPixels();
                }

                return aint;
            } catch (Exception exception) {
                LOGGER.error("Couldn't load texture {}", pPalette, exception);
                throw new IllegalArgumentException();
            }
        }
    }

    @Override
    public SpriteSourceType type() {
        return SpriteSourceTypes.PALETTED_FOLDER_PERMUTATIONS;
    }

    @OnlyIn(Dist.CLIENT)
    record PalettedSpriteSupplier(LazyLoadedImage baseImage, Supplier<IntUnaryOperator> palette, ResourceLocation permutationLocation) implements SpriteSource.SpriteSupplier {
        @Nullable
        public SpriteContents apply(SpriteResourceLoader loader) {
            try {
                NativeImage nativeimage = this.baseImage.get().mappedCopy(this.palette.get());
                return new SpriteContents(this.permutationLocation, new FrameSize(nativeimage.getWidth(), nativeimage.getHeight()), nativeimage, ResourceMetadata.EMPTY);
            } catch (IllegalArgumentException | IOException ioexception) {
                LOGGER.error("unable to apply palette to {}", this.permutationLocation, ioexception);
            } finally {
                this.baseImage.release();
            }

            return null;
        }

        public void discard() {
            this.baseImage.release();
        }
    }
}
