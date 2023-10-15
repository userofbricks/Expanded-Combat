package com.userofbricks.expanded_combat.item;

import com.userofbricks.expanded_combat.enchentments.GroundSlamEnchantment;
import com.userofbricks.expanded_combat.entity.ECFallingBlockEntity;
import com.userofbricks.expanded_combat.item.materials.Material;
import com.userofbricks.expanded_combat.item.materials.WeaponMaterial;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ECHammerWeaponItem extends ECWeaponItem{
    public ECHammerWeaponItem(Material material, WeaponMaterial weapon, Properties properties) {
        super(material, weapon, properties);
    }

    @Override
    public int getEnchantmentLevel(ItemStack stack, Enchantment enchantment) {

        return super.getEnchantmentLevel(stack, enchantment) + (enchantment instanceof GroundSlamEnchantment ? 2 : 0);
    }

    protected static void GroundSlam(float spreadarc, int distance, float maxy, float vec, boolean grab, float airborne, @NotNull LivingEntity attacker, int slamLevel) {
        float dmg = (float) (attacker.getAttributeValue(Attributes.ATTACK_DAMAGE) * 0.9);
        dmg += (dmg * 0.05f) * (slamLevel - 1);

        double perpFacing = (double)attacker.yBodyRot * 0.017453292519943295;
        double facingAngle = perpFacing + 1.5707963267948966;
        int hitY = Mth.floor(attacker.getBoundingBox().minY - 0.5);
        double spread = Math.PI * (double)spreadarc;
        int arcLen = Mth.ceil((double)distance * spread);
        double minY = attacker.getY() - 1.0;
        double maxY = attacker.getY() + (double)maxy;

        for(int i = 0; i < arcLen; ++i) {
            double theta = ((double)i / ((double)arcLen - 1.0) - 0.5) * spread + facingAngle;
            double vx = Math.cos(theta);
            double vz = Math.sin(theta);
            double px = attacker.getX() + vx * (double)distance + (double)vec * Math.cos((double)(attacker.yBodyRot + 90.0F) * Math.PI / 180.0);
            double pz = attacker.getZ() + vz * (double)distance + (double)vec * Math.sin((double)(attacker.yBodyRot + 90.0F) * Math.PI / 180.0);
            float factor = 1.0F - (float)distance / 12.0F;
            int hitX = Mth.floor(px);
            int hitZ = Mth.floor(pz);
            BlockPos pos = new BlockPos(hitX, hitY, hitZ);
            BlockPos abovePos = (new BlockPos(pos)).above();
            BlockState block = attacker.level().getBlockState(pos);
            BlockState blockAbove = attacker.level().getBlockState(abovePos);
            if (block != Blocks.AIR.defaultBlockState() && !block.hasBlockEntity() && !blockAbove.blocksMotion()) {
                ECFallingBlockEntity fallingBlockEntity = new ECFallingBlockEntity(attacker.level(), (double)hitX + 0.5, (double)hitY + 1.125, (double)hitZ + 0.5, block, 10);
                fallingBlockEntity.push(0.0, 0.075 + attacker.getRandom().nextGaussian() * 0.07, 0.0);
                attacker.level().addFreshEntity(fallingBlockEntity);
            }

            AABB selection = new AABB(px - 0.5, minY, pz - 0.5, px + 0.5, maxY, pz + 0.5);
            List<LivingEntity> hit = attacker.level().getEntitiesOfClass(LivingEntity.class, selection);

            for (LivingEntity entity : hit) {
                if (!attacker.isAlliedTo(entity) && entity != attacker) {
                    DamageSources damageSources = attacker.damageSources();
                    boolean flag = entity.hurt((attacker instanceof Player player ? damageSources.playerAttack(player) : damageSources.mobAttack(attacker)), dmg);
                    if (flag) {
                        if (grab) {
                            double magnitude = -4.0;
                            double x = vx * (double) (1.0F - factor) * magnitude;
                            double y = 0.0;
                            if (entity.onGround()) {
                                y += 0.15;
                            }

                            double z = vz * (double) (1.0F - factor) * magnitude;
                            entity.setDeltaMovement(entity.getDeltaMovement().add(x, y, z));
                        } else {
                            entity.setDeltaMovement(entity.getDeltaMovement().add(0.0, (double) (airborne * (float) distance) + attacker.level().random.nextDouble() * 0.15, 0.0));
                        }
                    }
                }
            }
        }

    }
}
