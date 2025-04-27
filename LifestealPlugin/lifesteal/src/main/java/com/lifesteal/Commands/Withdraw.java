package com.lifesteal.Commands;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.lifesteal.Plugin;
import com.lifesteal.LifestealClass.HealthClass;
import com.lifesteal.LifestealClass.LifestealController;

import net.md_5.bungee.api.ChatColor;

public class Withdraw {
    private boolean return_heart = true;
    LifestealController lifestealController;
    public boolean withdrawHearts(Player player, String[] args, Plugin plugin){
        lifestealController = new LifestealController(plugin);
        if(args.length >= 1){
            if(player.isOp()){
                if(Bukkit.getServer().getPlayer(args[0]) != null){
                    player = Bukkit.getServer().getPlayer(args[0]);
                    return_heart = false;
                }
                else{
                    player.sendMessage(ChatColor.getByChar('c') + "That player does not exist!");
                    return false;
                }
            }
        }
        
        //Make sure player has atleast 2 hearts
        if(new HealthClass(plugin).getCrystalHealthLimit(player) <= 2){
            player.sendMessage("You have no hearts left to withdraw!");
            return false;
        }

        lifestealController.handleHeartDeath(player, player, false, return_heart);

        return true;
    }

    public void helpMessage(Player player){
        player.sendMessage("/withdraw: withdraw a heart into your inventory");
    }
}
