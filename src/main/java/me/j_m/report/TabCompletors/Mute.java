package me.j_m.report.TabCompletors;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Mute implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> tabs = new ArrayList<>();
        if(args.length == 1){
            for (Player p : Bukkit.getOnlinePlayers()){
                if(p.getDisplayName().startsWith(args[0]))
                    tabs.add(p.getDisplayName());
            }
        }
        if(args.length == 2 && !(args[1].replaceAll("[0-9]", "").matches("[a-z]")) && !"perm".startsWith(args[1])){
            tabs.add(args[1].replaceAll("[^0-9]", "") + "s");
            tabs.add(args[1].replaceAll("[^0-9]", "") + "min");
            tabs.add(args[1].replaceAll("[^0-9]", "") + "h");
            tabs.add(args[1].replaceAll("[^0-9]", "") + "d");
            tabs.add(args[1].replaceAll("[^0-9]", "") + "w");
            tabs.add(args[1].replaceAll("[^0-9]", "") + "m");
            tabs.add(args[1].replaceAll("[^0-9]", "") + "y");
        }
        if(args.length == 2 && "perm".startsWith(args[1])){
            tabs.add("perm");
        }
        if(args.length == 3){
            tabs.add("Spamming");
            tabs.add("Swearing");
            tabs.add("Self Advertising");
            tabs.add("Fonts");
        }

        return tabs;
    }
}
