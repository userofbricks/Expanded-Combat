//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "conf"!

// 
// Decompiled by Procyon v0.5.36
// 

package com.userofbricks.expandedcombat.client.renderer.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.userofbricks.expandedcombat.ExpandedCombat;
import com.userofbricks.expandedcombat.item.ECItems;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.model.*;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.item.Item;
import net.minecraft.resources.ResourcePackType;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.RegistryObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class SpecialItemModels
{
    private static final String HANDHELD = "_handheld";
    private static List<Item> specialHandheldItems;
    
    public static void detectSpecials() {
        for (final RegistryObject<Item> ro : ECItems.ITEMS.getEntries()) {
            final Item item = (Item)ro.get();
            final ResourceLocation handheldModel = new ResourceLocation("expanded_combat", "models/item/" + item.getRegistryName().getPath() + "_handheld" + ".json");
            if (ExpandedCombat.modResourceExists(ResourcePackType.CLIENT_RESOURCES, handheldModel)) {
                addSpecialHandheld(item);
            }
        }
    }
    
    public static void addSpecialHandheld(final Item item) {
        SpecialItemModels.specialHandheldItems.add(item);
        ModelLoader.addSpecialModel((ResourceLocation)new ModelResourceLocation(item.getRegistryName() + "_handheld", "inventory"));
    }
    
    public static void onModelBake(final ModelBakeEvent event) {
        final Map<ResourceLocation, IBakedModel> map = (Map<ResourceLocation, IBakedModel>)event.getModelRegistry();
        for (final Item item : SpecialItemModels.specialHandheldItems) {
            final ResourceLocation itemRes = item.getRegistryName();
            final ResourceLocation modelName = (ResourceLocation)new ModelResourceLocation(itemRes, "inventory");
            final ResourceLocation handheldModelName = (ResourceLocation)new ModelResourceLocation(itemRes + "_handheld", "inventory");
            final IBakedModel defaultModel = map.get(modelName);
            final IBakedModel handheldModel = map.get(handheldModelName);
            final IBakedModel wrapperModel = (IBakedModel)new IBakedModel() {
                public List<BakedQuad> getQuads(final BlockState state, final Direction side, final Random rand) {
                    return (List<BakedQuad>)defaultModel.getQuads(state, side, rand);
                }
                
                public boolean isAmbientOcclusion() {
                    return defaultModel.isAmbientOcclusion();
                }
                
                public boolean isGui3d() {
                    return defaultModel.isGui3d();
                }

                public boolean isSideLit() {
                    return defaultModel.isSideLit();
                }
                
                public boolean isBuiltInRenderer() {
                    return defaultModel.isBuiltInRenderer();
                }
                
                public TextureAtlasSprite getParticleTexture() {
                    return defaultModel.getParticleTexture();
                }
                
                public ItemOverrideList getOverrides() {
                    return handheldModel.getOverrides();
                }
                
                public IBakedModel handlePerspective(final ItemCameraTransforms.TransformType transformType, final MatrixStack mat) {
                    IBakedModel modelToUse = defaultModel;
                    if (transformType == ItemCameraTransforms.TransformType.FIRST_PERSON_LEFT_HAND || transformType == ItemCameraTransforms.TransformType.FIRST_PERSON_RIGHT_HAND || transformType == ItemCameraTransforms.TransformType.THIRD_PERSON_LEFT_HAND || transformType == ItemCameraTransforms.TransformType.THIRD_PERSON_RIGHT_HAND) {
                        modelToUse = handheldModel;
                    }
                    return ForgeHooksClient.handlePerspective(modelToUse, transformType, mat);
                }
            };
            map.put(modelName, wrapperModel);
        }
    }
    
    static {
        SpecialItemModels.specialHandheldItems = new ArrayList<Item>();
    }
}
