package org.gamecie.spleefcie.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.gamecie.spleefcie.ArenaManager;
import org.gamecie.spleefcie.SpleefCie;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class CreateArenaCMD implements CommandExecutor {

    private static SpleefCie plugin = SpleefCie.plugin;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (command.getName().equalsIgnoreCase("arena")) {
                if (args.length >= 4) {
                    if (args[0].equalsIgnoreCase("create")) {
                        String name = args[1];
                        try {
                            int id = Integer.parseInt(args[2]);
                            boolean activated = Boolean.parseBoolean(args[3]);
                            plugin.arenaManagerHashMap.put(name, new ArenaManager(name, id,
                                    false, activated, new ArrayList<UUID>(), player.getLocation()));
                            player.sendMessage(ChatColor.GREEN + "Arena created");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    player.sendMessage(ChatColor.RED + "Invalid length");
                    player.sendMessage(ChatColor.RED + "Usage: /arena create <name> <id> <activated>");

                }

            }
        }
        return true;
    }
}
