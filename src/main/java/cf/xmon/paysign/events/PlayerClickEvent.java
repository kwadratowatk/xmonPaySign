package cf.xmon.paysign.events;

import cf.xmon.paysign.Plugin;
import cf.xmon.paysign.utils.ChatUtils;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.block.data.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.material.Button;

import java.util.Arrays;
import java.util.UUID;

public class PlayerClickEvent implements Listener {
    @EventHandler
    public void onClick(PlayerInteractEvent e){
        if (e.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }
        Block b = e.getClickedBlock();
        boolean t = false;
        if (b != null) {
            if (b.getType() == Material.ACACIA_SIGN || b.getType() == Material.ACACIA_WALL_SIGN || b.getType() == Material.BIRCH_SIGN || b.getType() == Material.BIRCH_WALL_SIGN || b.getType() == Material.DARK_OAK_SIGN || b.getType() == Material.DARK_OAK_WALL_SIGN || b.getType() == Material.JUNGLE_SIGN || b.getType() == Material.JUNGLE_WALL_SIGN || b.getType() == Material.OAK_SIGN || b.getType() == Material.OAK_WALL_SIGN) {
                Sign sign = (Sign) b.getState();
                if (sign.getLine(0).equals(ChatUtils.fixColor("&a[&2PaySign&a]"))){
                    String nick = sign.getLine(1);
                    if (!nick.equals("") && !nick.isEmpty()) {
                        String pay = sign.getLine(2);
                        int pay$int = Integer.parseInt(pay);
                        if (pay$int >= 0) {
                            Player p = e.getPlayer();
                            UUID uuid = Bukkit.getPlayerUniqueId(nick);
                            if (uuid != null) {
                                OfflinePlayer pay$to = Bukkit.getOfflinePlayer(uuid);
                                EconomyResponse r = Plugin.getEconomy().withdrawPlayer(p, pay$int);
                                if (r.transactionSuccess()) {
                                    Plugin.getEconomy().depositPlayer(pay$to, pay$int);
                                    Player pay$too = Bukkit.getPlayerExact(nick);
                                    if (pay$too != null) {
                                        ChatUtils.sendMsg(pay$too, "&2Otrzymałeś/aś &6" + pay$int + "$ &2z PaySign!");
                                    }
                                    ChatUtils.sendMsg(p, "&2Zapłaciłeś/aś &6" + pay$int + "$");
                                    String block = sign.getBlockData().getAsString().split("\\[")[1].split("=")[1].split(",")[0].replace(",", "").toUpperCase();
                                    BlockFace test;
                                    if (isInteger(block)){
                                        test = BlockFace.NORTH;
                                        t = true;
                                    }else {
                                        test = BlockFace.valueOf(block);
                                        t = false;
                                    }
                                    b.getLocation().getBlock().setType(Material.BIRCH_BUTTON);
                                    if (t){
                                        FaceAttachable af = (FaceAttachable) b.getBlockData();
                                        af.setAttachedFace(FaceAttachable.AttachedFace.FLOOR);
                                        b.setBlockData(af);
                                    } else {
                                        Directional blockData = (Directional) b.getBlockData();
                                        blockData.setFacing(test);
                                        b.setBlockData(blockData);
                                    }
                                    boolean finalT = t;
                                    /*
                                    Powerable blockdata4 = (Powerable) b.getBlockData();
                                    blockdata4.setPowered(true);
                                    b.setBlockData(blockdata4);
                                     */
                                    Bukkit.getScheduler().runTaskLater(Plugin.getInstance(), () ->{
                                        b.getLocation().getBlock().setType(sign.getType());
                                        if (finalT){
                                            Rotatable blockdata1 = (Rotatable) b.getBlockData();
                                            blockdata1.setRotation(test);
                                            b.setBlockData(blockdata1);
                                        } else {
                                            Directional blockData1 = (Directional) b.getBlockData();
                                            blockData1.setFacing(test);
                                            b.setBlockData(blockData1);
                                        }
                                        Sign nowa = (Sign) b.getState();
                                        for (int i = 0; i < sign.getLines().length; i++) {
                                            nowa.setLine(i, sign.getLine(i));
                                        }
                                        nowa.update();
                                    }, 42);

                                } else {
                                    ChatUtils.sendMsg(p, "&4Bład: &cTwoje saldo jest zbyt niskie!");
                                }
                            }else{
                                ChatUtils.sendMsg(p, "&4Bład: &cNie ma takiego użytkownika jak " + nick);
                            }
                        }
                    }
                }
            }
        }
    }

    private static boolean isInteger(String i){
        try{
            Integer.parseInt(i);
            return true;
        }catch (NumberFormatException e){
            return false;
        }
    }

}
