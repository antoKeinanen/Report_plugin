package me.j_m.report.commands;

import me.j_m.report.Report;
import me.j_m.report.handlers.FileLoader;
import me.j_m.report.handlers.PunishmentLoader;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class reload implements CommandExecutor {

    Report plugin;
    PunishmentLoader punishmentLoader;
    FileLoader fileLoader;


    public reload(Report plugin, PunishmentLoader punishmentLoader, FileLoader fileLoader){
        this.plugin = plugin;
        this.punishmentLoader = punishmentLoader;
        this.fileLoader = fileLoader;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;
            player.sendMessage("Reloading files");
            punishmentLoader.reloadConfig();
            fileLoader.reloadConfig();
        }
        punishmentLoader.reloadConfig();
        fileLoader.reloadConfig();
        return true;
    }
}
