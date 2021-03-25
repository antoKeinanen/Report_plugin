package me.j_m.report.commands;

import me.j_m.report.Report;
import me.j_m.report.handlers.PunishmentLoader;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventException;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TreeMap;

public class ban implements CommandExecutor, Listener {

    Report plugin;
    PunishmentLoader loader;
    SimpleDateFormat parser = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");

    public ban(Report plugin, PunishmentLoader loader){
        this.plugin = plugin;
        this.loader = loader;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;
            if (args.length <= 3){
                Player offender = Bukkit.getPlayer(args[0]);
                if (offender != null){
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(new Date());
                    cal.add(Calendar.MINUTE, 1);

                    loader.getConfig().set("bans." + player.getUniqueId().toString() + ".expires", parser.format(cal.getTime()));
                    loader.saveConfig();
                    offender.kickPlayer("§l§b Report network§r\n§a You have been banned from the server!§r\n");
                }
                else{
                    player.sendMessage(ChatColor.RED + "Player with name " + args[0] + " not found!");
                }
            }
            else
                player.kickPlayer("§l§b Report network§r\n\n§a You have been banned from the server!§r\n\n§aReason:§r§b cheating§r\n\n§aIf you think this is a problem contact staff on forums!§r\n\n§aBan id:§r§b #1234567§r");
        }

        return true;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){
        Player player = (Player) e.getPlayer();
        try {
            if (loader.getConfig().getConfigurationSection("bans").contains(player.getUniqueId().toString())) {
                Date expireDate = null;
                try {
                    expireDate = parser.parse(loader.getConfig().getString("bans." + player.getUniqueId() + ".expires"));
                } catch (Exception ex) {
                }

                if (expireDate.after(new Date())) {
                    player.kickPlayer("§l§b Report network§r\n\n§a You have been banned from the server!§r\n\n§aReason:§r§b cheating§r\n\n§aIf you think this is a problem contact staff on forums!§r\n\n§aBan id:§r§b #1234567§r");
                }
            }
        }
        catch (Exception ex){   }
    }
}
