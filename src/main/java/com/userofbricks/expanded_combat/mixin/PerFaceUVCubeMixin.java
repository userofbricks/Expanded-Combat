package com.userofbricks.expanded_combat.mixin;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.UVPair;
import net.minecraft.core.Direction;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Set;

@Mixin(ModelPart.Cube.class)
@OnlyIn(Dist.CLIENT)
public class PerFaceUVCubeMixin {
    @Mutable @Final @Shadow private final ModelPart.Polygon[] polygons;
    @Mutable @Final @Shadow public final float minX;
    @Mutable @Final @Shadow public final float minY;
    @Mutable @Final @Shadow public final float minZ;
    @Mutable @Final @Shadow public final float maxX;
    @Mutable @Final @Shadow public final float maxY;
    @Mutable @Final @Shadow public final float maxZ;

    /*
    public ModelPart.Cube bake(int p_171456_, int p_171457_) {
        UVPair pair = new UVPair(1, 1);
        return (ModelPart.Cube)(Object)(new PerFaceUVCubeMixin(1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, false, (float)p_171456_ * 1f, (float)p_171457_ * 1f, pair, pair, pair, pair, pair, pair, EnumSet.allOf(Direction.class)));
    }
     */


    public PerFaceUVCubeMixin(float x, float y, float z, float dimX, float height, float dimZ, float growX, float growY, float growZ, boolean mirror, float texTotalU, float texTotalV, UVPair down, UVPair up, UVPair north, UVPair south, UVPair east, UVPair west, Set<Direction> visibleFaces) {
        this.minX = x;
        this.minY = y;
        this.minZ = z;
        this.maxX = x + dimX;
        this.maxY = y + height;
        this.maxZ = z + dimZ;
        this.polygons = new ModelPart.Polygon[visibleFaces.size()];
        float f = x + dimX;
        float f1 = y + height;
        float f2 = z + dimZ;
        x -= growX;
        y -= growY;
        z -= growZ;
        f += growX;
        f1 += growY;
        f2 += growZ;
        if (mirror) {
            float f3 = f;
            f = x;
            x = f3;
        }

        ModelPart.Vertex downNorthWest = new ModelPart.Vertex(x, y, z, 0.0F, 0.0F);
        ModelPart.Vertex downNothEast = new ModelPart.Vertex(f, y, z, 0.0F, 8.0F);
        ModelPart.Vertex upNorthEast = new ModelPart.Vertex(f, f1, z, 8.0F, 8.0F);
        ModelPart.Vertex upNorthWest = new ModelPart.Vertex(x, f1, z, 8.0F, 0.0F);
        ModelPart.Vertex downSouthWest = new ModelPart.Vertex(x, y, f2, 0.0F, 0.0F);
        ModelPart.Vertex downSouthEast = new ModelPart.Vertex(f, y, f2, 0.0F, 8.0F);
        ModelPart.Vertex upSouthEast = new ModelPart.Vertex(f, f1, f2, 8.0F, 8.0F);
        ModelPart.Vertex upSouthWest = new ModelPart.Vertex(x, f1, f2, 8.0F, 0.0F);
        int i = 0;
        if (visibleFaces.contains(Direction.DOWN)) {
            this.polygons[i++] = new ModelPart.Polygon(new ModelPart.Vertex[]{downSouthEast, downSouthWest, downNorthWest, downNothEast}, up.u(), up.v(), up.u() + dimX, up.v() + dimZ, texTotalU, texTotalV, mirror, Direction.DOWN);
        }

        if (visibleFaces.contains(Direction.UP)) {
            this.polygons[i++] = new ModelPart.Polygon(new ModelPart.Vertex[]{upNorthEast, upNorthWest, upSouthWest, upSouthEast}, down.u(), down.v() + dimZ, down.u() + dimX, down.v(), texTotalU, texTotalV, mirror, Direction.UP);
        }

        if (visibleFaces.contains(Direction.WEST)) {
            this.polygons[i++] = new ModelPart.Polygon(new ModelPart.Vertex[]{downNorthWest, downSouthWest, upSouthWest, upNorthWest}, east.u(), east.v(), east.u() + dimZ, east.v() + height, texTotalU, texTotalV, mirror, Direction.WEST);
        }

        if (visibleFaces.contains(Direction.NORTH)) {
            this.polygons[i++] = new ModelPart.Polygon(new ModelPart.Vertex[]{downNothEast, downNorthWest, upNorthWest, upNorthEast}, north.u(), north.v(), north.u() + dimX, north.v() + height, texTotalU, texTotalV, mirror, Direction.NORTH);
        }

        if (visibleFaces.contains(Direction.EAST)) {
            this.polygons[i++] = new ModelPart.Polygon(new ModelPart.Vertex[]{downSouthEast, downNothEast, upNorthEast, upSouthEast}, west.u(), west.v(), west.u() + dimZ, west.v() + height, texTotalU, texTotalV, mirror, Direction.EAST);
        }

        if (visibleFaces.contains(Direction.SOUTH)) {
            this.polygons[i] = new ModelPart.Polygon(new ModelPart.Vertex[]{downSouthWest, downSouthEast, upSouthEast, upSouthWest}, south.u(), south.v(), south.u() + dimX, south.v() + height, texTotalU, texTotalV, mirror, Direction.SOUTH);
        }

    }
}
