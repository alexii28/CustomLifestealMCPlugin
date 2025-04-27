package com.lifesteal.Commands;

import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;

public class Rules {
    public static void rules(Player player){
        player.sendMessage(ChatColor.GOLD + "Oj's Lifesteal SMP Rules and Commands");
        player.sendMessage(ChatColor.GOLD + "Hard rules:");
        player.sendMessage(ChatColor.getByChar('c') + "No Excessive Griefing, or Cheating.");
        player.sendMessage(ChatColor.getByChar('c') + "No X-ray (F3+A) or Seedcracking");
        player.sendMessage(ChatColor.getByChar('c') + "No Explosives in combat (except TNT)");
        player.sendMessage(ChatColor.getByChar('c') + "No combat logging.");
        player.sendMessage(ChatColor.YELLOW + "For more rules, join the Discord!");
        player.sendMessage(ChatColor.GOLD + "Extra Commands");
        player.sendMessage(ChatColor.getByChar('c') + "/withdraw - Withdraw a heart crystal");
        player.sendMessage(ChatColor.getByChar('c') + "/banneditems - View the banned items");
    }
}
