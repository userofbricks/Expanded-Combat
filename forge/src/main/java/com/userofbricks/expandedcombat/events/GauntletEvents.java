package com.userofbricks.expandedcombat.events;

import com.userofbricks.expandedcombat.ExpandedCombat;
import com.userofbricks.expandedcombat.forge.ExpandedCombatForge;
import com.userofbricks.expandedcombat.item.ECGauntletItem;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.Optional;

@Mod.EventBusSubscriber(modid = ExpandedCombat.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class GauntletEvents {

    @SubscribeEvent
    public static void onEquipmentChange(LivingEquipmentChangeEvent ev) {
        if ((ev.getSlot() == EquipmentSlot.MAINHAND || ev.getSlot() == EquipmentSlot.OFFHAND) && ev.getEntityLiving() instanceof Player) {
            Player player = (Player)ev.getEntityLiving();
            ItemStack toStack = ev.getTo();
            Optional<ImmutableTriple<String, Integer, ItemStack>> optionalImmutableTriple = CuriosApi.getCuriosHelper().findEquippedCurio(ExpandedCombatForge.hands_predicate, player);
            ItemStack gauntlet = optionalImmutableTriple.map(stringIntegerItemStackImmutableTriple -> stringIntegerItemStackImmutableTriple.right).orElse(ItemStack.EMPTY);
            if (gauntlet.getItem() instanceof ECGauntletItem && optionalImmutableTriple.isPresent()) {
                if ((toStack.getItem() instanceof SwordItem || toStack.getItem() instanceof AxeItem)) {
                    ((ECGauntletItem) gauntlet.getItem()).hasWeaponInHand = true;
                } else {
                    ((ECGauntletItem) gauntlet.getItem()).hasWeaponInHand = false;
                }
            }
        }
    }

    public static void DamageGauntletEvent(AttackEntityEvent event) {
        Player player = event.getPlayer();
        Optional<ImmutableTriple<String, Integer, ItemStack>> optionalImmutableTriple = CuriosApi.getCuriosHelper().findEquippedCurio(ExpandedCombatForge.hands_predicate, player);
        ItemStack stack = optionalImmutableTriple.map(stringIntegerItemStackImmutableTriple -> stringIntegerItemStackImmutableTriple.right).orElse(ItemStack.EMPTY);
        CuriosApi.getCuriosHelper().getCuriosHandler(player).ifPresent(iCurioItemHandler -> {
            if (!player.isCreative() && stack.getItem() instanceof ECGauntletItem && optionalImmutableTriple.isPresent()) {
                stack.hurtAndBreak(1, (LivingEntity)player, damager -> CuriosApi.getCuriosHelper().onBrokenCurio(optionalImmutableTriple.get().getLeft(), optionalImmutableTriple.get().getMiddle(), damager));
            }
        });
    }
}
