package me.j_m.report.commands;

import me.j_m.report.Report;
import me.j_m.report.handlers.FileLoader;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class reports implements Listener, CommandExecutor {

    private final Report plugin;
    private final FileLoader loader;
    private Inventory inv = Bukkit.createInventory(null, 54, "Reports");

    public reports(Report plugin, FileLoader loader){
        this.plugin = plugin;
        this.loader = loader;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player){
            Player player = (Player) sender;
            inv.clear();
            for (final String report : loader.getConfig().getConfigurationSection("Reports").getKeys(false)){
                String root = "Reports." + report;
                inv.addItem(createItem(
                        report,
                        loader.getConfig().getString(root + ".reporter"),
                        loader.getConfig().getString(root + ".offender"),
                        0, //TODO add prev offences
                        loader.getConfig().getString(root + ".reason"),
                        loader.getConfig().getString(root + ".timeStamp"),
                        loader.getConfig().getString(root + ".offenderUID")

                ));
            }
            player.openInventory(inv);
        }
        return true;
    }

    ItemStack createItem(String id, String reporter, String offender, int prevOffences, String reason, String time, String offenderUUID){
        ItemStack item = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) item.getItemMeta();

        List<String> lore = new ArrayList<>();
        lore.add("Report number " + id);
        lore.add("Reported by " + reporter);
        lore.add("Offender " + offender);
        lore.add("Previous offences " + prevOffences);
        lore.add("Reason " + reason);
        lore.add("Time and date " + time);
        meta.setLore(lore);

        meta.setOwningPlayer(Bukkit.getOfflinePlayer(UUID.fromString(offenderUUID)));

        item.setItemMeta(meta);

        return item;
    }

    @EventHandler
    public void onInventoryClick(final InventoryClickEvent e){
        if (e.getInventory() != inv) return;

        e.setCancelled(true);

        final ItemStack clickedItem = e.getCurrentItem();

        if (clickedItem == null ||clickedItem.getType() == Material.AIR) return;

        final Player p = (Player) e.getWhoClicked();
    }

    @EventHandler
    public void onInventoryClick(final InventoryDragEvent e) {
        if (e.getInventory() == inv) {
            e.setCancelled(true);
        }
    }
}
