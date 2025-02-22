package com.lifesteal.ItemClass;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.SmithingRecipe;
import org.bukkit.inventory.SmithingTransformRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;



public abstract class CustomItemClass implements Listener {
    protected String name;
    protected Material material;
    protected List<String> lore;
    protected Rarity rarity;
    protected Plugin plugin;

    public CustomItemClass(String name, Material material, Rarity rarity, List<String> lore, Plugin plugin){
        this.name = name;
        this.material = material;
        this.rarity = rarity;
        this.lore = lore;
        this.plugin = plugin;

        createRecipe();
    }

    public ItemStack create() {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(rarity.getColor() + name);
        if(lore != null){
            meta.setLore(lore);
        }

        //Hide ALL item flags
        meta.addItemFlags(ItemFlag.HIDE_ADDITIONAL_TOOLTIP);
        meta.addItemFlags(ItemFlag.HIDE_ARMOR_TRIM);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_DESTROYS);
        meta.addItemFlags(ItemFlag.HIDE_DYE);
        meta.addItemFlags(ItemFlag.HIDE_PLACED_ON);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);

        for (Attribute attribute : Attribute.values()) {
            meta.removeAttributeModifier(attribute);
        }

        meta.setCustomModelData(1); //Make the item 'custom'

        item.setItemMeta(meta);
        return item;
    }

    /**
      * Registers a customRecipe for the item
      * @return <code>ShapedRecipe</code> the shaped recipe
      * @see CustomItemClass#createRecipe()
      */
    public ShapedRecipe createRecipe(){
        int count = 1;
        
        ShapedRecipe recipe = null;
        String keyName = generateKeyName(name);
        while(Bukkit.getRecipe(new NamespacedKey(plugin, keyName)) != null){
            keyName = keyName.concat(String.valueOf(count));
            count++;
        }
        recipe = new ShapedRecipe(new NamespacedKey(plugin, keyName), this.create());
        /*  Create shape using ShapedRecipe. Follow model below:
         *  recipe.shade("X", "X", "X");
         *  recipe.setIngredient('X', Material.(yourMaterial));
         */
        return recipe;
    }

     /**
      * Registers a customSmithingRecipe for the item
      * @return <code>SmithingRecipe</code> the smithing recipe
      * @see CustomItemClass#createSmithingRecipe()
      */
      public SmithingRecipe createSmithingRecipe(RecipeChoice template, RecipeChoice base, RecipeChoice addition){
        int count = 1;
        
        SmithingRecipe recipe = null;
        String keyName = generateKeyName(name);
        while(Bukkit.getRecipe(new NamespacedKey(plugin, keyName)) != null){
            keyName = keyName.concat(String.valueOf(count));
            count++;
        }
        recipe = new SmithingTransformRecipe(new NamespacedKey(plugin, keyName), this.create(), template, base, addition);
        return recipe;
    }

    private String generateKeyName(String name){
        return name.replaceAll(" ", "_");
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        NamespacedKey customRecipeKey = new NamespacedKey(plugin, generateKeyName(name));

        if (!player.hasDiscoveredRecipe(customRecipeKey)) {
            player.discoverRecipe(customRecipeKey);
        }
    }

    public void registerEvents(){
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    public String getName() {
        return name;
    }

    public Material getMaterial() {
        return material;
    }

    public List<String> getLore() {
        return lore;
    }

}
