package me.j_m.report.handlers;

import me.j_m.report.Report;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class PlayerMuteHandler implements Listener {

    Report plugin;
    PunishmentLoader loader;
    SimpleDateFormat parser = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");

    public PlayerMuteHandler(Report plugin, PunishmentLoader loader){
        this.plugin = plugin;
        this.loader = loader;
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent e){
        Player player = (Player) e.getPlayer();
        try {
            if (loader.getConfig().getConfigurationSection("mutes").contains(player.getUniqueId().toString())) {
                int muteAmount = loader.getConfig().getInt("mutes." + player.getUniqueId().toString() + ".amount");
                for (int i = 1; i <= muteAmount; i++) {
                    Date expireDate = parser.parse(loader.getConfig().getString("mutes." + player.getUniqueId().toString() + "." + i + ".expires"));
                    if (expireDate.after(new Date())) {
                        String reason = loader.getConfig().getString("mutes." + player.getUniqueId().toString() + "." + i + ".reason");
                        int id = loader.getConfig().getInt("mutes." + player.getUniqueId().toString() + "." + i + ".muteId");
                        e.setCancelled(true);
                        player.sendMessage(String.format("You have been muted for %s. Your you will be unmuted: %s. If you thin this is a problem contact staff on forums. Mute id: %s", reason, expireDate, id));
                    }
                }
            }
        }
        catch (Exception ex){
            System.out.println(Arrays.toString(ex.getStackTrace()));
        }
    }

}
