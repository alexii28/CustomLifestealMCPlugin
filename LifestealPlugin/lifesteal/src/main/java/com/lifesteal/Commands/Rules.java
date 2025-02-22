package com.lifesteal.Commands;

import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;

public class Rules {
    public static void rules(Player player){
        player.sendMessage(ChatColor.GOLD + "Oj's Lifesteal SMP Rules");
        player.sendMessage(ChatColor.getByChar('c') + "No Excessive Griefing, or Cheating.");
        player.sendMessage(ChatColor.getByChar('c') + "Lying and Using Glitches are Encouraged, but, not Duping.");
        player.sendMessage(ChatColor.getByChar('c') + "No Escaping combat through... Disconnecting, Elytras, or Riptipe");
        player.sendMessage(ChatColor.getByChar('c') + "Replay Mod IS allowed however, can NOT be used for finding bases, chests, caves, etc.");
        player.sendMessage(ChatColor.getByChar('c') + "No X-ray (F3+A) or Seedcracking");
        player.sendMessage(ChatColor.getByChar('c') + "No Explosives in combat (beds, respawn anchors, end crystals)");
        player.sendMessage(ChatColor.GOLD + "Good Practices (soft rules)");
        player.sendMessage(ChatColor.getByChar('a') + "Giving back loot after Stealing a Heart");
        player.sendMessage(ChatColor.getByChar('a') + "Stealing while offline or for no reason");
        player.sendMessage(ChatColor.getByChar('a') + "Dragon Egg must NOT be in an ender chest");
        player.sendMessage(ChatColor.GOLD + "Notes:");
        player.sendMessage(ChatColor.getByChar('a') + "Hearts can be given back if a user dies to a \"uncontrollable cause\" (crash, in a transition screen, etc) WITH PROOF. Ideally a clip however, death message + vouch can work too.");
        player.sendMessage(ChatColor.GOLD + "Failure to abide by the rules can result in temporary bans or hearts lost.");
    }
}
