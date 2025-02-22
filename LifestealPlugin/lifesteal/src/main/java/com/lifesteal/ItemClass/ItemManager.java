package com.lifesteal.ItemClass;

import org.bukkit.inventory.ItemStack;


public interface ItemManager {

    ItemStack addModifier(org.bukkit.inventory.ItemStack item, double value, boolean lore); 
    
}