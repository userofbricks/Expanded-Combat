package com.userofbricks.expanded_combat.item;

import com.userofbricks.expanded_combat.data.material.Material;
import com.userofbricks.expanded_combat.init.Materials;
import com.userofbricks.expanded_combat.init.Registries;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponents;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.Item;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Objects;
import java.util.Optional;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ECBowItem extends BowItem implements IMaterialItem {
    public final Holder.Reference<Material> material;

    public ECBowItem(Item.Properties builder, Holder.Reference<Material> material) {
        super(builder);
        this.material = material;
    }
    public DataComponentMap components() {
        DataComponentMap.Builder components = DataComponentMap.builder().addAll(super.components());

        components.set(DataComponents.MAX_DAMAGE, getMaterial().durabilities().bowCrossbowDurability())
                .set(DataComponents.MAX_STACK_SIZE, 1);
        if (getMaterial().defense().fireResistant()) components.set(DataComponents.FIRE_RESISTANT, Unit.INSTANCE);

        return Item.Properties.validateComponents(components.build());
    }

    private float getVelocitiMultiplier() {
        return getMaterial().offense().velocityMultiplier();
    }

    protected void shootProjectile(LivingEntity pShooter, Projectile pProjectile, int pIndex, float pVelocity, float pInaccuracy, float pAngle, @Nullable LivingEntity pTarget) {
        pProjectile.shootFromRotation(pShooter, pShooter.getXRot(), pShooter.getYRot() + pAngle, 0.0F, pVelocity * this.getVelocitiMultiplier(), pInaccuracy);
    }

    @Override
    public Holder.Reference<Material> getMaterialReference() {
        return material;
    }

    public Material getMaterial() {
        if (material.isBound()) return material.value();

        ClientPacketListener clientPacketListener = Minecraft.getInstance().getConnection();
        if (clientPacketListener == null) return Materials.NOTBOUNDBACKUP;

        Optional<Holder.Reference<Material>> reference = clientPacketListener.registryAccess().registryOrThrow(Registries.MATERIAL_REGISTRY_KEY).getHolder(material.key());
        return reference.map(Holder.Reference::value).orElse(Materials.NOTBOUNDBACKUP);
    }
}
