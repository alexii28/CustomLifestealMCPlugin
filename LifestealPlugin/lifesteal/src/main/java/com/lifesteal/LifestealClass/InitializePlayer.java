package com.lifesteal.LifestealClass;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.bukkit.attribute.Attribute;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.lifesteal.Plugin;
import com.lifesteal.Commands.Tutorial;

public class InitializePlayer extends HealthClass implements Listener{
    public InitializePlayer(Plugin plugin) {
        super(plugin);
    }

    //play a welcome message and initialize the user.
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){
        FileConfiguration playerData = plugin.getPlayerData();
        File playerDataFile = plugin.getPlayerDataFile();
        Player player = e.getPlayer();
        UUID playerUUID = player.getUniqueId();
        if(playerData.contains(playerUUID.toString())){
            return;  
        }
        Tutorial.tutorial(player);

        player.getAttribute(Attribute.MAX_HEALTH).setBaseValue(LifestealController.MINOR_DEFAULT_HEALTH + LifestealController.MAJOR_DEFAULT_HEALTH);

        playerData.set(playerUUID.toString(), playerUUID.toString());
        setCrystalHealthLimit(player, LifestealController.MINOR_DEFAULT_HEALTH + LifestealController.MAJOR_DEFAULT_HEALTH);
        try {
            playerData.save(playerDataFile);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}
