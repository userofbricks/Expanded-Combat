// Made with Blockbench 4.7.4
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


public class shieldLowerRight<T extends Entity> extends EntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("modid", "shieldlowerright"), "main");
	private final ModelPart frame_handle;

	public shieldLowerRight(ModelPart root) {
		this.frame_handle = root.getChild("frame_handle");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition frame_handle = partdefinition.addOrReplaceChild("frame_handle", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition handle_r1 = frame_handle.addOrReplaceChild("handle_r1", CubeListBuilder.create().texOffs(26, 0).addBox(-1.0F, -6.0F, 0.0F, 2.0F, 6.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-6.0F, -14.0F, -1.0F, 12.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(11, 1).addBox(5.0F, -13.0F, -1.0F, 1.0F, 20.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -3.1416F, 0.0F, 3.1416F));

		PartDefinition left = frame_handle.addOrReplaceChild("left", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition south_r1 = left.addOrReplaceChild("south_r1", CubeListBuilder.create().texOffs(1, 1).addBox(-6.0F, -14.0F, -1.0F, 1.0F, 22.0F, 0.0F, new CubeDeformation(0.0F))
		.texOffs(24, 1).addBox(-6.0F, -14.0F, 0.0F, 1.0F, 22.0F, 0.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-6.0F, -14.0F, -1.0F, 0.0F, 22.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -3.1416F, 0.0F, 3.1416F));

		PartDefinition bottom = frame_handle.addOrReplaceChild("bottom", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition top_r1 = bottom.addOrReplaceChild("top_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-6.0F, 8.0F, -1.0F, 12.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(3, 22).addBox(-5.0F, 7.0F, 0.0F, 11.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
		.texOffs(2, 22).addBox(-5.0F, 7.0F, -1.0F, 11.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
		.texOffs(12, 21).addBox(6.0F, 7.0F, -1.0F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -3.1416F, 0.0F, 3.1416F));

		PartDefinition rivits = frame_handle.addOrReplaceChild("rivits", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition cube_r1 = rivits.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(1, 7).addBox(-5.0F, -7.0F, -1.1F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(1, 12).addBox(-5.0F, -2.0F, -1.1F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(1, 17).addBox(-5.0F, 3.0F, -1.1F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(1, 2).addBox(-5.0F, -12.0F, -1.1F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(8, 2).addBox(2.0F, -12.0F, -1.1F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(8, 7).addBox(2.0F, -7.0F, -1.1F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(8, 12).addBox(2.0F, -2.0F, -1.1F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(8, 17).addBox(2.0F, 3.0F, -1.1F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -3.1416F, 0.0F, 3.1416F));

		return LayerDefinition.create(meshdefinition, 64, 32);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		frame_handle.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}