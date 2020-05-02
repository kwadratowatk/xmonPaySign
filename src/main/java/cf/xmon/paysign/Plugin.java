package cf.xmon.paysign;

import cf.xmon.paysign.events.PlayerClickEvent;
import cf.xmon.paysign.events.PlayerSignChange;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class Plugin extends JavaPlugin {
    private static Plugin instance;
    private static Economy econ = null;
    @Override
    public void onEnable() {
        System.out.println(" <---> xmonPaySign <--->");
        if (!setupEconomy() ) {
            System.err.println("Nie znaleziono pluginu vault!");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        instance = this;
        Bukkit.getPluginManager().registerEvents(new PlayerClickEvent(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerSignChange(), this);
    }

    @Override
    public void onDisable() {
        System.out.println(" <---> xmonPaySign <--->");
    }

    public static Plugin getInstance(){return instance;}
    public static Economy getEconomy() {
        return econ;
    }
    private boolean setupEconomy() {
        if (Bukkit.getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }
}
