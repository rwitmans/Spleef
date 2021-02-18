package org.gamecie.spleefcie.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.eyedevelop.prometheus.abstracts.command.SubCommand;
import org.eyedevelop.prometheus.interfaces.command.ICommand;
import org.gamecie.spleefcie.arena.ArenaManager;

public class SubCreate extends SubCommand {

    public SubCreate(String name, ICommand c) {
        super(name, c);
    }

    public SubCreate(String name, ICommand c, String permissionName) {
        super(name, c, permissionName);
    }

    @Override
    public String getDescription() {
        return "Create a new Spleef Arena at your current location.";
    }

    @Override
    public String getUsage() {
        return "create";
    }

    @Override
    public boolean action(CommandSender commandSender, String[] strings) {
        Player p = (Player) commandSender;

        ArenaManager.getManager().createArena(p.getLocation());
        p.sendMessage("Arena has been created at " + p.getLocation().toString());

        return true;
    }
}
