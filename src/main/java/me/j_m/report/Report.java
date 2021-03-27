package me.j_m.report;

import me.j_m.report.commands.*;
import me.j_m.report.handlers.FileLoader;
import me.j_m.report.handlers.PlayerMuteHandler;
import me.j_m.report.handlers.PunishmentLoader;
import org.bukkit.plugin.java.JavaPlugin;

public final class Report extends JavaPlugin {

    FileLoader loader = new FileLoader(this);
    PunishmentLoader punLoader = new PunishmentLoader(this);

    @Override
    public void onEnable() {
        report report = new report(this, loader);
        reports reports = new reports(this, loader);
        ban ban = new ban(this, punLoader);
        reload reload = new reload(this, punLoader, loader);
        mute mute = new mute(this, punLoader);
        PlayerMuteHandler muteHandler = new PlayerMuteHandler(this, punLoader);

        getServer().getPluginManager().registerEvents(reports, this);
        getServer().getPluginManager().registerEvents(ban, this);
        getServer().getPluginManager().registerEvents(muteHandler, this);

        this.getCommand("mute").setExecutor(mute);
        this.getCommand("report").setExecutor(report);
        this.getCommand("reports").setExecutor(reports);
        this.getCommand("ban").setExecutor(ban);
        this.getCommand("reload").setExecutor(reload);
    }

    @Override
    public void onDisable() {
        loader.saveConfig();
        punLoader.saveConfig();
    }
}
