package com.lifesteal.ItemClass.CustomItems;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class BannedItems implements Listener{
    public static final Material[] SOFT_BAN = {
        Material.ENDER_PEARL
    };
    public static final Material[] HARD_BAN = {
        Material.TOTEM_OF_UNDYING,
        Material.END_CRYSTAL
    };
    public static final Material[] MANUALLY_BAN = {
        Material.NETHERITE_HELMET,
        Material.NETHERITE_CHESTPLATE,
        Material.NETHERITE_LEGGINGS,
        Material.NETHERITE_HELMET,
        Material.MACE
    };

    public static final Material[][] BANNED_ITEMS = {
        HARD_BAN,
        SOFT_BAN
    };


    public BannedItems(){
        for(Material[] materialArr : BANNED_ITEMS){
            for(Material material : materialArr){
                if(Bukkit.getRecipe(NamespacedKey.minecraft(material.toString().toLowerCase())) != null){
                    Bukkit.removeRecipe(NamespacedKey.minecraft(material.toString().toLowerCase()));
                }
            }
        }

    }

    @EventHandler
    public void onItemPickup(EntityPickupItemEvent e){
        for(Material material : HARD_BAN){
            if(e.getItem().getItemStack() != null && e.getItem().getItemStack().isSimilar(new ItemStack(material))){
                e.setCancelled(true);
                e.getItem().remove();
            }
        }
    }

    @EventHandler
    public void onInventoryOpen(InventoryClickEvent e){
        for(Material material : HARD_BAN){
            if(e.getCurrentItem() != null && e.getCurrentItem().isSimilar(new ItemStack(material))){
                e.setCancelled(true);
                e.getCurrentItem().setAmount(0);
            }
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e){
        if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK){
            for(Material material : SOFT_BAN){
                if(e.getItem() != null && e.getItem().getType() == material){
                    e.setCancelled(true);
                }
            }
        }
    }
}
