package me.j_m.report;

import me.j_m.report.commands.report;
import me.j_m.report.commands.reports;
import me.j_m.report.handlers.FileLoader;
import org.bukkit.plugin.java.JavaPlugin;

public final class Report extends JavaPlugin {

    FileLoader loader = new FileLoader(this);

    @Override
    public void onEnable() {
        report report = new report(this, loader);
        reports reports = new reports(this, loader);

        getServer().getPluginManager().registerEvents(reports, this);

        this.getCommand("report").setExecutor(report);
        this.getCommand("reports").setExecutor(reports);
    }

    @Override
    public void onDisable() {
        loader.saveConfig();
    }
}
