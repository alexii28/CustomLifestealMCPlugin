package com.lifesteal.ItemClass.CustomItems;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import org.bukkit.BanEntry;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.ban.ProfileBanList;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.profile.PlayerProfile;

import com.lifesteal.Plugin;
import com.lifesteal.ItemClass.CustomItemClass;
import com.lifesteal.ItemClass.Rarity;

import net.md_5.bungee.api.ChatColor;


public class BeaconOfLife extends CustomItemClass{
    public BeaconOfLife(Plugin plugin) {
        super("Beacon of Life", Material.BEACON, Rarity.MYTHIC, Arrays.asList("Right Click to revive a fallen friend."), plugin);
    }
    
    //Create recipe
    @Override
    public ShapedRecipe createRecipe(){
        ShapedRecipe recipe = super.createRecipe();
        recipe.shape(  "YMY",
                                "MRM",
                                "DDD");
        recipe.setIngredient('Y', new RecipeChoice.ExactChoice(new MajorHeartCrystal(plugin).getItemCopy()));
        recipe.setIngredient('M', new RecipeChoice.ExactChoice(new MinorHeartCrystal(plugin).getItemCopy()));
        recipe.setIngredient('R', Material.RECOVERY_COMPASS);
        recipe.setIngredient('N', Material.NETHERITE_INGOT);
        recipe.setIngredient('D', Material.DIAMOND_BLOCK);

        Bukkit.addRecipe(recipe);

        return recipe;
    }

    @EventHandler
    public void onPlaceEvent(BlockPlaceEvent e){
        if(e.getItemInHand().isSimilar(itemCopy)){
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onRightClick(PlayerInteractEvent e){
        ItemStack item = e.getItem();
        if(item == null || !item.isSimilar(itemCopy)){
            return;
        }
        if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK){
            openGUI(e.getPlayer());
        }
    }

    private void openGUI(Player player){
        Inventory guiInventory = Bukkit.createInventory(player, 45, rarity.getColor() + getName());

        BanList<PlayerProfile> banList = Bukkit.getBanList(BanList.Type.PROFILE);
        List<BanEntry<PlayerProfile>> banEntries = new ArrayList<>(banList.getEntries());

        ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
        for (int i = 0; i < 45; i++){
            if(i < banEntries.size()){
                PlayerProfile playerProfile = banEntries.get(i).getBanTarget();

                SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();
                if(skullMeta != null){
                    skullMeta.setOwnerProfile(playerProfile);
                    skull.setItemMeta(skullMeta);
                }
                ItemMeta itemMeta = skull.getItemMeta();
                itemMeta.setDisplayName(playerProfile.getName());
    
    
                skull.setItemMeta(itemMeta);

                guiInventory.setItem(i, skull);
            }
            else{
                guiInventory.setItem(i, new ItemStack(Material.GRAY_STAINED_GLASS_PANE));
            }
            
        }

        player.openInventory(guiInventory);
    }
    

    //GUI Handler
    @EventHandler
    private void guiHandler(InventoryClickEvent e){
        Player player = (Player) e.getWhoClicked();
        if (!e.getView().getTitle().equalsIgnoreCase(rarity.getColor() + getName())){
            return;
        }
        e.setCancelled(true);
        ItemStack item = e.getCurrentItem();
        if(item == null){
            return;
        }
        if(item.getType() == Material.PLAYER_HEAD){
            player.closeInventory();
        
            ItemStack beaconItem = player.getInventory().getItemInMainHand();
            if(beaconItem == null){
                return;
            }
            if(!beaconItem.isSimilar(itemCopy)){
                player.sendMessage(ChatColor.RED + "Keep Holding the Beacon!");
                return;
            }
            ProfileBanList profileBanList = Bukkit.getBanList(BanList.Type.PROFILE);
            List<BanEntry<PlayerProfile>> profileBanListEntries = new ArrayList<>(profileBanList.getEntries());

            for (BanEntry<PlayerProfile> banEntry : profileBanListEntries){
                PlayerProfile revivedPlayerProfile = banEntry.getBanTarget();
                if(revivedPlayerProfile == null){
                    return;
                }
                if(revivedPlayerProfile.getName().equalsIgnoreCase(ChatColor.stripColor(item.getItemMeta().getDisplayName()))){
                    beaconItem.setAmount(beaconItem.getAmount()-1);
                    revivePlayer(player, revivedPlayerProfile);
                }
            }
        }
    }

    private void revivePlayer(Player player, PlayerProfile revivedPlayerProfile){
        ProfileBanList banList = Bukkit.getBanList(BanList.Type.PROFILE);
        banList.pardon(revivedPlayerProfile);
        
        final Sound BAN_SOUND = Sound.BLOCK_BEACON_ACTIVATE;

        player.getServer().broadcastMessage(ChatColor.getByChar('a') + player.getName() + " Has revived " + revivedPlayerProfile.getName() + "!");
        //play revive sound to all players
        Bukkit.getOnlinePlayers().forEach(person -> person.playSound(person.getLocation(), BAN_SOUND, 1.0f, 1.0f));
    }


}
