package com.lifesteal.LifestealClass;

import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import com.lifesteal.Plugin;

public class HeartLimitDisplay extends HealthClass implements Listener{
    public HeartLimitDisplay(Plugin plugin) {
        super(plugin);
    }

    @EventHandler
    private void onEntityRegainHealth(EntityRegainHealthEvent e){
        if(e.getEntityType() != EntityType.PLAYER){
            return;
        }

        Player player = (Player) e.getEntity();
        e.setCancelled(checkPlayerHealth(player, e.getAmount()));
    }

    @EventHandler
    private void onPlayerDamage(EntityDamageEvent e){
        if(e.getEntityType() != EntityType.PLAYER){
            return;
        }
        Player player = (Player) e.getEntity();
        setDefaultRegenRate(player, e.getFinalDamage());
    }



    @EventHandler
    private void onPlayerRespawn(PlayerRespawnEvent e){
        //wait 1 tick to let player become alive.
        Bukkit.getScheduler().runTask(plugin, () -> {
            checkPlayerHealth(e.getPlayer(), 0);
        });
        
    }

    @EventHandler
    private void onPlayerJoin(PlayerJoinEvent e){
        checkPlayerHealth(e.getPlayer(), 0);
    }
}