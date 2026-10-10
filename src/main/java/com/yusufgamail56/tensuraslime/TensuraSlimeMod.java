package com.yusufgamail56.tensuraslime;

import com.mojang.brigadier.arguments.StringArgumentType;
import net.minecraft.commands.Commands;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * Initial playable prototype for a Tensura-inspired slime progression mod.
 * Mana and learned abilities are handled on the logical server.
 */
@Mod(TensuraSlimeMod.MOD_ID)
public class TensuraSlimeMod {
    public static final String MOD_ID = "tensuraslime";

    private static final String KEY_MANA = "tensuraslime_mana";
    private static final String KEY_MANA_PULSE = "tensuraslime_learned_mana_pulse";
    private static final int MAX_MANA = 100;
    private static final int MANA_PULSE_COST = 20;

    public TensuraSlimeMod() {
        MinecraftForge.EVENT_BUS.addListener(this::registerCommands);
    }

    private void registerCommands(RegisterCommandsEvent event) {
        event.getDispatcher().register(Commands.literal("tensura")
            .then(Commands.literal("status")
                .executes(context -> showStatus(context.getSource().getPlayerOrException())))
            .then(Commands.literal("learn")
                .then(Commands.argument("ability", StringArgumentType.word())
                    .suggests((context, builder) -> {
                        builder.suggest("mana_pulse");
                        return builder.buildFuture();
                    })
                    .executes(context -> learnAbility(
                        context.getSource().getPlayerOrException(),
                        StringArgumentType.getString(context, "ability")
                    ))))
            .then(Commands.literal("cast")
                .then(Commands.argument("ability", StringArgumentType.word())
                    .suggests((context, builder) -> {
                        builder.suggest("mana_pulse");
                        return builder.buildFuture();
                    })
                    .executes(context -> castAbility(
                        context.getSource().getPlayerOrException(),
                        StringArgumentType.getString(context, "ability")
                    ))))
        );
    }

    private static CompoundTag getPlayerData(ServerPlayer player) {
        CompoundTag data = player.getPersistentData();
        if (!data.contains(KEY_MANA)) {
            data.putInt(KEY_MANA, MAX_MANA);
        }
        return data;
    }

    private static int showStatus(ServerPlayer player) {
        CompoundTag data = getPlayerData(player);
        boolean learned = data.getBoolean(KEY_MANA_PULSE);
        player.sendSystemMessage(Component.literal("§b[Büyük Bilge] §fDurum analizi tamamlandı."));
        player.sendSystemMessage(Component.literal("§7Mana: §b" + data.getInt(KEY_MANA) + "§7/§b" + MAX_MANA));
        player.sendSystemMessage(Component.literal("§7Mana Darbesi: " + (learned ? "§aÖğrenildi" : "§cÖğrenilmedi")));
        return 1;
    }

    private static int learnAbility(ServerPlayer player, String ability) {
        CompoundTag data = getPlayerData(player);
        if (!ability.equalsIgnoreCase("mana_pulse")) {
            player.sendSystemMessage(Component.literal("§c[Büyük Bilge] Bu yetenek tanımlı değil: " + ability));
            return 0;
        }
        if (data.getBoolean(KEY_MANA_PULSE)) {
            player.sendSystemMessage(Component.literal("§b[Büyük Bilge] §fMana Darbesi zaten öğrenilmiş."));
            return 1;
        }
        data.putBoolean(KEY_MANA_PULSE, true);
        player.sendSystemMessage(Component.literal("§b[Büyük Bilge] §fYeni yetenek öğrenildi: §eMana Darbesi"));
        player.sendSystemMessage(Component.literal("§7Kullanım: §f/tensura cast mana_pulse"));
        return 1;
    }

    private static int castAbility(ServerPlayer player, String ability) {
        CompoundTag data = getPlayerData(player);
        if (!ability.equalsIgnoreCase("mana_pulse")) {
            player.sendSystemMessage(Component.literal("§c[Büyük Bilge] Bu yetenek tanımlı değil: " + ability));
            return 0;
        }
        if (!data.getBoolean(KEY_MANA_PULSE)) {
            player.sendSystemMessage(Component.literal("§c[Büyük Bilge] Önce öğren: /tensura learn mana_pulse"));
            return 0;
        }

        int mana = data.getInt(KEY_MANA);
        if (mana < MANA_PULSE_COST) {
            player.sendSystemMessage(Component.literal("§c[Büyük Bilge] Yetersiz mana. Gereken: " + MANA_PULSE_COST));
            return 0;
        }

        data.putInt(KEY_MANA, mana - MANA_PULSE_COST);
        // Temporary prototype effect; a proper projectile/area attack will come later.
        player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 100, 0));
        player.sendSystemMessage(Component.literal("§b[Büyük Bilge] §fMana Darbesi etkinleştirildi. §7(-20 mana)"));
        player.sendSystemMessage(Component.literal("§7Kalan mana: §b" + data.getInt(KEY_MANA)));
        return 1;
    }
}
