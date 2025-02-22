package com.lifesteal.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class CommandHandler implements CommandExecutor{
    public Plugin plugin;
    public CommandHandler(Plugin plugin){
        this.plugin = plugin;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equals("withdraw") && sender instanceof Player){
            Withdraw.withdrawHearts((Player)sender, plugin);
        }

        if (command.getName().equals("lifestealrules") && sender instanceof Player){
            Rules.rules((Player)sender);
        }
        if (command.getName().equals("banneditems") && sender instanceof Player){
            BannedItemsCommand.bannedItemsCommand((Player)sender);
        }
       
        return true;
    }


    public Plugin getPlugin() {
        return plugin;
    }
}
