package com.userofbricks.expandedcombat.client.renderer.model;

import java.util.ArrayList;
import java.util.Map;

import net.minecraft.client.renderer.model.*;
import net.minecraftforge.client.ForgeHooksClient;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;

import java.util.Random;
import net.minecraft.util.Direction;
import net.minecraft.block.BlockState;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.model.ModelLoader;

import com.userofbricks.expandedcombat.ExpandedCombat;
import net.minecraft.resources.ResourcePackType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;
import com.userofbricks.expandedcombat.item.ECItems;
import net.minecraft.item.Item;
import java.util.List;

public class SpecialItemModels
{
    private static final String HANDHELD = "_handheld";
    private static List<Item> specialHandheldItems;
    
    public static void detectSpecials() {
        SpecialItemModels.specialHandheldItems.clear();
        for ( RegistryObject<Item> ro : ECItems.ITEMS.getEntries()) {
             Item item = ro.get();
             ResourceLocation handheldModel = new ResourceLocation("expanded_combat", "models/item/" + item.getRegistryName().getPath() + "_handheld.json");
            if (ExpandedCombat.modResourceExists(ResourcePackType.CLIENT_RESOURCES, handheldModel)) {
                addSpecialHandheld(item);
            }
        }
        //ECLog.info("Automatically detected and registered %d special handheld items!", SpecialItemModels.specialHandheldItems.size());
    }
    
    public static void addSpecialHandheld( Item item) {
        SpecialItemModels.specialHandheldItems.add(item);
        ModelLoader.addSpecialModel(new ModelResourceLocation(item.getRegistryName() + "_handheld", "inventory"));
    }
    
    public static void onModelBake( ModelBakeEvent event) {
         Map<ResourceLocation, IBakedModel> map = event.getModelRegistry();
        for ( Item item : SpecialItemModels.specialHandheldItems) {
             ResourceLocation itemRes = item.getRegistryName();
            ResourceLocation modelName = new ModelResourceLocation(itemRes, "inventory");
             ResourceLocation handheldModelName = new ModelResourceLocation(itemRes + "_handheld", "inventory");
             IBakedModel defaultModel = map.get(modelName);
            IBakedModel handheldModel = map.get(handheldModelName);
            if (defaultModel != null && handheldModel != null) {
                IBakedModel wrapperModel = new IBakedModel() {

                    public List<BakedQuad> getQuads( BlockState state,  Direction side,  Random rand) {
                        return defaultModel.getQuads(state, side, rand);
                    }

                    public boolean useAmbientOcclusion() {
                        return defaultModel.useAmbientOcclusion();
                    }

                    public boolean isGui3d() {
                        return defaultModel.isGui3d();
                    }

                    public boolean usesBlockLight() {
                        return defaultModel.usesBlockLight();
                    }

                    public boolean isCustomRenderer() {
                        return defaultModel.isCustomRenderer();
                    }

                    public TextureAtlasSprite getParticleIcon() {
                        return defaultModel.getParticleIcon();
                    }

                    public ItemOverrideList getOverrides() {
                        return handheldModel.getOverrides();
                    }

                    public IBakedModel handlePerspective( ItemCameraTransforms.TransformType transformType,  MatrixStack mat) {
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
    }

    static {
        SpecialItemModels.specialHandheldItems = new ArrayList<Item>();
    }
}
