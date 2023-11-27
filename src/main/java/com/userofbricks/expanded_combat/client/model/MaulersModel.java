package com.userofbricks.expanded_combat.client.model;

import javax.annotation.Nonnull;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.LivingEntity;

public class MaulersModel extends HumanoidModel<LivingEntity> {

	public MaulersModel(ModelPart root) {
		super(root);
	}

	public static LayerDefinition createLayer() {
		CubeDeformation deformation = new CubeDeformation(0.5F);
		CubeDeformation noDeformation = new CubeDeformation(0.0F);
		MeshDefinition meshdefinition = HumanoidModel.createMesh(deformation, 0.0f);
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition left_sleve = partdefinition.addOrReplaceChild("left_arm", CubeListBuilder.create()
		.texOffs(0, 16).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, deformation)
		.texOffs(13, 1).addBox(2.6F, 9.5F, 1.4F, 1.0F, 2.0F, 1.0F, noDeformation)
		.texOffs(13, 1).addBox(2.6F, 9.5F, -0.5F, 1.0F, 2.0F, 1.0F, noDeformation)
		.texOffs(13, 1).addBox(2.6F, 9.5F, -2.4F, 1.0F, 2.0F, 1.0F, noDeformation),
		PartPose.offset(-5.0F, 2.0F, 0.0F));

		left_sleve.addOrReplaceChild("cube_r4", CubeListBuilder.create()
						.texOffs(0, 1).mirror()
						.addBox(6.0F, -16.75F, -0.75F, 0.0F, 2.0F, 3.0F, noDeformation).mirror(false)
						.texOffs(1, -3).mirror()
						.addBox(5.75F, -18.25F, -1.75F, 0.0F, 2.0F, 3.0F, noDeformation).mirror(false),
				PartPose.offsetAndRotation(-5.0F, 22.0F, 0.0F, 0.0F, 0.0F, 0.1745F));

		left_sleve.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, -15.75F, 0.25F, 4.0F, 3.0F, 0.0F, noDeformation),
				PartPose.offsetAndRotation(7.0F, 22.0F, 0.0F, -0.1745F, 0.0F, 0.0F));

		left_sleve.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, -15.75F, -0.25F, 4.0F, 3.0F, 0.0F, noDeformation),
				PartPose.offsetAndRotation(7.0F, 22.0F, 0.0F, 0.1745F, 0.0F, 0.0F));

		PartDefinition right_sleve = partdefinition.addOrReplaceChild("right_arm", CubeListBuilder.create()
		.texOffs(0, 16).mirror().addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, deformation)
		.texOffs(13, 1).addBox(-3.6F, 9.5F, -2.4F, 1.0F, 2.0F, 1.0F, noDeformation)
		.texOffs(13, 1).addBox(-3.6F, 9.5F, -0.5F, 1.0F, 2.0F, 1.0F, noDeformation)
		.texOffs(13, 1).addBox(-3.6F, 9.5F, 1.4F, 1.0F, 2.0F, 1.0F, noDeformation),
		PartPose.offset(5.0F, 2.0F, 0.0F));

		right_sleve.addOrReplaceChild("cube_r1", CubeListBuilder.create()
						.texOffs(1, -3).addBox(-5.75F, -18.25F, -1.75F, 0.0F, 2.0F, 3.0F, noDeformation)
						.texOffs(0, 1).addBox(-6.0F, -16.75F, -0.75F, 0.0F, 2.0F, 3.0F, noDeformation),
				PartPose.offsetAndRotation(5.0F, 22.0F, 0.0F, 0.0F, 0.0F, -0.1745F));

		right_sleve.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, -15.75F, -0.25F, 4.0F, 3.0F, 0.0F, noDeformation),
				PartPose.offsetAndRotation(5.0F, 22.0F, 0.0F, 0.1745F, 0.0F, 0.0F));

		right_sleve.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, -15.75F, 0.25F, 4.0F, 3.0F, 0.0F, noDeformation),
				PartPose.offsetAndRotation(5.0F, 22.0F, 0.0F, -0.1745F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 32, 32);
	}

	@Override
	@Nonnull
	protected Iterable<ModelPart> headParts() {
		return ImmutableList.of();
	}

	@Override
	@Nonnull
	protected Iterable<ModelPart> bodyParts() {
		return ImmutableList
				.of(this.rightArm, this.leftArm);
	}
}