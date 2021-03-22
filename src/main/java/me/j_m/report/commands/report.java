package me.j_m.report.commands;

import me.j_m.report.Report;
import me.j_m.report.handlers.FileLoader;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;


public class report implements CommandExecutor {

    private final Report plugin;
    private final FileLoader loader;

    public report(Report plugin, FileLoader loader){
        this.plugin = plugin;
        this.loader = loader;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length >= 1){
                if (args.length >= 2){
                    int id = loader.getConfig().getInt("Amount");
                    id++;
                    loader.getConfig().set("Amount", id);

                    System.out.println(loader.getConfig().getInt("Amount"));
                }
                else {
                    if (Bukkit.getServer().getPlayer(args[0]) != null)
                        reportGui(player, args[0]);
                    else
                        player.sendMessage(ChatColor.RED + String.format("Did not fin player with name %s!", args[0]));
                }
            }
            else {
                player.sendMessage(ChatColor.RED + "Please give player to report: /report <player>");
            }

        }
        return true;
    }

    void reportGui(Player player, String offender){

        ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
        BookMeta bookMeta = (BookMeta) book.getItemMeta();

        BaseComponent[] page = new ComponentBuilder("●cheating")
                .event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, String.format("/report %s cheating", offender)))
                .event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(String.format("Report %s for cheating.", offender)).create()))
                .append("\n")
                .append(new ComponentBuilder("●inappropriate name/skin/cape")
                        .event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, String.format("/report %s appearance", offender)))
                        .event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(String.format("Report %s for inappropriate name/skin/cape.", offender)).create())).create())
                .append("\n")
                .append(new ComponentBuilder("●chat abuse")
                        .event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, String.format("/report %s chat", offender)))
                        .event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(String.format("Report %s for chat abuse.", offender)).create())).create())
                .append("\n")
                .append(new ComponentBuilder("●cheating")
                        .event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, String.format("/report %s cross teaming", offender)))
                        .event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(String.format("Report %s cross teaming.", offender)).create())).create()).create();

        bookMeta.spigot().addPage(page);

        bookMeta.setTitle("report");
        bookMeta.setAuthor(player.getDisplayName());

        book.setItemMeta(bookMeta);

        player.openBook(book);
    }

}
