// 
// Decompiled by Procyon v0.5.36
// 

package com.userofbricks.expandedcombat.util;

import net.minecraft.entity.Entity;
import net.minecraft.util.EntityDamageSource;

public class OffhandAttackDamageSource extends EntityDamageSource
{
    public OffhandAttackDamageSource(final Entity damageSourceEntityIn) {
        super("player", damageSourceEntityIn);
    }
}
