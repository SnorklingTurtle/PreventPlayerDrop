package org.snorklingturtle.preventplayerdrop.commands;

import org.bukkit.Material;
import static org.bukkit.ChatColor.*;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.jetbrains.annotations.NotNull;
import org.bukkit.persistence.PersistentDataType;
import org.snorklingturtle.preventplayerdrop.PreventPlayerDrop;

public class PreserveCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!command.getName().equalsIgnoreCase("preserve"))
            return false;

        if (!(commandSender instanceof Player)) {
            return false;
        }
        Player player = (Player) commandSender;

        ItemStack itemStack = player.getInventory().getItemInMainHand();
        if (itemStack.getType() == Material.AIR)
            return false;

        PreventPlayerDrop instance = PreventPlayerDrop.getInstance();

        if (!instance.HasPermission(player))
            return false;

        ItemMeta itemMeta = itemStack.getItemMeta();
        PersistentDataContainer persistentDataContainer = itemMeta.getPersistentDataContainer();
        NamespacedKey keyPreserve = instance.GetPersistentKey();

        String itemName = instance.GetFormattedName(itemStack.getType());

        if (!persistentDataContainer.has(keyPreserve))
        {
            persistentDataContainer.set(keyPreserve, PersistentDataType.BOOLEAN, true);
            player.sendMessage(YELLOW + itemName + " will be preserved in the inventory when player dies.");
        }
        else
        {
            persistentDataContainer.remove(keyPreserve);
            player.sendMessage(YELLOW + itemName + " will no longer be preserved in the inventory when player dies.");
        }

        itemStack.setItemMeta(itemMeta);

        return true;
    }
}
