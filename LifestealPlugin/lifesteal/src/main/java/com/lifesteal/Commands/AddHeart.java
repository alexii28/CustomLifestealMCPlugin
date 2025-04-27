package com.lifesteal.Commands;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.lifesteal.Plugin;
import com.lifesteal.ItemClass.CustomItems.MajorHeartCrystal;
import com.lifesteal.ItemClass.CustomItems.MinorHeartCrystal;

import net.md_5.bungee.api.ChatColor;

public class AddHeart {
    private Player player;
    private Plugin plugin;

    public AddHeart(Player player, Plugin plugin){
        this.player = player;
        this.plugin = plugin;
    }

    public void addHeart(String[] args){
        if(args[0].toLowerCase().equals("minor")){
            getTargetPlayer(args);

            new MinorHeartCrystal(plugin).addMinorHeart(player);
        }
        else if(args[0].toLowerCase().equals("major")){
            getTargetPlayer(args);

            new MajorHeartCrystal(plugin).addMajorHeart(player);
        }
        else{
            help();
        }

    }

    private void getTargetPlayer(String[] args){
        if(args.length >= 2){
            if(Bukkit.getServer().getPlayer(args[1]).isValid()){
                player = Bukkit.getServer().getPlayer(args[1]);
            }
            else{
                player.sendMessage(ChatColor.getByChar('c') + "That player does not exist!");
            }
        }
    }

    public void help() {
        player.sendMessage("Give the selected user a major or minor heart");
        player.sendMessage("/addheart <major|minor> [username]");
    }
}
