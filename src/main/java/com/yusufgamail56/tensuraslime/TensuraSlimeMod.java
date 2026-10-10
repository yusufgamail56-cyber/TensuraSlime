package com.yusufgamail56.tensuraslime;

import com.mojang.brigadier.Command;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

/**
 * Entry point for Slime Evolution RPG.
 *
 * This first milestone intentionally keeps the gameplay surface small:
 * it verifies that the NeoForge mod loads and exposes a server command.
 * Player progression, registered items, and custom entities will be added
 * in separate classes in later milestones.
 */
@Mod(TensuraSlimeMod.MOD_ID)
public final class TensuraSlimeMod {
    public static final String MOD_ID = "slimeevolution";

    public TensuraSlimeMod(IEventBus modEventBus) {
        NeoForge.EVENT_BUS.addListener(this::registerCommands);
    }

    private void registerCommands(RegisterCommandsEvent event) {
        event.getDispatcher().register(
            Commands.literal("slimeevolution")
                .then(Commands.literal("status")
                    .executes(context -> {
                        context.getSource().sendSuccess(
                            () -> Component.literal(
                                "[Slime Evolution RPG] Mod is loaded. Milestone 1 foundation is active."
                            ),
                            false
                        );
                        return Command.SINGLE_SUCCESS;
                    })
                )
        );
    }
}
