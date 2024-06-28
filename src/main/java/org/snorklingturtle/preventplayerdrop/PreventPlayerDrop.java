package org.snorklingturtle.preventplayerdrop;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.snorklingturtle.preventplayerdrop.commands.PreserveCommand;
import org.snorklingturtle.preventplayerdrop.listeners.PlayerDeath;


public final class PreventPlayerDrop extends JavaPlugin implements Listener {

    static PreventPlayerDrop instance;
    NamespacedKey keyPreserve = new NamespacedKey(this, "preserve_b97ce146e2b73312268595c8865b4489");
    Listener playerDeathListener = new PlayerDeath();
//    Listener playerJoinListener = new PlayerJoin();

    public static PreventPlayerDrop getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;

        PluginCommand preserveCommand = instance.getCommand("preserve");
        if (preserveCommand != null)
        {
            preserveCommand.setExecutor(new PreserveCommand());
        }

//        getServer().getPluginManager().registerEvents(playerJoinListener, this);
        getServer().getPluginManager().registerEvents(playerDeathListener, this);
    }

    @Override
    public void onDisable() {
        // Unregister events
//        if (playerJoinListener != null)
//        {
//            PlayerJoinEvent.getHandlerList().unregister(playerJoinListener);
//        }
        if (playerDeathListener != null)
        {
            PlayerDeathEvent.getHandlerList().unregister(playerDeathListener);
        }
    }

    public NamespacedKey GetPersistentKey()
    {
        return keyPreserve;
    }

    public boolean HasPermission(Player player)
    {
        return player.isOp() || player.hasPermission("preserve.command");
    }

    public String GetFormattedName(Material material)
    {
        String lowerCaseDisplay = material.toString().toLowerCase();
        StringBuilder formattedDisplay = new StringBuilder();
        for(String word : lowerCaseDisplay.split("_")) {
            String wordFormatted = word.substring(0, 1).toUpperCase() + word.substring(1);
            formattedDisplay.append(wordFormatted).append(" ");
        }
        return formattedDisplay.toString().trim();
    }
}
