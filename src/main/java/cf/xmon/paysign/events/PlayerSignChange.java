package cf.xmon.paysign.events;

import cf.xmon.paysign.Plugin;
import cf.xmon.paysign.utils.ChatUtils;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

public class PlayerSignChange implements Listener {
    @EventHandler
    public void onSignChange(SignChangeEvent e){
        if (ChatColor.stripColor(e.getLine(0)).equals("[PaySign]")){
            String nick = e.getLine(1);
            if (nick != null && !nick.equals("")) {
                String pay = e.getLine(2);
                if (pay != null && !pay.equals("")) {
                    try {
                        int pay$int = Integer.parseInt(pay);
                        if (pay$int >= 0) {
                            e.setLine(0, ChatUtils.fixColor("&a[&2PaySign&a]"));
                            ChatUtils.sendMsg(e.getPlayer(), "&2Utworzyłeś/aś &2PaySign!");
                        }else{
                            ChatUtils.sendMsg(e.getPlayer(), "&4Błąd: &cLiczba nie może być ujemna!");
                        }
                    }catch (NumberFormatException ee){
                        ChatUtils.sendMsg(e.getPlayer(), "&4Błąd: &cLiczba przekracza wartość int!");
                    }
                }
            }
        }
    }
}
