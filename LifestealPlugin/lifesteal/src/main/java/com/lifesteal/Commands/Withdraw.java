package com.lifesteal.Commands;

import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.Plugin;

import com.lifesteal.LifestealController;
import com.lifesteal.ItemClass.CustomItems.HeartCrystal;

public class Withdraw {
    public static boolean withdrawHearts(Player player, Plugin plugin){
        //Make sure Main Hand is empty
        PlayerInventory inventory = player.getInventory();
        if(!inventory.getItemInMainHand().getType().isAir() && !inventory.getItemInMainHand().isSimilar(new HeartCrystal(plugin).create())){
            player.sendMessage("Clear your main hand!");
            return false;
        }
        //Make sure player has atleast 2 hearts
        if(player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue() <= 2){
            player.sendMessage("You have no hearts to withdraw!");
            return false;
        }

        if(inventory.getItemInMainHand().getType().isAir()){
            inventory.setItemInMainHand(new HeartCrystal(plugin).create());
        }
        else{
            ItemStack item = inventory.getItemInMainHand();
            item.setAmount(item.getAmount() + 1);
        }
        LifestealController.removeHeart(player);
        return true;
    }
}
