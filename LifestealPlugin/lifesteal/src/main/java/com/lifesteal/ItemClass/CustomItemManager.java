package com.lifesteal.ItemClass;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class CustomItemManager implements CommandExecutor, Listener {
    private final JavaPlugin plugin;
    private final Map<String, CustomItemClass> customItems = new HashMap<>();

    public CustomItemManager(JavaPlugin plugin) {
        this.plugin = plugin;
        PluginCommand pluginCommand = plugin.getCommand("customitem");
        pluginCommand.setExecutor(this);
    }

     public void registerCustomItem(CustomItemClass item) {
        String itemName = item.getName().toLowerCase().replace(" ", "");
        customItems.put(itemName, item);
        item.registerEvents();

        try {
            // Create a new PluginCommand
            Constructor<PluginCommand> constructor = PluginCommand.class.getDeclaredConstructor(String.class, Plugin.class);
            constructor.setAccessible(true);
            PluginCommand command = constructor.newInstance(itemName, plugin);
            command.setPermission("op");

            // Set the command executor
            command.setExecutor((sender, cmd, label, args) -> {
                if (sender instanceof Player) {
                    Player player = (Player) sender;
                    player.getInventory().addItem(item.getItemCopy());
                    player.sendMessage("You've received the " + item.getName() + "!");
                    return true;
                }
                return false;
            });

            // Get the CommandMap and register the command
            Field commandMapField = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            commandMapField.setAccessible(true);
            CommandMap commandMap = (CommandMap) commandMapField.get(Bukkit.getServer());
            commandMap.register(plugin.getName(), command);

        } catch (Exception e) {
            plugin.getLogger().severe("Failed to register command for " + itemName + ": " + e.getMessage());
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("customitem")) {
            if (args.length < 1 || args[0].equalsIgnoreCase("help")) {
                sender.sendMessage(getHelpMessage());
                return true;
            }
            
            String itemName = args[0].toLowerCase();
            CustomItemClass item = customItems.get(itemName);
            
            if (item == null) {
                sender.sendMessage("Unknown custom item: " + itemName);
                return true;
            }
            
            if (sender instanceof Player) {
                Player player = (Player) sender;
                player.getInventory().addItem(item.getItemCopy());
                player.sendMessage("You've received the " + item.getName() + "!");
            } else {
                sender.sendMessage("This command can only be used by players.");
            }
            
            return true;
        }
        return false;
    }

    private String getHelpMessage() {
        StringBuilder message = new StringBuilder();
        message.append(ChatColor.GOLD).append("=== Available Custom Items ===\n");
        for (String itemName : customItems.keySet()) {
            message.append(ChatColor.YELLOW).append("/").append(itemName).append("\n");
        }
        message.append(ChatColor.GOLD).append("Use /customitem <itemname> to get an item");
        return message.toString();
    }

    //Cancel the ability to craft using Custom Items
    @EventHandler
    private void onPrepareCraft(PrepareItemCraftEvent e){
        CraftingInventory inventory = e.getInventory();
        for(ItemStack item : inventory.getMatrix()) {
            if(item != null && item.hasItemMeta()){
                ItemMeta meta = item.getItemMeta();
                if (!meta.getCustomModelDataComponent().getFloats().isEmpty()){
                    if(inventory.getResult() != null && !inventory.getResult().hasItemMeta()){
                        inventory.setResult(null);
                    }
                    return;
                }
                else if (inventory.getResult() != null && inventory.getResult().hasItemMeta()){
                    ItemMeta resultMeta = inventory.getResult().getItemMeta();
                    if (!resultMeta.getCustomModelDataComponent().getFloats().isEmpty()){
                        inventory.setResult(null);
                        return;
                    }
                }
            }
        }
    }
}