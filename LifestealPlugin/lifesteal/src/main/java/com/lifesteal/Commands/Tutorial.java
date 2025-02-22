package com.lifesteal.Commands;

import org.bukkit.entity.Player;

public class Tutorial {
    public static boolean tutorial(Player player){
        player.sendMessage("Tutorial message!");
        return true;
    }  
}
