package com.zvicraft.dev.blockcommand.utils;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import com.zvicraft.dev.blockcommand.Blockcommand;
import net.minecraft.world.level.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.jetbrains.annotations.NotNull;

public class BungeeManager implements PluginMessageListener {
    private final Blockcommand plugin;

    public BungeeManager(Blockcommand plugin) {
        this.plugin = plugin;

        plugin.getServer().getMessenger().registerOutgoingPluginChannel(plugin, "BungeeCord");
        plugin.getServer().getMessenger().registerIncomingPluginChannel(plugin, "BungeeCord", this);
    }

    public void unRegister() {
        plugin.getServer().getMessenger().unregisterOutgoingPluginChannel(plugin, "BungeeCord");
        plugin.getServer().getMessenger().unregisterIncomingPluginChannel(plugin, "BungeeCord", this);
    }

    public void moveServers(Player p, String server) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("Connect");
        out.writeUTF(server);
        p.sendPluginMessage(plugin, "BungeeCord", out.toByteArray());
    }

    @Override
    public void onPluginMessageReceived(@   NotNull String channel, @NotNull Player player, @NotNull byte[] message) {
        if (!channel.equals("BungeeCord")) return;
        // RECEIVE INFO FROM BUNGEECORD
    }


}
