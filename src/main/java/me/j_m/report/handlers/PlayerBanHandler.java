package me.j_m.report.handlers;

import me.j_m.report.Report;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class PlayerBanHandler implements Listener {

    Report plugin;
    PunishmentLoader loader;
    SimpleDateFormat parser = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");

    public PlayerBanHandler(Report plugin, PunishmentLoader loader){
        this.plugin = plugin;
        this.loader = loader;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){
        Player player = (Player) e.getPlayer();
        try {
            if (loader.getConfig().getConfigurationSection("bans").contains(player.getUniqueId().toString())) {
                int banAmount = loader.getConfig().getInt("bans." + player.getUniqueId().toString() + ".amount");
                for (int i = 1; i <= banAmount; i++) {
                    Date expireDate = parser.parse(loader.getConfig().getString("bans." + player.getUniqueId().toString() + "." + i + ".expires"));
                    if (expireDate.after(new Date())) {
                        String reason = loader.getConfig().getString("bans." + player.getUniqueId().toString() + "." + i + ".reason");
                        int id = loader.getConfig().getInt("bans." + player.getUniqueId().toString() + "." + i + ".banId");
                        player.kickPlayer(String.format("§l§bReport network§r\n\n§a You have been banned from the server!§r\n\n§aReason:§r§b %s§r\n\n§aYou will be unbanned:§r§b %s§r\n\n§aIf you think this is a problem contact staff on forums!§r\n\n§aBan id:§r§b #%s§r", reason, expireDate, id));
                    }
                }
            }
        }
        catch (Exception ex){
            System.out.println(Arrays.toString(ex.getStackTrace()));
        }
    }
}
