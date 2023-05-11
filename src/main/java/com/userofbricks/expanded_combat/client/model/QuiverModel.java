package com.userofbricks.expanded_combat.client.model;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.model.AgeableListModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

public class QuiverModel extends AgeableListModel<LivingEntity> {
    public ModelPart quiver;
    public ModelPart strap;
    public QuiverModel(ModelPart part) {
        this.quiver = part.getChild("quiver");
        this.strap = part.getChild("quiver_strap");
    }

    public static LayerDefinition createLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition part = mesh.getRoot();

        part.addOrReplaceChild("quiver",
                CubeListBuilder.create().texOffs(20, 0)
                        .addBox(4.5F, -23.0F, 4.0F, 8, 24, 6, new CubeDeformation(0.05f)),
                PartPose.offsetAndRotation(0f, 0.0F, 0, 0.0F, 0.0F, -0.6981f));

        part.addOrReplaceChild("quiver_strap",
                CubeListBuilder.create().texOffs(0, 0)
                        .addBox(7.5f, -23.75f, -4F, 2, 26, 8, new CubeDeformation(0.2f)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0, 0.0F, 0.0F, -0.6981f));
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
        return ImmutableList.of(this.quiver, this.strap);
    }

    @Override
    public void setupAnim(@NotNull LivingEntity livingEntity, float p_102619_, float p_102620_, float p_102621_, float p_102622_, float p_102623_) {

    }
}
