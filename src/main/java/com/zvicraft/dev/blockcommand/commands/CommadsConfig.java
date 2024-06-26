package com.zvicraft.dev.blockcommand.commands;

import com.zvicraft.dev.blockcommand.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static com.zvicraft.dev.blockcommand.Blockcommand.plugin;


public class CommadsConfig implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        Player player = (Player) sender;

        if (player.hasPermission("blockcmd.reload")) {
            plugin.reloadConfig();
            player.sendMessage(Utils.chat(plugin.getConfig().getString("reload")));
        } else {
            player.sendMessage(Utils.chat(plugin.getConfig().getString("none-permission")));
        }
        return true;
    }
}
