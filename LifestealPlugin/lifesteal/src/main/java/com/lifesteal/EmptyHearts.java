package com.lifesteal;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import com.lifesteal.Commands.Tutorial;

public class EmptyHearts implements Listener{

    private final double DEFAULT_CRYSTAL_LIMIT = 16; // hearts = limit/2
    private final int DEFAULT_SATURATED_LIMIT = 10; // hearts = limit/2
    private final int DEFAULT_UNSATURATED_LIMIT = 80; // hearts = limit/2

    public Plugin plugin;
    public EmptyHearts(Plugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e){
        Bukkit.getLogger().info("emptyhearts");
        Player player = e.getEntity();
        AttributeInstance maxHealth = player.getAttribute(Attribute.GENERIC_MAX_HEALTH);
        double newHealth = maxHealth.getBaseValue();
    }

    @EventHandler
    public void onEntityRegainHealth(EntityRegainHealthEvent e){
        if(e.getEntityType() != EntityType.PLAYER){
            return;
        }

        Player player = (Player) e.getEntity();
        double crystalHealthLimit = getCrystalHealthLimit(player);

        //double realPlayerHealth = baseAmount - times killed by players;
        if(player.getHealth() + e.getAmount() > crystalHealthLimit){
            e.setCancelled(true);
            player.setHealth(crystalHealthLimit);
            player.setSaturatedRegenRate(Integer.MAX_VALUE);
            player.setUnsaturatedRegenRate(Integer.MAX_VALUE);
        }
    }

    @EventHandler
    public void onPlayerDamage(EntityDamageEvent e){
        if(e.getEntityType() != EntityType.PLAYER){
            return;
        }
        Player player = (Player) e.getEntity();
        double crystalHealthLimit = getCrystalHealthLimit(player);
        if(player.getHealth() - e.getFinalDamage() < crystalHealthLimit){
            player.setSaturatedRegenRate(DEFAULT_SATURATED_LIMIT);
            player.setUnsaturatedRegenRate(DEFAULT_UNSATURATED_LIMIT);
        }

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
        playerData.set(playerUUID.toString(), playerUUID.toString());
        playerData.set(playerUUID.toString() + ".heartLimit", DEFAULT_CRYSTAL_LIMIT);
        try {
            playerData.save(playerDataFile);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    public double getCrystalHealthLimit(Player player){
        FileConfiguration playerData = plugin.getPlayerData();
        UUID playerUUID = player.getUniqueId();
        if(!playerData.contains(playerUUID.toString())){
            Bukkit.getLogger().warning("PLAYER (" + player.getName() + ") DOES NOT HAVE HEALTH DATA");
            return -1; 
        }
        return (double) playerData.get(playerUUID.toString() + ".heartLimit");
    }
}
