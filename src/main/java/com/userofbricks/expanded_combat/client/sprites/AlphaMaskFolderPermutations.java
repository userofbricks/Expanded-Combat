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
import net.minecraft.resources.FileToIdConverter;
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
import java.io.InputStream;
import java.util.*;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class AlphaMaskFolderPermutations implements SpriteSource {
    static final Logger LOGGER = LogUtils.getLogger();
    public static final Codec<AlphaMaskFolderPermutations> CODEC = RecordCodecBuilder.create((p_266838_) ->
            p_266838_.group(Codec.list(ResourceLocation.CODEC).fieldOf("permutations").forGetter((p_267300_) -> p_267300_.permutations),
                            Codec.BOOL.fieldOf("mask_name_as_folder").forGetter(palette -> palette.maskNameAsFolder),
                            Codec.STRING.fieldOf("source").forGetter((p_261592_) -> p_261592_.sourcePath))
                    .apply(p_266838_, AlphaMaskFolderPermutations::new));
    private final List<ResourceLocation> permutations;
    private final String sourcePath;
    private final boolean maskNameAsFolder;

    public AlphaMaskFolderPermutations(List<ResourceLocation> permutations, boolean maskNameAsFolder, String sourcePath) {
        this.permutations = permutations;
        this.maskNameAsFolder = maskNameAsFolder;
        this.sourcePath = sourcePath;
    }

    @Override
    public void run(ResourceManager resourceManager, Output output) {
        for(ResourceLocation maskSpriteLocation : this.permutations) {
            ResourceLocation maskFileLocation = TEXTURE_ID_CONVERTER.idToFile(maskSpriteLocation);
            Optional<Resource> optionalMaskResource = resourceManager.getResource(maskFileLocation);
            if (optionalMaskResource.isEmpty()) {
                LOGGER.warn("Unable to find mask texture {}", maskFileLocation);
            } else {

                FileToIdConverter filetoidconverter = new FileToIdConverter("textures/" + this.sourcePath, ".png");
                Map<ResourceLocation, Resource> foundTextures = filetoidconverter.listMatchingResources(resourceManager);

                LazyLoadedImage lazyMaskImage = new LazyLoadedImage(maskFileLocation, optionalMaskResource.get(), foundTextures.size());

                foundTextures.forEach((textureFileLocation, resource) -> {

                    try (InputStream inputstream = resource.open()) {
                        NativeImage baseImage = NativeImage.read(inputstream);

                        ResourceLocation finalSpriteLocation = maskSpriteLocation.withSuffix((maskNameAsFolder ? "/" : "_") + getTextureFileName(textureFileLocation));
                        output.add(finalSpriteLocation, new MaskedSpriteSupplier(baseImage, lazyMaskImage, finalSpriteLocation));
                    } catch (IOException ioexception) {
                        LOGGER.error("Using missing texture, unable to load {}", filetoidconverter.fileToId(textureFileLocation).withPrefix(this.sourcePath + "/"), ioexception);
                    }
                });
            }
        }
    }

    private String getTextureFileName(ResourceLocation fileLocation) {
        String path = fileLocation.getPath();
        String[] splitPath = path.split("/");
        String suffixedName = splitPath[splitPath.length-1];
        return suffixedName.substring(0, suffixedName.length() - ".png".length());
    }

    @Override
    public SpriteSourceType type() {
        return SpriteSourceTypes.ALPHA_MASK_FOLDER_PERMUTATIONS;
    }

    @OnlyIn(Dist.CLIENT)
    record MaskedSpriteSupplier(NativeImage baseNative, LazyLoadedImage maskImage, ResourceLocation permutationLocation) implements SpriteSupplier {
        @Nullable
        public SpriteContents get() {
            try {
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
                this.maskImage.release();
            }

            return null;
        }
        public void discard() {
            this.maskImage.release();
        }
    }
}
