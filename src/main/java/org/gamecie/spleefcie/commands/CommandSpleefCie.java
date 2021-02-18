package org.gamecie.spleefcie.commands;

import org.eyedevelop.prometheus.abstracts.command.Command;

public class CommandSpleefCie extends Command {

    public CommandSpleefCie(String name) {
        super(name);
    }

    public CommandSpleefCie(String name, String permissionName) {
        super(name, permissionName);
    }
}
