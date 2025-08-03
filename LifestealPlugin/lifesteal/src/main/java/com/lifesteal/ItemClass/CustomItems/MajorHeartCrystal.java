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
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;

import com.lifesteal.Plugin;
import com.lifesteal.ItemClass.CustomItemClass;
import com.lifesteal.ItemClass.Rarity;
import com.lifesteal.LifestealClass.HealthClass;

public class MajorHeartCrystal extends CustomItemClass{ 
    public MajorHeartCrystal(Plugin plugin) {
        super("Major Heart Crystal", Material.NETHER_STAR, Rarity.LEGENDARY, Arrays.asList("Add a heart."), plugin);
    }

    //Create recipe
    @Override
    public ShapedRecipe createRecipe(){
        ShapedRecipe recipe = super.createRecipe();
        recipe.shape(  "MDM",
                                "DGD",
                                "MDM");
        recipe.setIngredient('M', new RecipeChoice.ExactChoice(new MinorHeartCrystal(plugin).getItemCopy()));
        recipe.setIngredient('D', Material.DIAMOND_BLOCK);
        recipe.setIngredient('G', Material.ENCHANTED_GOLDEN_APPLE);

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
        if (item.isSimilar(itemCopy)){
            e.setCancelled(true);
            if(addMajorHeart(player)){
                item.setAmount(item.getAmount() - 1);
            }
        }

    }
    //Return always successful
    public boolean addMajorHeart(Player player){
        
        AttributeInstance playerHealthInstance = player.getAttribute(Attribute.MAX_HEALTH);
        playerHealthInstance.setBaseValue(playerHealthInstance.getBaseValue() + 2); //add a new empty heart.

        HealthClass healthClass = new HealthClass(plugin);
        double crystalHealthLimit = healthClass.getCrystalHealthLimit(player);
        healthClass.setCrystalHealthLimit(player, crystalHealthLimit + 2); //give the player a new heart.
        healthClass.setDefaultRegenRate(player, 0);
        return true;
    }
}
