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
    public ModelPart quiverStrap;
    public QuiverModel(ModelPart part) {
        this.quiver = part.getChild("quiver");
        this.quiverStrap = part.getChild("quiver_strap");
    }

    public static LayerDefinition createLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition part = mesh.getRoot();
        CubeDeformation cube = CubeDeformation.NONE;
        part.addOrReplaceChild("quiver",
                               CubeListBuilder.create().texOffs(10, 0).addBox(10.0F, -20.0F, 2.0F, 4, 12, 3, cube),
                               PartPose.offsetAndRotation(0.0F, 0.0F, -0.6981f, 0.0F, 0.0F, 0.0F));
        CubeDeformation cube2 = new CubeDeformation(0.1f);
        part.addOrReplaceChild("quiver_strap",
                               CubeListBuilder.create().texOffs(0, 0).addBox(11.5F, -20.75F, -2.0F, 1, 13, 4, cube2),
                               PartPose.offsetAndRotation(0.0F, 0.0F, -0.6981f, 0.0F, 0.0F, 0.0F));
        return LayerDefinition.create(mesh, 32, 32);
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
