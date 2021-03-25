package me.j_m.report.handlers;

import me.j_m.report.Report;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;

public class PunishmentLoader {
    private final Report plugin;
    private FileConfiguration dataConfig = null;
    private File configFile = null;

    public PunishmentLoader(Report plugin){
        this.plugin = plugin;
        saveDefaultConfig();
    }

    public void reloadConfig() {
        if (this.configFile == null)
            this.configFile = new File(this.plugin.getDataFolder(), "bans.yml");

        this.dataConfig = YamlConfiguration.loadConfiguration(this.configFile);

        InputStream defaultStream = plugin.getResource("bans.yml");
        if (defaultStream != null){
            YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(defaultStream));
            this.dataConfig.setDefaults(defaultConfig);
        }
    }

    public FileConfiguration getConfig(){
        if(this.dataConfig == null)
            reloadConfig();
        return dataConfig;
    }

    public void saveConfig(){
        if(this.dataConfig == null || this.configFile == null)
            return;
        try {
            this.getConfig().save(this.configFile);
        } catch (IOException e) {
            this.plugin.getLogger().log(Level.SEVERE, "Could not save config to " + this.configFile, e);
        }
    }

    public void saveDefaultConfig(){
        if (configFile == null)
            this.configFile = new File(this.plugin.getDataFolder(), "bans.yml");
        if (!this.configFile.exists()){
            System.out.println("No bans.yml file found creating one...");
            this.plugin.saveResource("bans.yml", false);
            System.out.println("bans.yml created successfully!");
        }
    }
}
