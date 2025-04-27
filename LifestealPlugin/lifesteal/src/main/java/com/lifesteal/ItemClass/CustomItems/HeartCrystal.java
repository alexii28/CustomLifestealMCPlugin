package com.lifesteal.ItemClass.CustomItems;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

import com.lifesteal.LifestealController;
import com.lifesteal.Plugin;
import com.lifesteal.ItemClass.CustomItemClass;
import com.lifesteal.ItemClass.Rarity;

public class HeartCrystal extends CustomItemClass{
    Plugin plugin;
    public HeartCrystal(Plugin plugin) {
        super("Heart Crystal", Material.NETHER_STAR, Rarity.EPIC, Arrays.asList("Add a heart."), plugin);
    }

    //Create recipe
    @Override
    public ShapedRecipe createRecipe(){
        ShapedRecipe recipe = super.createRecipe();
        recipe.shape(  "DED",
                                "ALN",
                                "DPD");
        recipe.setIngredient('D', Material.DIAMOND_BLOCK);
        recipe.setIngredient('E', Material.DRAGON_HEAD);
        recipe.setIngredient('A', Material.AMETHYST_SHARD);
        recipe.setIngredient('L', Material.LODESTONE);
        recipe.setIngredient('N', Material.ECHO_SHARD);
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
            if(LifestealController.addHeart(player)){
                item.setAmount(item.getAmount() - 1);
            }
        }
    }
}
