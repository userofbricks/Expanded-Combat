package com.userofbricks.expandedcombat.client.renderer.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.userofbricks.expandedcombat.ExpandedCombat;
import com.userofbricks.expandedcombat.item.ECItems;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fmllegacy.RegistryObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class SpecialItemModels
{
    private static final String HANDHELD = "_handheld";
    private static List<Item> specialHandheldItems;
    
    public static void detectSpecials() {
        SpecialItemModels.specialHandheldItems.clear();
        for ( RegistryObject<Item> ro : ECItems.ITEMS.getEntries()) {
             Item item = ro.get();
             ResourceLocation handheldModel = new ResourceLocation("expanded_combat", "models/item/" + item.getRegistryName().getPath() + "_handheld.json");
            if (ExpandedCombat.modResourceExists(PackType.CLIENT_RESOURCES, handheldModel)) {
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
         Map<ResourceLocation, BakedModel> map = event.getModelRegistry();
        for ( Item item : SpecialItemModels.specialHandheldItems) {
             ResourceLocation itemRes = item.getRegistryName();
            ResourceLocation modelName = new ModelResourceLocation(itemRes, "inventory");
             ResourceLocation handheldModelName = new ModelResourceLocation(itemRes + "_handheld", "inventory");
             BakedModel defaultModel = map.get(modelName);
            BakedModel handheldModel = map.get(handheldModelName);
            if (defaultModel != null && handheldModel != null) {
                BakedModel wrapperModel = new BakedModel() {

                    public List<BakedQuad> getQuads(BlockState state, Direction side, Random rand) {
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

                    public ItemOverrides getOverrides() {
                        return handheldModel.getOverrides();
                    }

                    public BakedModel handlePerspective( ItemTransforms.TransformType transformType,  PoseStack mat) {
                        BakedModel modelToUse = defaultModel;
                        if (transformType == ItemTransforms.TransformType.FIRST_PERSON_LEFT_HAND || transformType == ItemTransforms.TransformType.FIRST_PERSON_RIGHT_HAND || transformType == ItemTransforms.TransformType.THIRD_PERSON_LEFT_HAND || transformType == ItemTransforms.TransformType.THIRD_PERSON_RIGHT_HAND) {
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
