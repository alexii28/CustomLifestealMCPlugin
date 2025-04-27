package com.lifesteal.Commands;

import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;

public class Tutorial {
    public static boolean tutorial(Player player){
        player.sendMessage(ChatColor.GOLD + "Welcome to Lifesteal!");
        player.sendMessage(ChatColor.GOLD + "Please use " + ChatColor.GREEN + "/rules " + ChatColor.GOLD + "To get started!");
        return true;
    }  
}
