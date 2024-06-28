package org.snorklingturtle.preventplayerdrop.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.scheduler.BukkitRunnable;
import org.snorklingturtle.preventplayerdrop.PreventPlayerDrop;

import java.util.ArrayList;
import java.util.List;

public class PlayerDeath  implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerDeathDrop(PlayerDeathEvent e) {

        Player player = e.getEntity();
        List<ItemStack> toRemove = new ArrayList<>();
        List<ItemStack> toKeep = new ArrayList<>();
        PreventPlayerDrop instance = PreventPlayerDrop.getInstance();

        // Look through dropped items and check if any of the items has meta attached
        for (ItemStack itemDrop : e.getDrops()) {
            ItemMeta itemMeta = itemDrop.getItemMeta();
            PersistentDataContainer persistentDataContainer = itemMeta.getPersistentDataContainer();
            if (!persistentDataContainer.has(instance.GetPersistentKey()))
                continue;

            ItemStack item = itemDrop.clone();
            item.setAmount(itemDrop.getAmount());

            toKeep.add(item);
            toRemove.add(itemDrop);
        }

        // Remove dropped
        e.getDrops().removeAll(toRemove);

        new BukkitRunnable() {
            @Override
            public void run() {
                // Add clone back into inventory
                for (ItemStack keep : toKeep) {
                    player.getInventory().addItem(keep);
                }
            }
        }.runTaskLater(instance, 0);
    }

}
