package org.snorklingturtle.preventplayerdrop.listeners;

import org.bukkit.entity.Player;
import org.bukkit.command.PluginCommand;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.snorklingturtle.preventplayerdrop.PreventPlayerDrop;
import org.snorklingturtle.preventplayerdrop.commands.PreserveCommand;

public class PlayerJoin implements Listener {

    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent e) {

        Player player = e.getPlayer();
        PreventPlayerDrop instance = PreventPlayerDrop.getInstance();

        PluginCommand preserveCommand = instance.getCommand("preserve");
        if (preserveCommand == null)
        {
            return;
        }

        if (!instance.HasPermission(player))
        {
            return;
        }

        preserveCommand.setExecutor(new PreserveCommand());

    }
}
