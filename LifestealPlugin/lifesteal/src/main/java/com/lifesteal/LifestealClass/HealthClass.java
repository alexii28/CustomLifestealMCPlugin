package com.lifesteal.LifestealClass;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import com.lifesteal.Plugin;

public class HealthClass {
    protected Plugin plugin;
    private FileConfiguration playerData;
    private File playerDataFile;

    private final int DEFAULT_SATURATED_LIMIT = 10; // hearts = limit/2
    private final int DEFAULT_UNSATURATED_LIMIT = 80; // hearts = limit/2

    public HealthClass(Plugin plugin){
        this.plugin = plugin;
        this.playerData = plugin.getPlayerData();
        this.playerDataFile = plugin.getPlayerDataFile();
    }
    
    //Returns the player's healthLimit value, or -1 if null;
    public double getCrystalHealthLimit(Player player){
        UUID playerUUID = player.getUniqueId();
        if(!playerData.contains(playerUUID.toString())){
            Bukkit.getLogger().warning("PLAYER (" + player.getName() + ") DOES NOT HAVE HEALTH DATA");
            return -1; 
        }
        return playerData.getDouble(playerUUID.toString() + ".heartLimit");
    }

    //Returns the player's healthLimit value, or -1 if null;
    public void setCrystalHealthLimit(Player player, double value){
        UUID playerUUID = player.getUniqueId();
        if(!playerData.contains(playerUUID.toString())){
            Bukkit.getLogger().warning("PLAYER (" + player.getName() + ") DOES NOT HAVE HEALTH DATA"); 
            return;
        }
        playerData.set(playerUUID.toString() + ".heartLimit", value);
        try {
            playerData.save(playerDataFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        checkPlayerHealth(player, 0);
    }

    //Check user's health value. Set to limit if it exceeds limit.
    //Use healthIncrease 0 to only update healthbar.
    //returns true if successful, and false otherwise.
    public boolean checkPlayerHealth(Player player, double healthIncrease){
        double crystalHealthLimit = getCrystalHealthLimit(player);
        //make sure crystalHealthLimit has a value
        if(crystalHealthLimit == -1){
            return false;
        }
        if(player.getHealth() + healthIncrease > crystalHealthLimit){
            player.setHealth(crystalHealthLimit);
            player.setSaturatedRegenRate(Integer.MAX_VALUE);
            player.setUnsaturatedRegenRate(Integer.MAX_VALUE);
            return true;
        }
        return false;
    }

    //sets default regeneration values if user goes below health limit.
    //returns true if successful, and false otherwise.
    public boolean setDefaultRegenRate(Player player, double damageModifier){
        double crystalHealthLimit = getCrystalHealthLimit(player);
        if(player.getHealth() - damageModifier < crystalHealthLimit){
            player.setSaturatedRegenRate(DEFAULT_SATURATED_LIMIT);
            player.setUnsaturatedRegenRate(DEFAULT_UNSATURATED_LIMIT);
            return true;
        }
        return false;
    }
}
