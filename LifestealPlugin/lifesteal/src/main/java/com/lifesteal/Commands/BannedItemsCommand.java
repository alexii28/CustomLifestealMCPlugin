package com.lifesteal.Commands;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import com.lifesteal.ItemClass.CustomItems.BannedItems;

public class BannedItemsCommand {
    public static void bannedItemsCommand(Player player){
        player.sendMessage(ChatColor.GOLD + "List of all banned OR modified items on Lifesteal.");
        String bannedItemsStr = "";
        for(Material[] materialArr : BannedItems.BANNED_ITEMS){
            for(Material item : materialArr){
                bannedItemsStr += item + ", ";
                
            }
        }

        //add any additional items
        for(Material item : BannedItems.MANUAL_BAN){
            bannedItemsStr += item + ", ";
            
        }
        player.sendMessage(ChatColor.GOLD + bannedItemsStr);
    }
}
