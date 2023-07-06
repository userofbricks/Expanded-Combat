package com.userofbricks.expanded_combat.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.EnumSet;

@ParametersAreNonnullByDefault
public class UpperRightShieldPart extends EntityModel<LivingEntity> {
    private final ModelPart upper_right;

    public UpperRightShieldPart(ModelPart root) {
        this.upper_right = root.getChild("upper_right");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition upper_right = partdefinition.addOrReplaceChild("upper_right", CubeListBuilder.create(), PartPose.offset(5.0F, 24.0F, 0.0F));

        PartDefinition back_ur_r1 = upper_right.addOrReplaceChild("back_ur_r1", CubeListBuilder.create()
                .texOffs(15, 2).addBox(0.0F, -13.0F, 0.0F, 5.0F, 10.0F, 0.0F, EnumSet.of(Direction.SOUTH))
                .texOffs(7, 2).addBox(0.0F, -13.0F, -1.0F, 5.0F, 10.0F, 0.0F, EnumSet.of(Direction.NORTH)), PartPose.offsetAndRotation(-5.0F, 0.0F, 0.0F, -3.1416F, 0.0F, 3.1416F));

        return LayerDefinition.create(meshdefinition, 64, 32);
    }

    @Override
    public void setupAnim(LivingEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        upper_right.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}
