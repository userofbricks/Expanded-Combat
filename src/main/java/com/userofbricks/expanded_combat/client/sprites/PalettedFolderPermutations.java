package com.userofbricks.expanded_combat.client.sprites;

import com.google.common.base.Suppliers;
import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.userofbricks.expanded_combat.init.SpriteSourceTypes;
import it.unimi.dsi.fastutil.ints.Int2IntMap;
import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.renderer.texture.SpriteContents;
import net.minecraft.client.renderer.texture.atlas.SpriteSource;
import net.minecraft.client.renderer.texture.atlas.SpriteSourceType;
import net.minecraft.client.renderer.texture.atlas.sources.LazyLoadedImage;
import net.minecraft.client.resources.metadata.animation.AnimationMetadataSection;
import net.minecraft.client.resources.metadata.animation.FrameSize;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.FastColor;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.textures.ForgeTextureMetadata;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.IntUnaryOperator;
import java.util.function.Supplier;

import static net.minecraft.client.renderer.texture.atlas.sources.PalettedPermutations.loadPaletteEntryFromImage;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class PalettedFolderPermutations implements SpriteSource {
    static final Logger LOGGER = LogUtils.getLogger();
    public static final Codec<PalettedFolderPermutations> CODEC = RecordCodecBuilder.create((p_266838_) ->
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
                if (FastColor.ABGR32.alpha(j) != 0) {
                    int2intmap.put(FastColor.ABGR32.transparent(j), permutation[i]);
                }
            }

            return (colorInt) -> {
                int k = FastColor.ABGR32.alpha(colorInt);
                if (k == 0) {
                    return colorInt;
                } else {
                    int l = FastColor.ABGR32.transparent(colorInt);
                    int i1 = int2intmap.getOrDefault(l, FastColor.ABGR32.opaque(l));
                    int j1 = FastColor.ABGR32.alpha(i1);
                    return FastColor.ABGR32.color(k * j1 / 255, i1);
                }
            };
        }
    }

    @Override
    public SpriteSourceType type() {
        return SpriteSourceTypes.PALETTED_FOLDER_PERMUTATIONS;
    }

    @OnlyIn(Dist.CLIENT)
    record PalettedSpriteSupplier(LazyLoadedImage baseImage, Supplier<IntUnaryOperator> palette, ResourceLocation permutationLocation) implements SpriteSource.SpriteSupplier {
        @Nullable
        public SpriteContents get() {
            try {
                NativeImage nativeimage = this.baseImage.get().mappedCopy(this.palette.get());
                return new SpriteContents(this.permutationLocation, new FrameSize(nativeimage.getWidth(), nativeimage.getHeight()), nativeimage, AnimationMetadataSection.EMPTY, ForgeTextureMetadata.EMPTY);
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
