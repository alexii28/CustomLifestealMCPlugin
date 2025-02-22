package com.lifesteal.ItemClass;

import org.bukkit.ChatColor;

public enum Rarity {
    
    ADMIN(ChatColor.getByChar('4')),
    MYTHIC(ChatColor.getByChar('c')),
    LEGENDARY(ChatColor.getByChar('6')),
    EPIC(ChatColor.getByChar('d')),
    RARE(ChatColor.getByChar('9')),
    UNCOMMON(ChatColor.getByChar('a')),
    COMMON(ChatColor.getByChar('7'));

    private final ChatColor value;

    Rarity(ChatColor value) {
            this.value = value;
    }

    public ChatColor getColor() {
        return value;
    }
}