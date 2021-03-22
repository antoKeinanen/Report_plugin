package me.j_m.report;

import me.j_m.report.commands.report;
import me.j_m.report.handlers.FileLoader;
import org.bukkit.plugin.java.JavaPlugin;

public final class Report extends JavaPlugin {

    public FileLoader loader;

    @Override
    public void onEnable() {
        this.loader = new FileLoader(this);
        this.getCommand("report").setExecutor(new report(this, loader));
    }

    @Override
    public void onDisable() {
        loader.saveConfig();
    }
}
