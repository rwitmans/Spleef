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
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.gamecie.spleefcie.arena.ArenaStatus;
import org.gamecie.spleefcie.player.SpleefPlayer;

import java.util.ArrayList;
import java.util.List;

public class SpleefListener implements Listener {

    private static List<Player> players = new ArrayList<>();
    private static SpleefCie plugin;

    public SpleefListener(SpleefCie plugin) {
        this.plugin = plugin;
    }

    public void addPlayer(Player p) {
        players.add(p);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        plugin.getSpleefPlayerManager().registerPlayer(p);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        SpleefPlayer splayer = plugin.getSpleefPlayerManager().getSpleefPlayer(e.getPlayer().getUniqueId());
        if (splayer.isInArena() && splayer.getCurrentArena().getArenaStatus().equals(ArenaStatus.ONGOING)) {
            e.getPlayer().sendMessage("You have attempted to break a block");
            if (!e.getBlock().getType().equals(Material.SNOW_BLOCK)) {
                e.setCancelled(true);
            } else {
                e.setDropItems(false);
                e.getPlayer().getInventory().addItem(new ItemStack(Material.SNOWBALL, 4));
                splayer.getCurrentArena().addBrokenBlock(e.getBlock());
            }
        } else {
            e.setCancelled(true);
        }
    }


    @EventHandler
    public void onSnowballHitBlock(ProjectileHitEvent e) {
        Bukkit.getLogger().info("Snowball block event");
        if (e.getEntity() instanceof Snowball) {
            if (e.getHitBlock().getType().equals(Material.SNOW_BLOCK)) {
                if (e.getEntity().getShooter() instanceof Player) {
                    Player player = ((Player) e.getEntity().getShooter());
                    SpleefPlayer splayer = plugin.getSpleefPlayerManager().getSpleefPlayer(player.getUniqueId());
                    e.getHitBlock().breakNaturally();
                    splayer.getCurrentArena().addBrokenBlock(e.getHitBlock());
                }
            }
        }
    }

    @EventHandler
    public void onHit(EntityDamageByEntityEvent e) {
        e.setCancelled(true);
    }
}
