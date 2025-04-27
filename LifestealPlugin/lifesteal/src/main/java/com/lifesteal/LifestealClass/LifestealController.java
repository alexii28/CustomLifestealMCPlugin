package com.lifesteal.LifestealClass;

import java.util.Date;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

import com.lifesteal.Plugin;
import com.lifesteal.ItemClass.CustomItems.MajorHeartCrystal;
import com.lifesteal.ItemClass.CustomItems.MinorHeartCrystal;

public class LifestealController extends HealthClass implements Listener{
    public static final double MINOR_DEFAULT_HEALTH = 14;
    public static final double MAJOR_DEFAULT_HEALTH = 6;

    private final double MINOR_RESPAWN_HEARTS_LIMIT = 14;

    private final boolean DROP_HEART = true;

    public LifestealController(Plugin plugin){
        super(plugin);
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e){
        Player player = e.getEntity().getPlayer();
        Player killer = player.getKiller();

        handleHeartDeath(player, killer, DROP_HEART, false);
    }

    //Handles the heart death of the player
    //Only 1 boolean may be true.
    public void handleHeartDeath(Player player, Player killer, boolean doDropHeart, boolean doPutHeartInventory){
        //if player died, remove heart.
        if (player != null){
            reduceHeartLimit(player);

            if(doDropHeart && doPutHeartInventory){
                Bukkit.getLogger().warning("Possible Duplication warning. Dropping player heart and Putting in inventory");
            }

            HealthClass healthClass = new HealthClass(plugin);
            ItemStack item = null;
            //check if player should lose a minor or major heart
            if (player.getAttribute(Attribute.MAX_HEALTH).getBaseValue() <= MINOR_RESPAWN_HEARTS_LIMIT){
                //set dropped item to minor heart
                item = new MinorHeartCrystal(plugin).create();

                //check if player died to a different player
                if(killer != null && killer.getType() == EntityType.PLAYER && !player.equals(killer)){
                    //if user can store another minorHeart, give them it.
                    if(killer.getAttribute(Attribute.MAX_HEALTH).getBaseValue() > healthClass.getCrystalHealthLimit(killer)){
                        new MinorHeartCrystal(plugin).addMinorHeart(killer);
                        return;
                    }
                }
            }
            else{
                removeHeart(player);
                if(killer != null && killer.getType() == EntityType.PLAYER && !player.equals(killer)){
                    new MajorHeartCrystal(plugin).addMajorHeart(killer);
                    return;
                }
                else{
                    item = new MajorHeartCrystal(plugin).create();
                }
            }
            if(doDropHeart){
                Bukkit.getLogger().info("called1");
                dropHeart(player, item);
            } else if(doPutHeartInventory){
                Bukkit.getLogger().info("called2");
                inventoryHeart(player, item);
            }
        }
    }
    
    //Drop the player's heart on the ground as an item
    public void dropHeart(Player player, ItemStack item){
        //drop player's heart
        Bukkit.getLogger().info("called3");
        player.getWorld().dropItemNaturally(player.getLocation().add(0, 1, 0), item);
    }

    public void inventoryHeart(Player player, ItemStack item){
        Bukkit.getLogger().info("called4");
        if(!player.getInventory().addItem(item).isEmpty()){
            player.sendMessage("Inventory Full! Dropping Heart on ground");
            dropHeart(player, item);
        }
        
    }

    public void reduceHeartLimit(Player player){
        double newPlayerHealthLimit = getCrystalHealthLimit(player) - 2;
        //If player will reach 0 hearts, ban them. 
        if (newPlayerHealthLimit <= 0){
            //SET PLAYER SATURATION BACK TO DEFAULT VALUES

            heartBanPlayer(player);
            return;
        }
        //else, remove a temporary heart from player
        setCrystalHealthLimit(player, newPlayerHealthLimit);
        checkPlayerHealth(player, 0);
    }

    public void removeHeart(Player player){
        AttributeInstance attributeInstance = player.getAttribute(Attribute.MAX_HEALTH);
        attributeInstance.setBaseValue(attributeInstance.getBaseValue() - 2); //remove a heart

    }

    //Player has <= 0 health
    public void heartBanPlayer(Player player){
        //set Player health back to respawn values.
        AttributeInstance playerHealthInstance = player.getAttribute(Attribute.MAX_HEALTH);
        playerHealthInstance.setBaseValue(MINOR_DEFAULT_HEALTH);
        setCrystalHealthLimit(player, MINOR_DEFAULT_HEALTH);

        //reset all player stats
        player.setRespawnLocation(null);
        player.getInventory().clear();

        player.saveData();

        player.getServer().broadcastMessage(player.getDisplayName() + ChatColor.RED + " Ran out of hearts and was banned.");

        //play ban sound to all players
        final Sound BAN_SOUND = Sound.BLOCK_END_PORTAL_SPAWN;
        Bukkit.getOnlinePlayers().forEach(person -> person.playSound(person.getLocation(), BAN_SOUND, 1.0f, 1.0f));

        //ban player
        player.ban(ChatColor.RED + "Ran out of hearts! " + ChatColor.WHITE +" Ask a player to revive you.", (Date)null, null, true);
    }
    
}
