package org.gamecie.spleefcie;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class GameListener implements Listener {

    private static List<Player> players = new ArrayList<>();
    private static SpleefCie plugin;

    public GameListener(SpleefCie plugin) {
        this.plugin = plugin;
    }

    public void addPlayer(Player p) {
        players.add(p);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        Bukkit.getLogger().info("Block break event");
        boolean hasPlayerJoined = true;
        e.setDropItems(false);
        for (Player p : players) {
            if (e.getPlayer().equals(p)) {
                hasPlayerJoined = true;
                break;
            }
        }
        if (hasPlayerJoined) {

            e.getPlayer().sendMessage("You have attempted to break a block");
            if (!e.getBlock().getType().equals(Material.SNOW_BLOCK)) {
                e.setCancelled(true);
            } else {
                e.getPlayer().getInventory().addItem(new ItemStack(Material.SNOWBALL, 4));
            }
        }
    }

    @EventHandler
    public void onSnowballHitBlock(ProjectileHitEvent e) {
        Bukkit.getLogger().info("Snowball block event");
        if (e.getEntity() instanceof Snowball) {
            if (e.getHitBlock().getType().equals(Material.SNOW_BLOCK)) {
                e.getHitBlock().breakNaturally();
            }
        }
    }

    @EventHandler
    public void onMeleeHit(EntityDamageByEntityEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onLavaHit(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player) {
            if (e.getCause().equals(EntityDamageEvent.DamageCause.LAVA)) {
                ((Player) e.getEntity()).setGameMode(GameMode.SPECTATOR);
                ((Player) e.getEntity()).sendMessage("You died");
            } else {
                e.setCancelled(true);
            }
        }
    }
}
