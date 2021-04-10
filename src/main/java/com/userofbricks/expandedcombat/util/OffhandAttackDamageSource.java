package com.userofbricks.expandedcombat.util;

import net.minecraft.entity.Entity;
import net.minecraft.util.EntityDamageSource;

public class OffhandAttackDamageSource extends EntityDamageSource {

    public OffhandAttackDamageSource(Entity damageSourceEntityIn) {
        super("player", damageSourceEntityIn);
    }
}
