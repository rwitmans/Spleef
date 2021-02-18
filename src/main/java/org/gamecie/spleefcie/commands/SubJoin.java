package org.gamecie.spleefcie.commands;


import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.eyedevelop.prometheus.abstracts.command.SubCommand;
import org.eyedevelop.prometheus.interfaces.command.ICommand;
import org.gamecie.spleefcie.arena.ArenaManager;

public class SubJoin extends SubCommand {

    public SubJoin(String name, ICommand c) {
        super(name, c);
    }

    public SubJoin(String name, ICommand c, String permissionName) {
        super(name, c, permissionName);
    }

    @Override
    public String getDescription() {
        return "Join a spleef a random spleef arena.";
    }

    @Override
    public String getUsage() {
        return "join";
    }

    @Override
    public boolean action(CommandSender commandSender, String[] strings) {
        //For now, we join the one arena which exists
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage("You must be a player to use this command.");
        }

        ArenaManager.getManager().addPlayer((Player) commandSender, 1);
        return true;
    }
}
