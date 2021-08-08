// 
// Decompiled by Procyon v0.5.36
// 

package com.userofbricks.expandedcombat.util;

import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.entity.Entity;

public class OffhandAttackDamageSource extends EntityDamageSource
{
    public OffhandAttackDamageSource(final Entity damageSourceEntityIn) {
        super("player", damageSourceEntityIn);
    }
}
