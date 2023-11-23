package com.userofbricks.expanded_combat.init;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.tree.LiteralCommandNode;
import com.userofbricks.expanded_combat.commands.CommandIncreaseCharge;
import com.userofbricks.expanded_combat.commands.CommandSetAddedHearts;
import com.userofbricks.expanded_combat.commands.CommandSetStolenHearts;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;

import static com.userofbricks.expanded_combat.ExpandedCombat.MODID;

public class ModCommands {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        LiteralCommandNode<CommandSourceStack> commands = dispatcher.register(
                Commands.literal(MODID)
                        .then(CommandIncreaseCharge.register(dispatcher))
                        .then(CommandSetStolenHearts.register(dispatcher))
                        .then(CommandSetAddedHearts.register(dispatcher))
        );

        dispatcher.register(Commands.literal("EC").redirect(commands));
    }
}
