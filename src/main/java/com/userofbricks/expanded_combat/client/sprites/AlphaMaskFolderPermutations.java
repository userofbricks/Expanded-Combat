package com.userofbricks.expanded_combat.client.sprites;

import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.userofbricks.expanded_combat.init.SpriteSourceTypes;
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
import java.util.*;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class AlphaMaskFolderPermutations implements SpriteSource {
    static final Logger LOGGER = LogUtils.getLogger();
    public static final Codec<AlphaMaskFolderPermutations> CODEC = RecordCodecBuilder.create((p_266838_) ->
            p_266838_.group(Codec.list(ResourceLocation.CODEC).fieldOf("permutations").forGetter((p_267300_) -> p_267300_.permutations),
                            Codec.BOOL.fieldOf("mask_name_as_folder").forGetter(palette -> palette.maskNameAsFolder),
                            Codec.unboundedMap(Codec.STRING, ResourceLocation.CODEC).fieldOf("textures").forGetter((p_267234_) -> p_267234_.textures))
                    .apply(p_266838_, AlphaMaskFolderPermutations::new));
    private final List<ResourceLocation> permutations;
    private final Map<String, ResourceLocation> textures;
    private final boolean maskNameAsFolder;

    public AlphaMaskFolderPermutations(List<ResourceLocation> permutations, boolean maskNameAsFolder, Map<String, ResourceLocation> textures) {
        this.permutations = permutations;
        this.maskNameAsFolder = maskNameAsFolder;
        this.textures = textures;
    }

    @Override
    public void run(ResourceManager resourceManager, Output output) {
        for(ResourceLocation maskSpriteLocation : this.permutations) {
            ResourceLocation maskFileLocation = TEXTURE_ID_CONVERTER.idToFile(maskSpriteLocation);
            Optional<Resource> optionalMaskResource = resourceManager.getResource(maskFileLocation);
            if (optionalMaskResource.isEmpty()) {
                LOGGER.warn("Unable to find mask texture {}", maskFileLocation);
            } else {
                LazyLoadedImage lazyMaskImage = new LazyLoadedImage(maskFileLocation, optionalMaskResource.get(), textures.size());

                for(Map.Entry<String, ResourceLocation> entry : textures.entrySet()) {
                    ResourceLocation textureFileLocation = TEXTURE_ID_CONVERTER.idToFile(entry.getValue());
                    Optional<Resource> optionalTextureResource = resourceManager.getResource(textureFileLocation);
                    if (optionalTextureResource.isEmpty()) {
                        LOGGER.warn("Unable to find texture {}", textureFileLocation);
                    } else {
                        LazyLoadedImage lazyBaseImage = new LazyLoadedImage(textureFileLocation, optionalTextureResource.get(), 1);

                        ResourceLocation finalSpriteLocation = maskSpriteLocation.withSuffix((maskNameAsFolder ? "/" : "_") + entry.getKey());
                        output.add(finalSpriteLocation, new MaskedSpriteSupplier(lazyBaseImage, lazyMaskImage, finalSpriteLocation));
                    }
                }
            }
        }
    }

    @Override
    public SpriteSourceType type() {
        return SpriteSourceTypes.ALPHA_MASK_FOLDER_PERMUTATIONS;
    }

    @OnlyIn(Dist.CLIENT)
    record MaskedSpriteSupplier(LazyLoadedImage baseImage, LazyLoadedImage maskImage, ResourceLocation permutationLocation) implements SpriteSupplier {
        @Nullable
        public SpriteContents get() {
            try {
                NativeImage baseNative = baseImage.get();
                int width = baseNative.getWidth();
                int height = baseNative.getHeight();
                NativeImage nativeimage = new NativeImage(width, height, false);
                NativeImage alphaMaskNative = maskImage.get();

                for (int chWidth = 0; chWidth < width; chWidth++) {
                    for (int chHeight = 0; chHeight < height; chHeight++) {
                        if (FastColor.ABGR32.alpha(alphaMaskNative.getPixelRGBA(chWidth, chHeight)) != 0) {
                            nativeimage.setPixelRGBA(chWidth, chHeight, baseNative.getPixelRGBA(chWidth, chHeight));
                        } else {
                            nativeimage.setPixelRGBA(chWidth, chHeight, alphaMaskNative.getPixelRGBA(chWidth, chHeight));
                        }
                    }
                }
                return new SpriteContents(this.permutationLocation, new FrameSize(nativeimage.getWidth(), nativeimage.getHeight()), nativeimage, AnimationMetadataSection.EMPTY, ForgeTextureMetadata.EMPTY);
            } catch (IllegalArgumentException | IOException ioexception) {
                LOGGER.error("unable to apply alpha mask to {}", this.permutationLocation, ioexception);
            } finally {
                this.baseImage.release();
                this.maskImage.release();
            }

            return null;
        }
        public void discard() {
            this.baseImage.release();
            this.maskImage.release();
        }
    }
}
