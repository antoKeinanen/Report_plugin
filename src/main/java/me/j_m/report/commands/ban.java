package me.j_m.report.commands;

import me.j_m.report.Report;
import me.j_m.report.handlers.PunishmentLoader;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.text.SimpleDateFormat;
import java.util.*;

public class ban implements CommandExecutor {

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
            if (args.length >= 3){
                Player offender = Bukkit.getPlayer(args[0]);
                if (offender != null){
                    Calendar cal = Calendar.getInstance();
                    int time = -1;
                    cal.setTime(new Date());
                    try{
                        time = Integer.parseInt(args[1].replaceAll("[A-Za-z]", ""));
                    }
                    catch (Exception ex){
                        if(args[1].equalsIgnoreCase("perm")){
                            cal.add(Calendar.YEAR, 9999);
                        }
                    }
                    if(time > 0){
                        switch (args[1].replaceAll("[0-9]", "")){
                            case "s":
                                cal.add(Calendar.SECOND, time);
                                break;
                            case "min":
                                cal.add(Calendar.MINUTE, time);
                                break;
                            case "h":
                                cal.add(Calendar.HOUR, time);
                                break;
                            case "d":
                                cal.add(Calendar.DAY_OF_MONTH, time);
                                break;
                            case "w":
                                cal.add(Calendar.DAY_OF_MONTH, time * 7);
                                break;
                            case "m":
                                cal.add(Calendar.MONTH, time);
                                break;
                            case "y":
                                cal.add(Calendar.YEAR, time);
                                break;
                        }
                    }

                    int prevPuns = loader.getConfig().getInt("bans." + player.getUniqueId().toString() + ".amount");
                    int totalBans = loader.getConfig().getInt("totalBans");
                    prevPuns ++;
                    totalBans ++;
                    loader.getConfig().set("totalBans", totalBans);
                    loader.getConfig().set("bans." + player.getUniqueId().toString() + ".amount", prevPuns);
                    loader.saveConfig();

                    loader.getConfig().set("bans." + player.getUniqueId().toString() + "." + prevPuns + ".banId", totalBans);
                    loader.getConfig().set("bans." + player.getUniqueId().toString() + "." + prevPuns + ".reason", args[2]);
                    loader.getConfig().set("bans." + player.getUniqueId().toString() + "." + prevPuns + ".bannedBy", player.getDisplayName());
                    loader.getConfig().set("bans." + player.getUniqueId().toString() + "." + prevPuns + ".length", args[1]);
                    loader.getConfig().set("bans." + player.getUniqueId().toString() + "." + prevPuns + ".expires", parser.format(cal.getTime()));
                    loader.saveConfig();
                    player.kickPlayer(String.format("§l§bReport network§r\n\n§a You have been banned from the server!§r\n\n§aReason:§r§b %s§r\n\n§aYou will be unbanned:§r§b %s§r\n\n§aIf you think this is a problem contact staff on forums!§r\n\n§aBan id:§r§b #%s§r", args[2], parser.format(cal.getTime()), totalBans));
                }
                else{
                    player.sendMessage(ChatColor.RED + "Player with name " + args[0] + " not found!");
                }
            }
            else
                player.sendMessage(ChatColor.RED + "Invalid usage!");
        }

        return true;
    }
}
