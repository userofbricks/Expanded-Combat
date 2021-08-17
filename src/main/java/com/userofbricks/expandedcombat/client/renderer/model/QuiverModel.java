package com.userofbricks.expandedcombat.client.renderer.model;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.model.AgeableListModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.LivingEntity;

import javax.annotation.Nonnull;

public class QuiverModel<T extends LivingEntity> extends AgeableListModel<T>
{
    public ModelPart quiver;
    //public ModelPart quiverStrap;
    public QuiverModel(ModelPart part) {
        this.quiver = part.getChild("quiver");
        //this.quiverStrap = part.getChild("quiver_strap");
    }

    public static LayerDefinition createLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition part = mesh.getRoot();
        CubeDeformation cube = new CubeDeformation(0.05f);
        part.addOrReplaceChild("quiver",
                               CubeListBuilder.create().texOffs(20, 0).addBox(4.5F, -23.0F, 4.0F, 8, 24, 6, cube),
                               PartPose.offsetAndRotation(0f, 0.0F, 0, 0.0F, 0.0F, -0.6981f));
        CubeDeformation cube2 = new CubeDeformation(0.2f);
        //part.addOrReplaceChild("quiver_strap", CubeListBuilder.create().texOffs(0, 0).addBox(7.5f, -23.75f, -4F, 2, 26, 8, cube2), PartPose.offsetAndRotation(0.0F, 2.0F, 0, 0.0F, 0.0F, -0.6981f));
        return LayerDefinition.create(mesh, 64, 64);
    }

    @Override
    @Nonnull
    protected Iterable<ModelPart> headParts() {
        return ImmutableList.of();
    }

    @Override
    @Nonnull
    protected Iterable<ModelPart> bodyParts() {
        return ImmutableList.of(this.quiver);
    }

    @Override
    public void setupAnim(@Nonnull T t, float v, float v1, float v2, float v3, float v4) {
    }
}
