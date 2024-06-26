package com.zvicraft.dev.blockcommand;

import com.zvicraft.dev.blockcommand.commands.CommadsConfig;
import com.zvicraft.dev.blockcommand.utils.Cuboid;
import com.zvicraft.dev.blockcommand.utils.Utils;
import me.clip.placeholderapi.PlaceholderAPI;
import net.minecraft.world.entity.ai.behavior.BackUpIfTooClose;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

public final class Blockcommand extends JavaPlugin implements Listener       {
    public static Blockcommand plugin;
    private Cuboid cuboid1;
    private Cuboid cuboid2;
    private Cuboid cuboid3;
    private Cuboid cuboid4;
    public void loadConfig() {
        getConfig().options().copyDefaults(false);
        saveDefaultConfig();
    }
    @Override
    public void onEnable() {
        plugin = this;
    loadConfig();
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            /*
             * We register the EventListener here, when PlaceholderAPI is installed.
             * Since all events are in the main class (this class), we simply use "this"
             */
            Bukkit.getPluginManager().registerEvents(this, this);
        } else {
            /*
             * We inform about the fact that PlaceholderAPI isn't installed and then
             * disable this plugin to prevent issues.
             */
            getLogger().warning("Could not find PlaceholderAPI! This plugin is required.");
            Bukkit.getPluginManager().disablePlugin(this);
        }
        // Plugin startup logic
        getLogger().info(Utils.chat( "&ctest on"));
        Bukkit.getServer().getPluginManager().registerEvents(this, this);

        plugin.getCommand("blockreload").setExecutor(new CommadsConfig());

        cuboid1 = new Cuboid(
                new Location(Bukkit.getWorld(plugin.getConfig().getString("world1")), plugin.getConfig().getInt("x1.1"), plugin.getConfig().getInt("y1.1"), plugin.getConfig().getInt("z1.1")),
                new Location(Bukkit.getWorld(plugin.getConfig().getString("world1")), plugin.getConfig().getInt("x2.1"), plugin.getConfig().getInt("y2.1"), plugin.getConfig().getInt("z2.1")));

    cuboid2 = new Cuboid(
                new Location(Bukkit.getWorld(plugin.getConfig().getString("world2")), plugin.getConfig().getInt("x1.2"), plugin.getConfig().getInt("y1.2"), plugin.getConfig().getInt("z1.2")),
                new Location(Bukkit.getWorld(plugin.getConfig().getString("world2")), plugin.getConfig().getInt("x2.2"), plugin.getConfig().getInt("y2.2"), plugin.getConfig().getInt("z2.")));


    cuboid3 = new Cuboid(
                new Location(Bukkit.getWorld(plugin.getConfig().getString("world3")), plugin.getConfig().getInt("x1.3"), plugin.getConfig().getInt("y1.3"), plugin.getConfig().getInt("z1.3")),
            new Location(Bukkit.getWorld(plugin.getConfig().getString("world3")), plugin.getConfig().getInt("x2.3"), plugin.getConfig().getInt("y2.3"), plugin.getConfig().getInt("z2.3")));

 cuboid4 = new Cuboid(
        new Location(Bukkit.getWorld(plugin.getConfig().getString("world4")), plugin.getConfig().getInt("x1.4"), plugin.getConfig().getInt("y1.4"), plugin.getConfig().getInt("z1.4")),
        new Location(Bukkit.getWorld(plugin.getConfig().getString("world4")), plugin.getConfig().getInt("x2.4"), plugin.getConfig().getInt("y2.4"), plugin.getConfig().getInt("z2.4")));

        }
    @Override
    public void onDisable() {

    getServer().getScheduler().cancelTasks(this);
        getLogger().info(Utils.chat( "&atest off"));

        // Plugin shutdown logic
    }
    // WALKING
    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        if (event.getFrom().equals(event.getTo())) {
            return;
        }
        Player player = event.getPlayer();
        Player p = Bukkit.getPlayer(getName());

        Block block1 = player.getLocation().getBlock().getRelative(BlockFace.DOWN);
        Block block2 = player.getLocation().getBlock().getRelative(BlockFace.UP);
        if (block1.getType() == Material.LODESTONE) {


            if (cuboid1.contains(player.getLocation())) {
                new BukkitRunnable() {
                    public void run() {
                        if (plugin.getConfig().getBoolean("isConsole1")){
                         String cmdconsole = plugin.getConfig().getString("CMD1");
                          cmdconsole =  PlaceholderAPI.setPlaceholders(player, cmdconsole);
                          Bukkit.dispatchCommand(Bukkit.getConsoleSender(),cmdconsole);



                        }
                        else
                            player.performCommand(getConfig().getString("CMD1"));
                    }
                }.runTaskLater(plugin, plugin.getConfig().getLong("time")*20L);

               BukkitTask task = getServer().getScheduler().runTaskLater(this, () -> player.performCommand(plugin.getConfig().getString("CMD-serve1")), plugin.getConfig().getLong("time-server")*20L);

                task.cancel();
                player.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, 60, 3, false, false, false));
                dir2vec(player.getLocation(), player.getLocation());
                if (player.isFlying()) {
                    player.setFlying(true);
                    player.setAllowFlight(false);
                }

            }


        } else if (block1.getType() == Material.LODESTONE) {
            if (cuboid2.contains(player.getLocation())) {
                new BukkitRunnable() {
                    public void run() {
                        if (plugin.getConfig().getBoolean("isConsole2")){
                            String cmdconsole = plugin.getConfig().getString("CMD2");
                            cmdconsole =  PlaceholderAPI.setPlaceholders(player, cmdconsole);
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(),cmdconsole);     }                   else
                            player.performCommand(getConfig().getString("CMD3"));

                    }
                }.runTaskLater(plugin, plugin.getConfig().getLong("time")*20L);
                new BukkitRunnable() {
                    public void run() {
                        player.performCommand(getConfig().getString("CMD-serve2"));

                    }
                }.runTaskLater(plugin, plugin.getConfig().getLong("time-server")*20L);
                player.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, 60, 3, false, false, false));
                dir2vec(player.getLocation(), player.getLocation());
                if (player.isFlying()) {
                    player.setFlying(true);
                    player.setAllowFlight(false);
                }

            }
        } else if (block1.getType() == Material.LODESTONE) {
            if (cuboid3.contains(player.getLocation())) {
                new BukkitRunnable() {
                    public void run() {
                        if (plugin.getConfig().getBoolean("isConsole3")){
                            String cmdconsole = plugin.getConfig().getString("CMD3");
                            cmdconsole =  PlaceholderAPI.setPlaceholders(player, cmdconsole);
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(),cmdconsole);     }
                        else
                            player.performCommand(getConfig().getString("CMD3"));
                    }

                }.runTaskLater(plugin, plugin.getConfig().getLong("time")*20L);
                new BukkitRunnable() {
                    public void run() {
                        player.performCommand(getConfig().getString("CMD-serve3"));

                    }
                }.runTaskLater(plugin, plugin.getConfig().getLong("time-server")*20L);
                player.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, 60, 3, false, false, false));
                dir2vec(player.getLocation(), player.getLocation());
                if (player.isFlying()) {
                    player.setFlying(true);
                    player.setAllowFlight(false);
                }
            }
        }

           else if (block1.getType() == Material.LODESTONE) {
            if (cuboid4.contains(player.getLocation())) {
                new BukkitRunnable() {
                    public void run() {
                        if (plugin.getConfig().getBoolean("isConsole4")){
                            String cmdconsole = plugin.getConfig().getString("CMD4");
                            cmdconsole =  PlaceholderAPI.setPlaceholders(player, cmdconsole);
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(),cmdconsole);     }
                        else
                         player.performCommand(getConfig().getString("CMD4"));


                    }
                }.runTaskLater(plugin, plugin.getConfig().getLong("time")*20L);
                new BukkitRunnable() {
                    public void run() {
                        player.performCommand(getConfig().getString("CMD-serve4"));

                    }
                }.runTaskLater(plugin, plugin.getConfig().getLong("time-server")*20L);
                player.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, 60, 3, false, false, false));
                dir2vec(player.getLocation(), player.getLocation());
                if (player.isFlying()) {
                    player.setFlying(true);
                    player.setAllowFlight(false);
                }

//            Utils.sendServer(player,"galaxysmp");]
            }

        }
        }
    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        for (PotionEffect effect :player.getActivePotionEffects ()){
            player.removePotionEffect(effect.getType ());}

    }

    public static Vector dir2vec(Location center, Location as)
    {
        Vector vecCenter = center.toVector().normalize();
        Vector vecAs = as.toVector().normalize();

        Vector vecResult = vecAs.subtract(vecCenter);

        //vecResult.setX(vecCenter.getX());
        //vecResult.setZ(vecCenter.getZ());
        vecResult.setY(vecResult.getY() + 0.1);
        vecResult.normalize();

        return vecResult;//.multiply(3);

    }

    public static void Vector(Player p){
            Vector direction = p.getLocation().toVector().subtract(p.getLocation().toVector()).normalize();
            direction.setY(plugin.getConfig().getInt("speed"));
            p.setVelocity(direction);
            p.setFlying(true);
            p.setAllowFlight(true);

        }
    }
