package com.lifesteal.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.lifesteal.Plugin;

public class CommandHandler implements CommandExecutor{
    public Plugin plugin;
    public CommandHandler(Plugin plugin){
        this.plugin = plugin;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equals("withdraw") && sender instanceof Player){
            if(args.length >= 1){
                if(args[0].equalsIgnoreCase("help")){
                    new Withdraw().helpMessage((Player)sender);
                }
            }
            new Withdraw().withdrawHearts((Player)sender, args, plugin);
        }
        if (command.getName().equals("lifestealrules") && sender instanceof Player){
            Rules.rules((Player)sender);
        }
        if (command.getName().equals("banneditems") && sender instanceof Player){
            BannedItemsCommand.bannedItemsCommand((Player)sender);
        }
        if (command.getName().equals("addheart") && sender instanceof Player){
            AddHeart addHeart = new AddHeart((Player)sender, plugin);
            if(args.length <= 0){
                addHeart.help();
                return false;
            }
            addHeart.addHeart(args);
        }
       
        return true;
    }


    public Plugin getPlugin() {
        return plugin;
    }
}
