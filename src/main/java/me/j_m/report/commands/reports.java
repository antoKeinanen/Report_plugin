package me.j_m.report.commands;

import me.j_m.report.Report;
import me.j_m.report.handlers.FileLoader;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class reports implements CommandExecutor {

    private final Report plugin;
    private final FileLoader loader;

    public reports(Report plugin, FileLoader loader){
        this.plugin = plugin;
        this.loader = loader;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player){
            Player player = (Player) sender;

            for (final String report : loader.getConfig().getStringList("Reports")){

            }
        }

        return false;
    }

    void listOffenders(){

    }
}
