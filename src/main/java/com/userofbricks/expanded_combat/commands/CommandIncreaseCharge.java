package com.userofbricks.expanded_combat.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.userofbricks.expanded_combat.init.ItemDataComponents;
import com.userofbricks.expanded_combat.item.HeartStealerItem;
import com.userofbricks.expanded_combat.datagen.LangStrings;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

import java.util.Collection;

public class CommandIncreaseCharge{
    private static final DynamicCommandExceptionType ERROR_NOT_LIVING_ENTITY = new DynamicCommandExceptionType((p_137029_) -> Component.translatable(LangStrings.increaseChargeNotLivingEntity, p_137029_));
    private static final DynamicCommandExceptionType ERROR_NO_ITEM = new DynamicCommandExceptionType((p_137027_) -> Component.translatable(LangStrings.increaseChargeNoItem, p_137027_));
    private static final SimpleCommandExceptionType ERROR_NOTHING_HAPPENED = new SimpleCommandExceptionType(Component.translatable(LangStrings.increaseChargeNothingHappened));

    public static ArgumentBuilder<CommandSourceStack, ?> register(CommandDispatcher<CommandSourceStack> dispatcher) {
        return Commands.literal("IncreaseCharge")
                .requires(cs -> cs.hasPermission(1))
                .then(Commands.argument("targets", EntityArgument.entities())
                    .then(Commands
                        .argument("amount", IntegerArgumentType.integer())
                                .executes((commandSourceStackSource) -> addAmountToMainHand(commandSourceStackSource.getSource(), EntityArgument.getEntities(commandSourceStackSource, "targets"), commandSourceStackSource.getArgument("amount", Integer.class)))));
    }

    //Doesn't update clientside
    private static int addAmountToMainHand(CommandSourceStack source, Collection<? extends Entity> targets, Integer amount) throws CommandSyntaxException {
        int i = 0;
        for (Entity entity : targets) {
            if (entity instanceof LivingEntity livingEntity) {
                ItemStack itemStack = livingEntity.getMainHandItem();
                if (!itemStack.isEmpty() && itemStack.getItem() instanceof HeartStealerItem) {
                    itemStack.set(ItemDataComponents.CHARGE, itemStack.getOrDefault(ItemDataComponents.CHARGE, 0) + amount);
                    ++i;
                } else if (targets.size() == 1) {
                    throw ERROR_NO_ITEM.create(livingEntity.getName().getString());
                }
            } else if (targets.size() == 1) {
                throw ERROR_NOT_LIVING_ENTITY.create(entity.getName().getString());
            }
        }

        if (i == 0) {
            throw ERROR_NOTHING_HAPPENED.create();
        } else {
            if (targets.size() == 1) {
                source.sendSuccess(() -> Component.translatable(LangStrings.increaseChargeSingleSuccess, amount, targets.iterator().next().getDisplayName()), true);
            } else {
                source.sendSuccess(() -> Component.translatable(LangStrings.increaseChargeMultipleSuccesses, amount, targets.size()), true);
            }
            return i;
        }
    }
}
