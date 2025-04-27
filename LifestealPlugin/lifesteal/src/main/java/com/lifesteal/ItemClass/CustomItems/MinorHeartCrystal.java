package com.lifesteal.ItemClass.CustomItems;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

import com.lifesteal.Plugin;
import com.lifesteal.ItemClass.CustomItemClass;
import com.lifesteal.ItemClass.Rarity;
import com.lifesteal.LifestealClass.HealthClass;

public class MinorHeartCrystal extends CustomItemClass{
    public MinorHeartCrystal(Plugin plugin) {
        super("Minor Heart Crystal", Material.NETHER_STAR, Rarity.EPIC, Arrays.asList("Add a heart."), plugin);
    }

    //Create recipe
    @Override
    public ShapedRecipe createRecipe(){
        ShapedRecipe recipe = super.createRecipe();
        recipe.shape(  "DPD",
                                "ENE",
                                "DUD");
        recipe.setIngredient('D', Material.DIAMOND_BLOCK);
        recipe.setIngredient('U', Material.NETHERITE_UPGRADE_SMITHING_TEMPLATE);
        recipe.setIngredient('E', Material.ENDER_EYE);
        recipe.setIngredient('N', Material.NETHERITE_INGOT);
        recipe.setIngredient('P', Material.TURTLE_HELMET);

        Bukkit.addRecipe(recipe);

        return recipe;
    }

    @EventHandler
    public void setupMechanics(PlayerInteractEvent e){
        ItemStack item = e.getItem();
        if (item == null){
            return;
        }
        Player player = e.getPlayer();
        if (item.isSimilar(create())){
            e.setCancelled(true);
            if(addMinorHeart(player)){
                item.setAmount(item.getAmount() - 1);
            }
        }

    }
    //Return true if successful, false otherwise
    public boolean addMinorHeart(Player player){

        HealthClass healthClass = new HealthClass(plugin);
        double crystalHealthLimit = healthClass.getCrystalHealthLimit(player);
        AttributeInstance playerHealthInstance = player.getAttribute(Attribute.MAX_HEALTH);
        if(crystalHealthLimit < playerHealthInstance.getBaseValue()){
            healthClass.setCrystalHealthLimit(player, crystalHealthLimit + 2); //Add a minor heart
            healthClass.setDefaultRegenRate(player, 0);
            return true;
        }
        return false;
    }
}
