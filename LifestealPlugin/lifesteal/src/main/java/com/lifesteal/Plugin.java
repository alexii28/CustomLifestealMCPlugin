package com.lifesteal;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import com.lifesteal.Commands.CommandHandler;
import com.lifesteal.ItemClass.CustomItemManager;
import com.lifesteal.ItemClass.CustomItems.BannedItems;
import com.lifesteal.ItemClass.CustomItems.BeaconOfLife;
import com.lifesteal.ItemClass.CustomItems.HeartCrystal;

public class Plugin extends JavaPlugin{
    private Plugin plugin;

    public static final Logger LOGGER = Logger.getLogger("lifesteal");

    //customItems
    private CustomItemManager itemManager;

    private File playerDataFile;

    private FileConfiguration playerData;

    public void onEnable(){
        LOGGER.info("lifesteal enabled");
        plugin = this;

        instantiateFiles();

        //register Listeners
        Bukkit.getPluginManager().registerEvents(new BannedItems(), plugin);
        Bukkit.getPluginManager().registerEvents(new EmptyHearts(plugin), plugin);
        Bukkit.getPluginManager().registerEvents(new LifestealController(), plugin);

        itemManager = new CustomItemManager(this);
        //register command
        Bukkit.getPluginManager().registerEvents(itemManager, plugin);


        initializeCustomItems();
        
        //initialize commands
        this.getCommand("withdraw").setExecutor(new CommandHandler(plugin));
        this.getCommand("lifestealrules").setExecutor(new CommandHandler(plugin));
        this.getCommand("banneditems").setExecutor(new CommandHandler(plugin));
    }

    private void initializeCustomItems(){
        //create and register heartCrystal
        HeartCrystal heartCrystal = new HeartCrystal(plugin);
        itemManager.registerCustomItem(heartCrystal);

        //create beacon of life
        BeaconOfLife beaconOfLife = new BeaconOfLife(plugin);
        itemManager.registerCustomItem(beaconOfLife);
    }

    public boolean instantiateFiles(){
        if(!getDataFolder().exists()){
            getDataFolder().mkdirs();
        }
        //TODO: ADD .exists Check to not overwrite data!!!
        playerDataFile = new File(getDataFolder(), "playerdata.yml");
        playerData = YamlConfiguration.loadConfiguration(playerDataFile);
        Bukkit.getLogger().info("playerdata = " + playerData.toString());
        try {
            playerData.save(playerDataFile);
        } catch (IOException e) {
            Bukkit.getLogger().warning("PlayerData could not save to file");
            e.printStackTrace();
        }
        

        
        return true;
    }

    public void onDisable(){
        LOGGER.info("lifesteal disabled");
    }

    public File getPlayerDataFile() {
        return playerDataFile;
    }
    public FileConfiguration getPlayerData() {
        return playerData;
    }
}