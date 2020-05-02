package cf.xmon.paysign.utils;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class ChatUtils {
    public static String fixColor(String text) {
        return ChatColor.translateAlternateColorCodes('&', text.replace(">>", "»").replace("<<", "«").replace("{O}", "\u2022").replace("{NEWLINE}", "\n"));
    }
    public static boolean sendMsg(CommandSender sender, String message, String permission) {
        if (sender instanceof ConsoleCommandSender) {
            sendMsg(sender, message);
        }
        return permission != null && !permission.equals("") && sender.hasPermission(permission) && sendMsg(sender, message);
    }

    public static boolean sendMsg(CommandSender sender, String message) {
        if (sender instanceof Player) {
            if (message != null || !message.equals("")) {
                sender.sendMessage(fixColor(message));
            }
        }
        else {
            sender.sendMessage(ChatColor.stripColor(fixColor(message)));
        }
        return true;
    }
}
