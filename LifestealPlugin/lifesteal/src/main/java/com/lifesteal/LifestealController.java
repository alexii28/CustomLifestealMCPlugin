package com.lifesteal;

import java.util.Date;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class LifestealController implements Listener{
    @SuppressWarnings("unused")
    private Plugin plugin;
    
    private static final int MAXIMUM_HEALTH = 40; // MAXIMUM_HEALTH / 2 = MAXIMUM_HEARTS

    /*@EventHandler
    public void onPlayerDeath(PlayerDeathEvent e){
        Bukkit.getLogger().info("lifestealcontroller");
        Player player = e.getEntity().getPlayer();
        Player killer = player.getKiller();

        //displayedHealth = getHealth() / getMaxHealth() * getHealthScale()

        if (killer != null){
            addHeart(killer);
        }


        if (player != null){
            removeHeart(player);
        }
    }*/
    //Return true if successful, false otherwise
    public static boolean addHeart(Player player){
        AttributeInstance playerHealthInstance = player.getAttribute(Attribute.GENERIC_MAX_HEALTH);
        double playerHealthValue = playerHealthInstance.getBaseValue();
        if(playerHealthValue >= MAXIMUM_HEALTH)
            return false;
            
        playerHealthInstance.setBaseValue(playerHealthValue + 2);
        return true;
    }
    
    public static void removeHeart(Player player){
        AttributeInstance playerHealthInstance = player.getAttribute(Attribute.GENERIC_MAX_HEALTH);
        playerHealthInstance.setBaseValue(playerHealthInstance.getBaseValue() - 2);
        if (playerHealthInstance.getBaseValue() <= 0){
            playerHealthInstance.setBaseValue(20);

            //SET PLAYER SATURATION BACK TO DEFAULT VALUES

            heartBanPlayer(player);
        }
    }

    //Player has <= 0 health
    public static void heartBanPlayer(Player player){
        player.setRespawnLocation(null);
        player.saveData();
        final Sound BAN_SOUND = Sound.BLOCK_END_PORTAL_SPAWN;

        player.getServer().broadcastMessage(player.getDisplayName() + ChatColor.RED +" Ran out of hearts and was banned.");

        //play ban sound to all players
        Bukkit.getOnlinePlayers().forEach(person -> person.playSound(person.getLocation(), BAN_SOUND, 1.0f, 1.0f));

        //ban player
        player.ban(ChatColor.RED + "Ran out of hearts! " + ChatColor.WHITE +" Ask a player to revive you.", (Date)null, null, true);
    }
}
