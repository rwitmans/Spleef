package org.gamecie.spleefcie.arena;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.gamecie.spleefcie.SpleefCie;
import org.gamecie.spleefcie.player.SpleefPlayer;

import java.util.ArrayList;
import java.util.List;

public class Arena extends BukkitRunnable {

    private SpleefCie plugin;
    private int id;
    private String name;
    private Location arenaSpawn;
    private Location lobbySpawn;
    private int minPlayers;
    private int gameStartTimer;
    private ArenaStatus arenaStatus = ArenaStatus.WAIT;


    private List<SpleefPlayer> playersInGame = new ArrayList<>();
    private List<SpleefPlayer> spectators = new ArrayList<>();
    private List<Block> blocks = new ArrayList<>();


    public Arena(SpleefCie plugin, Location loc, int id, int minPlayers) {
        this.plugin = plugin;
        this.arenaSpawn = loc;
        this.id = id;
        this.lobbySpawn = Bukkit.getWorld("world").getSpawnLocation();
        this.minPlayers = minPlayers;

        this.runTaskTimer(plugin, 20, 20);
    }

    public ArenaStatus getArenaStatus() {
        return arenaStatus;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Location getSpawn() {
        return this.arenaSpawn;
    }

    public List<SpleefPlayer> getPlayers() {
        return this.playersInGame;
    }

    public void addBrokenBlock(Block block) {
        blocks.add(block);
    }

    public String getName() {
        return name;
    }

    public void startGame() {
        gameStartTimer = plugin.getConfig().getInt("Timer.Start");
        arenaStatus = ArenaStatus.ONGOING;
        for (SpleefPlayer splayer : playersInGame) {
            Player player = Bukkit.getPlayer(splayer.getUuid());
            player.sendMessage("Game started!");
            player.getInventory().addItem(new ItemStack(Material.DIAMOND_SHOVEL));
        }
    }

    public void restart() {
        List<SpleefPlayer> playerList = new ArrayList<>();
        playerList.addAll(spectators);
        playerList.addAll(playersInGame);
        for (SpleefPlayer splayer : playerList) {
            removePlayer(splayer);
        }
        playersInGame.clear();
        spectators.clear();

        for (Block block : blocks) {
            block.setType(Material.SNOW_BLOCK);
        }
        blocks.clear();

        arenaStatus = ArenaStatus.WAIT;
    }

    public boolean addPlayer(SpleefPlayer splayer) {
        Player player = Bukkit.getPlayer(splayer.getUuid());
        if (arenaStatus == ArenaStatus.ONGOING) {
            player.sendMessage("Game has already started.");
            return false;
        } else if (splayer.getCurrentArena().getArenaStatus() == ArenaStatus.ONGOING) {
            player.sendMessage("You are already in-game");
            return false;
        }
        playersInGame.add(splayer);
        player.setGameMode(GameMode.SURVIVAL);
        player.getInventory().clear();
        player.setFoodLevel(20);
        player.setHealth(player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue());
        splayer.setCurrentArena(this);
        splayer.toggleInArena();
        player.teleport(arenaSpawn);
        return true;
    }

    public void removePlayer(SpleefPlayer splayer) {
        Player player = Bukkit.getPlayer(splayer.getUuid());
        player.teleport(arenaSpawn);
        player.setGameMode(GameMode.SURVIVAL);
        player.getInventory().clear();
        player.setFoodLevel(20);
        player.setHealth(player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue());
        splayer.setCurrentArena(null);
        splayer.toggleInArena();
        plugin.getArenaManager().removePlayer(player);
    }

    public void makeLose(SpleefPlayer splayer) {
        playersInGame.remove(splayer);

        Player loser = Bukkit.getPlayer(splayer.getUuid());
        loser.sendMessage("You died");

        spectators.add(splayer);
        loser.teleport(arenaSpawn);
        loser.setGameMode(GameMode.SPECTATOR);

        checkWin();
    }

    public void checkWin() {
        if (arenaStatus == ArenaStatus.ONGOING) {
            if (playersInGame.size() == 1) {
                SpleefPlayer splayerWinner = playersInGame.get(0);
                Player winner = Bukkit.getPlayer(splayerWinner.getUuid());
                for (SpleefPlayer splayer : spectators) {
                    Bukkit.getPlayer(splayer.getUuid()).sendMessage(
                            winner.getDisplayName() + "has won the game!"
                    );
                }
                winner.sendMessage("You have won the game!");
                playersInGame.remove(splayerWinner);
                spectators.add(splayerWinner);
            } else if (playersInGame.size() == 0) {
                restart();
            }
        }
    }

    @Override
    public void run() {
        if (arenaStatus == ArenaStatus.WAIT) {
            if (playersInGame.size() >= minPlayers) {
                if (gameStartTimer <= 0) {
                    startGame();
                } else {
                    gameStartTimer--;
                }
            } else {
                gameStartTimer = plugin.getConfig().getInt("Timer.Start");
            }
        } else {
            if (playersInGame.size() == 0) {
                restart();
                return;
            }
            for (SpleefPlayer splayer : playersInGame) {
                Player player = Bukkit.getPlayer(splayer.getUuid());
                String blockType = player.getLocation().getBlock().getType().name();
                if (blockType.contains("WATER") || blockType.contains("LAVA")) {
                    makeLose(splayer);
                }
            }
        }
    }
}
